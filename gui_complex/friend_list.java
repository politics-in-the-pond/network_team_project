package ui_complex;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.*;
import java.sql.*;


public class FriendList extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame();
	JTree tree;
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("친구목록");
	private JCheckBox f1_check;
	private JCheckBox f2_check;
	private JCheckBox f3_check;
	private JCheckBox f4_check;
	private JCheckBox f5_check;
	public FriendList() {
		DefaultMutableTreeNode online = new DefaultMutableTreeNode("접속중인 친구");
		DefaultMutableTreeNode offline = new DefaultMutableTreeNode("비접속중인 친구");
		
		DefaultMutableTreeNode f1 = new DefaultMutableTreeNode("A");
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
		setSize(350,800);
		setVisible(true);
		
		//체크박스 component
		
		f1_check = new JCheckBox("초대하기",false);
		f2_check = new JCheckBox("초대하기",false);
		f3_check = new JCheckBox("초대하기",false);
		f4_check = new JCheckBox("초대하기",false);
		f5_check = new JCheckBox("초대하기",false);
		
		add(f1_check);
		add(f2_check);
		add(f3_check);
		add(f4_check);
		add(f5_check);
		
		//이미지작업		
		Image image1 = null;
		Image image2 = null;
		Image image3 = null;
	    try {
	        // 파일로부터 이미지 읽기
	        File sourceimage = new File("이미지 경료");
	        image1 = ImageIO.read(sourceimage);
	    
	        // InputStream으로부터 이미지 읽기
	        InputStream is = new BufferedInputStream(
	            new FileInputStream("이미지경로"));
	        image2 = ImageIO.read(is);
	    
	        // URL로 부터 이미지 읽기
	        URL url = new URL("이미지 url");
	        image3 = ImageIO.read(url);
	    } catch (IOException e) {
	    }
	    
	    // Use a label to display the image
	   
	    
	    JLabel label1 = new JLabel(new ImageIcon(image1));
	    JLabel label2 = new JLabel(new ImageIcon(image2));
	    JLabel label3 = new JLabel(new ImageIcon(image3));
	    
	    frame.getContentPane().add(label1, BorderLayout.CENTER);
	    frame.getContentPane().add(label2, BorderLayout.NORTH);
	    frame.getContentPane().add(label3, BorderLayout.SOUTH);
	    
	    frame.pack();
	    frame.setVisible(true);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void test() {
		 
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
			
			
			test();
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception ex) {}
		new FriendList();
	}

}
