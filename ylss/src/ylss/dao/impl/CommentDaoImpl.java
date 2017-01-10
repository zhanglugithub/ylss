package ylss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.CommentDao;
import ylss.model.table.Comment;

@Repository
@Transactional
public class CommentDaoImpl extends BaseDaoImpl<Comment, Integer> implements
		CommentDao {

	public CommentDaoImpl() {
		super(Comment.class);
	}

}
