package ylss.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.model.logic.SickHistory;
import ylss.model.table.Order;
import ylss.service.app.DoctorOrderService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("doctor")
public class DoctorOrderController {

	HashMap<String, Object> result = new HashMap<String, Object>();

	@Autowired
	@Resource
	DoctorOrderService doctorOrderService;

	@ResponseBody
	@RequestMapping("/getEvaluat.do")
	public List<Map<String, Object>> getEvaluat(int doctorId) {
		return doctorOrderService.getEvaluation(doctorId);
	}

	@ResponseBody
	@RequestMapping("/receiveOrder.do")
	// 接单
	public HashMap<String, Object> receiveOrder(@RequestParam int orderId) {
		return doctorOrderService.receiveOrder(orderId);
	}

	@ResponseBody
	@RequestMapping("/finishOrder.do")
	public HashMap<String, Object> finishOrder(Order urlOrder) {
		return doctorOrderService.finishOrder(urlOrder);
	}

	@ResponseBody
	@RequestMapping("/getCurrentOrder.do")
	public List<Map<String, Object>> getCurrentOrder(@RequestParam int doctorId) {
		return doctorOrderService.getCurrentOrder(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getCurrentOrder1.do")
	public List<Map<String, Object>> getCurrentOrder1(@RequestParam int doctorId) {
		return doctorOrderService.getCurrentOrder1(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getCurrentOrder2.do")
	public HashMap<String, Object> getCurrentOrder2(@RequestParam int doctorId,
			int pageNum) {
		return doctorOrderService.getCurrentOrder2(doctorId, pageNum);
	}

	@ResponseBody
	@RequestMapping("/getCurrentOrder21.do")
	public HashMap<String, Object> getCurrentOrder21(
			@RequestParam int doctorId, int pageNum) {
		return doctorOrderService.getCurrentOrder21(doctorId, pageNum);
	}

	@ResponseBody
	@RequestMapping("/getFinishOrder.do")
	public List<Map<String, Object>> getFinishOrder(@RequestParam int doctorId) {
		return doctorOrderService.getFinishOrder(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getFinishOrder2.do")
	public HashMap<String, Object> getFinishOrder2(@RequestParam int doctorId,
			int pageNo, int pageSize) {
		return doctorOrderService.getFinishOrder2(doctorId, pageNo, pageSize);
	}

	@ResponseBody
	@RequestMapping("/getFinishOrder1.do")
	public List<Map<String, Object>> getFinishOrder1(@RequestParam int doctorId) {
		return doctorOrderService.getFinishOrder1(doctorId);
	}

	@ResponseBody
	@RequestMapping("/getHistoryOrder.do")
	public HashMap<String, Object> getHistoryOrder(String phoneNo,
			int pageSize, int pageNo) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> historyOrder;
		try {
			historyOrder = doctorOrderService.getHistoryOrder(phoneNo, pageNo,
					pageSize);
			return resultMap.createResult(1, historyOrder);
		} catch (Exception e) {
			logger.error("getHistoryOrder", e);
			return resultMap.createResult(0, false);
		}
	}

	@ResponseBody
	@RequestMapping("/getOrderDetail.do")
	public HashMap<String, Object> getOrderDetail(@RequestParam int orderId) {
		return doctorOrderService.getOrderDetail(orderId);
	}

	@ResponseBody
	@RequestMapping("/getOrderDetail1.do")
	public HashMap<String, Object> getOrderDetail1(@RequestParam int orderId) {
		//return doctorOrderService.getOrderDetail1(orderId);
		return doctorOrderService.getOrderDetail1(orderId);
	}

	@ResponseBody
	@RequestMapping("getSickHistory.do")
	public List<SickHistory> getSickHistory(int patientId) {
		return doctorOrderService.getSickHistory(patientId);
	}

}
