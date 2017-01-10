package ylss.test.service.impl;


import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import ylss.service.web.AdminUserService;
import ylss.test.TestHead;

public class AdminUserServiceImplTest extends TestHead {

	@Resource
	AdminUserService adminUserService;
	
	@Test
	public void testLockUser() {
		String phoneNo="";
		Date lockDate= new Date(0L);
		adminUserService.lockUser(phoneNo, lockDate);
	}

}
