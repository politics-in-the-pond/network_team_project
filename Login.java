import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.*;

public class Login extends JFrame{
	public static String id = null;
	JoinDB jdb;
	LoginDB ljdb;
	FriendList fList;
	public Login() {
		JPanel panel = new JPanel();
		JLabel idLabel = new JLabel("ID");
		JLabel pwLabel = new JLabel("Password");
		JTextField idText = new JTextField(15);
		JPasswordField pwText = new JPasswordField(20);
		JButton logBtn = new JButton("Log In");
		JButton joinBtn = new JButton("Join");
		
		panel.add(idLabel);
		
		panel.add(pwLabel);
		panel.add(idText);
		panel.add(pwText);
		panel.add(logBtn);
		panel.add(joinBtn);
		
		logBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			
			public void actionPerformed(ActionEvent e) {
				

			}
		});
		
		logBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ljdb = new LoginDB();
				id = idText.getText();
				String pw = new String(pwText.getPassword());
				int result = ljdb.checkIDPW(id,pw);
				if(result == 0) {
					JOptionPane.showMessageDialog(null,"Logged in");
					fList = new FriendList();
					try {
						fList.FriendListPanel(id);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null,"Fail");

			}
		});
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		joinBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jdb = new JoinDB();
				jdb.JoinDBPanel();
			}
		});
		add(panel);
		setVisible(true);
		setSize(600,400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public static void main(String args[]) {
		new Login();
	}
}
