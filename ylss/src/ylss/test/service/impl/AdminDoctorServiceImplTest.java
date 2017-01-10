package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.model.constant.databaseConstant.DoctorType;
import ylss.model.table.Doctor;
import ylss.model.table.User;
import ylss.service.web.AdminDoctorService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class AdminDoctorServiceImplTest {
	@Autowired
	@Resource
	AdminDoctorService adminDoctorService;

	
	User aUser;

	Doctor aDoctor;
	HashMap<String, Object> result;
	

	String phoneNo = "18822037016";
	int  aDoctorId=62;
	@Test
	public void testGetDoctorVerifyInfo() {
		
		result=adminDoctorService.getDoctorVerifyInfo(phoneNo);

		aDoctor=(Doctor) result.get("msg");
	
		assertEquals(1, result.get("code"));
		assertEquals(59,aDoctor.getDoctorId());
	}

	@Test
	public void testListAllDoctorReview() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testReviewDoctorVerify() {
		aDoctor=new Doctor(aDoctorId);
		
		aDoctor.setDoctorNumber("BJ");
		aDoctor.setStarLevel(2);
		aDoctor.setDoctorType(DoctorType.doctor);
		aDoctor.setQualification(22);
		
		result= adminDoctorService.reviewDoctorVerify(aDoctor);
		
		assertEquals(1, result.get("code"));
		
	
	}
	
	@Test
	public void testRejectDoctorVerify() {
		aDoctor=new Doctor(aDoctorId);
		
		aDoctor.setDoctorNumber("BJ");
		aDoctor.setStarLevel(2);
		aDoctor.setDoctorType(DoctorType.doctor);
		aDoctor.setQualification(22);
		
		result= adminDoctorService.rejectDoctorVerify(aDoctor);
		
		if (1==(int)result.get("code")) {
						
		} else {
			fail((String)result.get("msg"));

		}


	}

}
