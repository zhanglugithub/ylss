package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.TableNameTDao;
import ylss.model.table.TableNameT;

@Repository
public class TableNameTDaoImpl extends BaseDaoImpl<TableNameT, Integer>
		implements TableNameTDao {

	public TableNameTDaoImpl() {
		super(TableNameT.class);
	}

	@Override
	public TableNameT getBytableName(String tableName) {
		return super.get("tableName", tableName);
	}

}
