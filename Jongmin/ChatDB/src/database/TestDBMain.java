package database;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestDBMain {

	public TestDBMain() {
		// TODO Auto-generated constructor stub
	}

	
	private static void testSignInnShowFriend(String id, String pw) {
		System.out.println("*********** testSignInnShowFriend ************");
		
		// 1. DB 생성 방법
		ChatDB db = new ChatDB();
	
		// 2. 로그인 방법: 로그인에 성공하면 사용자 정보를 담은 User 객체를 return
		User user = db.login(id, pw);
		if (user == null) {
			// 로그인 실패 (결과값 null)
			System.out.println("로그온 실패. 실패 사유: "+ db.getLastMessage());
			return;
		}
			
		// 3. 친구 목록 가져오기: 사용자를 이용해 친구 리스트 (User 객체 List)를 가져옴
		ArrayList<User> friends = db.getFriends(user);
		
		for(int i = 0; i < friends.size(); i++) {
			User friend = friends.get(i);
			System.out.print(friend.id + " : ");
			System.out.print(friend.name + " : ");
			System.out.print(friend.nickname + " : ");
			System.out.print(friend.email + " : ");
			System.out.println(friend.isConnected );
		}
	}
	
	private static void testSignUp(
			String id, 				//필수, 중복불허
			String pw, 				//필수
			String name, 			//필수
			String nickname, 		//필수, 중복불허
			String birthdate,		//필수
			String email, 			//필수
			String tel, 				//옵션
			String web, 				//옵션 github/홈페이지 주소
			String todaysComment    //옵션 오늘의 한마디
			) {
		System.out.println("*********** testSignUp ************");
		
		ChatDB db = new ChatDB();

		User user = new User();
		user.id = id;
		user.pw = pw;
		user.name = name;
		user.nickname = nickname;
		user.birthdate = birthdate;
		user.email = email;
		user.tel = tel;
		user.web = web;
		user.todaysComment = todaysComment;
	
		
		int ret = db.addUser(user);		
		if (ret != 0 ) {
			// 사용자 등록 실패 (결과값 0이 아닌 수)
			System.out.println("사용자 등록 실패. 실패 사유: "+ db.getLastMessage());
			return;
		}
			
	}
	
	private static void testEncryption() {
		System.out.println("*********** testEncryption ************");
		ChatDB db = new ChatDB();
		db.testEncryption("안녕하세요");
	}

	private static void testCleanup() {
		System.out.println("*********** testCleanup ************");

		ChatDB db = new ChatDB();
		int ret = db.cleanUpDB();
		if (ret != 0 ) {
			// DB 클린업 실패 (결과값 0이 아닌 수)
			System.out.println("DB 클린업 실패. 실패 사유: "+ db.getLastMessage());
			return;
		}
	}
	
	public static void main(String[] args) {
		ChatDB db = new ChatDB();
		
		
		/******* 로그인 *********/
		/*
		
		User user = db.login("000101", "000110");
		
		if (user == null) {
			//오류
			System.out.println("오류: " + db.getLastMessage());
		}else
			System.out.println(user.email );
		
		*/
		
		/************ DB 전체 내용 삭제 **********/

		/*
		int ret = db.cleanUpDB();
		
		if (ret > 0 ) {
			//오류
			System.out.println("오류: " + db.getLastMessage());			
		}else
			System.out.println("모든 DB가 삭제되었습니다.");			
		*/
		
		/**********사용자 등록**********/
		/*
		User user = new User();
		user.id = "0229323";
		user.pw = "12345";
		user.name = "전종민";
		user.nickname = "스누피전";
		user.birthdate = "2020-02-02";
		user.email = "jongmin@snoopy.com";
				
		int ret = db.addUser(user);

		if (ret > 0 ) {
			//오류
			System.out.println("오류: " + db.getLastMessage());			
		}else
			System.out.println("사용자가 등록되었습니다.");			
		*/
		
		/*********암호화 모듈 테스트 *********/
		/*
		db.testEncryption("안녕하세요");
		*/
			
		
		/*********테스트 사용자 등록 *********/
		
		/*
		testCleanup();

		System.out.println("");

		testSignUp("000001", "000110", "오리", "고라파덕", "2020-01-01", "paduck@pocket.mon", "010-1234-5678", "http://www.pocket.mon/paduck", ""); //SignUp 테스트"
		testSignUp("000002", "000110", "쥐"  , "피카츄" , "2020-02-02", "pikka@pocket.mon", "010-1234-5679", "http://www.pocket.mon/pikka", ""); //SignUp 테스트"
		testSignUp("000003", "000110", "거북", "꼬부기", "2020-03-01", "kkobu@pocket.mon", "010-1234-5678", "http://www.pocket.mon/kkobu", ""); //SignUp 테스트"
		testSignUp("000004", "000110", "도롱뇽", "파이리", "2020-04-21", "pa@pocket.mon", "010-1234-5678", "http://www.pocket.mon/pa", ""); //SignUp 테스트"
		testSignUp("000005", "000110", "식물", "이상해씨", "2020-01-23", "strangy@pocket.mon", "010-1234-5678", "http://www.pocket.mon/strangy", ""); //SignUp 테스트"
		

		System.out.println("");
		
		*/
		
		/*
		/////////// 친구 추가 ////////////
		
		int ret = 0;
		// 2. 로그인: 로그인에 성공하면 사용자 정보를 담은 User 객체를 return
		User user = db.login("000001", "000110");
		if (user == null) {
			// 로그인 실패 (결과값 null)
			System.out.println("로그온 실패. 실패 사유: "+ db.getLastMessage());
		}else {
			ret = db.addFriend(user, "000002");
			ret = db.addFriend(user, "000004");		
		}
		
		/////////// 친구 가져오기 ////////////
		
	    ArrayList<User> friends = db.getFriends(user);
		
	    if (friends == null) {
	    	System.out.println("친구가 없거나 친구를 가져오는데 문제가 발생");
	    }
	    else {
		    for(int i = 0; i < friends.size(); i++) {
		    		System.out.println( friends.get(i).name);
		    }
	    }
	    */
		
		/*** ID 찾기 패스워드 분실 시 이메일이나 닉네임으로 찾기 *****/ 
		System.out.println( db.findID("pikka@pocket.mon"));
		System.out.println( db.findID("파이리2"));
		

	}

}
