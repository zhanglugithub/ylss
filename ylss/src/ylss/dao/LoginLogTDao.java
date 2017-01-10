package ylss.dao;

import ylss.model.table.LoginLogT;

public interface LoginLogTDao extends BaseDao<LoginLogT, Integer> {

	public LoginLogT getByDeviceToken(String deviceToken);
}
