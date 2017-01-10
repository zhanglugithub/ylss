package ylss.controller.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Doctor;
import ylss.model.table.Feedback;
import ylss.model.table.Media;
import ylss.model.table.User;
import ylss.service.app.MediaService;
import ylss.service.web.AdminSystemService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("admin")
public class AdminSystemController {

	@Autowired
	@Resource
	AdminSystemService adminSystemService;

	@Resource
	MediaService mediaService;
	User aUser;

	Doctor aDoctor;
	HashMap<String, Object> result;

	@RequestMapping("/delMedia.do")
	public Object delMedia(Media media, ModelMap model) {
		HashMap<String, Object> resultMap = mediaService.delMedia(media);
		model.addAttribute(resultMap);
		return "/admin/listMedia.do?pageNo=1&pageSize=10";
	}

	@RequestMapping("/setMedia.do")
	public Object setMedia(@Valid Media media, BindingResult bindingResult,
			ModelMap model) {
		try {
			if (bindingResult.hasErrors()) {
				result.put("code", 0);

				String msg = bindingResult.getFieldError().getDefaultMessage();
				result.put("msg", msg);
			} else {
				mediaService.setMedia(media);
				return "listMedia";
			}
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
		return resultMap.createResult(0, "setMedia错误");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/listMedia.do")
	public Object listMedia(int pageNo, int pageSize, ModelMap model) {
		try {
			HashMap<String, Object> listMedia = mediaService.listMedia(pageNo,
					pageSize);
			List<Media> medias = (List<Media>) listMedia.get("results");
			model.addAttribute("listMedia", listMedia.get("result"));
			model.addAttribute("pageNo", medias.size());
			return "listMedia";
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, "setMedia错误");
		}
	}

	@RequestMapping("/updateMedia.do")
	public Object updateMedia(Media media, boolean review, ModelMap model) {
		if (review) {
			HashMap<String, Object> resultMedia = mediaService
					.toUpdateMedia(media);
			model.addAttribute("media", resultMedia.get("msg"));
			return "mediaDetail";
		} else {
			mediaService.updateMedia(media);
			return "redirect:listMedia.do?pageNo=1&pageSize=10";
		}
	}

	@RequestMapping("/listFeedback.do")
	public Object listFeedback(ModelMap model, int pageNo, int pageSize) {

		List<Feedback> feedbackList = adminSystemService.listFeedback(pageNo,
				pageSize);
		Long totalNo = adminSystemService.getFeedBackTotalNo();
		model.addAttribute("totalNo", totalNo);
		model.addAttribute("feedbackList", feedbackList);
		return "listFeedback";
	}

	@RequestMapping("/alterLength.do")
	public Object alterLength(ModelMap model, @RequestParam double length) {

		if (length < 5 | length > 50) {
			result = resultMap.createResult(0, "距离应该大于5并且小于50");
		} else {
			result = adminSystemService.alterLengthUserSee(length);
		}
		model.addAttribute("result", result);
		model.addAttribute("msg", result.get("msg"));
		return "alterLength";
	}

	@RequestMapping("/alterPercentDoctorGet.do")
	public Object alterPercentDoctorGet(ModelMap model,
			@RequestParam double percent) {
		if (percent <= 0 | percent >= 1) {
			result = resultMap.createResult(0, "医生得到的比例应该大于0 小于1 ");
		} else {
			result = adminSystemService.alterDoctorGetPercent(percent);
		}

		model.addAttribute("result", result);
		model.addAttribute("msg", result.get("msg"));
		return "alterPercentDoctorGet";
	}

	@RequestMapping(value = "/alterStartPage.do", headers = "content-type=multipart/*")
	public Object alterStartPage(ModelMap model,
			@RequestParam MultipartFile startPageImg) {
		result = adminSystemService.alterStartPage(startPageImg);
		model.addAttribute("msg", result.get("msg"));
		return "alterStartPage";
	}

}
