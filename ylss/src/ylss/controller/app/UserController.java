package ylss.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.UserDao;
import ylss.model.logic.UserInfo;
import ylss.model.logic.UserRegist;
import ylss.model.logic.UserUpload;
import ylss.model.table.AccountOperateHistory;
import ylss.service.app.AccountService;
import ylss.service.app.UserService;
import ylss.utils.resultMap;

/**
 * User的关于个人中心的功能 返回的{@code HashMap<String, Object> }code 为1 表示操作成功 0 表示操作失败
 * 
 * @author JACK
 * 
 */

@Controller
@RequestMapping("user")
public class UserController {

	private HashMap<String, Object> result;

	@Autowired
	@Resource
	UserService userService;

	@Autowired
	@Resource
	UserDao userDao;

	@Autowired
	AccountService accountService;

	/**
	 * 
	 * @param phoneNo
	 *            要获取验证码的手机号
	 * 
	 * @return {code:int,msg:String}
	 * 
	 *
	 */

	@ResponseBody
	@RequestMapping("/updateUserInfo.do")
	public HashMap<String, Object> updateUserInfo(String phoneNo,
			String userName, String sex) {// userId,userName
		HashMap<String, Object> result = userService.updateInfo(phoneNo,
				userName, sex);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getValidateCode.do")
	public HashMap<String, Object> getValidateCode( // 此处未加验证 因为短信平台会验证
			@RequestParam("phoneNo") String urlPhoneNo) {
		return userService.getValidateCode(urlPhoneNo);
	}

	/**
	 * @param phoneNo
	 * @param validateCode
	 *
	 * @return {code:0,msg:"失败"} {code:0,msg:"成功",token:"AASS```",clientId:"1" }
	 * 
	 */
	@ResponseBody
	@RequestMapping("/validateValidateCode.do")
	public HashMap<String, Object> validateValidateCode(
			@Valid UserRegist urlUser, BindingResult bindingResult) {

		result = new HashMap<String, Object>();

		if (bindingResult.hasErrors()) {
			result.put("code", 0);

			String msg = bindingResult.getFieldError().getDefaultMessage();
			result.put("msg", msg);

		} else {
			String urlValidateCode = urlUser.getValidateCode();
			String urlPhoneNo = urlUser.getPhoneNo();

			result = userService.validateValidateCode(urlValidateCode,
					urlPhoneNo, urlUser.getUserType());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/regists.do")
	public Map<String, Object> regists(@Valid UserRegist urlUser,
			BindingResult bindingResult) {

		try {
			result = new HashMap<String, Object>();

			if (userDao.getByPhoneNo(urlUser.getPhoneNo().toString()) != null) {
				return resultMap.createResult(0, "该号码已注册");
			}
			// if (bindingResult.hasErrors()) {
			// result.put("code", 0);
			//
			// String msg = bindingResult.getFieldError().getDefaultMessage();
			// result.put("msg", msg);
			//
			// }
			else {
				String urlValidateCode = urlUser.getValidateCode();
				String urlPhoneNo = urlUser.getPhoneNo();

				result = userService.validateValidateCode(urlValidateCode,
						urlPhoneNo, urlUser.getUserType());
			}

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString() + "请求错误");
		}
		return result;

	}

	/**
	 * 
	 * @param PhoneNo
	 * @param newPassword
	 * 
	 * @return {code:0,msg:"error"} {code:1,msg:"succ"}
	 */
	@ResponseBody
	@RequestMapping("/updatePasswordByToken.do")
	public HashMap<String, Object> updatePasswordByToken(
			@RequestParam("phoneNo") String urlPhoneNo,
			@RequestParam("newPassword") String urlNewPassword) {

		result = new HashMap<String, Object>();
		result.put("code", 0);

		if (urlNewPassword.length() < 6 || urlNewPassword.length() >= 15) {

			result.put("msg", "参数格式错误");

		} else {
			result = userService.updatePasswordByToken(urlPhoneNo,
					urlNewPassword);
		}
		return result;
	}

	/**
	 * 
	 * @param phoneNo
	 * @param oldPassword
	 * @param newPassword
	 * 
	 * @return {code:0,msg:"error"} {code:1,msg:"succ"}
	 */
	@ResponseBody
	@RequestMapping("/updatePasswordByOldPassword.do")
	public HashMap<String, Object> updatePasswordByOldPassword(
			@RequestParam("phoneNo") String urlPhoneNo,
			@RequestParam("oldPassword") String urlOldPassword,
			@RequestParam("newPassword") String urlNewPassword) {

		result = new HashMap<String, Object>();
		result.put("code", 0);

		if (urlOldPassword.length() < 6 || urlOldPassword.length() >= 15
				|| urlNewPassword.length() < 6 || urlNewPassword.length() >= 15) {
			result.put("msg", "参数长度错误");

		} else {
			result = userService.updatePasswordByOldPassword(urlPhoneNo,
					urlOldPassword, urlNewPassword);
		}

		return result;
	}

	/**
	 * 
	 * @param phoneNo
	 * @param password
	 * 
	 * @return {code:0,msg:"error"}
	 *         {code:1,msg:"succ",token:"aToken```",clientId:"aClientId"}
	 */
	// @ResponseBody
	// @RequestMapping("/login.do")
	// public HashMap<String, Object> login(@RequestParam("phoneNo") String
	// phoneNo,
	// @RequestParam("password") String password) {
	//
	// result = new HashMap<String, Object>();
	// result.put("code", 0);
	//
	// if (password.length() < 6 || password.length() >= 15) {
	// result.put("msg", "参数格式错误");
	// } else {
	// result = userServiceImpl.login(phoneNo, password);
	// }
	// return result;
	// }

	@ResponseBody
	@RequestMapping("/reLogin.do")
	public HashMap<String, Object> reLogin(String phoneNo, String deviceToken,
			String platform) {
		return userService.reLogin(phoneNo, deviceToken, platform);
	}

	@ResponseBody
	@RequestMapping("/login.do")
	public HashMap<String, Object> login(
			// 苹果登录
			@RequestParam("phoneNo") String phoneNo,
			@RequestParam("password") String password,
			@RequestParam("platform") String platform,
			@RequestParam("deviceToken") String deviceToken) {
		if (userDao.getByPhoneNo(phoneNo) == null) {
			return resultMap.createResult(0, "用户名或密码错误");
		}

		result = new HashMap<String, Object>();
		result.put("code", 0);

		if (password.length() < 6 || password.length() >= 15) {

			result.put("msg", "参数格式错误");
		} else {
			result = userService.login2(phoneNo, password, platform,
					deviceToken);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/login2.do")
	public HashMap<String, Object> login2(
			// 苹果登录
			@RequestParam("phoneNo") String phoneNo,
			@RequestParam("password") String password,
			@RequestParam("platform") String platform,
			@RequestParam("deviceToken") String deviceToken) {
		if (userDao.getByPhoneNo(phoneNo) == null) {
			return resultMap.createResult(0, "用户名或密码错误");
		}

//		if (userDao.getByPhoneNo(phoneNo).getDeviceToken() != null
//				&& !userDao.getByPhoneNo(phoneNo).getDeviceToken().equals("")) {
//			return resultMap.createResult(0, "登陆失败，您已登陆");
//		}
		result = new HashMap<String, Object>();
		result.put("code", 0);

		if (password.length() < 6 || password.length() >= 15) {

			result.put("msg", "参数格式错误");
		} else {
			result = userService.login2(phoneNo, password, platform,
					deviceToken);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/login3.do")
	public HashMap<String, Object> login3(
			// 苹果登录
			@RequestParam("phoneNo") String phoneNo,
			@RequestParam("password") String password,
			@RequestParam("platform") String platform, String deviceToken,
			@RequestParam("userType") String userType) {
		Logger logger = Logger.getLogger(this.getClass());

//		if (userDao.getByPhoneNo(phoneNo).getDeviceToken() != null
//				&& !userDao.getByPhoneNo(phoneNo).getDeviceToken().equals("")) {
//			return resultMap.createResult(0, "登陆失败，您已登陆");
//		}
		try {
			HashMap<String, Object> validateCode = userService
					.validateValidateCode(password, phoneNo, userType);
			if (validateCode.get("code").equals(0)) {
				return resultMap.createResult(0, validateCode.get("msg"));
			}
			if (validateCode.get("code").equals(1)) {
				HashMap<String, Object> passwordByToken = userService
						.updatePasswordByToken2(phoneNo, password);
				if (passwordByToken.get("code").equals(1)) {
					result = userService.login3(phoneNo, password, platform,
							deviceToken);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("login3" + e);
			return resultMap.createResult(0, false);
		}
	}

	/**
	 * 
	 * @param phoneNo
	 * @return {code:0,msg:"error"} {code:1,msg:"succ"}
	 */
	@ResponseBody
	@RequestMapping("/logout.do")
	public HashMap<String, Object> logout(
			@RequestParam("phoneNo") String urlPhoneNo) {

		result = new HashMap<String, Object>();
		result.put("code", 0);

		result = userService.logout(urlPhoneNo);

		return result;
	}

	/**
	 * 
	 * @param phoneNo
	 * @param newUserName
	 * @return {code:0,msg:"error"} {code:1,msg:"succ"}
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserName.do")
	public HashMap<String, Object> updateUserName(
			@RequestParam("phoneNo") String urlPhoneNo,
			@RequestParam("newUserName") String urlNewUserName) {

		result = new HashMap<String, Object>();
		result.put("code", 0);

		if (urlNewUserName.length() > 16) {
			result.put("msg", "参数过长");
		} else {
			result = userService.updateUserName(urlPhoneNo, urlNewUserName);
		}
		return result;
	}

	/**
	 * 
	 * @param phoneNo
	 * @param img
	 *            (小于2MB)
	 * @return {code:0,msg:"error"} {code:1,msg:"succ"}
	 */

	@ResponseBody
	@RequestMapping(value = "/updateImg.do")
	// , headers = "content-type=multipart/*"
	public HashMap<String, Object> updateHeadImg(@Valid UserUpload aUp,
			BindingResult bindingResult) {

		result = new HashMap<String, Object>();
		result.put("code", 0);

		if (bindingResult.hasErrors()) {

			String msg = bindingResult.getFieldError().getDefaultMessage();
			result.put("msg", msg);

		} else {

			MultipartFile urlHeadIcon = aUp.getImg();
			String urlPhoneNo = aUp.getPhoneNo();
			result = userService.updateHeadIcon(urlPhoneNo, urlHeadIcon,
					"headIcon");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/getUserInfo.do")
	public Object getUserInfo(@RequestParam("phoneNo") String urlPhoneNo) {
		UserInfo result = new UserInfo(userDao.getByPhoneNo(urlPhoneNo));
		return result;
	}

	@ResponseBody
	@RequestMapping("/getAccountOperateHistories.do")
	public List<AccountOperateHistory> getAccountOperateHistories(
			@RequestParam String phoneNo) {

		return accountService.getAccountOperateHistory2(phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getCurrentMoney.do")
	public double getCurrentMoney(String phoneNo) {
		return accountService.getCurrentMoney(phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getCurrentMoneyIos.do")
	public HashMap<String, Object> getCurrentMoneyIos(String phoneNo) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("currentMoney", accountService.getCurrentMoney(phoneNo));
		return result;
	}

	@ResponseBody
	@RequestMapping("/isUserExist.do")
	public HashMap<String, Object> isUserExist(String phoneNo) {
		return userService.isUserExist(phoneNo);
	}

}
