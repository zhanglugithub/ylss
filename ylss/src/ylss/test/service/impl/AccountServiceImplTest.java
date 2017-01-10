package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.service.app.AccountService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class AccountServiceImplTest {

	@Resource
	AccountService accountService;
	
	String phoneNo = "15102276525";
	int  aDoctorId=56;
	
	HashMap<String, Object> result;
	
	@Test
	public void testRecharge() {
		
	    result= 	accountService.recharge(10000, phoneNo);	    
	    assertEquals(1, result.get("code"));
	}

	@Test
	public void testWithdraw() {
	    result= 	accountService.withdraw(1, phoneNo);	    
	    assertEquals(1, result.get("code"));
	}

	@Test
	public void testGetAccountOperateHistory() {
	    result= 	accountService.getAccountOperateHistory(phoneNo);    
	    assertEquals(1, result.get("code"));
	}

}
