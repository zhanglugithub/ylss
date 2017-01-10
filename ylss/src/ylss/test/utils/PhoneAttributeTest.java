package ylss.test.utils;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import ylss.dao.UserDao;
import ylss.model.table.User;
import ylss.utils.PhoneAttribution;

public class PhoneAttributeTest {

	@Resource
	UserDao userDao;

	@Test
	public void attributeTest() {
		List<User> all = userDao.getAll("from User");
		for (User user : all) {
			String phoneNo = user.getPhoneNo();
			try {
				JSONObject resultPhone = PhoneAttribution.getRequest1(phoneNo);// 手机号
				System.out.println();
				JSONObject resultJson = resultPhone.getJSONObject("result");
				if (resultPhone.get("resultcode").equals(200)) {
					String province = (String) resultJson.get("province");
					String city = (String) resultJson.get("city");
					String address = province + city;
					if (user.getPhoneNo().equals(phoneNo)) {
						user.setAttribution(address);
						userDao.update(user);
					}
				} else {
					user.setAttribution(null);
					userDao.update(user);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
}
