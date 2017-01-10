package ylss.controller.app;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Browse;
import ylss.model.table.Comment;
import ylss.model.table.Zan;
import ylss.service.app.SufferersCircleService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("patient")
public class SufferersCircleController {

	@Resource
	SufferersCircleService sufferersCircleService;

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

	@ResponseBody
	@RequestMapping("/getShuo.do")
	public HashMap<String, Object> getShuo(int shuoId, int useId,
			String phoneNo, int pageNo) {
		HashMap<String, Object> shuo = sufferersCircleService.getShuo(shuoId,
				useId, phoneNo, pageNo);
		return shuo;
	}

	@ResponseBody
	@RequestMapping("/getShuoAll.do")
	public HashMap<String, Object> getShuoAll(int userId, int pageNo,
			int pageSize) {
		HashMap<String, Object> shuo = sufferersCircleService.getShuoAll(
				userId, pageNo, pageSize);
		return shuo;
	}

	/**
	 * 评论
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setComment.do")
	public HashMap<String, Object> setComment(Comment comment, String phoneNo) {
		HashMap<String, Object> result = sufferersCircleService.setComment(
				phoneNo, comment);
		return result;
	}

	@ResponseBody
	@RequestMapping("/setBrowse.do")
	public HashMap<String, Object> setBrowse(Browse browse, String phoneNo) {
		HashMap<String, Object> result = sufferersCircleService.setBrowse(
				browse, phoneNo);
		return result;
	}

	@ResponseBody
	@RequestMapping("/setZan.do")
	public HashMap<String, Object> setZan(Zan zan, String phoneNo) {
		HashMap<String, Object> result = sufferersCircleService.setZan(zan,
				phoneNo);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getZan.do")
	public HashMap<String, Object> getZan(String phoneNo, int scId) {
		HashMap<String, Object> resultZan = sufferersCircleService.getZan(
				phoneNo, scId);
		return resultZan;
	}

	@ResponseBody
	@RequestMapping("/getBrowse.do")
	public HashMap<String, Object> getBrowse(int shuoId, int pageNo,
			int pageSize) {
		HashMap<String, Object> browseMap = sufferersCircleService.getBrowse(
				shuoId, pageNo, pageSize);
		return browseMap;
	}
}
