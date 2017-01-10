package ylss.dao;

import ylss.model.table.Browse;

public interface BrowseDao extends BaseDao<Browse, Integer> {

	public Browse getByUserId(Integer userId);

}
