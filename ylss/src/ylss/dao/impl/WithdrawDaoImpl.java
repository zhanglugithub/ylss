package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.WithdrawDao;
import ylss.model.table.Withdraw;

@Repository
public class WithdrawDaoImpl extends BaseDaoImpl<Withdraw, Integer> implements WithdrawDao {
	public WithdrawDaoImpl() {
		super(Withdraw.class);
	}
}
