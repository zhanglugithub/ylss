package ylss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.BrowseDao;
import ylss.model.table.Browse;

@Repository
@Transactional
public class BrowseDaoImpl extends BaseDaoImpl<Browse, Integer> implements
		BrowseDao {

	public BrowseDaoImpl() {
		super(Browse.class);
	}

	@Override
	public Browse getByUserId(Integer userId) {
		return get("userId", userId);
	}

}
