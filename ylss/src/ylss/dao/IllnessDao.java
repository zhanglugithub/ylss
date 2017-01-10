package ylss.dao;

import ylss.model.table.IllnessT;

public interface IllnessDao extends BaseDao<IllnessT, Integer> {

	public IllnessT getByUid(String uId);

}
