package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.AccountOperateHistoryDao;
import ylss.model.table.AccountOperateHistory;

@Repository
public class AccountOperateHistoryDaoImpl extends
		BaseDaoImpl<AccountOperateHistory, Integer> implements
		AccountOperateHistoryDao {
	public AccountOperateHistoryDaoImpl() {
		super(AccountOperateHistory.class);
	}
}
