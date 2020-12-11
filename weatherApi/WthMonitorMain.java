package api_using;

import java.text.SimpleDateFormat;

public class WthMonitorMain {
    public static void main(String[] args) {
        String[] location = { "충청남도", "천안시서북구", "부성동" };
        Coord coLocationCode = null;
        WeatherSet weather = null;
        LocationCodeFetcher lcf = new LocationCodeFetcher();
        WeatherFetcher wf = new WeatherFetcher();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 정각");
        
        coLocationCode = lcf.fetchLocationCode(location);
        System.out.println("location code : " + coLocationCode.getSx() + ", " + coLocationCode.getSy());
        weather = wf.fetchWeather(coLocationCode.getSx(), coLocationCode.getSy());
        System.out.println("발표시각 : " + sdf.format(weather.getBaseDate()));
        System.out.println(sdf.format(weather.getFcstDate()) + "의 강수확률은 " + weather.getPop() + "%, 하늘은 " + weather.getSky() + "입니다");
    }
}
