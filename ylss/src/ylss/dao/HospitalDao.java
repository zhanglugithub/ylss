package ylss.dao;

import ylss.model.table.HospitalT;

public interface HospitalDao extends BaseDao<HospitalT, Integer> {

	public HospitalT getByUid(String uid);

}
