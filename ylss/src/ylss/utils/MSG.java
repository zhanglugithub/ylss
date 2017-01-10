package ylss.utils;

import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ylss.model.constant.UtilConstant;

public class MSG {

	private static String mesUser = UtilConstant.smsUser;
	private static String mesPassword = UtilConstant.smsPassword;

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	public static HashMap<String, Object> sendToUser(String phoneNo, String content) {

		HashMap<String, Object> result = new HashMap<String, Object>();

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = {

				new NameValuePair("account", mesUser), new NameValuePair("password", mesPassword), //
				new NameValuePair("mobile", phoneNo), new NameValuePair("content", content), };

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			System.out.println("SubmitResultSubmitResult====" + SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);

			Element root = doc.getRootElement();
			int code = Integer.parseInt(root.elementText("code"));

			if (code == 2) {
				code = 1;
			} else {
				code = 0;
			}

			String msg = root.elementText("msg"); // getMessage

			result.put("code", code);
			result.put("msg", msg);

		} catch (Exception e) {
			result.put("code", 0);
			result.put("msg", "获取失败");

		}
		return result;

	}
}