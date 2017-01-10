package ylss.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ylss.model.table.Order;
import ylss.model.table.User;

public interface PatientOrderService {

	public HashMap<String, Object> createOrder(Order urlOrder);

	public HashMap<String, Object> createOrder2(Order urlOrder);

	public HashMap<String, Object> cancelOrder(int orderId);

	public HashMap<String, Object> cancelOrder2(int orderId, String reason);

	public HashMap<String, Object> payOrder(int orderId); // 回调函数地址

	public HashMap<String, Object> evaluateOrder(int urlOrder,
			String evaluation, double reviewStarLevel);

	public List<Map<String, Object>> getCurrentOrder(int patientId);

	public List<Map<String, Object>> getCurrentOrder1(int patientId);

	public List<Map<String, Object>> getFinishOrder(int patientId);

	public List<Map<String, Object>> getFinishOrder1(int patientId);

	public HashMap<String, Object> getOrderDetail(int orderId);

	public HashMap<String, Object> getOrderDetail1(int orderId);

	public List<Map<String, Object>> getEvaluation(int doctorId, int patientId);

	public Map<String, Object> getFinishOrder2(int patientId, int pageNo,
			int pageSize);

	public HashMap<String, Object> getEvaluation2(int doctorId, int orderId,
			int pageNo, int pageSize);

	public HashMap<String, Object> getEvaluation1(int doctorId, int orderId);

	public HashMap<String, Object> isHaveOrder(User user);

	public HashMap<String, Object> payOrder2(int orderId);

	public HashMap<String, Object> getMyOrder(String phoneNo);

	// public List<Evaluat> getEvaluation(int doctorId, int pageNum);

}
