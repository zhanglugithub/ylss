package ylss.dao;

import ylss.model.table.Account;

public interface AccountDao extends BaseDao<Account, Integer> {

	public boolean addMoney(int userId, double money);

	public boolean subMoney(int userId, double money);
}
