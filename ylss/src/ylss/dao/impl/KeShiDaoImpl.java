package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.KeShiDao;
import ylss.model.table.KeShiT;

@Repository
public class KeShiDaoImpl extends BaseDaoImpl<KeShiT, Integer> implements
		KeShiDao {

	public KeShiDaoImpl() {
		super(KeShiT.class);
	}

	@Override
	public KeShiT getByUid(String uId) {
		return super.get("uid", uId);
	}

}
