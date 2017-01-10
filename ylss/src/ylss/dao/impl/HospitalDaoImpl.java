package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.HospitalDao;
import ylss.model.table.HospitalT;

@Repository
public class HospitalDaoImpl extends BaseDaoImpl<HospitalT, Integer> implements
		HospitalDao {

	public HospitalDaoImpl() {
		super(HospitalT.class);
	}

	@Override
	public HospitalT getByUid(String uid) {
		return super.get("uid", uid);
	}

}
