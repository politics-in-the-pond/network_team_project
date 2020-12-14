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
	message chattingRoom;
	String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
	String sql = null;
	String sql2 = null;
	Connection conn = null;
	
	/*********** JM : START ****************/
	//프렌드 리스트 루트 노드 명 (상수 처리)
	final String FL_NODE_ROOT = "친구목록";
	final String FL_NODE_ONLINE = "접속중인 친구";
	final String FL_NODE_OFFLINE = "비접속중인 친구";

	//친구목록 만들기
	DefaultMutableTreeNode root = new DefaultMutableTreeNode(FL_NODE_ROOT);
	/*********** JM : END ****************/
	

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
		ArrayList<Friend> friendData = new ArrayList<Friend>();
		//연결된 DB에서 회원 정보를 뽑아옴 
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
			
			/*********** JM : START ****************/

			sql2 = "select * from member";
			rs2 = stmt.executeQuery(sql2);
			while(rs2.next()) {
				//sql에서 id, nickname, member_code를 Friend 형식으로 friendData에 저장하기
				Friend friend = new Friend(rs2.getString("id"),  rs2.getString("nickname"), Long.parseLong( rs2.getString("member_code")));
				friendData.add(friend);
			}
			/*********** JM : END ****************/
			
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	    Calendar c1 = Calendar.getInstance();

		String strToday = sdf.format(c1.getTime());
		
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey );
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10","UTF-8"));//한 페이지의 결과 
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1","UTF-8"));//페이지번호  
		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON","UTF-8"));//받아올 데이터 타입  
		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(strToday,"UTF-8"));//조회하고싶은날
		urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1100","UTF-8"));//api 제공 시간  
		urlBuilder.append("&" +
		URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55","UTF-8"));//위도 
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
			//공공데이터에서 뽑아온 값을 String형태로 저장
			for(i = 0 ;i < catList.size();i++) {
				printResult += "category : " + catList.get(i) + " 예보 값:  " + fcValList.get(i) + " 예측 일자  : " + fcDateList.get(i) + " 예측 시간 : " + fcTimeList.get(i) + "\n";
			}
			
			JFrame frame = new JFrame();
			JPanel weatherPanel = new JPanel();

			JList l = new JList(printResult.split("\n"));
			//printResult에 있는 값을 \n으로 구분해 JList에 
			panel3.add(l);
			panel3.setBorder(new TitledBorder(new LineBorder(Color.black,5),"동네 날씨 예보 "));
		}
		/*********** JM : START ****************/
		// 친구목록 안에 있는 온라인, 오프라인 목록 만들기
		DefaultMutableTreeNode online = new DefaultMutableTreeNode(FL_NODE_ONLINE);
		DefaultMutableTreeNode offline = new DefaultMutableTreeNode(FL_NODE_OFFLINE);
		
		
		ArrayList<DefaultMutableTreeNode> f = new ArrayList<DefaultMutableTreeNode>();

		// db에서 자신을 제외한 nickname 가져와서 보여주기
		for (int i = 0; i< friendData.size(); i++) {
			Friend friend = friendData.get(i);
			//System.out.println("<"+userName + "> <" +friend.getNickName()+">");
			if(userNick.compareTo(friend.getNickName())!=0) {				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(friend.getNickName());
				//System.out.println(node);
				f.add(node);
				}
		}
		
		// 온라인 목록과 오프라인 목록을 친구 목록에 추가하기
		root.add(online);
		root.add(offline);
		
		// 소켓이랑 연동을 하지 못해서 임시로 온라인 오프라인 나누는 코드
		for (int i = 0; i < f.size(); i++) {
			if (i%2 == 0) online.add(f.get(i));
			else offline.add(f.get(i));
		}
		/*
		online.add(f1); //이 코드를 이용해 친구의 온,오프라인 확인
		online.add(f2);
		online.add(f3);
		
		offline.add(f4);
		offline.add(f5);
		*/
		tree = new JTree(root);
		tree.expandRow(1);
		tree.expandRow(5);
		//tree.setRowHeight(50);
		
		// 아이콘 이미지 설정하기
		DefaultTreeCellRenderer dt = new DefaultTreeCellRenderer();
		dt.setOpenIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Open.gif"));
		dt.setClosedIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Close.gif"));
		dt.setLeafIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Leaf.gif"));
		
		tree.setCellRenderer(dt);
		
		JScrollPane js = new JScrollPane(tree);
		
		// 채팅방으로 넘어가는 버튼 만들기
		chatBtn = new JButton("Make chatroom");
		try {
			chatBtn.addMouseListener(new MouseAdapter(){
				@Override
				// "Make chatroom"버튼을 누를 시 그 전에 선택한 닉네임 값 가져오기
				public void mouseClicked(MouseEvent e) {
					chattingRoom = new message();
					chattingRoom.messagePanel();
					TreePath[] treePaths = tree.getSelectionModel().getSelectionPaths();
				
					//make_member_code 함수 호출을 위해 사용
					idcode_gen code_gen = new idcode_gen();
					long[] member_codes = new long[treePaths.length];
					
					int i = 0;
					for (TreePath treePath : treePaths) {
						
						DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
					    Object userObject = selectedElement.getUserObject();
					    
						String nodename = userObject.toString();
						
						// 친구목록, 온라인, 오프라인 선택을 제외하고 나머지 닉네임을 선택할 때 그 닉네임 member_code 보여주기
					    if (nodename.compareTo(FL_NODE_ROOT) != 0 && nodename.compareTo(FL_NODE_ONLINE) != 0 && nodename.compareTo(FL_NODE_OFFLINE) != 0)  {
					    	
					    	Friend foundF = Friend.findUsingNickname(friendData, nodename);
					    	//long code = code_gen.make_member_code(foundF.getId(), foundF.getNickName());
					    	long code = foundF.getMemberCode();
							member_codes[i] = code;
							i++;
					    } 
					}
					// message UI 부름
					// 사용할 채팅참가 친구의 member code 목록: member_codes[] 배열 사용
					for (long member_code : member_codes) {
						System.out.println(member_code);
					}
				}
			});
			
		}catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		/*********** JM : END ****************/

		
  
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
