import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class secession {
	void secessionPanel(String id) throws ClassNotFoundException {
		
		Statement stmt = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
		String sql = null;
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "root", passwd = "000110";
			conn = DriverManager.getConnection(url, user,passwd);
			stmt = conn.createStatement();
			sql = "delete member where id='" + id + "'";
			rs = stmt.executeQuery(sql);
		}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
			}

			
