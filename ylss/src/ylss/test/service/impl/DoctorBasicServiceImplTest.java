package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.UtilConstant;
import ylss.model.table.Doctor;
import ylss.model.table.User;
import ylss.service.app.DoctorBasicService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class DoctorBasicServiceImplTest {

	@Autowired
	@Resource
	DoctorBasicService doctorBasicServiceImpl;

	User aUser;

	Doctor aDoctor;
	HashMap<String, Object> result;

	String phoneNo = "18822037016";

	@Test
	public void testApplyOffer() {
//		
//		ApplyOffer applyOffer=new ApplyOffer(1,1);
//		result= doctorBasicServiceImpl.applyOffer(applyOffer);
//		assertEquals(0,result.get("code"));
	}

	@Test
	public void testGetOfferList() {
		doctorBasicServiceImpl.getOfferList();
	}

	@Test
	public void testSetServiceState() {
		
		result= doctorBasicServiceImpl.setServiceState(phoneNo, true);
		assertEquals(1, result.get("code"));
		
	}

	@Test
	public void testUpdateLocation() {
		
		
		result= doctorBasicServiceImpl.updateLocation(phoneNo, 151,88);
		assertEquals(1, result.get("code"));
	
	}

	@Test
	public void testGetVerifyInfo() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSubmitVerify() {

		try {
			aDoctor = new Doctor();
			File file = new File(UtilConstant.absoluteUploadPathWindows
					+ "/headIcon/a.jpg");

			FileInputStream input = new FileInputStream(file);
			MultipartFile aFile = new MockMultipartFile("a", input);
			MultipartFile imgs[] = new MultipartFile[3];
			
		     for(int i=0;i<3;i++) {
		    	 imgs[i]=aFile;
		     }
			
			result = doctorBasicServiceImpl
					.submitVerify(aDoctor, phoneNo, imgs);
			System.out.println(result.get("msg"));
			assertEquals(1, result.get("code"));
		} catch (Exception e) {
			fail(e.toString()); // TODO
		}

		
	}

}
