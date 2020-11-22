import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
public class JoinDB implements MouseListener {
	JFrame frame;
	JPanel logPanel;
	JPanel logPanel1;
	JPanel logPanel2;
	JPanel logPanel3;
	JTextField idText,nameText, birthText, nickText, emailText, pnumText, homeText = null;
	JPasswordField pwText, pwCheckText = null;
	JButton joinBtn, checkBtn;
	void JoinDBPanel() {
		frame = new JFrame("Join");
		logPanel = new JPanel();
		logPanel1 =  new JPanel(new GridLayout(9,1));
		logPanel2 = new JPanel(new GridLayout(9,1));
		logPanel3 = new JPanel();
		
		JLabel idLabel = new JLabel("ID", JLabel.CENTER);
		JLabel pwLabel = new JLabel("PW", JLabel.CENTER);
		JLabel pwCheckLabel = new JLabel("Check PW", JLabel.CENTER);
		JLabel nickLabel = new JLabel("Nickname", JLabel.CENTER);
		JLabel emailLabel = new JLabel("E-Mail", JLabel.CENTER);
		JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
		JLabel birthLabel = new JLabel("Birth Date"+"\n"+"yy/mm/dd", JLabel.CENTER);
		JLabel pnumLabel = new JLabel("Phone number(option) ", JLabel.CENTER);
		JLabel homeLabel = new JLabel("Homepage address(option)", JLabel.CENTER);
		
		logPanel1.add(idLabel);
		logPanel1.add(pwLabel);
		logPanel1.add(pwCheckLabel);
		logPanel1.add(nickLabel);
		logPanel1.add(emailLabel);
		logPanel1.add(nameLabel);
		logPanel1.add(birthLabel);
		logPanel1.add(pnumLabel);
		logPanel1.add(homeLabel);
		
		idText = new JTextField(20);
		idText.addMouseListener(this);
		pwText = new JPasswordField(20);
		pwText.addMouseListener(this);
		pwCheckText = new JPasswordField(20);
		pwCheckText.addMouseListener(this);
		nickText = new JTextField(20);
		nickText.addMouseListener(this);
		emailText = new JTextField(20);
		emailText.addMouseListener(this);
		nameText = new JTextField(20);
		nameText.addMouseListener(this);
		birthText = new JTextField(20);
		birthText.addMouseListener(this);
		pnumText = new JTextField(20);
		pnumText.addMouseListener(this);
		homeText = new JTextField(50);
		homeText.addMouseListener(this);
		
		logPanel2.add(idText);
		logPanel2.add(pwText);
		logPanel2.add(pwCheckText);
		logPanel2.add(nickText);
		logPanel2.add(emailText);
		logPanel2.add(nameText);
		logPanel2.add(birthText);
		logPanel2.add(pnumText);
		logPanel2.add(homeText);
		
		checkBtn = new JButton("Check ID");
		logPanel3.add(checkBtn, BorderLayout.NORTH);
		checkBtn.addMouseListener(this);
		
		frame.add(logPanel,BorderLayout.NORTH);
		frame.add(logPanel1,BorderLayout.WEST);
		frame.add(logPanel2, BorderLayout.CENTER);
		frame.add(logPanel3, BorderLayout.EAST);
		
		JPanel logPanel4 = new JPanel();
		JLabel askLabel = new JLabel("Join?");
		joinBtn = new JButton("YES");
		JButton disBtn = new JButton("NO");
		
		joinBtn.addMouseListener(this);
		logPanel4.add(askLabel);
		logPanel4.add(joinBtn);
		logPanel4.add(disBtn);
		frame.add(logPanel4, BorderLayout.SOUTH);
		
		disBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				dbClose();
			}
		});
		
		frame.setBounds(450,250,350,200);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;
	String url = "jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
	Properties info = null;
	Connection conn = null;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(idText))
			idText.setText("");
		else if(e.getSource().equals(pwText))
			pwText.setText("");
		else if(e.getSource().equals(pwCheckText))
			pwText.setText("");
		else if(e.getSource().equals(nickText))
			pwText.setText("");
		else if(e.getSource().equals(emailText))
			pwText.setText("");
		else if(e.getSource().equals(nameText))
			nameText.setText("");
		else if(e.getSource().equals(birthText))
			birthText.setText("");
		else if(e.getSource().equals(pnumText))
			pwText.setText("");
		else if(e.getSource().equals(homeText))
			pwText.setText("");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			info = new Properties();
			info.setProperty("user", "root");
			info.setProperty("password", "000110");
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			
			if(e.getSource().equals(checkBtn)) {
				sql = "select * from joinDB where id = '" + idText.getText() + "'";
				rs = stmt.executeQuery(sql);
				if(rs.getString("id").equals(idText))
					JOptionPane.showMessageDialog(logPanel3, "Unavailable ID");
				else
					JOptionPane.showMessageDialog(logPanel3, "Available ID");
				if(rs.next() == true || (idText.getText().isEmpty()) == true || (nameText.getText().isEmpty()) ||(nickText.getText().isEmpty())||(emailText.getText().isEmpty())|| (birthText.getText().isEmpty())) {
					JOptionPane.showMessageDialog(logPanel3, "Empty value");
	
				}else if((birthText.getText().length()) != 6)
					JOptionPane.showMessageDialog(logPanel3, "Invalid birth date");
				else if(pwText.getPassword() != pwCheckText.getPassword()){
					JOptionPane.showMessageDialog(logPanel3, "Passwords do not match");
				}
				else {
					sql = "insert into joinDB values ('" + idText.getText() + "','" + pwText.getText() + "','"
							+ nameText.getText() + "','" + birthText.getText() + "')";
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(logPanel3, "Joined!");
					frame.dispose();
					dbClose();
				}
			}
		
		}catch(Exception ee) {
			System.out.println("Error");
			ee.printStackTrace();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	public void dbClose() {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn!= null)
				conn.close();
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
}
