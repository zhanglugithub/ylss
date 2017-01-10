package ylss.service.web.impl;



import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.UserDao;
import ylss.model.constant.databaseConstant.UserStatus;
import ylss.model.constant.databaseConstant.UserType;
import ylss.model.table.User;
import ylss.service.web.AdminSelfService;
import ylss.utils.MD5;
import ylss.utils.resultMap;
@Service
@Transactional
public class AdminSelfServiceImpl implements AdminSelfService {

	@Resource
	@Autowired
	UserDao userDao;
	
	@Override
	public HashMap<String, Object> login(String phoneNo,String password) {
	
		User dbUser = userDao.getByPhoneNo(phoneNo);

		if(dbUser==null) {
			return resultMap.createResult(0,"该用户不存在");
		}
		
		String urlPasswordMD5 = MD5.parseMD5(password);
		String dbPassword = dbUser.getPassword();
		Date dbLockTime = dbUser.getLockTime();
		String userType=dbUser.getUserType();
		
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
		if (!userType.equals(UserType.service)) {
			return resultMap.createResult(0,"用户类型应该为service");
		}
		return resultMap.createResult(1,"登录成功");

	}


}
