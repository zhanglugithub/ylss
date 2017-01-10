package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.DoctoryTypeDao;
import ylss.model.table.DoctorTypeT;

@Repository
public class DoctoryTypeDaoImpl extends BaseDaoImpl<DoctorTypeT, Integer>
		implements DoctoryTypeDao {

	public DoctoryTypeDaoImpl() {
		super(DoctorTypeT.class);
	}

	@Override
	public DoctorTypeT getByUid(String uId) {
		return super.get("uid", uId);
	}

}
