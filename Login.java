import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.*;

public class Login extends JFrame{
	public static String id = null;
	JoinDB jdb;
	LoginDB ljdb;
	FriendList fList;
	waitingRoomGUI gui;
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
					gui = new waitingRoomGUI();
					try {
						gui.waitingGUIPanel(id);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
				else
					JOptionPane.showMessageDialog(null,"Fail");

			}
		});
		//login 버튼을 누르면 db에 있는 data를 조회 한 후, 사용자가 입력한 id와 일치하는 id가 있는지 대조하고 있다면 로그인 성공이라는팝업창과 함께 친구목록으로 넘어감.  
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
		});//join버튼을 누르면 회원가입을 하는 팝업이 뜸  
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
