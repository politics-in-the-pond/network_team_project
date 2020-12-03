package my_package;

import java.util.Vector;

public class chatroom {
	
	chatroom(boolean b, String name, String roomID, String secure){
		this.isOpen = b;
		this.room_name = name;
		this.roomID = roomID;
		this.secure = secure;
	}
	
	private String roomID;
	private String secure; //비번
	private boolean isOpen;
	private String room_name;
	private Vector<member> room_members = new Vector<member>();
	
	public String getRoomID() {
		return roomID;
	}
	public String getSecure() {
		return secure;
	}
	public Vector<member> getRoom_members() {
		return room_members;
	}
	public void add(member m) {
		this.room_members.add(m);
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
}
