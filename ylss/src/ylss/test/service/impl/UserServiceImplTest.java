package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.UserDao;
import ylss.model.constant.UtilConstant;
import ylss.model.constant.databaseConstant.UserStatus;
import ylss.model.table.User;
import ylss.service.app.UserService;
import ylss.test.TestHead;
import ylss.utils.MD5;


public class UserServiceImplTest  extends TestHead{

	@Autowired
	@Resource
	UserService userServiceImpl;

	User aUser;

	@Autowired
	@Resource
	UserDao aUserDaoImpl;

	String urlPhoneNo = "18638303792";
	String validateCode;
	String urlPassword = "1234567";
	String platform = "android";
	String deviceToken = "fgq33ewwg";
	HashMap<String, Object> result;

	@Test
	public void testGetValidateValidateCode() {
		Map<String,Object> code = userServiceImpl.validateValidateCode("339513", "18939272070", "patient");
		System.out.println(code);
	}
	
	@Test
	public void testGetValidateCode() {

		result = userServiceImpl.getValidateCode(urlPhoneNo);
		
		assertEquals(1, result.get("code"));
		
		aUser=aUserDaoImpl.getByPhoneNo(urlPhoneNo);
		
		
		assertNotNull(aUser);
		
		assertNotNull(aUser.getValidateCode());
		
	}

	@Test
	public void testUpdatePasswordByToken() {

		result = userServiceImpl.updatePasswordByToken(urlPhoneNo, urlPassword);

		assertEquals(1, result.get("code"));
		
		assertEquals(aUserDaoImpl.getByPhoneNo(urlPhoneNo).getPassword(),
				MD5.parseMD5(urlPassword));
	}


	@Test
	public void testLogin() {

		result = userServiceImpl.login2(urlPhoneNo, urlPassword,platform,deviceToken);
		assertEquals(1, result.get("code"));
		
		aUser=aUserDaoImpl.getByPhoneNo(urlPhoneNo);
		
		assertEquals(UserStatus.online, aUser.getStatus());
	}

	@Test
	public void testUpdatePasswordByOldPassword() {

		String newPassword="1234567";
		
		result = userServiceImpl.updatePasswordByOldPassword(urlPhoneNo,
				urlPassword, newPassword);
		assertEquals(1, result.get("code"));
		
		aUser=aUserDaoImpl.getByPhoneNo(urlPhoneNo);
		
		assertEquals(MD5.parseMD5(newPassword), aUser.getPassword());
		
	}

	@Test
	public void testUpdateUserName() {

		String newUserName ="李四";
		result = userServiceImpl.updateUserName(urlPhoneNo,newUserName);
		
		assertEquals(1, result.get("code"));
		
		
		aUser=aUserDaoImpl.getByPhoneNo(urlPhoneNo);
		
		assertEquals(newUserName, aUser.getUserName());
		
	}

	@Test
	public void testUpdateHeadIcon() {
		try {

			File file = new File(UtilConstant.absoluteUploadPathWindows+"/headIcon/a");
			
			FileInputStream input = new FileInputStream(file);
			MultipartFile aFile = new MockMultipartFile("a", input);

			result = userServiceImpl.updateHeadIcon(urlPhoneNo, aFile, "headIcon");			
			@SuppressWarnings("unused")
			File createFile = new File(UtilConstant.absoluteUploadPathWindows+"/headIcon/"+urlPhoneNo);
		
		assertEquals(1, result.get("code"));
		} catch (Exception e) {
			System.out.println(e);
			
			fail("捕获到异常"+e);
		}

	}

	@Test
	public void testLogout() {
		
		result = userServiceImpl.logout(urlPhoneNo);
		assertEquals(1, result.get("code"));

		aUser=aUserDaoImpl.getByPhoneNo(urlPhoneNo);
		assertEquals(UserStatus.able,aUser.getStatus());
	}
	
	


}
