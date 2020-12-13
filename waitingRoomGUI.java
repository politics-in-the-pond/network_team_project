import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.cj.xdevapi.JsonParser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
public class waitingRoomGUI extends JFrame{
	JFrame frame;
	JPanel panel;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	APIweather api;
	userDB user;
	JButton logoutBtn ;
	JButton secessionBtn;
	secession sec;
	void waitingGUIPanel(String id) throws IOException {
		frame = new JFrame("WaitRoom");
		panel = new JPanel();
		panel1 = new JPanel(new GridLayout(5,1));
		panel3 = new JPanel();
		logoutBtn = new JButton("Logout");
		secessionBtn = new JButton("secession ");
		user = new userDB();
		ArrayList<Object> userData = user.userPanel(id);
		
		JLabel userNamePanel = new JLabel("Name :" + userData.get(0).toString() ,JLabel.CENTER);
		panel1.add(userNamePanel);
		JLabel userNickPanel = new JLabel("Nickname :"+ userData.get(1).toString(),JLabel.CENTER);
		panel1.add(userNickPanel);
		JLabel userHomePanel = new JLabel("Homepage :" + userData.get(2).toString(), JLabel.CENTER);
		panel1.add(userHomePanel);
		
		panel1.setBorder(new TitledBorder(new LineBorder(Color.black,5),"내 프로필  "));
		
		secessionBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				sec = new secession();
				try {
					sec.secessionPanel(id);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
				}
			});
		logoutBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource().equals(logoutBtn)) {
				frame.dispose();
				}
			}

			
		});
		api = new APIweather();
		String printResult = api.apiPanel();
		JPanel weatherPanel = new JPanel();
		System.out.println(printResult);
		JList l = new JList(printResult.split("\n"));
		panel3.add(l);
		panel3.setBorder(new TitledBorder(new LineBorder(Color.black,5),"동네 날씨 예보 "));
		panel1.add(logoutBtn, BorderLayout.WEST);
		panel1.add(secessionBtn,BorderLayout.EAST);
		
		frame.add(panel1,BorderLayout.NORTH);
		frame.add(panel3,BorderLayout.SOUTH);
		frame.setBounds(450,250,800,900);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
