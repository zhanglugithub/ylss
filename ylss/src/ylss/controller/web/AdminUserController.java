package ylss.controller.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Doctor;
import ylss.model.table.User;
import ylss.service.app.SufferersCircleService;
import ylss.service.app.UserService;
import ylss.service.web.AdminUserService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("admin")
public class AdminUserController {

	@Autowired
	@Resource
	AdminUserService aAdminUserService;

	@Resource
	UserService userService;

	@Resource
	SufferersCircleService sufferersCircleService;

	@RequestMapping("/lockUser.do")
	public Object lockUser(String phoneNo,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date lockDate,
			ModelMap model) {
		String msg = aAdminUserService.lockUser(phoneNo, lockDate);
		model.addAttribute("msg", msg);
		return "lockUser";
	}

	@ResponseBody
	@RequestMapping("/getDoctor.do")
	public HashMap<String, Object> getDoctor() {
		HashMap<String, Object> doctorGPS = userService.getDoctorGPS();
		return doctorGPS;
	}

	@RequestMapping("countDoctor.do")
	public Object countDoctor(Doctor doctor, int pageNo, int pageSize,
			ModelMap model) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> countDoctor = null;

		countDoctor = userService.countDoctor(doctor, pageNo, pageSize);

		model.addAttribute("doctorList", countDoctor.get("doctorList"));
		model.addAttribute("doctorNo", countDoctor.get("doctorNo"));
		model.addAttribute("expertNo", countDoctor.get("expertNo"));
		model.addAttribute("totalNo", countDoctor.get("totalNo"));
		model.addAttribute("msg", countDoctor.get("msg"));
		logger.info("医生：" + countDoctor);
		return "listDoctor";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/listUser.do")
	public Object listUser(User user, int pageNo, int pageSize, ModelMap model) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		HashMap<String, Object> listUser;
		try {
			listUser = userService.getUser(user, pageNo, pageSize);
			List<User> list = (List<User>) listUser.get("msg");
			model.addAttribute("listUser", listUser.get("msg"));
			model.addAttribute("totalNo", userService.getUserNo());
			model.addAttribute("yesterday", list.get(0));
			return "listUser";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/getCode.do")
	public Object getCode(String phoneNo, ModelMap model) {
		HashMap<String, Object> rsultMap = aAdminUserService.getCode(phoneNo);
		model.addAttribute("phone", rsultMap.get("phone"));
		model.addAttribute("code", rsultMap.get("code"));
		model.addAttribute("vId", rsultMap.get("vId"));
		model.addAttribute("msg", rsultMap.get("msg"));
		return "code";
	}

	@ResponseBody
	@RequestMapping(value = "/setShuo.do", headers = "content-type=multipart/*")
	public HashMap<String, Object> setShuo(@RequestParam String phoneNo,
			String address, String shuo, HttpServletRequest request,
			@RequestParam("imgs") MultipartFile[] imgs) {
		if (imgs.length > 9) {
			return resultMap.createResult(0, "图片数量过多");
		}
		return sufferersCircleService.setShuo(phoneNo, address, shuo, imgs);
	}

}
