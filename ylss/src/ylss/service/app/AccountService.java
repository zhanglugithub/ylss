package ylss.service.app;

import java.util.HashMap;
import java.util.List;

import ylss.model.table.AccountOperateHistory;

public interface AccountService {

	public HashMap<String, Object> recharge(double moneyAmount, String phoneNo);

	public HashMap<String, Object> withdraw(double moneyAmount, String phoneNo);

	public HashMap<String, Object> getAccountOperateHistory(String phoneNo);

	public List<AccountOperateHistory> getAccountOperateHistory2(String phoneNo);

	public Double getCurrentMoney(String phoneNo);
}
