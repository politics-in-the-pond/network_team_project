package my_package;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
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
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {

	JFrame frame = new JFrame("Chatter");//
	JTextField textField = new JTextField(50);//
	JButton b = new JButton("file");

	String serverAddress;
	int port;
	private InputStream is;
	private DataInputStream in;
	OutputStream os;
	DataOutputStream out;
	Vector<String> names = new Vector<>();
	boolean login_comp = false;

	private static File fileOpenDlg() {
		String user_name = System.getProperty("user.name");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("일단jpg만 넣어주세요", "jpg"));
		chooser.setCurrentDirectory(new File("C:\\Users\\" + user_name + "\\Desktop"));
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			return f;
		}
		return null;
	}

	public ChatClient(String serverAddress, int port) {
		this.serverAddress = serverAddress;
		this.port = port;
		String_crypto crypt = new String_crypto();

		textField.setEditable(true);
		frame.getContentPane().add(textField, BorderLayout.SOUTH);
		frame.getContentPane().add(b, BorderLayout.NORTH);
		frame.pack();

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File src = fileOpenDlg();
				try {
					FileInputStream fin = new FileInputStream(src);
					byte[] temp = new byte[16777216];
					int length = fin.read(temp);
					fin.close();
					byte[] file = new byte[length];
					System.arraycopy(temp, 0, file, 0, length);
					byte[] enc_file = crypt.do_fencrypt(file);
					byte[] output = new byte[enc_file.length + 17];
					System.out.println(enc_file.length);
					System.out.println(length);
					// 샘플코드
					output[0] = 0x03;
					System.arraycopy(enc_file, 0, output, 17, enc_file.length);
					System.arraycopy(crypt.ltob(crypt.MT19937_long(126321621)), 0, output, 1, 8);
					System.arraycopy(crypt.ltob(crypt.MT19937_long(12123123)), 0, output, 9, 8);
					out.write(output);
					out.flush();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					byte[] temp = crypt.do_encrypt(textField.getText());
					byte[] output = new byte[temp.length + 17];

					// 샘플코드
					output[0] = 0x02;
					System.arraycopy(temp, 0, output, 17, temp.length);
					System.arraycopy(crypt.ltob(crypt.MT19937_long(126321621)), 0, output, 1, 8);
					System.arraycopy(crypt.ltob(crypt.MT19937_long(12123123)), 0, output, 9, 8);
					out.write(output);
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} // textField에 입력된 내용을 전송한다.
				textField.setText(""); // textField를 비운다.
			}
		});
	}

	private void run() throws IOException, ClassNotFoundException {
		String_crypto crypt = new String_crypto();
		try {
			Socket socket = new Socket(serverAddress, port);
			is = socket.getInputStream();
			in = new DataInputStream(is);
			os = socket.getOutputStream();
			out = new DataOutputStream(os);
			String id = "thisistest";
			String password = "test1234";
			String line = null;
			byte[] temp = new byte[16777216];
			byte type = 0x00;

			while (true) {
				int how_many = in.read(temp);
				if (how_many == 0)
					continue;
				if (!login_comp) {
					byte[] input = new byte[how_many];
					System.arraycopy(temp, 0, input, 0, how_many);
					line = crypt.do_decrypt(input);
					System.out.println("***" + line);
					if (line.equals("LOGIN")) {
						out.write(crypt.do_encrypt(id + " " + password));
						out.flush();
					} else if (line.equals("LOGINSUC")) {
						login_comp = true;
					}
				} else {
					if((how_many-37)%16!=0) continue;
					byte[] input = new byte[how_many - 17];
					System.arraycopy(temp, 17, input, 0, how_many - 17);
					type = temp[0]; // type 00 = 공백, 01 = 설정&쿼리, 02 = 메시지, 03 = 파일

					if (type == 0x02) {
						line = crypt.do_decrypt(input);
						System.out.println("***" + line);
					}

					if (type == 0x03) {
						String user_name = System.getProperty("user.name");
						FileOutputStream fout = new FileOutputStream(
								"C:\\Users\\" + user_name + "\\Desktop\\sample.jpg");
						System.out.println(how_many);
						byte[] file = crypt.do_fdecrypt(input);
						fout.write(file);
						fout.close();
					}
				}
			}
		} finally {

		}
	}

	public static void main(String[] args) throws Exception {

		int bytelength = 0;
		String serverAddress = null;
		int port = 0;
		String_crypto crypt = new String_crypto();

		byte[] reads = new byte[50];
		byte[] portb = new byte[4];

		try {
			File file = new File("./src/my_package/config.dat");
			FileInputStream inb = new FileInputStream(file);
			bytelength = inb.read(reads);
			inb.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		byte[] ipaddress = new byte[bytelength - 4];
		System.arraycopy(reads, 0, portb, 0, 4);
		System.arraycopy(reads, 4, ipaddress, 0, bytelength - 4);

		port = crypt.btoi(portb);
		serverAddress = crypt.do_decrypt(ipaddress);
		ChatClient client = new ChatClient(serverAddress, port); // 클라이언트 객체 생성

		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
		client.run();
	}
}
