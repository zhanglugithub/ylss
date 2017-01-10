package ylss.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import ylss.utils.MSG;
import ylss.utils.RandomCode;

public class MSGTest {

	@Test
	public void testSendToUser() {
		String  phoneNo="15102276526";
		String  content= "您的验证码是：" + RandomCode.getRandomCode(6) + "。请不要把验证码泄露给其他人。";
		HashMap<String, Object>   sendSmsResult  =  MSG.sendToUser(phoneNo,content);
		assertEquals("提交成功", sendSmsResult.get("msg"));
		
		phoneNo="1510227652";
		content= "您的验证码是：" + RandomCode.getRandomCode(6) + "。请不要把验证码泄露给其他人。";
	   sendSmsResult  =  MSG.sendToUser(phoneNo,content);
		assertEquals("手机格式不正确，格式为11位的数字", sendSmsResult.get("msg"));
	}

}
