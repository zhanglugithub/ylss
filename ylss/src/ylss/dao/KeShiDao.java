package ylss.dao;

import ylss.model.table.KeShiT;

public interface KeShiDao extends BaseDao<KeShiT, Integer> {

	public KeShiT getByUid(String uId);

}
