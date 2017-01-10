package ylss.test.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import ylss.service.app.impl.UserServiceImpl;
import ylss.test.TestHead;

public class Testlogin extends TestHead{

	@Autowired
	@Resource
	private UserServiceImpl userServiceImpl;
	//
	// @Test
	// public void loginTest(String urlPhoneNo, String urlPassword,String
	// platform,String deviceToken){
	// HashMap<String,Object> login = userServiceImpl.login(urlPhoneNo,
	// urlPassword,platform,deviceToken);
	// Map<String, Object> map = new HashMap<String, Object>();
	//
	// }
}
