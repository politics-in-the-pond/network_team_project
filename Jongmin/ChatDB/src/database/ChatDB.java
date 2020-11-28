package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class ChatDB {
    private static int fDEBUG = -1;         //DEBUG 플래그 (== -1: 메시지 끔, 0~2: 각 단계 별 메시지 보여짐 ) 
    private static String lastMessage;     // 최종 수행 메시지 (오류 등의 원인들을 보여줄때 사용)
    
    // (private) 코드 디버깅 용 메시지 출력. fDEBUG 가 0보다 작으면 콘솔에 메시지가 출력되지 않음. (대신 LastMessage에는 저장됨) 
	private static void DEBUG(int level, String fname ,String str) {
		lastMessage = str;
		if (fDEBUG >= level)
			System.out.println("[DEBUG("+ level +"):ChatDB:" + fname + "] " + str);
	}
	
	// 최종 수행했던 함수의 결과 메시지를 돌려줌.
	public static String getLastMessage() {
		return lastMessage;
	}
    
	// (private) 스트링 값을 단일 따옴표로 싸서 돌려줌 - SQL 작성을 위해 사용
	private static String SQ(String str) {
        return "'" + str + "'";
    }
	
	// (private) 패스워드 암호화를 위한 암호화 함수 > 암호화 모듈을 이용
	private static String encrypt(String str) {
		// (TODO!) 암호화 예제. 최종 암호화 함수 적용해서 아래 내용을 변경
		byte[] targetBytes = str.getBytes(); 
		Encoder encoder = Base64.getEncoder(); 
		byte[] encodedBytes = encoder.encode(targetBytes); // Base64 디코딩 
		
		return new String(encodedBytes);
	}

	// (private) 패스워드 암호화를 위한 복호화 함수 > 암호화 모듈을 이용
	private static String decrypt(String str) {
		// (TODO!) 복호화 함수 적용해서 아래 내용 변경
		byte[] encodedBytes = str.getBytes();
		Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes = decoder.decode(encodedBytes);

		return new String(decodedBytes);
	}
	
	//암호화 및 복호화 모듈의 구현 정확성 테스트 : 해당 값의 암호화 값과 복호화 값을 보여줌
	public static void testEncryption(String str) {
		DEBUG(1,"testEncryption","Original String: " +str);
		str = encrypt(str);
		DEBUG(1,"testEncryption","Encryted String: " +str);
		str = decrypt(str);
		DEBUG(1,"testEncryption","Decrypt String: " +str);
	}
	
	// ResultSet을 User 객체로 바꾸어 반환
	private static User getUser(ResultSet result) throws Exception {
		try {
			User user = new User(); 
			user.id = result.getString("id");
			user.name = result.getString("name");
			user.nickname = result.getString("nickname");
			user.email = result.getString("email");
			
			
			return user;
		}catch(Exception e) {throw e;}		
	}
    
	// TimeStamp (로그인 / 로그아웃)
	private void setTimeStamp() {
		 LocalDateTime.now().toString();
	}
	
	public ChatDB() {
	}

	// 해당 유저의 친구를 찾응 - 실패 또는 친구가 없는 경우 null 값 반환
	public static ArrayList<User> getFriends(User user) {
		try {
			Connection con = getConnection();
			String sql = "SELECT * FROM users WHERE id IN (SELECT friend_id FROM friends WHERE id = "+ SQ(user.id) +");";
			DEBUG(1,"getFriends",  sql);
			
			PreparedStatement statement = con.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<User> array = new ArrayList<User>();
			int n = 0;
			while(result.next()) {

				User friend = getUser(result);				
				DEBUG(2,"getFriends","id : " + friend.id + " | name : " + friend.name);
	
				array.add(friend);
				n++;
			}
			DEBUG(1,"getFriends", n +"개의 친구 목록 가져옴");
			return array;
		} catch(Exception e) { DEBUG(0,"addUser",e.getMessage());}
		return null;
	}
	
	// 사용자 추가 (사용자 등록) - 성공인 경우 0, 실패인 경우 0이 아닌 수를 반환 
	public static int addUser(User user) {
		try {
			Connection con = getConnection();
			String sql = "INSERT INTO chat.users (id, pw, name, nickname, birthdate, email, tel, web) VALUES ("
					+ SQ(user.id) + ","
					+ SQ(encrypt(user.pw)) + ","
					+ SQ(user.name) + ","
					+ SQ(user.nickname) + ","
					+ SQ(user.birthdate) + ","// 2018-12-11;
					+ SQ(user.email) + ","
					+ SQ(user.tel) + ","									
					+ SQ(user.web) + ")";
			PreparedStatement posted = con.prepareStatement(sql);
			posted.executeUpdate();
			DEBUG(1,"addUser", "사용자 "+ user.id +" 추가됨");
			return 0;

		} catch(Exception e) { 
			DEBUG(0,"addUser",e.getMessage());
		}
		
		return 1;
	}
	
	// id를 이용해서 친구 추가 - 실패 시 0이 아닌 값 반환
	public static int addFriend(User user, String FriendID) {
		try {
			Connection con = getConnection();
			PreparedStatement posted = con.prepareStatement(
							"INSERT INTO chat.friends(id, friend_id) " 
							+ "VALUES ("
									+ SQ(user.id) + ","
									+ SQ(FriendID) + ");");
			posted.executeUpdate();
			DEBUG(1,"addFriend", "사용자 "+ user.id +"의 친구 " + FriendID +" 추가됨");			
			return 0;
		} catch(Exception e) { DEBUG(0,"addFriend",e.getMessage());}
		
		return 1;
	}
	

	// email이나 nickname을 이용해서 ID를 찾는 함수 - 실패 시 null값 반환
	public static String findID(String str ) {
		try {
			Connection con = getConnection();
			String sql = "SELECT id FROM users WHERE email=" + SQ(str) + " OR nickname = " + SQ(str);
			DEBUG(1,"findID", sql);
			PreparedStatement statement = con.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();			
			
			if (result.next()) {
				String id = result.getString("id");			
				DEBUG(1,"findID","ID: " + id );
				return id ;			
			}
			DEBUG(0,"findID","해당 사용자 없음");
			return null;
		} catch(Exception e) { DEBUG(0,"findID",e.getMessage());}
					
		return null; //실패 시 null 전송
	}
	
	// (주의!) DB내 모든 Table의 정보를 지움 - 실패 시 0이 아닌 수 반환
	public static int cleanUpDB() {
		try {
			Connection con = getConnection();
			PreparedStatement posted = con.prepareStatement("TRUNCATE TABLE users;  ");
			posted.executeUpdate();
			posted = con.prepareStatement("TRUNCATE TABLE friends;  ");
			posted.executeUpdate();

			DEBUG(1, "cleanUpDB", "delete Completed.");
			return 0; // 성공 시 0을 전달

		} catch(Exception e) {DEBUG(0, "cleanUpDB", e.toString());}
		
		return 1; //실패시 1을 전달
	}
	
	//DB Connection 가져오기 - 실패 시 null값 반환
	public static Connection  getConnection() {
		// 함수 수행 전 아래 명령으로 해당 데이터베이스 존재 여부 & 접근 가능 확인 가능
		// $ mysql -uroot -p000110 -e 'SHOW DATABASES'

		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/chat?serverTimezone=UTC";
			String username = "root";
			String password = "000110";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			DEBUG(0, "getConnectoin", "DB 연결 됨");
			return conn;
		} catch(Exception e) { DEBUG(0,"addUser",e.toString());}
		
		DEBUG(0,"getConnectoin", "DB 연결 실패");
		return null;	
	}

	// id로 사용자 조회 - User 객체 반환 - 실패 시 null 값 반환
	public static User findUser(String id)  {
		try {
			Connection con = getConnection();
			String sql = "SELECT * FROM users WHERE id=" + SQ(id) ;
			DEBUG(1,"findUser", sql);
			
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();			
			
			if (result.next()) {
				User user = getUser(result);			
				user.pw = decrypt(result.getString("pw"));
			
				DEBUG(1,"findUser","사용자 " + user.nickname + " 찾음");
				return user;
			}
			DEBUG(0,"findUser","해당 사용자 없음");
			return null;
		} catch(Exception e) { DEBUG(0,"findUser",e.getMessage());}
					
		return null; //실패 시 null 전송
	}
	
	
	// id, pw로 사용자 로그인 - 실패 시 null 값 반환 (실패 사유는 getLastMessage()로 얻어옴)
	public static User login(String id, String pw ) {
		User user = findUser(id);
		
		// 해당 아이디에 해당하는 유저가 없는 경우
		if (user == null) return null;
		
		if (pw.compareTo(user.pw)> 0) {
			DEBUG(0,"login","패스워드 불일치");
			return null;
		}
		else {
			DEBUG(1,"login","사용자 " + user.nickname + " 찾음");
			return user;
		}		
	}


}
