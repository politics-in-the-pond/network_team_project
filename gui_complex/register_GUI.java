package ui_complex;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class register_GUI extends JFrame{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//로그인화면
	
	public register_GUI()
	{
		JPanel p = new JPanel();
        p.setLayout(null);
        
		//component들을 열심히 선언
		Label id_label= new Label("ID");
		add(id_label);
		Label password_label= new Label("PW");
		add(password_label);
		Label name_label= new Label("이름");
		add(name_label);
		Label nickname_label= new Label("별명");
		add(nickname_label);
		Label birth_label= new Label("생년월일");
		add(birth_label);
		Label phonenum_label= new Label("전화번호");
		add(phonenum_label);	
		Label WEB_label= new Label("WEB");
		add(WEB_label);
		
		TextField id_textfield = new TextField();
		add(id_textfield);
		TextField password_textfield = new TextField();
		add(password_textfield);
		TextField name_textfield = new TextField();
		add(name_textfield);
		TextField nickname_textfield = new TextField();
		add(nickname_textfield);
		TextField birth_textfield = new TextField();
		add(birth_textfield);
		TextField phonenum_textfield = new TextField();
		add(phonenum_textfield);
		TextField WEB_textfield = new TextField();
		add(WEB_textfield);
		
		JButton ID_btn = new JButton("중복체크");
		add(ID_btn);
		JButton nickname_btn = new JButton("중복체크");
		add(nickname_btn);
		JButton complete_btn = new JButton("완료");
		add(complete_btn);
		
		//component들의 크기를 선언 setBound(x,y,w,h)
		id_label.setBounds(40, 20, 40, 40);
		password_label.setBounds(40, 60, 60, 40);
		name_label.setBounds(40, 100, 40, 40);
		nickname_label.setBounds(40, 140, 60, 40);
		birth_label.setBounds(40, 180, 40, 40);
		phonenum_label.setBounds(40, 220, 60, 40);
		WEB_label.setBounds(40, 260, 40, 40);
		
		
		
		
		id_textfield.setBounds(160, 20, 200, 30);
		password_textfield.setBounds(160, 60, 200, 30);
		name_textfield.setBounds(160, 100, 200, 30);
		nickname_textfield.setBounds(160, 140, 200, 30);
		birth_textfield.setBounds(160, 180, 200, 30);
		phonenum_textfield.setBounds(160, 220, 200, 30);
		WEB_textfield.setBounds(160, 260, 200, 30);
		
		
		ID_btn.setBounds(390, 20, 120, 30);
		nickname_btn.setBounds(390, 100, 120, 30);
		complete_btn.setBounds(390, 260, 120, 30);
		
		add(p);
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("로그인 화면 ");
		setVisible(true);
		
			}
		
	public static void main(String args[]) {
		new register_GUI();
	}
	}

