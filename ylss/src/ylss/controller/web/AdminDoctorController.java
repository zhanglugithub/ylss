package ylss.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.CategoryT;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorTypeT;
import ylss.model.table.HospitalT;
import ylss.model.table.KeShiT;
import ylss.model.table.User;
import ylss.service.app.UserService;
import ylss.service.web.AdminDoctorService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("admin")
public class AdminDoctorController {

	@Autowired
	@Resource
	AdminDoctorService adminDoctorService;

	@Resource
	UserService userService;

	User aUser;

	Doctor aDoctor;
	HashMap<String, Object> result;

	@RequestMapping("/listDoctorVerify.do")
	public Object listDoctorVerify(int pageNo, int pageSize, ModelMap model) {
		List<Doctor> doctorVerifyList = adminDoctorService.listAllDoctorReview(
				pageNo, pageSize);
		model.addAttribute("doctorVerifyList", doctorVerifyList);
		return "listDoctorVerify";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/listDoctorVerified.do")
	public Object listDoctorVerified(int pageNo, int pageSize, ModelMap model) {
		HashMap<String, Object> VerifedList = adminDoctorService
				.listAllDoctorReviewed(pageNo, pageSize);
		List<Doctor> doctors = (List<Doctor>) VerifedList.get("results");
		model.addAttribute("doctorVerifiedList", VerifedList.get("result"));
		model.addAttribute("totalNo", doctors.size());
		return "listDoctorVerified";
	}

	@RequestMapping("/listDoctor.do")
	public Object listDoctor(int pageNo, int pageSize, Model model) {
		List<Doctor> listAllDoctor = adminDoctorService.listAllDoctor(pageNo,
				pageSize);
		model.addAttribute("listAllDoctor", listAllDoctor);
		return "listDoctorPassport";
	}

	@RequestMapping("/validatePassport.do")
	public Object validatePassport(Integer doctorId, Model model) {
		HashMap<String, Object> passport = adminDoctorService
				.validatePassport(doctorId);
		model.addAttribute("code", passport.get("code"));
		model.addAttribute("passport", passport.get("msg"));
		return "getPassport";
	}

	@RequestMapping("/getDoctorVerify.do")
	public Object getDoctorVerify(@RequestParam String phoneNo, ModelMap model) {
		Logger logger = Logger.getLogger(this.getClass());

		result = adminDoctorService.getDoctorVerifyInfo(phoneNo);

		model.addAttribute("doctor", new Doctor());

		model.addAttribute("code", result.get("code"));
		model.addAttribute("doctorInfo", result.get("msg"));
		logger.info(result.values());
		logger.info(model);
		return "getDoctorVerify";
	}

	@RequestMapping("/getDoctorVerified.do")
	public Object getDoctorVerified(@RequestParam String phoneNo, ModelMap model) {

		result = adminDoctorService.getDoctorVerifyInfo(phoneNo);

		model.addAttribute("doctor", new Doctor());

		model.addAttribute("code", result.get("code"));
		model.addAttribute("doctorInfo", result.get("msg"));
		return "getDoctorVerified";
	}

	@RequestMapping("/getDoctorInfo.do")
	public Object getDoctorInfo(@RequestParam int doctorId, ModelMap model) {

		HashMap<String, Object> result = adminDoctorService
				.getDoctorInfo(doctorId);
		model.addAttribute("doctorInfo", result.get("doctorInfo"));
		model.addAttribute("haveIcon", result.get("haveIcon"));
		return "getDoctorInfo";
	}

	@RequestMapping("/reviewVerify.do")
	public Object reviewVerify(Doctor aDoctor, String userName, ModelMap model,
			boolean reviewSuccess) {

		HashMap<String, Object> result;
		if (reviewSuccess) {
			result = adminDoctorService.updateUser(userName, aDoctor);
			result = adminDoctorService.reviewDoctorVerify(aDoctor);
		} else {
			result = adminDoctorService.rejectDoctorVerify(aDoctor);
		}
		model.addAttribute("msg", result.get("msg"));
		return "listDoctorVerify";
	}

	@RequestMapping("/revalidateDoctorVerified.do")
	public Object removeDoctorVerified(Doctor doctor, String doctorName,
			Model model) {
		Map<String, Object> result = adminDoctorService.removeVerified(doctor,
				doctorName);
		model.addAttribute("msg", result.get("msg"));
		return "listDoctorVerified";
	}

	@RequestMapping("/toUpLoad.do")
	public Object toUpLoad(Doctor doctor, Model model) {
		Map<String, Object> result = adminDoctorService.upload(doctor);
		model.addAttribute("doctor", result.get("msg"));
		return "upload";
	}

	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping(value = "/submitVerifyImg.do", headers = "content-type=multipart/*")
	public HashMap<String, Object> submitVerifyImg(Doctor aDoctor,
			MultipartFile img0, MultipartFile img1, MultipartFile img2,
			MultipartFile img, HttpServletRequest request) {

		try {
			if (img != null || !(img.isEmpty())) {
				result = userService.updateHeadIcon(userService
						.getUser(aDoctor).getPhoneNo(), img, "headIcon");
			}
			if (img0 == null || img0.isEmpty()) {
				return resultMap.createResult(0, "图片0为空");
			}
			if (img1 == null || img1.isEmpty()) {
				return resultMap.createResult(0, "图片1为空");
			}
			if (img2 == null || img2.isEmpty()) {
				return resultMap.createResult(0, "图片2为空");
			}
			result = adminDoctorService.submitVerifyImg(aDoctor, img0, img1,
					img2);

			return result;
		} catch (Exception e) {
			return resultMap.createResult(0, e);
		}
	}

	@RequestMapping("/getHospital.do")
	public Object getHospital(int pageNo, int pageSize, ModelMap model) {
		HashMap<String, Object> hospitals = adminDoctorService.getHospital(
				pageNo, pageSize);
		model.addAttribute("hospitals", hospitals);
		return "search/listHospital";
	}

	@RequestMapping("/detailHospital.do")
	public Object detailHospital(HospitalT urlhospital, ModelMap model) {
		HashMap<String, Object> hospitals = adminDoctorService
				.detailHospital(urlhospital);
		if (hospitals.get("msg") == null) {
			model.addAttribute("hospitals", hospitals);
			return "search/HospitalInfo";
		} else {
			return "redirect:getHospital.do?pageNo=1&pageSize=10";
		}
	}

	@RequestMapping("/setHospital.do")
	public Object setHospital(HospitalT hosptal, ModelMap model) {
		adminDoctorService.setHospital(hosptal);
		return "redirect:getHospital.do?pageNo=1&pageSize=10";
	}

	@RequestMapping("/getDoctorType.do")
	public Object getDoctorType(int pageNo, int pageSize, ModelMap model) {
		HashMap<String, Object> doctorType = adminDoctorService.getDoctorType(
				pageNo, pageSize);
		model.addAttribute("doctorType", doctorType);
		return "search/listDoctorType";
	}

	@RequestMapping("/detailDoctorType.do")
	public Object detailDoctorType(DoctorTypeT doctorType, ModelMap model) {
		HashMap<String, Object> detailType = adminDoctorService
				.detailDoctorType(doctorType);
		if (detailType.get("msg") == null) {
			model.addAttribute("detailType", detailType);
			return "search/doctorTypeInfo";
		} else {
			return "redirect:getDoctorType.do?pageNo=1&pageSize=10";
		}
	}

	@RequestMapping("/setDoctorType.do")
	public Object setDoctorType(DoctorTypeT doctorType, ModelMap model) {
		HashMap<String, Object> setDoctorType = adminDoctorService
				.setDoctorType(doctorType);
		return "redirect:getDoctorType.do?pageNo=1&pageSize=10";
	}

	@RequestMapping("/getKeShi.do")
	public Object getKeShi(int pageNo, int pageSize, ModelMap model) {
		HashMap<String, Object> keShi = adminDoctorService.getKeShi(pageNo,
				pageSize);
		model.addAttribute("keShi", keShi);
		return "search/listKeShi";
	}

	@RequestMapping("/detailKeShi.do")
	public Object detailKeShi(KeShiT keShi, ModelMap model) {
		HashMap<String, Object> deKeShi = adminDoctorService.detailKeShi(keShi);
		if (deKeShi.get("msg") == null) {
			model.addAttribute("deKeShi", deKeShi);
			return "search/keShiInfo";
		} else {
			return "redirect:getKeShi.do?pageNo=1&pageSize=10";
		}
	}

	@RequestMapping("/setKeShi.do")
	public Object setKeShi(KeShiT keShi, ModelMap model) {
		HashMap<String, Object> setKeShi = adminDoctorService.setKeShi(keShi);
		model.addAttribute(setKeShi);
		return "redirect:getKeShi.do?pageNo=1&pageSize=10";
	}

	@RequestMapping("/getCategory.do")
	public Object getCategory(int pageNo, int pageSize, ModelMap model) {
		HashMap<String, Object> category = adminDoctorService.getCategory(
				pageNo, pageSize);
		model.addAttribute("categorys", category);
		return "search/listCategory";
	}

	@RequestMapping("/detailCategory.do")
	public Object getDetailCategory(CategoryT category, ModelMap model) {
		HashMap<String, Object> categoryMap = adminDoctorService
				.getDetailCategory(category);
		if (categoryMap.get("msg") == null) {
			model.addAttribute("category", categoryMap);
			return "search/categoryInfo";
		} else {
			return "redirect:getCategory.do?pageNo=1&pageSize=10";
		}
	}

	@RequestMapping("/setCategory.do")
	public Object setCategory(CategoryT category, ModelMap model) {
		HashMap<String, Object> cat = adminDoctorService.setCategory(category);
		if (cat.get("msg") == null) {
			model.addAttribute("doctors", cat);
			return "search/categoryInfo";
		} else {
			return "redirect:getCategory.do?pageNo=1&pageSize=10";
		}
	}
}
