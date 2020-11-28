package database;

import java.time.LocalDate;
import java.time.LocalDateTime;

// (`id`, `nickname`, `name`, `pw`, `birthdate`, `email`, `tel`, `web`) 
public class User {
	public String id; 				//필수, 중복불허
	public String pw; 				//필수
	public String name; 			//필수
	public String nickname; 		//필수, 중복불허
	public String birthdate;		//필수
	public String email; 			//필수. 중복불허
	public String tel; 				//옵션
	public String web; 				//옵션 github/홈페이지 주소
	public String todaysComment;    //옵션 오늘의 한마디

	public LocalDateTime lastLogin;  //정보, 최근 접속 시간
	public LocalDateTime lastLogOut; //정보, 최근 로그아웃시간
	
	public boolean isConnected;		 		//현재 접송중? 
	public String ip;               		//접속 IP
	public int socketNumber;        		//접속 Socket Number 
	public LocalDateTime lastStatusChecked; //최종 상태 체크 시간 - 형식: 2020-12-11T13:12:11
	
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean checkUserStatus() {
		// Socket Class를 호출해서 현재 사용자가 접속되어 있는지 확인
		// ex. Client.checkUserStatus(boolean connected,String ip,int soketnumber)
		
		// 현재 시간을 최종 상태 체크시간으로 Update
		lastStatusChecked = LocalDateTime.now();
		
		return false;
	}
	
}
