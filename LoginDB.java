import java.sql.*;
import java.util.*;

public class LoginDB{
	String id = null;
	String pw = null;
	
	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
	String sql = null;
	Properties info = null;
	Connection conn = null;
	
	int checkIDPW(String id, String pw) {
		this.id = id;
		this.pw = pw;
		int result = 1;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			info = new Properties();
			info.setProperty("user", "root");
			info.setProperty("password","000110");
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			
			sql = "select * from DB where id = '" + id +  "'";
			rs = stmt.executeQuery(sql);
			if(rs.next() == false || (id.isEmpty()) == true ) {
				result = 1;
			}else {
				sql = "select * from (select * from DB where id = '" + id + "')";
				rs = stmt.executeQuery(sql);
				while(rs.next() == true) {
					if(rs.getString(2).equals(pw))
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