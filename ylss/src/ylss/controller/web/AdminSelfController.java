package ylss.controller.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ylss.model.logic.UserLogin;
import ylss.model.table.User;
import ylss.service.web.AdminSelfService;
import ylss.utils.resultMap;

@Controller
@SessionAttributes("userType")
@RequestMapping("/admin")
public class AdminSelfController {

	@Autowired
	@Resource
	AdminSelfService aAdminSelfService;

	@RequestMapping("/login.do")
	public Object login( @Valid UserLogin userLogin, BindingResult bindingResult, ModelMap model) { //String phoneNo,String password,
		if (bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			model.addAttribute("result",resultMap.createResult(0,msg));
			return "login";
		}
		
		HashMap<String,Object> result= aAdminSelfService.login(userLogin.getPhoneNo(),userLogin.getPassword());
		if ((int)result.get("code")!=0) {
			model.addAttribute("result",result);
			return "login";
		}
		model.addAttribute("userType", "service");
		return "index";
	}

	@RequestMapping("/logout.do")
	public Object logout(User aUser, ModelMap model,HttpSession session) {
		session.invalidate();
		model.remove("userType");
		return "login";
	}

}
