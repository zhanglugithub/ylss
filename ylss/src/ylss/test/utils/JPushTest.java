package ylss.test.utils;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.service.app.PatientOrderService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class JPushTest {

	@Autowired
	@Resource
	PatientOrderService patientOrderServiceImpl;
	
	@Test
	public void testSendMSG() {
		patientOrderServiceImpl.payOrder(994);
		
	}

}
