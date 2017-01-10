package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.service.web.AdminSelfService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class AdminSelfServiceImplTest {

	@Resource
	AdminSelfService adminSelfService;
	
	@Test
	public void testLogin() {
		HashMap<String,Object> result=	adminSelfService.login("18822037016","1234567");

		assertEquals(1, result.get("code"));
	}


}
