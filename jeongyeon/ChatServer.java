package my_package;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
	private static Vector<member> members = new Vector<>();
	private static Vector<chatroom> chatrooms = new Vector<>(); // member 들어감

	public static void main(String[] args) throws Exception {
		String_crypto crypt = new String_crypto();

		int port = 0;
		byte[] reads = new byte[50];
		byte[] ipaddress = new byte[50];
		byte[] portb = new byte[4];
		try {
			File file = new File("./src/my_package/config.dat");
			FileInputStream inb = new FileInputStream(file);
			inb.read(reads);
			inb.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.arraycopy(reads, 0, portb, 0, 4);
		port = crypt.btoi(portb);
		System.out.println("Server port is : " + port);

		System.out.println("server is running.");
		ExecutorService pool = Executors.newFixedThreadPool(500);
		try (ServerSocket listener = new ServerSocket(port)) {
			while (true) {
				pool.execute(new Handler(listener.accept()));
			}
		}
	}

	private static class Handler implements Runnable {
		private String nameID = "10101011"; // 회원번호 임시
		private Socket socket;
		private InputStream is;
		private DataInputStream in;
		OutputStream os;
		DataOutputStream out;
		String_crypto crypt = new String_crypto();

		public Handler(Socket socket) {
			this.socket = socket;
		}

		public void run() { // 스레드 내부에서 작동하는 함수
			member me = new member();
			byte[] temp = new byte[16777216];
			byte type = 0x00;
			try {
				is = socket.getInputStream(); // 입력
				in = new DataInputStream(is);
				os = socket.getOutputStream();
				out = new DataOutputStream(os);
				while (true) {
					out.write(crypt.do_encrypt("LOGIN")); // 서버가 열려있음
					out.flush();
					int how_many = in.read(temp);
					if (how_many == 0)
						return;
					byte[] IDPW = new byte[how_many];
					System.arraycopy(temp, 0, IDPW, 0, how_many);
					String dec_IDPW = crypt.do_decrypt(IDPW); // ID PW 형식
					String ID = dec_IDPW.substring(0, dec_IDPW.indexOf(' '));
					String pw = dec_IDPW.substring(dec_IDPW.indexOf(' ') + 1);

					//
					// DB에서 ID에 맞는 nameID 불러오기
					//

					if (nameID == null) {
						out.write(crypt.do_encrypt("error 0")); // 회원이 아님
						out.flush();
						return;
					}

					//
					// DB에서 nameID에 맞는 pw불러오기
					//
					String DBPW = "test1234";// 변경*****

					if (!pw.equals(DBPW)) {
						out.write(crypt.do_encrypt("error 0")); // 비밀번호 틀림
						out.flush();
						return;
					}

					me.setName("testname"); // DB에서 닉네임 가져와야함
					me.setWriter(out);
					me.setNameid(nameID);

					members.add(me);
					break;

				}
				out.write(crypt.do_encrypt("LOGINSUC")); // 로그인 성공
				out.flush();

				while (true) {
					// 바이트 입력부
					int how_many = in.read(temp);
					if (how_many == 0)
						continue;
					byte[] input = new byte[how_many - 17];
					System.arraycopy(temp, 17, input, 0, how_many - 17);
					type = temp[0]; // type 00 = 공백, 01 = 설정&쿼리, 02 = 메시지, 03 = 파일
					long chatroomID;
					long srcuserID;

					if (type == 0x02) {
						String input_str = crypt.do_decrypt(input);
						System.out.println(input_str);
						byte[] output = new byte[how_many];
						System.arraycopy(temp, 0, output, 0, how_many);
						for (member mb : members) {
							mb.getWriter().write(output);
							mb.getWriter().flush();
						}
					}
					if (type == 0x03) {
						System.out.println(how_many);
						byte[] number = new byte[4];
						byte[] header = new byte[21];
						int block_num = 0;
						System.arraycopy(temp, 17, number, 0, 4);
						System.arraycopy(temp, 0, header, 0, 21);
						for (member mb : members) {
							mb.getWriter().write(header);
							mb.getWriter().flush();
						}
						block_num = crypt.btoi(number);
						for (int i = 0; i <= block_num; i++) {
							how_many = in.read(temp);
							System.out.println(how_many);
							byte[] output = new byte[how_many];
							System.arraycopy(temp, 0, output, 0, how_many);
							for (member mb : members) {
								mb.getWriter().write(output);
								mb.getWriter().flush();
							}
						}
					}
					String tmpchatroomid = null;

					/*
					 * for(chatroom cr : chatrooms) { if(tmpchatroomid.equals(cr.getRoomID())) { for
					 * (member mem : cr.room_members) {
					 * mem.getWriter().println(crypt.do_encrypt("MSG" + input + "@" + me.getNameid()
					 * +"$"+cr.getRoomID())); } } }
					 */

				}
			} catch (Exception e) {
				System.out.println(e);
			} finally { // 클라이언트 종료 시 배열리스트들에서 제거
				if (me.getWriter() != null) {
					members.remove(me);
				}
				if (nameID != null) {
					System.out.println(nameID + " is leaving");
					for (member mem : members) {
						try {
							mem.getWriter().write(crypt.do_encrypt(nameID + "0"));
							mem.getWriter().flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
