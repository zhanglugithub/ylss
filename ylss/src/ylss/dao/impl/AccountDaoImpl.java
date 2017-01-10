package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.AccountDao;
import ylss.model.table.Account;

@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account, Integer> implements
		AccountDao {
	public AccountDaoImpl() {
		super(Account.class);
	}

	public boolean addMoney(int userId, double money) {
		
		if (money < 0) {
			return false;
		}
		Account account =getById(userId);
		if (account==null) {
			return false;
		}
		double currentBalance = account.getBalance();
		account.setBalance(currentBalance + money);
		update(account);
		return true;
	}
	public boolean subMoney(int userId, double money) {
		
		if (money < 0) {
			return false;
		}
		Account account =getById(userId);
		if (account==null) {
			return false;
		}
		double currentBalance = account.getBalance();
		account.setBalance(currentBalance - money);
		update(account);

		return true;
	}
}
