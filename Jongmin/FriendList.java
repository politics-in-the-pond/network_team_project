import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.sql.*;
import java.util.*;


public class FriendList extends JFrame{
	public static void main1(String[] args) {
		Panel Info = new Panel();
		Panel FriendList = new Panel();
		Panel Weatherapi = new Panel();
		
		Info.setBounds(0, 0, 100, 100);
		FriendList.setBounds(0, 100, 100, 100);
		Weatherapi.setBounds(0, 200, 100, 100);
	}
	
	public FriendList() {
		JTree tree;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("친구목록");
		DefaultMutableTreeNode online = new DefaultMutableTreeNode("접속중인 친구");
		DefaultMutableTreeNode offline = new DefaultMutableTreeNode("비접속중인 친구");
		
		DefaultMutableTreeNode f1 = new DefaultMutableTreeNode("고라파덕");
		DefaultMutableTreeNode f2 = new DefaultMutableTreeNode("B");
		DefaultMutableTreeNode f3 = new DefaultMutableTreeNode("C");
		DefaultMutableTreeNode f4 = new DefaultMutableTreeNode("D");
		DefaultMutableTreeNode f5 = new DefaultMutableTreeNode("E");
		
		root.add(online);
		root.add(offline);
		
		online.add(f1); //이 코드를 이용해 친구의 온,오프라인 확인
		online.add(f2);
		online.add(f3);
		
		offline.add(f4);
		offline.add(f5);
		
		tree = new JTree(root);
		
		tree.expandRow(1);
		tree.expandRow(5);
		tree.setRowHeight(50);
		
		DefaultTreeCellRenderer dt = new DefaultTreeCellRenderer();
		dt.setOpenIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Open.gif"));
		dt.setClosedIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Close.gif"));
		dt.setLeafIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Leaf.gif"));
		
		tree.setCellRenderer(dt);
		
		JScrollPane js = new JScrollPane(tree);
		add("Center",js);
		setSize(350,500);
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
        // variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
 
        // Step 1: Loading or registering MySQL JDBC driver class
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfex) {
            System.out.println("Problem in loading MySQL JDBC driver");
            cnfex.printStackTrace();
        }
 
        // Step 2: Opening database connection
        try {
 
            // Step 2.A: Create and get connection using DriverManager
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat?serverTimezone=UTC", "root", "000110"); 
 
            // create SQL query to fetch all player records
            String sqlSelectQuery = "SELECT id, name, nickname,birthdate FROM users";
 
            // Step 2.B: Creating JDBC Statement 
             preparedStatement = connection.prepareStatement(sqlSelectQuery);
 
            // Step 2.C: Executing SQL & retrieve data into ResultSet
            resultSet = preparedStatement.executeQuery();
 
            System.out.println("ID\t이름\t별명\t생일");
            System.out.println("==\t====\t===\t====");
 
            // processing returned data and printing into console
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1) + "\t" + 
                        resultSet.getString(2) + "\t" + 
                        resultSet.getString(3) + "\t" +
                        resultSet.getDate(4));
            }
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
 
            // Step 3: Closing database connection
            try {
                if(null != connection) {
 
                    // cleanup resources, once after processing
                    resultSet.close();
                    preparedStatement.close();
 
                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }
	public static void main(String[] args) {
		try {
			String id = "000001";
			String pw = "000110";
			
			User userdb = new User();
			
			int result = 0;
			result = userdb.checkIDPW(id, pw);
			
			System.out.println("결과:" + result);
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception ex) {}
		new FriendList();
	}

}

		
	
	
