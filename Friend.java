import java.util.ArrayList;
import java.util.Collection;

import javax.swing.tree.TreePath;

//Friend Data Class for Friend List
public class Friend {
	private String id;
	private String nickname;
	private long member_code;
	
	
	// Initial Function for Friend Class
	Friend(String id, String nickname){
		this.id = id;
		this.nickname = nickname;
	}
	
	Friend(String id, String nickname, long member_code){
		this.id = id;
		this.nickname = nickname;
		this.member_code = member_code;
	}
	
	
	// Define Getters for the data
	public String getId() {
		return id;
	}
	public String getNickName() {
		return nickname;
	}
	public long getMemberCode() {
		return member_code;
	}
	
	// Define Setters for the data
	public void setMemberCode(long data) {
		this.member_code = data ;
	}
	
	public static Friend findUsingNickname(ArrayList<Friend> fd, String nickname) {
	
		for (Friend f : fd) 
			if (nickname.compareTo(f.getNickName())==0 ) return f;
		
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		ArrayList<Friend> friendData = new ArrayList<Friend>();
		

		friendData.add(new Friend("234", "eeee"));
		friendData.add(new Friend("456", "wwerd"));
		friendData.add(new Friend("riehg", "고라파덕"));
		
		idcode_gen code_gen = new idcode_gen();
		//long code = code_gen.make_member_code("234", "eeee");
		//System.out.println("234" +"  " + "eeee" +"  "+ code);
		
		long[] member_codes = new long[friendData.size()];
		
		for(int i = 0; i < friendData.size(); i++) {
			Friend data = friendData.get(i);
			System.out.println(data.getId() +"  "+ data.getNickName());
			
			long code = code_gen.make_member_code(data.getId(), data.getNickName());
			member_codes[i] = code;
		}
		
		for(int j = 0; j < friendData.size(); j++) System.out.println(member_codes[j]);
		

		Friend foundF =  findUsingNickname(friendData, "고라파덕");
		
		System.out.println(foundF.getId() + " " + foundF.getNickName() + " was found!");
		
		//System.out.println(a.getId() + "  " + a.getNickName() );
		

	}

}
