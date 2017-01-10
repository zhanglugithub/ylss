package ylss.controller.app;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.service.app.AroundPatientService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("patient")
public class AroundPatientController {

	@Resource
	AroundPatientService aroundPatientService;

	@ResponseBody
	@RequestMapping("/getAroundPatient.do")
	public HashMap<String, Object> getAroundPatient(
			@RequestParam double longitude, @RequestParam double latitude,
			String phoneNo,int pageNo,int pageSize) {
		if (longitude > 180 | longitude < 0 | latitude > 90 | latitude < 0) {
			return resultMap.createResult(0, "180>long>0 90>la>0");
		}
		return aroundPatientService.getAroundPatient(longitude, latitude,
				phoneNo,pageNo,pageSize);
	}
}
