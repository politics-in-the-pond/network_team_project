package ui_complex;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class login_gui extends JFrame{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//로그인화면
	
	public login_gui()
	{
		//component 들을 선언
		JPanel p = new JPanel();
        p.setLayout(null);
        
		
		Label id_label= new Label("ID");
		add(id_label);
		Label password_label= new Label("password");
		add(password_label);
		
		TextField id_textfield = new TextField();
		add(id_textfield);
		TextField password_textfield = new TextField();
		add(password_textfield);
		
		JButton login_btn = new JButton("login");
		add(login_btn);
		JButton register_btn = new JButton("click here to register");
		add(register_btn);
		
		//만들어진 버튼 "CLICK HERE TO REGISTER"를 누르면 REGISTER GUI창이 열린다
		register_btn.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unused")
			public void actionperformed(ActionEvent e) {
				new register_GUI();
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		}
				);
		
		//component들의 크기 선언
		id_label.setBounds(40, 20, 40, 40);
		password_label.setBounds(40, 60, 60, 40);
		id_textfield.setBounds(150, 20, 200, 30);
		password_textfield.setBounds(150, 60, 200, 30);
		login_btn.setBounds(380, 60, 80, 30);
		register_btn.setBounds(380, 110, 200, 30);
		add(p);
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("로그인 화면 ");
		setVisible(true);
		
		login_btn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			
			public void actionPerformed(ActionEvent e) {
				

			}
		});
		login_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				

			}
		});
		login_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		login_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jdb = new JoinDB();
				jdb.JoinDBPanel();
			}
		});
		
			}
		
	public static void main(String args[]) {
		new login_gui();
	}
	}

		
