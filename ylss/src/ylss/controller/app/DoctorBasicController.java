package ylss.controller.app;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.UtilConstant;
import ylss.model.table.Doctor;
import ylss.model.table.Offer;
import ylss.service.app.AccountService;
import ylss.service.app.DoctorBasicService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("doctor")
public class DoctorBasicController {

	HashMap<String, Object> result = new HashMap<String, Object>();

	@Autowired
	@Resource
	DoctorBasicService doctorBasicService;

	@Autowired
	AccountService accountService;

	@ResponseBody
	@RequestMapping(value = "/submitVerify.do", headers = "content-type=multipart/*")
	public HashMap<String, Object> submitVerify(@RequestParam String phoneNo,
			@RequestParam(value = "img[]") MultipartFile[] imgs,
			Doctor aDoctor, HttpServletRequest request) {

		for (MultipartFile aImg : imgs) {
			if (aImg.getSize() > UtilConstant.maxUploadSize) {

				return resultMap.createResult(0, "图片不能超过2MB");
			}
			if (aImg.isEmpty()) {

				return resultMap.createResult(0, "图片为空");
			}
		}

		result = doctorBasicService.submitVerify(aDoctor, phoneNo, imgs);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/submitVerifyIos.do", headers = "content-type=multipart/*")
	public HashMap<String, Object> submitVerify(@RequestParam String phoneNo,
			MultipartFile img0, MultipartFile img1, MultipartFile img2,
			Doctor aDoctor, HttpServletRequest request) {
		if (img0 == null || img0.isEmpty()) {
			return resultMap.createResult(0, "图片0为空");
		}
		if (img1 == null || img1.isEmpty()) {
			return resultMap.createResult(0, "图片1为空");
		}
		if (img2 == null || img2.isEmpty()) {
			return resultMap.createResult(0, "图片2为空");
		}
		result = doctorBasicService.submitVerifyIos(aDoctor, phoneNo, img0,
				img1, img2);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/submitVerify2.do", headers = "content-type=multipart/*")
	public HashMap<String, Object> submitVerify2(@RequestParam String phoneNo,
			@RequestParam(value = "img[]") MultipartFile[] imgs,
			Doctor aDoctor, String userName, HttpServletRequest request) {
		if (imgs.length < 4) {
			return resultMap.createResult(0, "请完善认证信息");
		}
		result = doctorBasicService.submitVerifyIos2(phoneNo, imgs, aDoctor,
				userName);
		return result;
	}

	@ResponseBody
	@RequestMapping("/submitVerifyIosDoctor.do")
	public HashMap<String, Object> submitVerify(@RequestParam String phoneNo,
			Doctor aDoctor) {

		return doctorBasicService.submitVerifyIosDoctor(aDoctor, phoneNo);
	}

	@ResponseBody
	@RequestMapping("/submitVerifyIosDoctor2.do")
	public HashMap<String, Object> submitVerify2(@RequestParam String phoneNo,
			Doctor aDoctor, String userName) {
		return doctorBasicService.submitVerifyIosDoctor2(aDoctor, phoneNo,
				userName);
	}

	@ResponseBody
	@RequestMapping(value = "/submitVerifyIosImg.do", headers = "content-type=multipart/*")
	public HashMap<String, Object> submitVerifyIosImage(
			@RequestParam String phoneNo, MultipartFile img, String imgName) {
		if (img == null || img.isEmpty()) {
			return resultMap.createResult(0, "图片为空");
		}
		HashMap<String, Object> result = doctorBasicService.submitVerifyIosImg(
				phoneNo, img, imgName);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getAccountOperateHistories.do")
	public HashMap<String, Object> getAccountOperateHistories(String phoneNo) {

		return accountService.getAccountOperateHistory(phoneNo);
	}

	@ResponseBody
	@RequestMapping("/withdraw.do")
	public HashMap<String, Object> withdraw(String phoneNo,
			@RequestParam double moneyAmount) {
		return accountService.withdraw(moneyAmount, phoneNo);
	}

	@ResponseBody
	@RequestMapping("/updateLocation.do")
	public HashMap<String, Object> updateLocation(String phoneNo,
			@RequestParam double longitude, @RequestParam double latitude) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) { // 在中国
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}

		return doctorBasicService.updateLocation(phoneNo, longitude, latitude);
	}

	@ResponseBody
	@RequestMapping("/setServiceState.do")
	public HashMap<String, Object> setServiceState(String phoneNo,
			@RequestParam boolean state) {
		return doctorBasicService.setServiceState(phoneNo, state);
	}

	@ResponseBody
	@RequestMapping("/getDoctorVerify.do")
	public HashMap<String, Object> getDoctorVerify(String phoneNo) {
		return doctorBasicService.getVerifyInfo(phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getOfferList.do")
	public List<Offer> getOfferList() {
		return doctorBasicService.getOfferList();
	}

}
