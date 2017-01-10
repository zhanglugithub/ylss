package ylss.utils;

import java.util.Map;

import org.apache.commons.logging.Log;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import ylss.model.constant.UtilConstant;

public class JPush {
	// 医生端私钥
	private static final String appKey = UtilConstant.jpushAppKey;
	private static final String masterSercret = UtilConstant.jpushMasterSercret;
	private static final String AppKey_ios = UtilConstant.jpush_appKey_ios;
	private static final String master_sercret_ios = UtilConstant.jpush_master_sercret_ios;

	// 患者端私钥
	private static final String appKey1 = UtilConstant.jpushAppKey1;
	private static final String masterSercret1 = UtilConstant.jpushMasterSercret1;

	/**
	 * android医生端推送
	 * 
	 * @param alias
	 *            别名
	 * @param content
	 *            推送内容
	 * @return 推送成功 true 失败false
	 */
	public static boolean sendMSGAndroid(String alias, String content,
			Map<String, String> extras) {
		JPushClient jPushClient = new JPushClient(masterSercret, appKey);
		PushPayload aPayload = PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.alias(alias.toString()))
				.setNotification(Notification.android(content, null, extras))
				.build();
		try {
			jPushClient.sendPush(aPayload);
			return true;
		} catch (APIConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * android患者端推送
	 * 
	 * @param alias
	 *            别名
	 * @param content
	 *            推送内容
	 * @return 推送成功 true 失败false
	 */
	public static boolean sendMSGPatientAndroid(String alias, String content,
			Map<String, String> extras) {
		JPushClient aJPushClient = new JPushClient(masterSercret1, appKey1);
		PushPayload aPayload = PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.android(content, null, extras))
				.build();
		try {
			aJPushClient.sendPush(aPayload);
			return true;
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static Log log;

	public static String sendNotificationIOS(String alias, String content,
			Map<String, String> extras) {
		JPushClient jPushClient = new JPushClient(master_sercret_ios,
				AppKey_ios);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.registrationId(alias))
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification.newBuilder()
												.addExtras(extras)
												.setAlert(content).setBadge(1)
												.setSound("sound.caf").build())
								.build())
				.setMessage(Message.content(content))
				.setOptions(
						Options.newBuilder().setApnsProduction(true).build())
				.build();

		try {
			PushResult sendPush = jPushClient.sendPush(payload);
			return sendPush.toString();
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return "null";
	}

	public static String sendNotificationPatientIOS(String alias,
			String content, Map<String, String> extras) {
		JPushClient jPushClient = new JPushClient(masterSercret1, appKey1);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.registrationId(alias))
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification.newBuilder()
												.addExtras(extras)
												.setAlert(content).setBadge(1)
												.setSound("sound.caf").build())
								.build())
				.setMessage(Message.content(content))
				.setOptions(
						Options.newBuilder().setApnsProduction(true).build())
				.build();

		try {
			PushResult sendPush = jPushClient.sendPush(payload);
			return sendPush.toString();
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return "null";
	}
}
