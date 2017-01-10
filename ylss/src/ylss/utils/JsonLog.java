package ylss.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonLog {

	private static String ADDRESS_LINUX = "/root/ylss/";
	private static String ADDRESS_WINDOWS = "D:\\ylss\\";
	private static String MYSQL_BACK_DATE = "mysql_back_date";
	private static String MYSQL_BACK_MONTH = "mysql_back_month";
	private static String MYSQL_BACK_WEEK = "mysql_back_week";

	private static Date DATE = new Date();
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd");
	private static String DATA_STR = DATE_FORMAT.format(DATE);

	public static JSONObject getDataLog(String fileName) {
		File file;
		Path path = Paths.get(ADDRESS_WINDOWS);
		if (path.toFile().exists()) {
			file = new File(ADDRESS_WINDOWS + MYSQL_BACK_DATE + "\\" + DATA_STR
					+ "\\" + fileName + "_" + DATA_STR + ".log");
		} else {
			file = new File(ADDRESS_LINUX + MYSQL_BACK_DATE + "/" + DATA_STR
					+ "/" + fileName + "_" + DATA_STR + ".log");
		}
		try {
			BufferedReader oldFile = new BufferedReader(new FileReader(file));
			char[] buf = new char[1024];
			int len = -1;
			String result = "";
			while ((len = oldFile.read(buf)) != -1) {
				result = result + new String(buf, 0, len);
			}
			oldFile.close();
			return new JSONObject(result);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			return new JSONObject(resultMap.createResult(0, false));
		}
	}

	public static HashMap<String, Object> setDataLog(HashMap<String, Object> map)
			throws UnsupportedEncodingException {
		String key = "";
		for (Entry<String, Object> mapEntry : map.entrySet()) {
			key = mapEntry.getKey();
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(map.toString()
				.getBytes("UTF-8"));
		if ((Paths.get("D:\\").toFile().exists())) {
			if (Paths.get(ADDRESS_WINDOWS + MYSQL_BACK_DATE + "\\").toFile()
					.exists()) {
				new File(ADDRESS_WINDOWS + MYSQL_BACK_DATE + "\\").mkdirs();
			}
		} else {
			if (Paths.get(ADDRESS_LINUX + MYSQL_BACK_DATE + "/").toFile()
					.exists()) {
				new File(ADDRESS_LINUX + MYSQL_BACK_DATE + "/").mkdirs();
			}
		}
		File files = null;
		Path path = Paths.get(ADDRESS_WINDOWS);
		if (path.toFile().exists()) {
			files = new File(ADDRESS_WINDOWS + MYSQL_BACK_DATE + "\\"
					+ DATA_STR + "\\" + key + "_" + DATA_STR + ".log");
		} else {
			files = new File(ADDRESS_LINUX + MYSQL_BACK_DATE + "/" + DATA_STR
					+ "/" + key + "_" + DATA_STR + ".log");
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(bais,
					"UTF-8"));
			BufferedWriter newFile = new BufferedWriter(new FileWriter(files));
			int len = -1;
			char[] buf = new char[1024];
			while ((len = br.read(buf)) != -1) {
				newFile.write(buf);
				newFile.flush();
			}
			newFile.close();
			return resultMap.createResult(1, true);
		} catch (IOException e) {
			e.printStackTrace();
			return resultMap.createResult(0, false);
		}
	}

}
