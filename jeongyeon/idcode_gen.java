package my_package;

import java.text.SimpleDateFormat;

public class idcode_gen {
	public long make_member_code(String id, String nickname) { // 회원 고유번호 생성
		SimpleDateFormat base_date_format = new SimpleDateFormat("yyyyMMddHHMM");
		String base_time = base_date_format.format(System.currentTimeMillis());
		String key = id + nickname + base_time;
		int a = 0;
		for (int i = 0; i < key.length(); i++) {
			a = a << 2;
			a += key.charAt(i) * i;
			a = a >> 1;
			a += key.charAt(i);
		}
		String_crypto crypt = new String_crypto();
		long result = crypt.MT19937_long(a);
		return result;
	}

	public long make_room_code(String roomname, String maker_id) { // 방 고유번호 생성
		SimpleDateFormat base_date_format = new SimpleDateFormat("yyyyMMddHHMM");
		String base_time = base_date_format.format(System.currentTimeMillis());
		String key = roomname + maker_id + base_time;
		int a = 0;
		for (int i = 0; i < key.length(); i++) {
			a = a << 2;
			a += key.charAt(i) * i;
			a = a >> 1;
			a += key.charAt(i);
		}
		String_crypto crypt = new String_crypto();
		long result = crypt.MT19937_long(a);
		return result;
	}
}
