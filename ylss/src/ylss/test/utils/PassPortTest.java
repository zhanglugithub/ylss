package ylss.test.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import ylss.utils.PassportValidate;

public class PassPortTest {

	@Test
	public void passPortTest() throws JSONException {
		PassportValidate passPortValidate = new PassportValidate();
		String httpUrl = "http://apis.baidu.com/apistore/idservice/id";
		String httpArg = "id=420984198704207896";
		String jsonResult = passPortValidate.request(httpArg);
		JSONObject json = new JSONObject(jsonResult);
		int errNum = (int) json.get("errNum");
		String retMsg = (String) json.get("retMsg");
		JSONObject retData = json.getJSONObject("retData");
		String address = (String) retData.get("address");
		String sex = (String) retData.get("sex");
		String birthday = (String) retData.get("birthday");
		System.out.println(jsonResult);
	}
}
