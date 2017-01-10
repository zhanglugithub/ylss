package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.dao.PatientInfoDao;
import ylss.dao.UserDao;
import ylss.model.table.Doctor;
import ylss.model.table.Feedback;
import ylss.service.app.DoctorOrderService;
import ylss.service.app.PatientBasicService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class PatientBasicServiceImplTest {

	@Resource
	UserDao userDao;

	@Resource
	DoctorOrderService doctorOrderService;

	@Autowired
	@Resource
	PatientBasicService aPatientBasicService;

	@Resource
	PatientInfoDao aPatientInfoDao;

	Doctor aDoctor;
	HashMap<String, Object> result;

	@Test
	public void testoGetEvaluat() {
		List<Map<String, Object>> evaluation = doctorOrderService
				.getEvaluation(335);
		for (Map<String, Object> map : evaluation) {

			System.out.println(map.get("patientName"));
		}
	}

	@Test
	public void testGetDoctorInfo() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetAroundDoctor() {
//		result = aPatientBasicService.getAroundDoctor(100, 37);
//		assertEquals(1, result.get("code"));
//
//		@SuppressWarnings("unchecked")
//		List<DoctorInfo> doctorInfoList = (List<DoctorInfo>) result.get("msg");
//
//		assertEquals(1, doctorInfoList.size());
	}

	@Test
	public void testSubmitFeedback() {

		Feedback aFeedback = new Feedback();

		aFeedback.setAdviceContent("不想生病啊 ");
		aFeedback.setContactWay("并不想告诉你联系方式");

		HashMap<String, Object> result = aPatientBasicService
				.submitFeedback(aFeedback);

		assertEquals(1, result.get("code"));
	}

	@Test
	public void testGetPatientInfo() {
		result = aPatientBasicService.listPatientInfo(64);
		assertEquals(1, result.get("code"));
	}

	@Test
	public void testAddPatientInfo() {

		String infoName = "我的家";
		Integer patientId = 64;
		String patientName = "景天";
		Date birthday = new Date();
		String PatientPhone = "22222";
		String address = "在木星";
		String sex = "man";
		// PatientInfo aPatientInfo = new PatientInfo(infoName, patientId,
		// patientName, birthday, PatientPhone, address,
		// sex);
		// aPatientBasicService.addPatientInfo(aPatientInfo);
	}

	@Test
	public void testUpdatePatientInfo() {
		String infoName = "新的征程";
		Integer patientId = 64;
		String patientName = "景天";
		Date birthday = new Date();
		String PatientPhone = "22222";
		String address = "在木星";
		String sex = "man";
		// PatientInfo aPatientInfo = new PatientInfo(infoName, patientId,
		// patientName, birthday, PatientPhone, address,
		// sex);
		// aPatientInfo.setInfoId(2);
		// result = aPatientBasicService.updatePatientInfo(aPatientInfo);
		assertEquals(1, result.get("code"));
	}

}
