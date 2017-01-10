package ylss.controller.app;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.utils.RedisUtil;

@Controller
@RequestMapping("user")
public class JedisController {

	@ResponseBody
	@RequestMapping("/updateLat.do")
	public HashMap<String, Object> updateLat(String phoneNo, Double latitude,
			Double longitude) {
		HashMap<String, Object> result = RedisUtil.setLat(phoneNo, latitude,
				longitude);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getLat.do")
	public HashMap<String, Object> getLat(String phoneNo) {
		HashMap<String, Object> lat = RedisUtil.getLat(phoneNo);
		return lat;
	}
}
