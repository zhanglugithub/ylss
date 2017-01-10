package ylss.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.UserDao;
import ylss.model.table.User;
import ylss.utils.Token;

@Repository
@Transactional
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	public User getById(Integer id) {
		return super.getById(id);
	}

	@Override
	public User getByPhoneNo(String phoneNo) {
		return get("phoneNo", phoneNo);
	}

	// 只有验证验证码时才会使用此函数.tokenValidateCode 已经弃用
	@Override
	public User setNewToken(User dbUser) {

		String token = Token.getToken(dbUser.getUserId().toString());

		dbUser.setToken(token);
		dbUser.setTokenValidDate(new Date(2000000000000L));

		return dbUser;
	}

	// @Override
	// public boolean push(String phoneNo, String content) {
	//
	// String alias = getByPhoneNo(phoneNo).getClientId();
	// return JPush.sendMSG(alias, content);
	// }


}
