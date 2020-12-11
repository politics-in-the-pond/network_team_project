/* LocationCodeFetcher.java */
package api_using;

import java.util.Map;

import org.json.simple.JSONArray;

public class LocationCodeFetcher {
    private final String sUrlDef = "http://www.kma.go.kr/DFSROOT/POINT/DATA/";
    private final String sUrlMdlHead = "mdl.";
    private final String sUrlLeafHead = "leaf.";
    private final String sUrlTail = ".json.txt";
    private Map<String, String> mapTop;
    private Map<String, String> mapMdl;
    private Map<String, Coord> mapLeaf;

    private JakeJsonParser jjp;

    public LocationCodeFetcher() {
        jjp = JakeJsonParser.getInstance();
    }

    private String getStrUrl(String s) {
        if (s.equals("top"))
            return sUrlDef + "top" + sUrlTail;
        else
            return sUrlDef;
    }

    private String getStrUrl(String s, String code) {
        String tmp = null;
        if (s.equals("mdl"))
            tmp = sUrlMdlHead;
        else if (s.equals("leaf"))
            tmp = sUrlLeafHead;
        return sUrlDef + tmp + code + sUrlTail;
    }

    public Coord fetchLocationCode(String[] saLocation) { // 인자: {시군구, 시도, 동면읍}
        JSONArray jsonArrTop = null;
        JSONArray jsonArrMdl = null;
        JSONArray jsonArrLeaf = null;

        jsonArrTop = jjp.getRemoteJSONArray(getStrUrl("top"));
        mapTop = jjp.getJsonSubMap(jsonArrTop);
        jsonArrMdl = jjp.getRemoteJSONArray(getStrUrl("mdl", mapTop.get(saLocation[0])));
        mapMdl = jjp.getJsonSubMap(jsonArrMdl);
        jsonArrLeaf = jjp.getRemoteJSONArray(getStrUrl("leaf", mapMdl.get(saLocation[1])));
        mapLeaf = jjp.getJsonLeafMap(jsonArrLeaf);
        return mapLeaf.get(saLocation[saLocation.length - 1]);
    }

    public Map<String, String> getMapTop() {
        return mapTop;
    }

    public Map<String, String> getMapMdl() {
        return mapMdl;
    }

    public Map<String, Coord> getMapLeaf() {
        return mapLeaf;
    }
}