import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame{
	JoinDB jdb;
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
				JOptionPane.showMessageDialog(null, "Logged in");

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
