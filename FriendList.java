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
import java.util.*;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
public class FriendList extends JFrame{
	JFrame frame;
	JPanel panel;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JTree tree;
	JButton logoutBtn;
	JButton secessionBtn;
	JButton chatBtn;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
	String sql = null;
	String sql2 = null;
	Connection conn = null;
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("친구목록");
	

	public void FriendListPanel(String id) throws IOException, ParseException {
		frame = new JFrame("WaitRoom");
		panel = new JPanel();
		panel1 = new JPanel(new GridLayout(5,1));
		panel3 = new JPanel();
		logoutBtn = new JButton("Logout");
		secessionBtn = new JButton("secession ");
		String userName = null;
		String userNick = null;
		String userHome = null;
		panel1.add(logoutBtn, BorderLayout.WEST);
		panel1.add(secessionBtn,BorderLayout.EAST);
		ArrayList<Object> friendData = new ArrayList();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "root", passwd = "000110";
			conn = DriverManager.getConnection(url, user,passwd);
			stmt = conn.createStatement();
			sql = "select * from member where id = '" + id +  "'";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				userName = rs.getString("name");
				userNick = rs.getString("nickname");
				userHome = rs.getString("home");
			}
			
			sql2 = "select * from member";
			rs2 = stmt.executeQuery(sql2);
			while(rs2.next()) {
				friendData.add(rs2.getString("nickname"));
			}
			secessionBtn.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getSource().equals(secessionBtn)) {
					String secessSql = "delete member where id='" + id + "'";
					try {
						stmt.executeUpdate(secessSql);
						rs3 = stmt.executeQuery(secessSql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.dispose();
					}
					
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
			
		}catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		JLabel userNamePanel = new JLabel("Name :" + userName ,JLabel.CENTER);
		panel1.add(userNamePanel);
		JLabel userNickPanel = new JLabel("Nickname :"+ userNick,JLabel.CENTER);
		panel1.add(userNickPanel);
		JLabel userHomePanel = new JLabel("Homepage :" + userHome, JLabel.CENTER);
		panel1.add(userHomePanel);
		
		panel1.setBorder(new TitledBorder(new LineBorder(Color.black,5),"내 프로필  "));
		String serviceKey = "WdoDhkmlcQ%2FO2oKSAGprlgcCkJnlvpQz%2FuvdC%2Fo9ezvRrg05%2Fd2pfzBOBdO1TMkxn49SnDXfyCKucyqt9OrPnA%3D%3D";
		//홈페이지에서 받은 API key
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst");
		//api call back url
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey );
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10","UTF-8"));//한 페이지의 결과 
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1","UTF-8"));//페이지번호  
		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON","UTF-8"));//받아올 데이터 타입  
		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20201212","UTF-8"));//조회하고싶은날
		urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1100","UTF-8"));//api 제공 시간  
		urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55","UTF-8"));//위도 
		urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127","UTF-8"));//경도  

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();//http와 연결 
		conn.setRequestMethod("GET");//get 방식으로 전송해 파라미터 받아오기 
		conn.setRequestProperty("Accept","application/json");
		System.out.println("Response code: " + conn.getResponseCode());//response code받아오기  
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}//rd로 데이터 읽어오기  
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = rd.readLine()) != null) {
			sb.append(line).append("\n");
		}//sb로 입력받은 데이터 저장  
		rd.close();
		conn.disconnect();
		String result = sb.toString(); 
		result = result.replace("<","");
		System.out.println(result);
		JSONParser parser = new JSONParser();
		//JSONObject obj = (JSONObject) parser.parse(response.getBody());
		Object obj = null;
		try {
			obj = (Object)parser.parse(result);
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObj = (JSONObject) obj;
		//만들어진 문자열 데이터 객체화  
		JSONObject parse_response = (JSONObject) jsonObj.get("response");
		
		//response key통해 객체 생성  
		JSONObject parse_body = (JSONObject)parse_response.get("body");
		//response로 부터 body 찾기  
		JSONObject parse_items = (JSONObject)parse_body.get("items");
		//body로 부터 items 찾기  
		JSONArray parse_item = (JSONArray) parse_items.get("item");
		//items로 부터 itemlist 받
		String category;
		JSONObject weather;
		String day = "";
		String time = "";
//		String categoryArr[] = null;
//		String fcstValueArr[] = null;
//		String fcstDateArr[] = null;
//		String fcstTimeArr[]= null;
		ArrayList<String> catList = new ArrayList();
		ArrayList<String> fcValList = new ArrayList();
		ArrayList<String> fcDateList = new ArrayList();
		ArrayList<String> fcTimeList = new ArrayList();

		for(int i = 0;i < parse_item.size(); i++) {
			weather = (JSONObject)parse_item.get(i);
			Object fcstValue = weather.get("fcstValue");
			Object fcstDate = weather.get("fcstDate");
			Object fcstTime = weather.get("fcstTime");
			category = (String)weather.get("category");
			catList.add(category);
			fcValList.add(fcstValue.toString());
			fcDateList.add(fcstDate.toString());
			fcTimeList.add(fcstTime.toString());
			//category, fcstValue, fcstDate, fcstTime과 같은 값 받아오기  
			if(!day.equals(fcstDate.toString())) {
				day = fcstDate.toString();
			}
			if(!time.equals(fcstTime.toString())) {
				time = fcstTime.toString();
				System.out.println(day + " " + time);
			}


			String printResult = "";
			for(i = 0 ;i < catList.size();i++) {
				printResult += "category : " + catList.get(i) + " 예보 값:  " + fcValList.get(i) + " 예측 일자  : " + fcDateList.get(i) + " 예측 시간 : " + fcTimeList.get(i) + "\n";
			}
			
			JFrame frame = new JFrame();
			JPanel weatherPanel = new JPanel();

			JList l = new JList(printResult.split("\n"));
			panel3.add(l);
			panel3.setBorder(new TitledBorder(new LineBorder(Color.black,5),"동네 날씨 예보 "));
		}
		//전종민 시작
		DefaultMutableTreeNode online = new DefaultMutableTreeNode("접속중인 친구");
		DefaultMutableTreeNode offline = new DefaultMutableTreeNode("비접속중인 친구");
		
		
		ArrayList<DefaultMutableTreeNode> f = new ArrayList<DefaultMutableTreeNode>();

		for (int i = 0; i< friendData.size(); i++) {
			System.out.println("<"+userName + "> <" +friendData.get(i).toString()+">");
			if(userNick.compareTo(friendData.get(i).toString())!=0) {				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(friendData.get(i));
				System.out.println(node);
				f.add(node);
				}
		}
		
		root.add(online);
		root.add(offline);
		
		for (int i = 0; i < f.size(); i++) {
			if (i%2 == 0) online.add(f.get(i));
			else offline.add(f.get(i));
		}
		
		tree = new JTree(root);
		
		tree.expandRow(1);
		tree.expandRow(5);
		//tree.setRowHeight(50);
		
		DefaultTreeCellRenderer dt = new DefaultTreeCellRenderer();
		dt.setOpenIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Open.gif"));
		dt.setClosedIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Close.gif"));
		dt.setLeafIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Leaf.gif"));
		
		tree.setCellRenderer(dt);
		
		JScrollPane js = new JScrollPane(tree);
		
		chatBtn = new JButton("Make chatroom");
		try {
			chatBtn.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Enter Chat Room!");
					//
					//For multiple selection you can use
					TreePath[] treePaths = tree.getSelectionModel().getSelectionPaths();
					for (TreePath treePath : treePaths) {
						
						DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
					    Object userObject = selectedElement.getUserObject(); 
					    if (friendData.contains(userObject)) {
						    //Do what you want with selected element's user object
						    System.out.println(userObject + " is in the list!");
					    }
					}
				}				
			});
			
		}catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		
		//전종민 끝
		
  
		frame.add(panel1,BorderLayout.NORTH);
		frame.add(js,BorderLayout.CENTER);
		frame.add(chatBtn, BorderLayout.EAST);
		frame.add(panel3,BorderLayout.SOUTH);
		frame.setBounds(450,250,800,900);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}


	private MutableTreeNode f(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	private DefaultMutableTreeNode DefaultMutableTreeNode(Object object) {
		// TODO Auto-generated method stub
		return null;
	}


	}
