package my_package;

import java.util.Vector;

public class chatroom {

	chatroom(boolean b, String name, long roomID) {
		this.isOpen = b;
		this.room_name = name;
		this.roomID = roomID;
	}

	private long roomID; // 방 고유번호
	private boolean isOpen; // 온라인
	private String room_name; // 방 이름
	public Vector<member> room_members = new Vector<member>(); // 멤버들

	public long getRoomID() {
		return roomID;
	}

	public void add(member m) { // 멤버추가
		this.room_members.add(m);
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
}
