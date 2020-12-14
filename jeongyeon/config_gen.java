package my_package;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;

public class config_gen {

	public static void writeToFile(byte[] bData, int port) {
		if (bData == null) {
			return;
		}
		byte[] bytes = new byte[4];
		try {
			File file = new File("./src/my_package/config.dat");
			FileOutputStream out = new FileOutputStream(file);
			bytes[0] = (byte) ((port & 0xFF000000) >> 24);
			bytes[1] = (byte) ((port & 0x00FF0000) >> 16);
			bytes[2] = (byte) ((port & 0x0000FF00) >> 8);
			bytes[3] = (byte) (port & 0x000000FF);
			out.write(bytes);
			out.write(bData);
			out.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public static void main(String[] args) { // 설정정보 생성
		String_crypto ab = new String_crypto();
		String address = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			address = ip.getHostAddress();
		} catch (Exception e) {
			System.out.println(e);
		}
		int port = 7650;
		System.out.println(address);
		writeToFile(ab.do_encrypt(address), port);
	}
}
