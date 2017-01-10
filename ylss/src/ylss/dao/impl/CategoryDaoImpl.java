package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.CategoryDao;
import ylss.model.table.CategoryT;

@Repository
public class CategoryDaoImpl extends BaseDaoImpl<CategoryT, Integer> implements
		CategoryDao {

	public CategoryDaoImpl() {
		super(CategoryT.class);
	}

}
