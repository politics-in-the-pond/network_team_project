import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class userDB {
	public ArrayList<Object> userPanel(String id) {
		String userName = null;
		String userNick = null;
		String userHome = null;
		Statement stmt = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
		String sql = null;
		String sql2 = null;
		Connection conn = null;
		ArrayList<Object> userData = new ArrayList();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "root", passwd = "000110";
			conn = DriverManager.getConnection(url, user,passwd);
			stmt = conn.createStatement();
			sql = "select * from member where id = '" + id +  "'";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				userData.add(rs.getString("name"));
				userData.add(rs.getString("nickname"));
				userData.add(rs.getString("home"));
				
			}
			
		}catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		
		return userData;
		
	}

}
