package ylss.service.app.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.AccountDao;
import ylss.dao.AccountOperateHistoryDao;
import ylss.dao.DoctorDao;
import ylss.dao.DoctorRealtimeInfoDao;
import ylss.dao.EvaluatDao;
import ylss.dao.OrderDao;
import ylss.dao.PatientInfoDao;
import ylss.dao.UserDao;
import ylss.model.constant.databaseConstant.AccountOperateType;
import ylss.model.constant.databaseConstant.DoctorStatus;
import ylss.model.constant.databaseConstant.OrderStatus;
import ylss.model.logic.OrderInfo;
import ylss.model.logic.SickHistory;
import ylss.model.table.Account;
import ylss.model.table.AccountOperateHistory;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorRealtimeInfo;
import ylss.model.table.Evaluat;
import ylss.model.table.Order;
import ylss.model.table.PatientInfo;
import ylss.model.table.User;
import ylss.service.app.DoctorOrderService;
import ylss.utils.SendNotification;
import ylss.utils.resultMap;

@Service
@Transactional
public class DoctorOrderServiceImpl implements DoctorOrderService {

	@Resource
	UserDao userDao;

	@Resource
	EvaluatDao evaluatDao;

	@Resource
	OrderDao orderDao;

	@Resource
	AccountDao accountDao;

	@Resource
	DoctorDao doctorDao;

	@Resource
	AccountOperateHistoryDao accountOperateHistoryDao;

	@Resource
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	@Resource
	PatientInfoDao patientInfoDao;

	@Override
	public List<SickHistory> getSickHistory(int patientId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patientId", patientId);
		String hql = "from Order where patientId=:patientId and status in ('canceled','finished','evaluated')";

		List<Order> orderList = orderDao.getAll(hql, params);
		List<SickHistory> sickHistoryList = new ArrayList<SickHistory>();
		for (Order order : orderList) {
			sickHistoryList.add(new SickHistory(order));
		}

		return sickHistoryList;
	}

	@Override
	public HashMap<String, Object> receiveOrder(int orderId) {// 接单

		try {
			Order order = orderDao.getById(orderId);
			int doctorId = order.getDoctorId();
			String hql = "from Order where doctorId='" + doctorId
					+ "' and status='" + OrderStatus.received + "'";
			List<Order> orderList = orderDao.findPage(hql, null);
			// if (orderList.size() > 0) {
			// return resultMap.createResult(0, "您有未完成的订单，\n无法再次接单");
			// }
			if (order.getStatus().equals(OrderStatus.canceled)) {
				return resultMap.createResult(0, "此订单已取消！");
			}
			if (!order.getStatus().equals(OrderStatus.unreceived)) {
				return resultMap.createResult(0, "订单状态不太对呀!");
			}

			order.setStatus(OrderStatus.received);
			order.setStatusTime(new Date());
			orderDao.update(order);
			DoctorRealtimeInfo doctorRealtimeInfo = doctorRealtimeInfoDao
					.getById(doctorId);
			doctorRealtimeInfo.setServiceStatus(DoctorStatus.service);
			doctorRealtimeInfoDao.update(doctorRealtimeInfo);
			try {
				Integer patientId = order.getPatientId();
				User byId = userDao.getById(patientId);
				String platform = byId.getPlatform();
				String deviceToken = byId.getDeviceToken();
				String userType = byId.getUserType();
				String content = "亲,您的医生已接单！";
				SendNotification.sendNotifiction(deviceToken, platform,
						patientId, content, userType, null);
				return resultMap.createResult(1, "接单成功");
			} catch (Exception e) {
				return resultMap.createResult(0, "内catch" + e.toString());
			}
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	// 完成订单要调用的接口
	@Override
	public HashMap<String, Object> finishOrder(Order urlOrder) {
		Logger logger = Logger.getLogger(DoctorOrderServiceImpl.class);
		try {
			String prescription = urlOrder.getPrescription();
			int orderId = urlOrder.getOrderId();

			Order order = orderDao.getById(orderId);
			if (!order.getStatus().equals(OrderStatus.received)) {
				return resultMap.createResult(0, "订单状态不太对呀!");
			}
			// 打钱
			int doctorId = order.getDoctorId();
			Account doctorAccount = accountDao.getById(doctorId);
			double currentBalance = doctorAccount.getBalance();
			doctorAccount
					.setBalance(currentBalance + order.getDoctorGetMoney());
			accountDao.update(doctorAccount);
			// 记录

			AccountOperateHistory aAccountOperateHistory = new AccountOperateHistory(
					order.getDoctorId(), AccountOperateType.treat,
					order.getDoctorGetMoney(), order.getOrderId(), new Date());
			accountOperateHistoryDao.save(aAccountOperateHistory);

			// 更新订单状态
			order.setCheckResult(urlOrder.getCheckResult());
			order.setPrescription(prescription);
			order.setStatus(OrderStatus.finished);
			order.setFinishTime(new Date());
			orderDao.update(order);

			// 更新servcieCount
			Doctor doctor = doctorDao.getById(doctorId);
			DoctorRealtimeInfo doctorRealtimeInfo = doctor
					.getDoctorRealtimeInfo();
			int doctorServiceCount = doctorRealtimeInfo.getServiceCount();
			doctorRealtimeInfo.setServiceCount(doctorServiceCount + 1);
			doctorRealtimeInfo.setServiceStatus(DoctorStatus.open);
			doctorRealtimeInfoDao.update(doctorRealtimeInfo);

			Map<String, Object> returnInfo = new HashMap<String, Object>();
			// 医生给患者推送接单消息
			try {
				Integer patientId = order.getPatientId();
				User byId = userDao.getById(patientId);
				String deviceToken = byId.getDeviceToken();
				String platform = byId.getPlatform();
				String content = "亲，您的医生已完成订单,请评价";
				String userType = byId.getUserType();
				SendNotification.sendNotifiction(deviceToken, platform,
						patientId, content, userType, null);
				logger.info(returnInfo);
				return resultMap.createResult(1, "完成订单");
			} catch (Exception e) {
				logger.info("finishOrder" + "内catch" + e.toString());
				return resultMap.createResult(0, e.toString());
			}

		} catch (Exception e) {
			logger.info("finishOrder" + "外catch" + e.toString());
			return resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getCurrentOrder2(int doctorId, int pageNum) {

		Map<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> result = null;
		HashMap<String, Object> results = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		params.put("doctorId", doctorId);
		String hql = "from Order where doctorId=:doctorId and status in ('received','unreceived') order by createTime desc";
		HashMap<String, Object> orderInfoList = orderDao.getOrderInfoList(hql,
				params, pageNum);
		List<Order> orderList = (List<Order>) orderInfoList.get("result");

		for (Order order : orderList) {
			result = new HashMap<String, Object>();
			result.put("orderId", order.getOrderId());
			result.put("doctorId", order.getDoctorId());
			result.put("doctorName", order.getDoctor().getUser().getUserName());
			result.put("patientId", order.getPatientId());
			result.put("patientName", order.getPatient().getUserName());
			result.put("patientPhone", order.getPatient().getPhoneNo());
			result.put("illnessDesc", order.getIllnessDesc());
			result.put("serviceTime", order.getServiceTime());
			result.put("status", order.getStatus());
			result.put("doctorGetMoney", order.getDoctorGetMoney());
			result.put("patientPayMoney", order.getPatientPayMoney());
			result.put("checkResult", order.getCheckResult());
			result.put("prescription", order.getPrescription());
			result.put("createTime", order.getCreateTime());
			result.put("finishTime", order.getFinishTime());
			result.put("orderType", order.getOrderType());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			resultList.add(result);
		}
		results.put("result", resultList);
		results.put("pageCount", orderInfoList.get("pageCount"));

		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getCurrentOrder21(int doctorId, int pageNum) {

		Map<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> result = null;
		HashMap<String, Object> results = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		params.put("doctorId", doctorId);
		String hql = "from Order where doctorId=:doctorId and status in ('received','unreceived') order by createTime desc";
		HashMap<String, Object> orderInfoList = orderDao.getOrderInfoList(hql,
				params, pageNum);
		List<Order> orderList = (List<Order>) orderInfoList.get("result");
		for (Order order : orderList) {
			result = new HashMap<String, Object>();
			result.put("orderId", order.getOrderId());
			result.put("patientId", order.getPatientId());
			result.put("doctorId", order.getDoctorId());
			result.put("illnessDesc", order.getIllnessDesc());
			result.put("serviceTime", order.getServiceTime());
			result.put("status", order.getStatus());
			result.put("patientPayMoney", order.getPatientPayMoney());
			result.put("doctorGetMoney", order.getDoctorGetMoney());
			result.put("checkResult", order.getCheckResult());
			result.put("prescription", order.getPrescription());
			result.put("orderType", order.getOrderType());
			result.put("createTime", order.getCreateTime());
			result.put("finishTime", order.getFinishTime());
			result.put("statusTime", order.getStatusTime());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			result.put("doctorName", order.getDoctor().getUser().getUserName());
			result.put("patientName", order.getPatient().getUserName());
			result.put("patientPhone", order.getPatient().getPhoneNo());
			resultList.add(result);
		}
		results.put("result", resultList);
		results.put("pageCount", orderInfoList.get("pageCount"));

		return results;
	}

	@Override
	// public List<OrderInfo> getFinishOrder(int doctorId, int pageNum) {
	public HashMap<String, Object> getFinishOrder(int doctorId, int pageNum) {

		Map<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> result = null;
		HashMap<String, Object> results = null;
		List<Object> resultList = new ArrayList<Object>();
		params.put("doctorId", doctorId);
		String hql = "from Order where doctorId=:doctorId and status in ('finished','evaluated')";
		HashMap<String, Object> orderInfoList = orderDao.getOrderInfoList(hql,
				params, pageNum);
		Set<String> keySet = orderInfoList.keySet();

		for (String orderKey : keySet) {
			results = new HashMap<String, Object>();
			if (orderKey.equals("result")) {
				List<Order> list = (List<Order>) orderInfoList.get(orderKey);
				for (Order order : list) {
					result = new HashMap<String, Object>();
					result.put("orderId", order.getOrderId());
					result.put("doctorId", order.getDoctorId());
					result.put("doctorName", order.getDoctor().getUser()
							.getUserName());
					result.put("patientId", order.getPatientId());
					result.put("patientName", order.getPatient().getUserName());
					int patientInfoId = order.getPatientInfoId();
					if (patientInfoId != 0) {
						PatientInfo patientInfoById = patientInfoDao
								.getById(patientInfoId);
						Map<String, Object> patientInfo = new HashMap<String, Object>();
						patientInfo.put("infoId", patientInfoById.getInfoId());
						patientInfo.put("infoName",
								patientInfoById.getInfoName());
						patientInfo.put("patientId",
								patientInfoById.getPatientId());
						patientInfo.put("patientName",
								patientInfoById.getPatientName());
						patientInfo.put("birthday",
								patientInfoById.getBirthday());
						patientInfo.put("contactPhone",
								patientInfoById.getContactPhone());
						patientInfo
								.put("address", patientInfoById.getAddress());
						patientInfo.put("sex", patientInfoById.getSex());
						result.put("patientInfo", patientInfo);
					} else {
						result.put("patientInfo", null);
					}
					result.put("illnessDesc", order.getIllnessDesc());
					result.put("serviceTime", order.getServiceTime());
					result.put("status", order.getStatus());
					result.put("doctorGetMoney", order.getDoctorGetMoney());
					result.put("patientPayMoney", order.getPatientPayMoney());
					result.put("checkResult", order.getCheckResult());
					result.put("prescription", order.getPrescription());
					result.put("createTime", order.getCreateTime());
					result.put("finishTime", order.getFinishTime());
					result.put("orderType", order.getOrderType());
					resultList.add(result);
				}
			}
			results.put("result", resultList);
			results.put("pageCount", orderInfoList.get(orderKey));
		}
		return results;
	}

	@Override
	public HashMap<String, Object> getOrderDetail(int orderId) {
		try {
			OrderInfo orderInfo = new OrderInfo(orderDao.getById(orderId));
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("orderId", orderInfo.getOrderId());
			result.put("status", orderInfo.getStatus());
			result.put("serviceTime", orderInfo.getServiceTime());
			result.put("createTime", orderInfo.getCreateTime());
			result.put("patientName", orderInfo.getPatientName());
			result.put("doctorName", orderInfo.getDoctorName());
			result.put("doctorPhone", orderInfo.getDoctorPhone());
			result.put("patientPhone", orderInfo.getPatientPhone());
			result.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			result.put("patientPayMoney", orderInfo.getPatientPayMoney());
			result.put("illnessDesc", orderInfo.getIllnessDesc());
			result.put("orderType", orderInfo.getOrderType());
			result.put("checkResult", orderInfo.getCheckResult());
			result.put("prescription", orderInfo.getPrescription());
			result.put("evaluation", orderInfo.getEvaluation());
			result.put("starLevel", orderInfo.getStarLevel());
			Order order = orderDao.getById(orderInfo.getOrderId());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			return resultMap.createResult(1, result);

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString()
					+ "如果是nullpointer 一般是订单Id不对");
		}
	}

	@Override
	public HashMap<String, Object> getOrderDetail1(int orderId) {
		try {
			OrderInfo orderInfo = new OrderInfo(orderDao.getById(orderId));
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("orderId", orderInfo.getOrderId());
			result.put("status", orderInfo.getStatus());
			result.put("serviceTime", orderInfo.getServiceTime());
			result.put("createTime", orderInfo.getCreateTime());
			result.put("finishTime", orderInfo.getFinishTime());
			result.put("statusTime", orderInfo.getStatusTime());
			result.put("patientName", orderInfo.getPatientName());
			result.put("doctorName", orderInfo.getDoctorName());
			result.put("doctorPhone", orderInfo.getDoctorPhone());
			result.put("patientPhone", orderInfo.getPatientPhone());
			result.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			result.put("patientPayMoney", orderInfo.getPatientPayMoney());
			result.put("illnessDesc", orderInfo.getIllnessDesc());
			result.put("orderType", orderInfo.getOrderType());
			result.put("checkResult", orderInfo.getCheckResult());
			result.put("prescription", orderInfo.getPrescription());
			result.put("evaluation", orderInfo.getEvaluation());
			result.put("starLevel", orderInfo.getStarLevel());
			Order order = orderDao.getById(orderInfo.getOrderId());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			return resultMap.createResult(1, result);

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString()
					+ "如果是nullpointer 一般是订单Id不对");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getEvaluation(int doctorId) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("doctorId", doctorId);
			String hql = "from Evaluat where doctorId=:doctorId by createTime";
			List<Evaluat> evaluatList = evaluatDao.getEvaluatList(hql, params);
			Map<String, Object> result = null;
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			for (Evaluat evaluat : evaluatList) {
				result = new HashMap<String, Object>();
				result.put("patientName", evaluat.getPatientName());
				result.put("starLevel", evaluat.getStarLevel());
				result.put("createTime", evaluat.getCreateTime());
				result.put("evaluation", evaluat.getEvaluation());
				arrayList.add(result);
			}
			return arrayList;

		} catch (Exception e) {
			return (List<Map<String, Object>>) resultMap.createResult(0,
					e.toString());
		}

	}

	@Override
	public List<Map<String, Object>> getFinishOrder(int doctorId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doctorId", doctorId);
		Map<String, Object> result = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String hql = "from Order where doctorId=:doctorId and status in ('finished','evaluated')  order by finishTime desc";
		List<OrderInfo> orderInfos = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo orderInfo : orderInfos) {
			result = new HashMap<String, Object>();
			result.put("orderId", orderInfo.getOrderId());
			result.put("status", orderInfo.getStatus());
			result.put("serviceTime", orderInfo.getServiceTime());
			result.put("createTime", orderInfo.getCreateTime());
			result.put("patientName", orderInfo.getPatientName());
			result.put("doctorName", orderInfo.getDoctorName());
			result.put("doctorPhone", orderInfo.getDoctorPhone());
			result.put("patientPhone", orderInfo.getPatientPhone());
			result.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			result.put("patientPayMoney", orderInfo.getPatientPayMoney());
			result.put("illnessDesc", orderInfo.getIllnessDesc());
			result.put("orderType", orderInfo.getOrderType());
			result.put("checkResult", orderInfo.getCheckResult());
			result.put("prescription", orderInfo.getPrescription());
			result.put("evaluation", orderInfo.getEvaluation());
			result.put("starLevel", orderInfo.getStarLevel());
			Order order = orderDao.getById(orderInfo.getOrderId());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			resultList.add(result);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getFinishOrder2(int doctorId, int pageNo,
			int pageSize) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 5;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doctorId", doctorId);
		Map<String, Object> result = null;
		HashMap<String, Object> results = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String hql = "from Order where doctorId=:doctorId and status in ('finished','evaluated')  order by finishTime desc";
		HashMap<String, Object> orderInfMap = orderDao.getOrderInfoList2(hql,
				params, pageNo, pageSize);
		List<OrderInfo> orderInfos = (List<OrderInfo>) orderInfMap
				.get("result");
		for (OrderInfo orderInfo : orderInfos) {
			result = new HashMap<String, Object>();
			result.put("orderId", orderInfo.getOrderId());
			result.put("status", orderInfo.getStatus());
			result.put("serviceTime", orderInfo.getServiceTime());
			result.put("createTime", orderInfo.getCreateTime());
			result.put("patientName", orderInfo.getPatientName());
			result.put("doctorName", orderInfo.getDoctorName());
			result.put("doctorPhone", orderInfo.getDoctorPhone());
			result.put("patientPhone", orderInfo.getPatientPhone());
			result.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			result.put("patientPayMoney", orderInfo.getPatientPayMoney());
			result.put("illnessDesc", orderInfo.getIllnessDesc());
			result.put("orderType", orderInfo.getOrderType());
			result.put("checkResult", orderInfo.getCheckResult());
			result.put("prescription", orderInfo.getPrescription());
			result.put("evaluation", orderInfo.getEvaluation());
			result.put("starLevel", orderInfo.getStarLevel());
			Order order = orderDao.getById(orderInfo.getOrderId());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			resultList.add(result);
		}
		results.put("orderInfos", resultList);
		results.put("pageCount", orderInfMap.get("pageCount"));
		return resultMap.createResult(1, results);
	}

	@Override
	public List<Map<String, Object>> getFinishOrder1(int doctorId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("doctorId", doctorId);
		String hql = "from Order where doctorId=:doctorId and status in ('finished','evaluated')  order by finishTime desc";
		List<OrderInfo> orderInfos = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo orderInfo : orderInfos) {
			result = new HashMap<String, Object>();
			result.put("orderId", orderInfo.getOrderId());
			result.put("status", orderInfo.getStatus());
			result.put("serviceTime", orderInfo.getServiceTime());
			result.put("createTime", orderInfo.getCreateTime());
			result.put("finishTime", orderInfo.getFinishTime());
			result.put("statusTime", orderInfo.getStatusTime());
			result.put("patientName", orderInfo.getPatientName());
			result.put("doctorName", orderInfo.getDoctorName());
			result.put("doctorPhone", orderInfo.getDoctorPhone());
			result.put("patientPhone", orderInfo.getPatientPhone());
			result.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			result.put("patientPayMoney", orderInfo.getPatientPayMoney());
			result.put("illnessDesc", orderInfo.getIllnessDesc());
			result.put("orderType", orderInfo.getOrderType());
			result.put("checkResult", orderInfo.getCheckResult());
			result.put("prescription", orderInfo.getPrescription());
			result.put("evaluation", orderInfo.getEvaluation());
			result.put("starLevel", orderInfo.getStarLevel());
			Order order = orderDao.getById(orderInfo.getOrderId());
			int patientInfoId = order.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getCurrentOrder(int doctorId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("doctorId", doctorId);
		String hql = "from Order where doctorId=:doctorId and status in ('received','unreceived') order by createTime desc";
		List<OrderInfo> orderInfo = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo Info : orderInfo) {
			result = new HashMap<String, Object>();
			result.put("orderId", Info.getOrderId());
			result.put("status", Info.getStatus());
			result.put("serviceTime", Info.getServiceTime());
			result.put("createTime", Info.getCreateTime());
			result.put("patientName", Info.getPatientName());
			result.put("doctorName", Info.getDoctorName());
			result.put("doctorPhone", Info.getDoctorPhone());
			result.put("patientPhone", Info.getPatientPhone());
			result.put("doctorGetMoney", Info.getDoctorGetMoney());
			result.put("patientPayMoney", Info.getPatientPayMoney());
			result.put("illnessDesc", Info.getIllnessDesc());
			result.put("orderType", Info.getOrderType());
			result.put("checkResult", Info.getCheckResult());
			result.put("prescription", Info.getPrescription());
			result.put("evaluation", Info.getEvaluation());
			result.put("starLevel", Info.getStarLevel());
			Integer patientInfoId = orderDao.getById(Info.getOrderId())
					.getPatientInfoId();
			if (patientInfoId != null) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoId);
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", "");
			}
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getCurrentOrder1(int doctorId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("doctorId", doctorId);
		String hql = "from Order where doctorId=:doctorId and status in ('received','unreceived') order by createTime desc";
		List<OrderInfo> orderInfo = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo Info : orderInfo) {
			result = new HashMap<String, Object>();
			result.put("orderId", Info.getOrderId());
			result.put("status", Info.getStatus());
			result.put("serviceTime", Info.getServiceTime());
			result.put("createTime", Info.getCreateTime());
			result.put("patientName", Info.getPatientName());
			result.put("doctorName", Info.getDoctorName());
			result.put("doctorPhone", Info.getDoctorPhone());
			result.put("patientPhone", Info.getPatientPhone());
			result.put("doctorGetMoney", Info.getDoctorGetMoney());
			result.put("patientPayMoney", Info.getPatientPayMoney());
			result.put("illnessDesc", Info.getIllnessDesc());
			result.put("orderType", Info.getOrderType());
			result.put("checkResult", Info.getCheckResult());
			result.put("prescription", Info.getPrescription());
			result.put("evaluation", Info.getEvaluation());
			result.put("starLevel", Info.getStarLevel());
			int patientInfoId = orderDao.getById(Info.getOrderId())
					.getPatientInfoId();
			if (patientInfoId != 0) {
				PatientInfo patientInfoById = patientInfoDao
						.getById(patientInfoId);
				Map<String, Object> patientInfo = new HashMap<String, Object>();
				patientInfo.put("infoId", patientInfoById.getInfoId());
				patientInfo.put("infoName", patientInfoById.getInfoName());
				patientInfo.put("patientId", patientInfoById.getPatientId());
				patientInfo
						.put("patientName", patientInfoById.getPatientName());
				patientInfo.put("birthday", patientInfoById.getBirthday());
				patientInfo.put("contactPhone",
						patientInfoById.getContactPhone());
				patientInfo.put("address", patientInfoById.getAddress());
				patientInfo.put("sex", patientInfoById.getSex());
				result.put("patientInfo", patientInfo);
			} else {
				result.put("patientInfo", null);
			}
			resultList.add(result);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getHistoryOrder(String phoneNo, int pageNo,
			int pageSize) {
		Integer doctorId = userDao.getByPhoneNo(phoneNo).getUserId();
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 5;
		}
		String hql = "from Order where doctorId=:doctorId and status in ('canceled','finished','evaluated') order by createTime desc";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("doctorId", doctorId);
		HashMap<String, Object> historyMap = doctorDao.findPage(hql, params,
				pageNo, pageSize);
		List<Order> historyOrder = (List<Order>) historyMap.get("result");
		HashMap<String, Object> results = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		for (Order order : historyOrder) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("createTime", order.getCreateTime());
			resultMap.put("userName", order.getPatient().getUserName());
			resultMap.put("phoneNo", order.getPatient().getPhoneNo());
			resultMap.put("haveIcon", order.getPatient().getHaveIcon());
			resultMap.put("status", order.getStatus());
			resultMap.put("orderId", order.getOrderId());
			resultList.add(resultMap);
		}
		results.put("pageCount", historyMap.get("pageCount"));
		results.put("historyOrder", resultList);

		return results;
	}
}
