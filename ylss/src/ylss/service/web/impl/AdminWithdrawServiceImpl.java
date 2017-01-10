package ylss.service.web.impl;

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
import ylss.model.table.AccountOperateHistory;
import ylss.model.table.Doctor;
import ylss.model.table.User;
import ylss.model.table.Withdraw;
import ylss.service.app.AccountService;
import ylss.service.web.AdminWithdrawService;
import ylss.utils.resultMap;

@Service
@Transactional
public class AdminWithdrawServiceImpl implements AdminWithdrawService {

	@Resource
	UserDao userDao;
	
	@Resource
	DoctorDao doctorDao;

	@Resource
	AccountService accountService;

	@Resource
	@Autowired
	AccountDao accountDao;

	@Resource
	WithdrawDao withdrawDao;

	@Resource
	AccountOperateHistoryDao accountOperateHistoryDao;

	@Override
	public List<Withdraw> listWithdraw() {
		
		String hql="from Withdraw"
				+ " where "
				+ "status='verifying'";
			
		return withdrawDao.getAll(hql);
	}

	@Override
	public HashMap<String, Object> reviewDoctorWithDraw(int withdrawId,
			String operateType) {

		Withdraw aWithdraw = withdrawDao.getById(withdrawId);
		if (!aWithdraw.getStatus().equals(WithdrawStatus.verifying)) {
			return resultMap.createResult(0, "提现记录状态不对呦~");
		}
		if (operateType.equals(WithdrawStatus.paid)) {

			payWithdraw(aWithdraw);

		} else {
			rejectWithdraw(aWithdraw);
		}
		return resultMap.createResult(1, "操作成功");
	}

	@Override
	public HashMap<String, Object> updateDoctorAlipay(String phoneNo,
			String newAlipayNo) {
		User aUser=userDao.getByPhoneNo(phoneNo);
		if (aUser==null) {
			return resultMap.createResult(0,"该用户不存在");
		}
		int doctorId =aUser.getUserId();
		Doctor aDoctor=doctorDao.getById(doctorId);
		if (aDoctor==null) {
			return resultMap.createResult(0,"该用户不是医生");
		}
		aDoctor.setAlipayNo(newAlipayNo);
		doctorDao.update(aDoctor);
		return resultMap.createResult(1, "更新成功");
	}

	private void payWithdraw(Withdraw aWithdraw) {

		// 首先线下打款
		aWithdraw.setStatus(WithdrawStatus.paid);

		Integer aWithdrawId = -aWithdraw.getWithdrawId();
	
		
		AccountOperateHistory aAccountOperateHistory = accountOperateHistoryDao
				.get("orderId", aWithdrawId);

		aAccountOperateHistory.setOperateType(AccountOperateType.withdrawPaid);

		accountOperateHistoryDao.update(aAccountOperateHistory);
		withdrawDao.update(aWithdraw);
	}

	private void rejectWithdraw(Withdraw aWithdraw) {

		int doctorId = aWithdraw.getDoctorId();
		String phoneNo = userDao.getById(doctorId).getPhoneNo();
		double moneyAmount = aWithdraw.getMoney();

		accountService.recharge(moneyAmount, phoneNo);

		aWithdraw.setStatus(WithdrawStatus.unpassed);

		Integer aWithdrawId = -aWithdraw.getWithdrawId();
		AccountOperateHistory aAccountOperateHistory = accountOperateHistoryDao
				.get("orderId", aWithdrawId);

		aAccountOperateHistory
				.setOperateType(AccountOperateType.withdrawUnpassed);

		accountOperateHistoryDao.update(aAccountOperateHistory);
		withdrawDao.update(aWithdraw);
	}

}
