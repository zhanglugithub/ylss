package ylss.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.model.table.Order;
import ylss.model.table.PatientInfo;
import ylss.model.table.User;
import ylss.service.app.AccountService;
import ylss.service.app.PatientBasicService;
import ylss.service.app.PatientOrderService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("patient")
public class PatientOrderController {

	private HashMap<String, Object> result;

	@Autowired
	@Resource
	PatientOrderService patientOrderService;

	@Resource
	PatientBasicService patientBasicService;

	@Autowired
	AccountService accountService;

	@ResponseBody
	@RequestMapping("/getMyOrder.do")
	public HashMap<String, Object> getMyOrder(String phoneNo) {
		return patientOrderService.getMyOrder(phoneNo);
	}

	@ResponseBody
	@RequestMapping("/isHaveOrder.do")
	public HashMap<String, Object> isHaveOrder(User user) {
		return patientOrderService.isHaveOrder(user);
	}

	@ResponseBody
	@RequestMapping("/getAccountOperateHistories.do")
	public HashMap<String, Object> getAccountOperateHistories(
			@RequestParam String phoneNo) {
		return accountService.getAccountOperateHistory(phoneNo);
	}

	@ResponseBody
	@RequestMapping("/getEvaluation.do")
	public List<Map<String, Object>> getEvaluation(int doctorId, int orderId) {
		return patientOrderService.getEvaluation(doctorId, orderId);
	}

	@ResponseBody
	@RequestMapping("/getEvaluation1.do")
	public HashMap<String, Object> getEvaluation1(int doctorId, int orderId) {
		return patientOrderService.getEvaluation1(doctorId, orderId);
	}

	@ResponseBody
	@RequestMapping("/getEvaluation2.do")
	public HashMap<String, Object> getEvaluation2(int doctorId, int orderId,
			int pageNo, int pageSize) {
		return patientOrderService.getEvaluation2(doctorId, orderId, pageNo,
				pageSize);
	}

	@ResponseBody
	@RequestMapping("/createOrder.do")
	public HashMap<String, Object> createOrder(@Valid Order urlOrder,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			return resultMap.createResult(0, msg);
		}
		result = patientOrderService.createOrder(urlOrder);
		return result;
	}

	@ResponseBody
	@RequestMapping("/cancelOrder.do")
	public HashMap<String, Object> cancelOrder(@RequestParam int orderId) {
		return patientOrderService.cancelOrder(orderId);
	}

	@ResponseBody
	@RequestMapping("/quickOrder.do")
	public HashMap<String, Object> quickOrder(@Valid Order urlOrder,
			String address, String userName, String phoneNo,
			BindingResult bindingResult) {
		HashMap<String, Object> reuslts = new HashMap<String, Object>();
		if (bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			return resultMap.createResult(0, msg);
		}
		HashMap<String, Object> info = patientBasicService.setInfo(address,
				userName, "0", phoneNo, urlOrder.getPatientId());
		PatientInfo patientInfo = (PatientInfo) info.get("msg");
		Integer infoId = patientInfo.getInfoId();
		urlOrder.setPatientInfoId(infoId);
		reuslts = patientOrderService.createOrder2(urlOrder);
		if ((int) (reuslts.get("code")) == 0) {
			return reuslts;
		} else {
			return patientOrderService.payOrder2((int) reuslts.get("orderId"));
		}
	}

	@ResponseBody
	@RequestMapping("/onlineOrder.do")
	public HashMap<String, Object> onlineOrder(@Valid Order urlOrder,
			BindingResult bindingResult) {
		HashMap<String, Object> reuslts = new HashMap<String, Object>();
		if (bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			return resultMap.createResult(0, msg);
		}
		reuslts = patientOrderService.createOrder2(urlOrder);
		if ((int) (reuslts.get("code")) == 0) {
			return reuslts;
		} else {
			return patientOrderService.payOrder2((int) reuslts.get("orderId"));
		}
	}

	@ResponseBody
	@RequestMapping("/cancelOrder2.do")
	public HashMap<String, Object> cancelOrder2(@RequestParam int orderId,
			String reason) {
		return patientOrderService.cancelOrder2(orderId, reason);
	}

	@ResponseBody
	@RequestMapping("/payOrder.do")
	public HashMap<String, Object> payOrder(@RequestParam int orderId) {
		return patientOrderService.payOrder(orderId);
	}

	@ResponseBody
	@RequestMapping("/evaluateOrder.do")
	public HashMap<String, Object> evaluateOrder(int orderId,
			String evaluation, double reviewStarLevel) {
		return patientOrderService.evaluateOrder(orderId, evaluation,
				reviewStarLevel);
	}

	@ResponseBody
	@RequestMapping("/getCurrentOrder.do")
	public List<Map<String, Object>> getCurrentOrder(@RequestParam int patientId) {
		return patientOrderService.getCurrentOrder(patientId);
	}

	@ResponseBody
	@RequestMapping("/getCurrentOrder1.do")
	public List<Map<String, Object>> getCurrentOrder1(
			@RequestParam int patientId) {
		return patientOrderService.getCurrentOrder1(patientId);
	}

	@ResponseBody
	@RequestMapping("/getFinishOrder.do")
	public List<Map<String, Object>> getFinishOrder(@RequestParam int patientId) {
		return patientOrderService.getFinishOrder(patientId);
	}

	@ResponseBody
	@RequestMapping("/getFinishOrder2.do")
	public Map<String, Object> getFinishOrder2(@RequestParam int patientId,
			int pageNo, int pageSize) {
		return patientOrderService.getFinishOrder2(patientId, pageNo, pageSize);
	}

	@ResponseBody
	@RequestMapping("/getFinishOrder1.do")
	public List<Map<String, Object>> getFinishOrder1(@RequestParam int patientId) {
		return patientOrderService.getFinishOrder1(patientId);
	}

	@ResponseBody
	@RequestMapping("/getOrderDetail.do")
	public HashMap<String, Object> getOrderDetail(@RequestParam int orderId) {
		return patientOrderService.getOrderDetail(orderId);
	}

	@ResponseBody
	@RequestMapping("/getOrderDetail1.do")
	public HashMap<String, Object> getOrderDetail1(@RequestParam int orderId) {
		return patientOrderService.getOrderDetail1(orderId);
	}

}
