package ylss.dao;

import ylss.model.table.SufferersCircle;

public interface SufferersCircleDao extends BaseDao<SufferersCircle, Integer> {
	public SufferersCircle getByUserId(int userId);
}
