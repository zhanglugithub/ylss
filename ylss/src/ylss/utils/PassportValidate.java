package ylss.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ylss.model.constant.UtilConstant;

public class PassportValidate {
	private final static String httpUrl = "http://apis.baidu.com/apistore/idservice/id";

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public String request(String httpArg) {
		/*
		 * 请将apikey作为参数添加到header中； 当返回{"errNum":300003,"errMsg":
		 * "url is not parse"} 时，请校验是否传入apikey；
		 * 
		 */

		try {
			BufferedReader reader = null;
			String result = null;
			StringBuffer sbf = new StringBuffer();
			String httpUrls = httpUrl + "?" + "id=" + httpArg;
			URL url = new URL(httpUrls);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", UtilConstant.apikey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
			return result;
		} catch (Exception e) {
			return e.toString();
		}
	}

}
