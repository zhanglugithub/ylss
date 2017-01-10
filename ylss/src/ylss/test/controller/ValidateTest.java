package ylss.test.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ylss.service.app.UserService;
import ylss.test.TestHead;

@SuppressWarnings("unused")
public class ValidateTest extends TestHead {

	@Resource
	@Autowired
	UserService userServiceImpl;
	
	@Test
	public void TestGetValidate() {
		//获取验证码
		Map<String,Object> validateCode = userServiceImpl.getValidateCode("13702140378");
		System.out.println(validateCode);
	}
	
	@Test
	public void TestValidateValidateCode(){
		//验证码对比
		Map<String, Object> result = userServiceImpl.validateValidateCode("763113", "13702140378", "doctor");
		System.out.println(result);
	}
}
