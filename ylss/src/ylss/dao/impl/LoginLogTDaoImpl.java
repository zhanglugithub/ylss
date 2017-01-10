package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.LoginLogTDao;
import ylss.model.table.LoginLogT;

@Repository
public class LoginLogTDaoImpl extends BaseDaoImpl<LoginLogT, Integer> implements
		LoginLogTDao {

	public LoginLogTDaoImpl() {
		super(LoginLogT.class);
	}

	@Override
	public LoginLogT getByDeviceToken(String deviceToken) {
		return super.get("deviceToken", deviceToken);
	}

}
