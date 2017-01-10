package ylss.test.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ylss.test.TestHead;
import ylss.utils.SendNotification;

public class SendNotificationTest extends TestHead {

	@Test
	public void sendIOSTest() {
		String deviceToken = "071f9c1522b";
		String deviceTokenP = "02189d1341f";
		String platform = "ios";
		String content = "======测试通知======医生端=======";
		String contentP = "======测试通知======患者端=======";
		String userType = "doctor";
		String userTypep = "patient";
		int userId = 0;
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("code", "1");
		SendNotification.sendNotifiction(deviceToken, platform, userId,
				content, userType, extras);
		SendNotification.sendNotifiction(deviceTokenP, platform, userId,
				contentP, userTypep, extras);
	}
}
