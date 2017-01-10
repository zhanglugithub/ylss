package ylss.dao;

import ylss.model.table.TableNameT;

public interface TableNameTDao extends BaseDao<TableNameT, Integer> {

	TableNameT getBytableName(String tableName);

}
