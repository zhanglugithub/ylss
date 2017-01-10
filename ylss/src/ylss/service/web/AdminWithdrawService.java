package ylss.service.web;

import java.util.HashMap;
import java.util.List;

import ylss.model.table.Withdraw;

public interface AdminWithdrawService {
	public List<Withdraw> listWithdraw();
	public HashMap<String, Object> reviewDoctorWithDraw(int withdrawId, String  operateType);

	public HashMap<String, Object> updateDoctorAlipay(String phoneNo,
			String newAlipayNo);

}
