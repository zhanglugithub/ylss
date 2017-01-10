package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.IllnessDao;
import ylss.model.table.IllnessT;

@Repository
public class llnessDaoImpl extends BaseDaoImpl<IllnessT, Integer> implements
		IllnessDao {

	public llnessDaoImpl() {
		super(IllnessT.class);
	}

	@Override
	public IllnessT getByUid(String uId) {
		return super.get("uid", uId);
	}

}
