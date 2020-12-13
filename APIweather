import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class APIweather {
	String printResult = "";
	String apiPanel() throws IOException {
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
			for(i = 0 ;i < catList.size();i++) {
				printResult += "category : " + catList.get(i) + " 예보 값:  " + fcValList.get(i) + " 예측 일자  : " + fcDateList.get(i) + " 예측 시간 : " + fcTimeList.get(i) + "\n";
			}
			
//			JFrame frame = new JFrame();
//			JPanel weatherPanel = new JPanel();
//
//			JList l = new JList(printResult.split("\n"));
//			panel3.add(l);
//			panel3.setBorder(new TitledBorder(new LineBorder(Color.black,5),"동네 날씨 예보 "));
		}
		return printResult;
	}
}
