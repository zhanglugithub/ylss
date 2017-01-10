package ylss.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class IosNotifition {

	private static final String certificate_Win = "D:/aps_production.p12";
	private static final String certificate_Win_User = "D:/aps_production_user.p12";
	private static final String certificate_Linux = "/root/aps_production.p12";
	private static final String certificate_Linux_User = "/root/aps_production_user.p12";
	private static final String cer_password = "lian?+1017";
	private static final String cer_password_user = "lian?+1017";

	public static void getIOSNotification(String deviceToken, String content) {
		try {
			// 从客户端获取的deviceToken
			// 定义消息模式
			System.out.println(deviceToken);
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(content);
			payLoad.addBadge(1);// 消息推送标记数，小红圈中显示的数字。
			payLoad.addSound("default");
			// 注册deviceToken
			PushNotificationManager pushManager = PushNotificationManager
					.getInstance();
			pushManager.addDevice("iPhoneDoctor", deviceToken);
			// 连接APNS
			// String host = "gateway.sandbox.push.apple.com";
			String host = "gateway.push.apple.com";
			int port = 2195;
			String certificatePath = "";
			Path path = Paths.get(certificate_Linux);
			if (path.toFile().exists()) {
				certificatePath = certificate_Linux;
			} else {
				certificatePath = certificate_Win;
			}
			// String certificatePath = "/root/aps_developer.p12";//
			// 前面生成的用于JAVA后台连接APNS服务的*.p12文件位置
			// String certificatePath = "D:/aps_production.p12";//
			// 前面生成的用于JAVA后台连接APNS服务的*.p12文件位置
			// String certificatePassword = "lian?-1017";// p12文件密码。
			pushManager.initializeConnection(host, port, certificatePath,
					cer_password, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
			// 发送推送
			Device client = pushManager.getDevice("iPhoneDoctor");
			pushManager.setRetryAttempts(100);
			pushManager.sendNotification(client, payLoad);
			System.out.println("推送消息: " + client.getToken() + "\n"
					+ payLoad.toString() + " ");
			// 停止连接APNS
			pushManager.stopConnection();
			// 删除deviceToken
			pushManager.removeDevice("iPhoneDoctor");
			System.out.println("Push End");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getPatientIOSNotification(String deviceToken,
			String content) {
		try {
			System.out.println(deviceToken);
			// 从客户端获取的deviceToken
			// 定义消息模式
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(content);
			payLoad.addBadge(1);// 消息推送标记数，小红圈中显示的数字。
			payLoad.addSound("default");
			// 注册deviceToken
			PushNotificationManager pushManager = PushNotificationManager
					.getInstance();
			pushManager.addDevice("iPhonePatient", deviceToken);
			// 连接APNS
			// String host = "gateway.sandbox.push.apple.com";
			String host = "gateway.push.apple.com";
			int port = 2195;
			String certificatePath = "";
			Path path = Paths.get(certificate_Linux_User);
			if (path.toFile().exists()) {
				certificatePath = certificate_Linux_User;
			} else {
				certificatePath = certificate_Win_User;
			}
			// String certificatePath = "/root/aps_developer_user.p12";
			// 前面生成的用于JAVA后台连接APNS服务的*.p12文件位置
			// String certificatePath = "D:/aps_production_user.p12";//
			pushManager
					.initializeConnection(host, port, certificatePath,
							cer_password_user,
							SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
			// 发送推送
			Device client = pushManager.getDevice("iPhonePatient");
			pushManager.setRetryAttempts(100);
			pushManager.sendNotification(client, payLoad);
			System.out.println("推送消息: " + client.getToken() + "\n"
					+ payLoad.toString() + " ");
			// 停止连接APNS
			pushManager.stopConnection();
			// 删除deviceToken
			pushManager.removeDevice("iPhonePatient");
			System.out.println("Push End");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// String str =
		// "0ec61de2 d7d6ced0 1203c34b 75c312e1 75fb7079 7b057482 dc5ba610 b4cf443c";
//		String str = "aec40813 41b50b89 fc5b7fd6 38f703e9 3270a202 ec80a25e 6344c070 5a534e83";
//		String str = "0ec61de2 d7d6ced0 1203c34b 75c312e1 75fb7079 7b057482 dc5ba610 b4cf443c";
		String str = "40d3818f d382e6b0 e9a91c9e ab2da9e8 f63f9246 9765b9f0 550e72ca 03195da0";
		// String str =
		// "c441d3da d62e41ef 12dc258d 6fb68a52 31862dc1 b825d09e 75c55e2f aa772ba2";
		getIOSNotification(str, "接单了！！！！！！<医生端>！！！！！！！！！！！！！！！！！！！！！");
		getPatientIOSNotification(str, "接单成功!!!!!!!!!< 用户端 >!!!!");
	}
}
