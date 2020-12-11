/* JakeJsonParser.java */
package api_using;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JakeJsonParser {
    private JakeJsonParser() {}
    private static class IODHI {
        private static final JakeJsonParser instance = new JakeJsonParser();
    }
    public static JakeJsonParser getInstance() {
        return IODHI.instance;
    }

    public JSONArray getRemoteJSONArray(String url) {
        StringBuffer jsonHtml = new StringBuffer();
        try {
            URL u = new URL(url);
            InputStream uis = u.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(uis, "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                jsonHtml.append(line + "\r\n");
            }
            br.close();
            uis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArr = (JSONArray) JSONValue.parse(jsonHtml.toString());
        return jsonArr;
    }

    public JSONArray getWeatherJSONArray(String url) {
        StringBuffer jsonHtml = new StringBuffer();
        JSONArray jsonArr = null;
        JSONObject jsonObj = null;
        String[] saAttribName = { "response", "body", "items" };
        try {
            URL u = new URL(url);
            InputStream uis = u.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(uis, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                jsonHtml.append(line + "\r\n");
            }
            br.close();
            uis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(jsonHtml.toString());

        jsonObj = (JSONObject) JSONValue.parse(jsonHtml.toString());
        for (int i = 0; i < saAttribName.length; i++)
            jsonObj = (JSONObject) jsonObj.get(saAttribName[i]);
        jsonArr = (JSONArray) jsonObj.get("item");

        return jsonArr;
    }

    public Map<String, String> getJsonSubMap(JSONArray jsonArrSource) {
        Map<String, String> jsonMap = new LinkedHashMap<String, String>();

        // 기상청 API 에서 받아오는 JSON은 code:value형식만 갖추므로
        // 이 Algorithm으로 일관되게 mapping할 수 있음
        // (leaf단에서는 x,y값이 추가로 들어가므로 다른 method사용해야함)
        for (int i = 0; i < jsonArrSource.size(); i++) {
            JSONObject jsonObjItem = (JSONObject) jsonArrSource.get(i); // JSONArray에서 JSONObject하나씩 가져옴
            String code = (String) jsonObjItem.get("code"); // JSONObject에서 key, value 가져옴
            String value = (String) jsonObjItem.get("value");
            jsonMap.put(value, code); // 지역이름으로 code를 알아내길 원하므로 K,V를 바꿔서 mapping
        }
        return jsonMap;
    }

    public Map<String, Coord> getJsonLeafMap(JSONArray jsonArrSource) {
        Map<String, Coord> jsonMap = new LinkedHashMap<String, Coord>();
        for (int i = 0; i < jsonArrSource.size(); i++) {
            JSONObject jsonObjItem = (JSONObject) jsonArrSource.get(i);
            String value = (String) jsonObjItem.get("value");
            String x = (String) jsonObjItem.get("x");
            String y = (String) jsonObjItem.get("y");
            jsonMap.put(value, new Coord(x, y));
        }
        return jsonMap;
    }
}

