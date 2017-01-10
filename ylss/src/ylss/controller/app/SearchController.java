package ylss.controller.app;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.service.app.SearchService;

@Controller
@RequestMapping("patient")
public class SearchController {

	@Resource
	SearchService searchService;

	@ResponseBody
	@RequestMapping("/searchByIllness.do")
	public HashMap<String, Object> searchByIllness(String illness,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		HashMap<String, Object> illnesss = searchService.byIllness(illness,
				condition, latitude, longitude, pageNo, pageSize);
		return illnesss;
	}

	@ResponseBody
	@RequestMapping("/searchByDcotorType.do")
	public HashMap<String, Object> searchByDcotorType(String doctorType,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		HashMap<String, Object> doctortype = searchService.byDoctorType(
				doctorType, condition, latitude, longitude, pageNo, pageSize);
		return doctortype;
	}

	@ResponseBody
	@RequestMapping("/searchByHospital.do")
	public HashMap<String, Object> searchByHospital(String hospital,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		HashMap<String, Object> doctorByHospital = searchService.byHospital(
				hospital, condition, latitude, longitude, pageNo, pageSize);
		return doctorByHospital;
	}

	@ResponseBody
	@RequestMapping("/searchByKeShi.do")
	public HashMap<String, Object> searchByKeShi(String keShi,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		HashMap<String, Object> byKeShi = searchService.byKeShi(keShi,
				condition, latitude, longitude, pageNo, pageSize);
		return byKeShi;
	}

	@ResponseBody
	@RequestMapping("/searchByNurse.do")
	public HashMap<String, Object> searchByNurse(String nurse,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		HashMap<String, Object> byNurse = searchService.byNurse(nurse,
				condition, latitude, longitude, pageNo, pageSize);
		return byNurse;
	}

	@ResponseBody
	@RequestMapping("/detailDoctor.do")
	public HashMap<String, Object> detailDoctor(int doctorId) {
		return searchService.getDetailDoctor(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getDoctorType.do")
	public HashMap<String, Object> getDoctorType() {
		return searchService.getDoctorType();
	}

	@ResponseBody
	@RequestMapping("/getHospital.do")
	public HashMap<String, Object> getHospital() {
		return searchService.getHospital();
	}

	@ResponseBody
	@RequestMapping("/getKeShi.do")
	public HashMap<String, Object> getKeShi() {
		return searchService.getKeShi();
	}
}
