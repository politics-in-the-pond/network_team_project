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
	JTextField idText, pwText,nameText, birthText = null;
	JButton joinBtn, checkBtn;
	void JoinDBPanel() {
		frame = new JFrame("Join");
		logPanel = new JPanel();
		logPanel1 =  new JPanel(new GridLayout(4,1));
		logPanel2 = new JPanel(new GridLayout(4,1));
		logPanel3 = new JPanel();
		
		JLabel idLabel = new JLabel("ID", JLabel.CENTER);
		JLabel pwLabel = new JLabel("PW", JLabel.CENTER);
		JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
		JLabel birthLabel = new JLabel("Birth Date\nyy/mm/dd", JLabel.CENTER);
		logPanel1.add(idLabel);
		logPanel1.add(pwLabel);
		logPanel1.add(nameLabel);
		logPanel1.add(birthLabel);
		
		idText = new JTextField(20);
		idText.addMouseListener(this);
		pwText = new JTextField(20);
		pwText.addMouseListener(this);
		nameText = new JTextField(20);
		nameText.addMouseListener(this);
		birthText = new JTextField(20);
		birthText.addMouseListener(this);
		
		logPanel2.add(idText);
		logPanel2.add(pwText);
		logPanel2.add(nameText);
		logPanel2.add(birthText);
		
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
		else if(e.getSource().equals(nameText))
			nameText.setText("");
		else if(e.getSource().equals(birthText))
			birthText.setText("");
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
				if(rs.next() == true || (idText.getText().isEmpty()) == true || (nameText.getText().isEmpty()) || (birthText.getText().isEmpty())) {
					JOptionPane.showMessageDialog(logPanel3, "Empty value");
	
				}else if((birthText.getText().length()) != 6)
					JOptionPane.showMessageDialog(logPanel3, "Invalid birth date");
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
