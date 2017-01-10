package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.ZanDao;
import ylss.model.table.Zan;

@Repository
public class ZanDaoImpl extends BaseDaoImpl<Zan, Integer> implements ZanDao {

	public ZanDaoImpl() {
		super(Zan.class);
	}

	@Override
	public Zan getByUserId(Integer userId) {
		return get("userId", userId);
	}

}
