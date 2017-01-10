package ylss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.SufferersCircleDao;
import ylss.model.table.SufferersCircle;

@Repository
@Transactional
public class SufferersCircleDaoImpl extends
		BaseDaoImpl<SufferersCircle, Integer> implements SufferersCircleDao {

	public SufferersCircleDaoImpl() {
		super(SufferersCircle.class);
	}

	@Override
	public SufferersCircle getByUserId(int userId) {
		return get("userId", userId);
	}
}
