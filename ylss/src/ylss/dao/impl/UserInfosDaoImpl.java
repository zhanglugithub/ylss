package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.UserInfosDao;
import ylss.model.table.UserInfos;

@Repository
public class UserInfosDaoImpl extends BaseDaoImpl<UserInfos, Integer> implements
		UserInfosDao {

	public UserInfosDaoImpl() {
		super(UserInfos.class);
	}

}
