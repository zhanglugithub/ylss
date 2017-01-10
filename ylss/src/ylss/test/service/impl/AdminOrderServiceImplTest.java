package ylss.test.service.impl;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.service.web.AdminOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class AdminOrderServiceImplTest {

	@Resource
	AdminOrderService adminOrderService;
	
	@Test
	public void testListAllOrder() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCancelOrder() {
		adminOrderService.cancelOrder(33);
	}

}
