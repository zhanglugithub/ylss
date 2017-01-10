package ylss.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.ManageConstant;
import ylss.utils.FileTool;

@Controller
@RequestMapping("admin")
public class Test {

	@ResponseBody
	@RequestMapping("/getPercentDoctorGet.do")
	public Object getPercentDoctorGet() {

		return ManageConstant.DoctorGetPercent;
	}
	@ResponseBody
	@RequestMapping("/getLength.do")
	public Object getLength() {

		return ManageConstant.PatientCanSee;

	}

	@ResponseBody
	@RequestMapping("/testChange.do")
	public Object testChange(@RequestParam("percent") double percent) {

		ManageConstant.DoctorGetPercent = percent;
		return ManageConstant.DoctorGetPercent;

	}

	@ResponseBody
	@RequestMapping("/testUploadFile.do")
	public Object testUploadFile(@RequestParam("file") MultipartFile file) {

		return FileTool.uploadImg(file, "a", "testFileUpload");
	}

}
