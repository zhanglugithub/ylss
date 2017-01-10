package ylss.test.service.impl;




import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.model.table.Offer;
import ylss.service.web.AdminOfferService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class AdminOfferServiceImplTest {

	@Resource
	AdminOfferService adminOfferService;
	
	@Test
	public void testAddOffer() {
		 Offer aOffer=new Offer();
		adminOfferService.addOffer(aOffer);		
	}
	
//	@Test 
//	public void testGetApplyOffer() {	
//		List<ApplyOffer> applyOfferList=adminOfferService.listApplyOffer(1, 10);
//		System.out.println(applyOfferList);
//	}

}
