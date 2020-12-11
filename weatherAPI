import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
public class weatherAPI{
	public static void main(String[] args) throws IOException, ParseException{
		
		String serviceKey = "WdoDhkmlcQ%2FO2oKSAGprlgcCkJnlvpQz%2FuvdC%2Fo9ezvRrg05%2Fd2pfzBOBdO1TMkxn49SnDXfyCKucyqt9OrPnA%3D%3D";
		//홈페이지에서 받은 API key
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst");
		//api call back url
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey );
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10","UTF-8"));//한 페이지의 결과 
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1","UTF-8"));//페이지번호  
		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON","UTF-8"));//받아올 데이터 타입  
		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20201211","UTF-8"));//조회하고싶은날
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
		Object obj = (Object)parser.parse(result);
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
		for(int i = 0;i < parse_item.size(); i++) {
			weather = (JSONObject)parse_item.get(i);
			Object fcstValue = weather.get("fcstValue");
			Object fcstDate = weather.get("fcstDate");
			Object fcstTime = weather.get("fcstTime");
			category = (String)weather.get("category");
			//category, fcstValue, fcstDate, fcstTime과 같은 값 받아오기  
			if(!day.equals(fcstDate.toString())) {
				day = fcstDate.toString();
			}
			if(!time.equals(fcstTime.toString())) {
				time = fcstTime.toString();
				System.out.println(day + " " + time);
			}
			
			System.out.print("\tcategory :" + category);
			System.out.print(", fcst_Value : "+ fcstValue);
			System.out.print(", fcstDate : " + fcstDate);
			System.out.println(", \nfcstTime : " + fcstTime);
			//출력  
		}
	}
}
