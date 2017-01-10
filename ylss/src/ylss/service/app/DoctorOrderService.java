package ylss.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ylss.model.logic.SickHistory;
import ylss.model.table.Order;

public interface DoctorOrderService {

	public List<SickHistory> getSickHistory(int patientId);

	public HashMap<String, Object> receiveOrder(int orderId);

	public HashMap<String, Object> finishOrder(Order urlOrder);

	public HashMap<String, Object> getCurrentOrder2(int doctorId, int pageNum);

	public HashMap<String, Object> getCurrentOrder21(int doctorId, int pageNum);

	public List<Map<String, Object>> getFinishOrder(int doctorId);

	public HashMap<String, Object> getFinishOrder(int doctorId, int pageNum);

	public List<Map<String, Object>> getFinishOrder1(int doctorId);

	public HashMap<String, Object> getOrderDetail(int orderId);

	public HashMap<String, Object> getOrderDetail1(int orderId);

	public List<Map<String, Object>> getEvaluation(int doctorId);

	// public List<Evaluat> getEvaluation(int doctorId,int pageNum);

	public List<Map<String, Object>> getCurrentOrder(int doctorId);

	public List<Map<String, Object>> getCurrentOrder1(int doctorId);

	public HashMap<String, Object> getHistoryOrder(String phoneNo, int pageNo,
			int pageSize);

	public HashMap<String, Object> getFinishOrder2(int doctorId, int pageNo,
			int pageSize);

}
