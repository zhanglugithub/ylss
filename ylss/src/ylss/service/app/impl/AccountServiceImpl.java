package ylss.service.app.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.AccountDao;
import ylss.dao.AccountOperateHistoryDao;
import ylss.dao.DoctorDao;
import ylss.dao.UserDao;
import ylss.dao.WithdrawDao;
import ylss.model.constant.databaseConstant.AccountOperateType;
import ylss.model.constant.databaseConstant.WithdrawStatus;
import ylss.model.table.Account;
import ylss.model.table.AccountOperateHistory;
import ylss.model.table.Doctor;
import ylss.model.table.User;
import ylss.model.table.Withdraw;
import ylss.service.app.AccountService;
import ylss.utils.resultMap;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	public static final int NoOrder = 0;

	@Resource
	@Autowired
	UserDao userDao;

	@Resource
	@Autowired
	WithdrawDao withdrawDao;

	@Resource
	@Autowired
	AccountDao accountDao;

	@Resource
	DoctorDao doctorDao;

	@Resource
	@Autowired
	AccountOperateHistoryDao accountOperateHistoryDao;

	private Account dbAccount;

	@Override
	public HashMap<String, Object> recharge(double moneyAmount, String phoneNo) {

		try {

			if (moneyAmount <= 0) {
				return resultMap.createResult(0, "参数非法");
			}

			int userId = userDao.getByPhoneNo(phoneNo).getUserId();
			dbAccount = accountDao.getById(userId);

			double currentBalance = dbAccount.getBalance();
			dbAccount.setBalance(currentBalance + moneyAmount);
			accountDao.update(dbAccount);
			AccountOperateHistory aHistory = new AccountOperateHistory(userId, AccountOperateType.recharge, moneyAmount,
					NoOrder, new Date());
			accountOperateHistoryDao.save(aHistory);
			return resultMap.createResult(1, "充值成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> withdraw(double moneyAmount, String phoneNo) {

		try {

			if (moneyAmount <= 0.1) { // 防止 提取0.001的情况发生
				return resultMap.createResult(0, "提现金额不能小于0");
			}

			int userId = userDao.getByPhoneNo(phoneNo).getUserId();
			Doctor doctor = doctorDao.getById(userId);
			if (doctor == null) {
				return resultMap.createResult(0, "只有医生才能提现");
			}

			dbAccount = accountDao.getById(userId);
			double currentBalance = dbAccount.getBalance();
			if (currentBalance < moneyAmount) {
				return resultMap.createResult(0, "余额不足");
			}

			// 扣钱
			dbAccount.setBalance(currentBalance - moneyAmount); // 打钱操作人工完成，不考虑
																// double 不准的问题。
			accountDao.update(dbAccount);

			// 加提现记录
			Withdraw newWithdraw = new Withdraw(userId, moneyAmount, WithdrawStatus.verifying);
			int withdrawId = (int) withdrawDao.save(newWithdraw);

			// 加余额操作日志
			AccountOperateHistory aHistory = new AccountOperateHistory(userId, AccountOperateType.withdrawVerifying,
					moneyAmount, -withdrawId, new Date()); // 提现时没有orderId ，取反
															// 代表withdrawID

			accountOperateHistoryDao.save(aHistory);

			return resultMap.createResult(1, "操作成功");

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> getAccountOperateHistory(String phoneNo) {
		
		User dbUser = userDao.getByPhoneNo(phoneNo);

		List<AccountOperateHistory> operateHistories = dbUser.getOperateHistories();

		return resultMap.createResult(1, operateHistories);
	}

	public List<AccountOperateHistory> getAccountOperateHistory2(String phoneNo) {

		User dbUser = userDao.getByPhoneNo(phoneNo);
		List<AccountOperateHistory> operateHistories;

		operateHistories = dbUser.getOperateHistories();

		return operateHistories;
	}

	@Override
	public Double getCurrentMoney(String phoneNo) {
		int userId = userDao.getByPhoneNo(phoneNo).getUserId();
		return accountDao.getById(userId).getBalance();
	}

}
