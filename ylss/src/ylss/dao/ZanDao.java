package ylss.dao;

import ylss.model.table.Zan;

public interface ZanDao extends BaseDao<Zan, Integer> {

	public Zan getByUserId(Integer userId);

}
