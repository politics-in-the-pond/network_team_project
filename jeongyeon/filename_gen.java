package my_package;

import java.text.SimpleDateFormat;

public class filename_gen {
	int count = 0;
	String app_name = "cryptalk"; // 바꿔도 돼요...
	String before_time = "a";

	public String get_filename() {
		String result = null;
		SimpleDateFormat base_date_format = new SimpleDateFormat("yyyyMMdd_HHMM");
		String base_time = base_date_format.format(System.currentTimeMillis());
		if (!base_time.equals(before_time)) {
			count = 0;
		} else {
			count++;
		}
		before_time = base_time;
		result = app_name + "_" + base_time + "_" + Integer.toString(count);
		return result;
	}
}
