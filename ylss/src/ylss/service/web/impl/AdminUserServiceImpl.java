package ylss.service.web.impl;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.UserDao;
import ylss.dao.ValidateCodeDao;
import ylss.model.table.ValidateCode;
import ylss.service.web.AdminUserService;
import ylss.utils.resultMap;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

	@Resource
	UserDao userDao;

	@Resource
	ValidateCodeDao validateCodeDao;

	@Override
	public String lockUser(String phoneNo, Date lockDate) {

//		User user = userDao.getByPhoneNo(phoneNo);
//		if (user == null) {
//			return "该用户不存在";
//		}
//		user.setTokenValidDate(new Date(UtilConstant.aDateLongAgo));
//		user.setLockTime(lockDate);
//		userDao.update(user);
		return "操作成功";
	}

	@Override
	public HashMap<String, Object> getCode(String phoneNo) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			ValidateCode validateCode = validateCodeDao.getByPhoneNo(phoneNo);
			if (validateCode == null) {
				return resultMap.createResult(0, "不存在");
			}
			String vCode = validateCode.getValidateCode();
			String phoneNum = validateCode.getPhoneNo();
			result.put("code", vCode);
			result.put("phone", phoneNum);
			result.put("vId", validateCode.getId());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
