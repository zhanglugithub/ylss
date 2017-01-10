package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.FeedBackDao;
import ylss.model.table.Feedback;

@Repository
public class FeedBackDaoImpl extends BaseDaoImpl<Feedback, Integer> implements FeedBackDao {
	
	public FeedBackDaoImpl() {
		super(Feedback.class);
	}

}
