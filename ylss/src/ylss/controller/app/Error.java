package ylss.controller.app;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.utils.resultMap;

@Controller
@RequestMapping("error")
public class Error {

	HashMap<String, Object> result;
	
	@ResponseBody
	@RequestMapping("/error.do")
	public Object error( String msg) {
	
		return resultMap.createResult(0, msg);
	}

	
}
