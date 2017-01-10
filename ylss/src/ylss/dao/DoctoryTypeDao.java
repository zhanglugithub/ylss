package ylss.dao;

import ylss.model.table.DoctorTypeT;

public interface DoctoryTypeDao extends BaseDao<DoctorTypeT, Integer> {

	public DoctorTypeT getByUid(String uId);

}
