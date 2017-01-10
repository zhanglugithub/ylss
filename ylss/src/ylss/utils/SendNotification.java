package ylss.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class SendNotification {

	public static HashMap<String, Object> sendNotifiction(String deviceToken,
			String platform, Integer userId, String content, String userType,
			Map<String, String> extras) {
		Logger logger = Logger.getLogger(SendNotification.class);
		if (deviceToken == null || deviceToken == "") {
			return resultMap.createResult(0, "医生已下线");
		}
		if (extras == null) {
			extras = new HashMap<String, String>();
			extras.put("code", "1");
		}
		HashMap<String, Object> returnInfo = new HashMap<String, Object>();
		try {
			// 推送
			if (userType.equalsIgnoreCase("doctor")) {
				if (platform.equalsIgnoreCase("android")) {
					if (deviceToken.equals("") || deviceToken.equals(null)) {
						JPush.sendMSGAndroid(((int) userId) + "",
								"请更新您的软件,\n否则无法正常使用", extras);
						return resultMap.createResult(1, "操作失败");
					}
					boolean android = JPush.sendMSGAndroid(deviceToken,
							content, extras);
					returnInfo.put("android", android);
				} else {
					if (deviceToken.length() == 72) {
						IosNotifition.getIOSNotification(deviceToken, content);
					} else {
						JPush.sendNotificationIOS(deviceToken, content, extras);
					}
					returnInfo.put("IOS", true);
				}
			} else {
				if (platform.equalsIgnoreCase("android")) {
					JPush.sendMSGPatientAndroid(deviceToken, content, extras);
				} else {
					if (deviceToken.length() == 72) {
						IosNotifition.getPatientIOSNotification(deviceToken,
								content);
					} else {
						JPush.sendNotificationPatientIOS(deviceToken, content,
								extras);
					}
				}
			}
			return resultMap.createResult(1, "下单成功");
		} catch (Exception e) {
			logger.error("sendNotifiction", e);
			return resultMap.createResult(0, false);
		}
	}
}
