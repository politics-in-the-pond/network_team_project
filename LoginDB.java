import java.sql.*;
import java.util.*;

public class LoginDB{
	String id = null;
	String pw = null;
	
	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
	String sql = null;
	Connection conn = null;
	
	int checkIDPW(String id, String pw) {
		this.id = id;
		this.pw = pw;
		int result = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "root", passwd = "000110";
			conn = DriverManager.getConnection(url, user,passwd);
			stmt = conn.createStatement();
			
			sql = "select * from member where id = '" + id +  "'";
			rs = stmt.executeQuery(sql);
			if(rs.next() == false || (id.isEmpty()) == true ) {
				result = 1;
			}else {
				sql = "select * from (select * from member where id = '" + id + "')";
				rs = stmt.executeQuery(sql);
				while(rs.next() == true) {
					if(rs.getString("pw").equals(pw))
						result = 0;
					else
						result = 1;
				}
			}
		}catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return result;
	}
}
