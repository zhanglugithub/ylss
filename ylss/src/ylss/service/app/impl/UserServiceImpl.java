/**
 * 
 */
package ylss.service.app.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import ylss.dao.AccountDao;
import ylss.dao.DoctorDao;
import ylss.dao.DoctorGPSDao;
import ylss.dao.DoctorRealtimeInfoDao;
import ylss.dao.LoginLogTDao;
import ylss.dao.PatientInfoDao;
import ylss.dao.UserDao;
import ylss.dao.UserInfosDao;
import ylss.dao.ValidateCodeDao;
import ylss.model.constant.UtilConstant;
import ylss.model.constant.databaseConstant.DoctorStatus;
import ylss.model.constant.databaseConstant.UserStatus;
import ylss.model.constant.databaseConstant.UserType;
import ylss.model.logic.DoctorInfo;
import ylss.model.table.Account;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorRealtimeInfo;
import ylss.model.table.LoginLogT;
import ylss.model.table.User;
import ylss.model.table.UserInfos;
import ylss.model.table.ValidateCode;
import ylss.service.app.UserService;
import ylss.utils.FileTool;
import ylss.utils.MD5;
import ylss.utils.MSG;
import ylss.utils.PhoneAttribution;
import ylss.utils.RandomCode;
import ylss.utils.RedisUtil;
import ylss.utils.SendNotification;
import ylss.utils.resultMap;

/**
 * @author JACK 病人和医生的通用 用户逻辑
 * 
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	@Autowired
	UserDao userDao;

	@Resource
	@Autowired
	AccountDao accountDao;

	@Resource
	LoginLogTDao loginLogTDao;

	@Resource
	@Autowired
	ValidateCodeDao validateCodeDao;

	@Resource
	DoctorGPSDao doctorGPSDao;

	@Resource
	DoctorDao doctorDao;

	@Resource
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	@Resource
	UserInfosDao userInfoDao;

	@Resource
	PatientInfoDao patientInfoDao;

	private String hql;

	@Override
	public HashMap<String, Object> getValidateCode(String urlPhoneNo) {
		Logger logger = Logger.getLogger(this.getClass());
		// 判断是否存在且有无密码
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("phoneNo", urlPhoneNo);
		hql = "from User where phoneNo=:phoneNo and password is null and validateCode is null";
		List<User> userNo = userDao.getAll(hql, params);
		try {
			for (User user : userNo) {
				logger.info(userNo);
				accountDao.delete(accountDao.getById(user.getUserId()));
				userInfoDao.delete(userInfoDao.getById(user.getUserId()));
				userDao.delete(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, "获取失败");
		}

		// 获取验证码
		String validateCode = RandomCode.getRandomCode(4);
		String content = "您的验证码是：" + validateCode + "。请不要把验证码泄露给其他人。";
		HashMap<String, Object> result = MSG.sendToUser(urlPhoneNo, content);
		if ((int) result.get("code") == 1) {
			ValidateCode validates = validateCodeDao.getByPhoneNo(urlPhoneNo);
			if (validates == null) {
				validates = new ValidateCode();
				validates.setPhoneNo(urlPhoneNo);
				validateCodeDao.save(validates);
			}

			validates.setValidateCode(validateCode);
			validates.setCreateTime(new Date());
			validateCodeDao.update(validates);
		}

		return result;

	}

	@Override
	public HashMap<String, Object> validateValidateCode(String urlValidateCode,
			String urlPhoneNo, String userType) {
		// 比对验证码
		if (userType == null) {
			return resultMap.createResult(0, "userType不为空");
		}
		if (!userType.equals(UserType.doctor)
				&& !userType.equals(UserType.patient)) {
			return resultMap.createResult(0, "用户类型不正确");
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);

		ValidateCode vCode = validateCodeDao.getByPhoneNo(urlPhoneNo);

		if (vCode == null) {
			return resultMap.createResult(0, "请先获取验证码");
		}

		String dbValidateCode = vCode.getValidateCode();

		if (!dbValidateCode.equals(urlValidateCode)) {
			return resultMap.createResult(0, "验证码错误");
		}
		Date createTime = validateCodeDao.getByPhoneNo(urlPhoneNo)
				.getCreateTime();
		if (createTime.getTime() + (60 * 30 * 1000) < new Date().getTime()) {
			return resultMap.createResult(0, "验证超时，请从新获取");
		}

		// 插入数据
		User dbUser = userDao.getByPhoneNo(urlPhoneNo);
		User user = null;
		if (dbUser == null) {
			user = new User();
			user.setPhoneNo(urlPhoneNo);
			user.setUserType(userType);
			user.setUserName(urlPhoneNo);
			user.setCreateTime(new Date());
			int newUserId = (int) userDao.save(user);

			Account newAccount = new Account(newUserId, 0.0);
			accountDao.save(newAccount);

			String clientId = user.getUserId().toString();
			user.setClientId(clientId);
			user = userDao.setNewToken(user);
			userDao.saveOrUpdate(user);

			UserInfos userInfo = new UserInfos(userDao.getByPhoneNo(urlPhoneNo)
					.getUserId());
			userInfoDao.save(userInfo);

			result.put("clientId", user.getClientId());
			result.put("token", user.getToken());

			String phoneNo = user.getPhoneNo();
			try {
				HashMap<String, Object> userAddress = PhoneAttribution
						.userAddress(phoneNo);
				String address = (String) userAddress.get("msg");
				user.setAttribution(address);
				userDao.update(user);

			} catch (JSONException e) {
				e.printStackTrace();
				return resultMap.createResult(0, false);
			}

		}
		if (dbUser != null) {
			if (!userDao.getById(dbUser.getUserId()).getUserType()
					.equals(userType)) {
				return resultMap.createResult(0,
						"用户类型错误。如果您是医生，请在医生端登陆；\n如果您是用户，请在用户端登陆");
			}
			result.put("clientId", dbUser.getClientId());
			result.put("token", dbUser.getToken());
		}
		result.put("code", 1);
		result.put("msg", "验证通过");

		return result;
	}

	@Override
	public HashMap<String, Object> updatePasswordByToken(String urlPhoneNo,
			String urlPassword) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		User dbUser = userDao.getByPhoneNo(urlPhoneNo);
		// 验证token创建时间是否过期
		// Date dbTokenValidateDate = dbUser.getTokenValidDate();
		// Date now = new Date();
		// long timeAfterCreate = now.getTime() + UtilConstant.tokenLast -
		// dbTokenValidateDate.getTime();
		// 验证验证码是否过期
		// ValidateCode dbValidate = validateCodeDao.getByPhoneNo(urlPhoneNo);
		// Date dbValidateTime = dbValidate.getCreateTime();
		// Date now = new Date();
		// long timeAfterCreate = now.getTime() - dbValidateTime.getTime();
		// if (timeAfterCreate > UtilConstant.validateCodeValidTime) {
		// return resultMap.createResult(0, "请重新获取验证码");
		// }

		dbUser.setPassword(MD5.parseMD5(urlPassword));
		userDao.setNewToken(dbUser);
		userDao.update(dbUser);

		return resultMap.createResult(1, "修改成功");
	}

	@Override
	public HashMap<String, Object> updatePasswordByToken2(String urlPhoneNo,
			String urlPassword) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			User dbUser = userDao.getByPhoneNo(urlPhoneNo);
			dbUser.setValidateCode(MD5.parseMD5(urlPassword));
			userDao.setNewToken(dbUser);
			userDao.update(dbUser);
			return resultMap.createResult(1, "修改成功");
		} catch (Exception e) {
			logger.error("updatePasswordByToken2", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> updatePasswordByOldPassword(
			String urlPhoneNo, String urlOldPassword, String urlNewPassword) {

		User dbUser = userDao.getByPhoneNo(urlPhoneNo);
		String dbPassword = dbUser.getPassword();
		String urlPassword = MD5.parseMD5(urlOldPassword);

		if (!dbPassword.equals(urlPassword)) {
			return resultMap.createResult(0, "旧密码错误");
		}

		dbUser.setPassword(MD5.parseMD5(urlNewPassword));
		dbUser = userDao.setNewToken(dbUser);
		userDao.update(dbUser);
		return resultMap.createResult(1, "修改成功");
	}

	@Override
	public HashMap<String, Object> updateUserName(String urlPhoneNo,
			String urlNewUserName) {

		User dbUser = userDao.getByPhoneNo(urlPhoneNo);

		dbUser.setUserName(urlNewUserName);

		userDao.update(dbUser);

		return resultMap.createResult(1, "修改用户名成功");
	}

	@Override
	public HashMap<String, Object> updateHeadIcon(String urlPhoneNo,
			MultipartFile image, String folderName) {

		try {
			User dbUser = userDao.getByPhoneNo(urlPhoneNo);
			dbUser.setHaveIcon(true);
			userDao.update(dbUser);
			FileTool.uploadImg(image, urlPhoneNo, folderName);
			return resultMap.createResult(1, "上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(1, "updateImg 上传失败");
		}
	}

	//
	// @Override
	// public HashMap<String, Object> login(String urlPhoneNo, String
	// urlPassword, String platform, String deviceToken) { //
	// 登录时没有token,传入新的token
	//
	// HashMap<String, Object> result = new HashMap<String, Object>();
	// result.put("code", 0);
	// User dbUser = userDao.getByPhoneNo(urlPhoneNo);
	// if (platform != null && !platform.equals("")) {
	// dbUser.setPlatform(platform);
	// }
	//
	// if (deviceToken != null && !platform.equals("")) {
	// dbUser.setDeviceToken(deviceToken);
	// }
	//
	// User phoneNo = userDao.getByPhoneNo(urlPhoneNo);
	// if (phoneNo == null) {
	// return resultMap.createResult(0, "该用户不存在");
	// }
	//
	// String urlPasswordMD5 = MD5.parseMD5(urlPassword);
	// String dbPassword = phoneNo.getPassword();
	//
	// Date dbLockTime = phoneNo.getLockTime();
	//
	// if (dbPassword == null && "".equals(dbPassword)) {
	// return resultMap.createResult(0, "请先设置密码");
	// }
	// if (!urlPasswordMD5.equals(dbPassword)) {
	// return resultMap.createResult(0, "密码错误");
	// }
	// if (dbLockTime.after(new Date()) | phoneNo.getStatus() ==
	// UserStatus.disabled) {
	// return resultMap.createResult(0, "用户被锁定");
	// }
	//
	// phoneNo.setStatus(UserStatus.online);
	// // dbUser = userDao.setNewToken(dbUser);
	// userDao.update(dbUser);
	//
	// result.put("code", 1);
	// result.put("msg", "登录成功");
	// result.put("token", phoneNo.getToken());
	// result.put("clientId", phoneNo.getUserId().toString());
	// return result;
	// }
	@Override
	public HashMap<String, Object> login2(String urlPhoneNo,
			String urlPassword, String platform, String deviceToken) { // 登录时没有token,传入新的token

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		User dbUser = userDao.getByPhoneNo(urlPhoneNo);

		if (dbUser == null) {
			return resultMap.createResult(0, "该用户不存在");
		}

		String urlPasswordMD5 = MD5.parseMD5(urlPassword);
		String dbPassword = dbUser.getPassword();

		Date dbLockTime = dbUser.getLockTime();

		if (dbPassword == null) {
			return resultMap.createResult(0, "请先设置密码");
		}
		if (!urlPasswordMD5.equals(dbPassword)) {
			return resultMap.createResult(0, "密码错误");
		}
		if (dbLockTime.after(new Date())
				| dbUser.getStatus() == UserStatus.disabled) {
			return resultMap.createResult(0, "用户被锁定");
		}

		if (dbUser.getStatus().equals(UserStatus.online)) {
			String hql = "from LoginLogT where isDel='1' and phoneNo='"
					+ urlPhoneNo + "' order by loginTime desc";
			List<LoginLogT> loginLogList = loginLogTDao.findPage(hql, null);
			HashMap<String, String> resultNotification = new HashMap<String, String>();
			resultNotification.put("code", "0");
			LoginLogT myLoginLog = null;
			if (loginLogList.size() != 0) {
				for (LoginLogT loginLogT : loginLogList) {
					if (loginLogT.getDeviceToken().equals(deviceToken)) {
						myLoginLog = loginLogT;
					}
				}
			}
			if (myLoginLog == null) {
				LoginLogT logT = new LoginLogT();
				logT.setDeviceToken(deviceToken);
				logT.setIsDel("1");
				logT.setLoginTime(new Date());
				logT.setPhoneNo(urlPhoneNo);
				logT.setDeviceType(platform);
				loginLogTDao.save(logT);
				if (loginLogList.size() != 0) {
					LoginLogT loginLogT = loginLogList.get(0);
					SendNotification.sendNotifiction(
							loginLogT.getDeviceToken(),
							loginLogT.getDeviceType(), dbUser.getUserId(),
							"您已在其他设备登陆,请退出登录", dbUser.getUserType(),
							resultNotification);
				}
			} else {
				LoginLogT logT = loginLogList.get(0);
				String token = logT.getDeviceToken();
				String deviceType = logT.getDeviceType();
				User user = userDao.getByPhoneNo(logT.getPhoneNo());
				Integer id = user.getUserId();
				String userType = user.getUserType();
				for (LoginLogT loginLogT2 : loginLogList) {
					if (loginLogT2.getDeviceToken().equals(deviceToken)) {
						loginLogT2.setLoginTime(new Date());
						loginLogTDao.update(loginLogT2);
					}
				}
				if (!token.equals(deviceToken)) {
					SendNotification.sendNotifiction(token, deviceType, id,
							"您已在其他设备登陆,请退出登录", userType, resultNotification);
				}
			}
		}

		if (platform != null && !platform.equals("")) {
			dbUser.setPlatform(platform);
		}

		if (deviceToken != null && !deviceToken.equals("")) {
			dbUser.setDeviceToken(deviceToken);
		}

		int userId = userDao.getByPhoneNo(urlPhoneNo).getUserId();
		UserInfos userInfo = userInfoDao.getById(userId);
		if (userInfo == null) {
			UserInfos info = new UserInfos(userId);
			userInfoDao.save(info);
		}

		dbUser.setStatus(UserStatus.online);
		userDao.update(dbUser);

		result.put("code", 1);
		result.put("msg", "登录成功");
		result.put("token", dbUser.getToken());
		result.put("clientId", dbUser.getUserId().toString());
		result.put("userName", dbUser.getUserName());
		result.put("haveIcon", dbUser.getHaveIcon());
		result.put("userType", dbUser.getUserType());
		return result;
	}

	@Override
	public HashMap<String, Object> login3(String urlPhoneNo,
			String urlPassword, String platform, String deviceToken) { // 登录时没有token,传入新的token

		HashMap<String, Object> result = new HashMap<String, Object>();
		User dbUser = userDao.getByPhoneNo(urlPhoneNo);

		if (dbUser == null) {
			return resultMap.createResult(0, "该用户不存在");
		}

		String urlPasswordMD5 = MD5.parseMD5(urlPassword);
		String dbPassword = dbUser.getValidateCode();

		Date dbLockTime = dbUser.getLockTime();

		if (dbPassword == null) {
			return resultMap.createResult(0, "请先设置密码");
		}
		if (!urlPasswordMD5.equals(dbPassword)) {
			return resultMap.createResult(0, "密码错误");
		}
		if (dbLockTime.after(new Date())
				| dbUser.getStatus() == UserStatus.disabled) {
			return resultMap.createResult(0, "用户被锁定");
		}

		if (dbUser.getStatus().equals(UserStatus.online)) {
			String hql = "from LoginLogT where isDel='1' and phoneNo='"
					+ urlPhoneNo + "' order by loginTime desc";
			List<LoginLogT> loginLogList = loginLogTDao.findPage(hql, null);
			HashMap<String, String> resultNotification = new HashMap<String, String>();
			resultNotification.put("code", "0");
			LoginLogT myLoginLog = null;
			if (loginLogList.size() != 0) {
				for (LoginLogT loginLogT : loginLogList) {
					if (loginLogT.getDeviceToken().equals(deviceToken)) {
						myLoginLog = loginLogT;
					}
				}
			}
			if (myLoginLog == null) {
				LoginLogT logT = new LoginLogT();
				logT.setDeviceToken(deviceToken);
				logT.setIsDel("1");
				logT.setLoginTime(new Date());
				logT.setPhoneNo(urlPhoneNo);
				logT.setDeviceType(platform);
				loginLogTDao.save(logT);
				if (loginLogList.size() != 0) {
					LoginLogT loginLogT = loginLogList.get(0);
					SendNotification.sendNotifiction(
							loginLogT.getDeviceToken(),
							loginLogT.getDeviceType(), dbUser.getUserId(),
							"您已在其他设备登陆,请退出登录", dbUser.getUserType(),
							resultNotification);
				}
			} else {
				LoginLogT logT = loginLogList.get(0);
				String token = logT.getDeviceToken();
				String deviceType = logT.getDeviceType();
				User user = userDao.getByPhoneNo(logT.getPhoneNo());
				Integer id = user.getUserId();
				String userType = user.getUserType();
				for (LoginLogT loginLogT2 : loginLogList) {
					if (loginLogT2.getDeviceToken().equals(deviceToken)) {
						loginLogT2.setLoginTime(new Date());
						loginLogTDao.update(loginLogT2);
					}
				}
				if (!token.equals(deviceToken)) {
					SendNotification.sendNotifiction(token, deviceType, id,
							"您已在其他设备登陆,请退出登录", userType, resultNotification);
				}
			}
		}

		if (platform != null && !platform.equals("")) {
			dbUser.setPlatform(platform);
		}

		if (deviceToken != null && !deviceToken.equals("")) {
			dbUser.setDeviceToken(deviceToken);
		}

		int userId = dbUser.getUserId();
		UserInfos userInfo = userInfoDao.getById(userId);
		if (userInfo == null) {
			UserInfos info = new UserInfos(userId);
			userInfoDao.save(info);
		}

		dbUser.setStatus(UserStatus.online);
		userDao.update(dbUser);
		if (dbUser.getUserType().equals("doctor")) {
			Doctor doctor = doctorDao.getById(dbUser.getUserId());
			if (doctor == null) {
				result.put("sex", "");
			} else {
				String sex = doctor.getSex();
				result.put("sex", sex);
			}
		} else {
			UserInfos userInfos = userInfoDao.getById(userId);
			String sex = userInfos.getSex();
			if (sex == null) {
				result.put("sex", "");
			} else {
				result.put("sex", sex);
			}
		}

		result.put("code", 1);
		result.put("msg", "登录成功");
		result.put("token", dbUser.getToken());
		result.put("clientId", dbUser.getUserId().toString());
		result.put("userName", dbUser.getUserName());
		result.put("haveIcon", dbUser.getHaveIcon());
		result.put("userType", dbUser.getUserType());
		return result;
	}

	public HashMap<String, Object> login(String urlPhoneNo, String urlPassword) { // 登录时没有token,传入新的token
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);

		User dbUser = userDao.getByPhoneNo(urlPhoneNo);

		if (dbUser == null) {
			return resultMap.createResult(0, "该用户不存在");
		}

		String urlPasswordMD5 = MD5.parseMD5(urlPassword);
		String dbPassword = dbUser.getPassword();

		Date dbLockTime = dbUser.getLockTime();

		if (dbPassword == null) {
			return resultMap.createResult(0, "请先设置密码");
		}
		if (!urlPasswordMD5.equals(dbPassword)) {
			return resultMap.createResult(0, "密码错误");
		}
		if (dbLockTime.after(new Date())
				| dbUser.getStatus() == UserStatus.disabled) {
			return resultMap.createResult(0, "用户被锁定");
		}

		dbUser.setStatus(UserStatus.online);
		// dbUser = userDao.setNewToken(dbUser);
		userDao.update(dbUser);

		result.put("code", 1);
		result.put("msg", "登录成功");
		result.put("token", dbUser.getToken());
		result.put("clientId", dbUser.getUserId().toString());
		return result;
	}

	@Override
	public HashMap<String, Object> logout(String phoneNo) {
		User dbUser = userDao.getByPhoneNo(phoneNo);
		dbUser.setTokenValidDate(new Date(UtilConstant.aDateLongAgo));
		dbUser.setStatus(UserStatus.able);
		dbUser.setDeviceToken("");
		userDao.update(dbUser);
		return resultMap.createResult(1, "成功退出");
	}

	@Override
	public HashMap<String, Object> isUserExist(String phoneNo) {
		User dbUser = userDao.getByPhoneNo(phoneNo);
		return dbUser == null ? resultMap.createResult(1, "用户不存在") : resultMap
				.createResult(0, "用户已存在");
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getDoctorGPS() {
		Logger logger = Logger.getLogger(UserServiceImpl.class);

		int doctorOnline = 0;
		int expertOnline = 0;

		Map<String, Object> params = new HashMap<String, Object>();
		String serviceStatus = "('" + DoctorStatus.open + "','"
				+ DoctorStatus.service + "')";
		params.put("serviceStatus", serviceStatus);
		String hql = "from DoctorRealtimeInfo where serviceStatus in "
				+ serviceStatus
				+ " and longitude is not '0.0' order by serviceCount";
		List<DoctorRealtimeInfo> DoctorRealtimeInfoList = doctorGPSDao
				.findPage(hql, null);
		HashMap<String, Object> results = new HashMap<String, Object>();
		List<Object> doctorsList = new ArrayList<Object>();
		for (DoctorRealtimeInfo doctorInfo : DoctorRealtimeInfoList) {
			Doctor dbDoctor = doctorDao.getById(doctorInfo.getDoctorId());
			DoctorInfo info = new DoctorInfo(dbDoctor);
			Map<String, Object> doctorMap = new HashMap<String, Object>();
			doctorMap.put("doctorId", info.getDoctorId());
			doctorMap.put("haveIcon", userDao.getById(doctorInfo.getDoctorId())
					.getHaveIcon());
			doctorMap.put("doctorType", info.getDoctorType());
			doctorMap.put("qualificationNo", info.getQualificationNo());
			if (info.getDoctorId() == dbDoctor.getDoctorId()) {
				doctorMap.put("sex", dbDoctor.getSex());
				doctorMap.put("doctorPhone", dbDoctor.getUser().getPhoneNo());
				doctorMap.put("department", dbDoctor.getDepartment());
				doctorMap.put("specialty", dbDoctor.getSpecialty());
				HashMap<String, Object> lat = RedisUtil.getLat(dbDoctor
						.getUser().getPhoneNo());
				if (lat.get("msg") == null) {
					RedisUtil.setLat(dbDoctor.getUser().getPhoneNo(),
							info.getLatitude(), info.getLongitude());
					lat = RedisUtil.getLat(dbDoctor.getUser().getPhoneNo());
				}
				HashMap<String, String> weiZhi = (HashMap<String, String>) lat
						.get("msg");
				doctorMap.put("latitude", weiZhi.get("latitude"));
				doctorMap.put("longitude", weiZhi.get("longitude"));
			}
			doctorMap.put("doctorName", info.getDoctorName());
			doctorMap.put("starLevel", info.getStarLevel());
			doctorMap.put("hospital", info.getHospital());
			doctorMap.put("qualification", info.getQualification());
			doctorMap.put("birthday", info.getBirthday());
			doctorsList.add(doctorMap);
			if ("expert".equals(dbDoctor.getDoctorType())) {
				expertOnline = expertOnline + 1;
			}
			if ("doctor".equals(dbDoctor.getDoctorType())) {
				doctorOnline = doctorOnline + 1;
			}
		}
		results.put("doctorsList", doctorsList);
		results.put("expert", expertOnline);
		results.put("doctor", doctorOnline);
		return results;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public HashMap<String, Object> getUser(User user, int pageNo, int pageSize) {

		Logger logger = Logger.getLogger(this.getClass());
		if (pageNo == 0) {
			pageNo = 1;
		}
		int yesterday = 0;
		int online = 0;
		int userNo = 0;
		int doctorNo = 0;
		int patientNo = 0;
		int yesDoctor = 0;
		int yesPatient = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		long time = date.getTime();
		Map<String, Object> listUsers = null;
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultList = new HashMap<String, Object>();
		Map<String, Object> result = null;
		String hql = null;
		try {
			hql = "from User order by createTime desc";
			listUsers = userDao.findPage(hql, null, pageNo, pageSize);
			for (User userss : (List<User>) listUsers.get("results")) {
				if (userss.getAttribution() == null) {
					String phoneNo = userss.getPhoneNo();
					try {
						JSONObject resultPhone = PhoneAttribution
								.getRequest1(phoneNo);// 手机号
						JSONObject resultJson = resultPhone
								.getJSONObject("result");
						if (resultPhone.get("resultcode").equals("200")) {
							String province = (String) resultJson
									.get("province");
							String city = (String) resultJson.get("city");
							String address = "";
							if (province.equalsIgnoreCase(city)) {
								address = province + city;
							} else {
								address = province;
							}
							if (userss.getPhoneNo().equals(phoneNo)) {
								userss.setAttribution(address);
								userDao.update(userss);
							}
						} else {
							userss.setAttribution(null);
							userDao.update(userss);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				if (dateFormat.parse(dateFormat.format(time)).getTime() > userss
						.getCreateTime().getTime()
						&& userss.getCreateTime().getTime() >= (dateFormat
								.parse(dateFormat.format(time - 60 * 60 * 24
										* 1000))).getTime()) {
					yesterday = yesterday + 1;
					if (userss.getUserType().equals("doctor")) {
						yesDoctor = yesDoctor + 1;
					}
					resultList.put("yesDoctor", yesDoctor);
					if (userss.getUserType().equals("patient")) {
						yesPatient = yesPatient + 1;
					}
					resultList.put("yesPatient", yesPatient);
				}
				if (userss.getStatus().equals("online")) {
					online = online + 1;
				}
				resultList.put("online", online);
				if (userss.getUserId() != 0) {
					userNo = userNo + 1;
				}
				resultList.put("userNo", userNo);
				if (userss.getUserType().equals("doctor")) {
					doctorNo = doctorNo + 1;
				}
				resultList.put("doctorNo", doctorNo);
				if (userss.getUserType().equals("patient")) {
					patientNo = patientNo + 1;
				}
				resultList.put("patientNo", patientNo);
			}
			resultList.put("yesterday", yesterday);
			results.add(resultList);// 必须在此处添加，因为controller取0角标

			for (User users : (List<User>) listUsers.get("result")) {
				result = new HashMap<String, Object>();
				result.put("userId", users.getUserId());
				result.put("haveIcon", users.getHaveIcon());
				result.put("phoneNo", users.getPhoneNo());
				result.put("userName", users.getUserName());
				result.put("userType", users.getUserType());
				result.put("createTime", users.getCreateTime());
				result.put("attribution", users.getAttribution());
				results.add(result);
			}

			return resultMap.createResult(1, results);

		} catch (Exception e) {
			return resultMap.createResult(0, "获取用户失败" + e);
		}
	}

	@Override
	public int getUserNo() {
		return (int) userDao.countAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> countDoctor(Doctor doctor, int pageNo,
			int pageSize) {
		int doctorNo = 0;
		int expertNo = 0;
		String hql = null;
		HashMap<String, Object> driMap = null;
		Map<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Doctor> doctorLists = new ArrayList<Doctor>();
		List<DoctorRealtimeInfo> driList = null;

		try {
			String doctorStatus = "('" + DoctorStatus.open + "','"
					+ DoctorStatus.service + "')";
			params.put("doctorType", doctor.getDoctorType());
			if (doctor.getDoctorType() == null) {
				hql = "from DoctorRealtimeInfo where serviceStatus in "
						+ doctorStatus + " and longitude is not null";
				driMap = doctorRealtimeInfoDao.findPage(hql, null, pageNo,
						pageSize);
			} else {
				hql = "from DoctorRealtimeInfo where serviceStatus in "
						+ doctorStatus
						+ " and doctorType=:doctorType and longitude is not null";
				driMap = doctorRealtimeInfoDao.findPage(hql, params, pageNo,
						pageSize);
			}
			List<DoctorRealtimeInfo> alls = doctorRealtimeInfoDao
					.getAll("from DoctorRealtimeInfo where serviceStatus in "
							+ doctorStatus + " and longitude is not null");

			String hqlDoctor = "from Doctor";
			List<Doctor> doctorList = doctorDao.findPage(hqlDoctor, null);

			driList = (List<DoctorRealtimeInfo>) driMap.get("result");
			for (Doctor doctors : doctorList) {
				for (DoctorRealtimeInfo all : alls) {
					if (all.getDoctorId() == doctors.getDoctorId()) {
						if ("doctor".equals(doctors.getDoctorType())) {
							doctorNo = doctorNo + 1;
						}
						if ("expert".equals(doctors.getDoctorType())) {
							expertNo = expertNo + 1;
						}
					}
				}
				for (DoctorRealtimeInfo doctorRealtimeInfo : driList) {
					if (doctorRealtimeInfo.getDoctorId() == doctors
							.getDoctorId()) {
						doctorLists.add(doctors);
					}
				}
			}
			result.put("doctorNo", doctorNo);
			result.put("expertNo", expertNo);
			result.put("totalNo", alls.size());
			result.put("doctorList", doctorLists);
			return result;
		} catch (Exception e) {
			return resultMap.createResult(0, "获取医生失败" + e);
		}
	}

	public List<Doctor> getDoctor() {
		String hql = "from Doctor where Status=" + "'" + DoctorStatus.verified
				+ "'";
		List<Doctor> doctorList = doctorDao.getAll(hql);
		return doctorList;
	}

	@Override
	public User getUser(Doctor doctor) {
		int doctorId = doctor.getDoctorId();
		User user = userDao.getById(doctorId);
		return user;
	}

	@Override
	public HashMap<String, Object> updateInfo(String phoneNo, String userName,
			String sex) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			User dbUser = userDao.getByPhoneNo(phoneNo);
			if (userName == null || userName.equals("")
					|| userName.equalsIgnoreCase("null")) {
			} else {
				dbUser.setUserName(userName);
				userDao.update(dbUser);
			}
			if (sex == null || sex.equals("") || sex.equalsIgnoreCase("null")) {
			} else {
				if (dbUser.getUserType().equalsIgnoreCase("patient")) {
					UserInfos userInfos = userInfoDao.getById(dbUser
							.getUserId());
					userInfos.setSex(sex);
					userInfoDao.update(userInfos);
				} else {
					Doctor doctor = doctorDao.getById(dbUser.getUserId());
					doctor.setSex(sex);
					doctorDao.update(doctor);
				}
			}
			return resultMap.createResult(1, "修改成功");
		} catch (Exception e) {
			logger.error("updateInfo", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> reLogin(String phoneNo, String deviceToken,
			String platform) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		try {
			String hql = "from LoginLogT where phoneNo='" + phoneNo
					+ "' and deviceToken='" + deviceToken
					+ "' order by loginTime desc";
			List<LoginLogT> loginLogList = loginLogTDao.findPage(hql, null);
			if (loginLogList.size() == 0) {
				return resultMap.createResult(1, "未在此设备登陆过");
			} else {
				LoginLogT loginLogT = loginLogList.get(0);
				loginLogT.setLoginTime(new Date());
				loginLogTDao.update(loginLogT);
				User user2 = userDao.getByPhoneNo(phoneNo);
				User user = userDao.setNewToken(user2);
				if (user.getLockTime().after(new Date())
						| user.getStatus() == UserStatus.disabled) {
					return resultMap.createResult(1, "用户被锁定");
				}
				user.setPlatform(platform);
				user.setDeviceToken(deviceToken);
				userDao.update(user);
				if (user.getUserType().equals("doctor")) {
					Doctor doctor = doctorDao.getById(user.getUserId());
					if (doctor == null) {
						resultsMap.put("sex", "");
					} else {
						String sex = doctor.getSex();
						resultsMap.put("sex", sex);
					}
				} else {
					UserInfos userInfos = userInfoDao.getById(user.getUserId());
					String sex = userInfos.getSex();
					if (sex == null) {
						resultsMap.put("sex", "");
					} else {
						resultsMap.put("sex", sex);
					}
				}
				resultsMap.put("token", user.getToken());
				resultsMap.put("clientId", user.getClientId());
				resultsMap.put("userName", user.getUserName());
				resultsMap.put("haveIcon", user.getHaveIcon());
				resultsMap.put("userType", user.getUserType());
			}
			return resultMap.createResult(1, resultsMap);
		} catch (Exception e) {
			logger.error("reLogin", e);
			return resultMap.createResult(0, false);
		}
	}

}
