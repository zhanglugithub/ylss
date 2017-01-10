package ylss.controller.app;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.model.constant.UtilConstant;
import ylss.model.table.Feedback;
import ylss.model.table.PatientInfo;
import ylss.service.app.MediaService;
import ylss.service.app.PatientBasicService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("patient")
public class PatientBasicController {

	private HashMap<String, Object> result;

	@Autowired
	@Resource
	PatientBasicService patientBasicService;

	@Resource
	MediaService mediaService;

	@ResponseBody
	@RequestMapping("/getMedia.do")
	public HashMap<String, Object> getMedia(int pageNum) {
		return mediaService.getMedia(pageNum);
	}

	@ResponseBody
	@RequestMapping("/getMedia2.do")
	public HashMap<String, Object> getMedia2(int pageNum) {
		return mediaService.getMedia2(pageNum);
	}

	@ResponseBody
	@RequestMapping("/submitFeedback.do")
	public HashMap<String, Object> submitFeedback(Feedback aFeedback) {
		result = patientBasicService.submitFeedback(aFeedback);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getAroundDoctor.do")
	public HashMap<String, Object> getAroundDoctor(
			@RequestParam double longitude, @RequestParam double latitude,
			String phoneNo) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) { // 在中国
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}
		// 如果是老板的手机号,就返回所有的医生
		if (phoneNo != null && phoneNo.equals(UtilConstant.bossPhoneNo)) {
			return patientBasicService.getAllDoctor();
		}
		return patientBasicService
				.getAroundDoctor(longitude, latitude, phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getDoctorStatus.do")
	public HashMap<String, Object> getDoctorStatus(int doctorId) {

		return patientBasicService.getDoctorStatus(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getAroundDoctor2.do")
	public HashMap<String, Object> getAroundDoctor2(
			@RequestParam double longitude, @RequestParam double latitude,
			String phoneNo) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) { // 在中国
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}
		// 如果是老板的手机号,就返回所有的医生
		if (phoneNo != null && phoneNo.equals(UtilConstant.bossPhoneNo)) {
			return patientBasicService.getAllDoctor();
		}
		return patientBasicService.getAroundDoctor2(longitude, latitude,
				phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getAroundDoctor3.do")
	public HashMap<String, Object> getAroundDoctor3(
			@RequestParam double longitude, @RequestParam double latitude,
			String phoneNo) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) { // 在中国
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}
		// 如果是老板的手机号,就返回所有的医生
		if (phoneNo != null && phoneNo.equals(UtilConstant.bossPhoneNo)) {
			return patientBasicService.getAllDoctor();
		}
		return patientBasicService.getAroundDoctor3(longitude, latitude,
				phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getAroundDoctor31.do")
	public HashMap<String, Object> getAroundDoctor31(
			@RequestParam double longitude, @RequestParam double latitude,
			String phoneNo, int pageNo, int pageSize, String doctorType) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) { // 在中国
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}
		// 如果是老板的手机号,就返回所有的医生
		if (phoneNo != null && phoneNo.equals(UtilConstant.bossPhoneNo)) {
			return patientBasicService.getAllDoctor();
		}
		return patientBasicService.getAroundDoctor31(longitude, latitude,
				phoneNo, pageNo, pageSize, doctorType);
	}

	@ResponseBody
	@RequestMapping("/getAroundDoctorBy.do")
	public HashMap<String, Object> getAroundDoctorBy(
			@RequestParam double longitude, @RequestParam double latitude,
			String phoneNo, String condition) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) { // 在中国
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}
		// 如果是老板的手机号,就返回所有的医生
		if (phoneNo != null && phoneNo.equals(UtilConstant.bossPhoneNo)) {
			return patientBasicService.getAllDoctor();
		}
		return patientBasicService.getAroundDoctorBy(longitude, latitude,
				phoneNo, condition);
	}

	@ResponseBody
	@RequestMapping("/getDoctorInfo.do")
	public HashMap<String, Object> getDoctorInfo(@RequestParam int doctorId) {
		return patientBasicService.getDoctorInfo(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getDoctorInfo2.do")
	public HashMap<String, Object> getDoctorInfo2(@RequestParam int doctorId) {
		return patientBasicService.getDoctorInfo2(doctorId);
	}

	@ResponseBody
	@RequestMapping("/delPatientInfo.do")
	public HashMap<String, Object> delPatientInfo(PatientInfo aPatientInfo) {
		return patientBasicService.delPatientInfo(aPatientInfo);
	}

	@ResponseBody
	@RequestMapping("/addPatientInfo.do")
	public HashMap<String, Object> addPatientInfo(PatientInfo aPatientInfo) {
		return patientBasicService.addPatientInfo(aPatientInfo);
	}

	@ResponseBody
	@RequestMapping("/addPatientInfo2.do")
	public HashMap<String, Object> addPatientInfo2(PatientInfo aPatientInfo) {
		return patientBasicService.addPatientInfo2(aPatientInfo);
	}

	@ResponseBody
	@RequestMapping("/updatePatientInfo.do")
	public HashMap<String, Object> updatePatientInfo(PatientInfo aPatientInfo) {
		return patientBasicService.updatePatientInfo(aPatientInfo);
	}

	@ResponseBody
	@RequestMapping("/listPatientInfo.do")
	public HashMap<String, Object> listPatientInfo(@RequestParam int patientId) {
		return patientBasicService.listPatientInfo(patientId);
	}

	@ResponseBody
	@RequestMapping("/listPatientInfo2.do")
	public HashMap<String, Object> listPatientInfo2(@RequestParam int patientId) {
		return patientBasicService.listPatientInfo2(patientId);
	}

}
