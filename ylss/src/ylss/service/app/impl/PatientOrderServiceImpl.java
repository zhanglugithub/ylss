package ylss.service.app.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ylss.model.constant.ManageConstant;
import ylss.model.constant.databaseConstant.AccountOperateType;
import ylss.model.constant.databaseConstant.DoctorStatus;
import ylss.model.constant.databaseConstant.DoctorType;
import ylss.model.constant.databaseConstant.OrderStatus;
import ylss.model.constant.databaseConstant.UserStatus;
import ylss.model.logic.OrderInfo;
import ylss.model.table.Account;
import ylss.model.table.AccountOperateHistory;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorRealtimeInfo;
import ylss.model.table.Evaluat;
import ylss.model.table.Order;
import ylss.model.table.PatientInfo;
import ylss.model.table.User;
import ylss.service.app.PatientOrderService;
import ylss.utils.SendNotification;
import ylss.utils.resultMap;

@Transactional
@Service
public class PatientOrderServiceImpl implements PatientOrderService {

	@Resource
	EvaluatDao evaluatDao;

	@Resource
	UserDao userDao;

	@Resource
	OrderDao orderDao;

	@Resource
	AccountDao accountDao;

	@Resource
	DoctorDao doctorDao;

	@Resource
	AccountOperateHistoryDao accountOperateHistoryDao;

	@Resource
	PatientInfoDao patientInfoDao;

	@Resource
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	@Override
	public HashMap<String, Object> createOrder(Order urlOrder) {
		Logger logger = Logger.getLogger(PatientBasicServiceImpl.class);
		try {
			if (doctorRealtimeInfoDao.getById(urlOrder.getDoctorId())
					.getServiceStatus().equals(DoctorStatus.close)
					| userDao.getById(urlOrder.getDoctorId()).equals(
							UserStatus.able)) {
				return resultMap.createResult(0, "医生或护士已下线，\n请选择其他医生");
			}

			if (doctorRealtimeInfoDao.getById(urlOrder.getDoctorId())
					.getServiceStatus().equals(DoctorStatus.service)) {
				return resultMap.createResult(0, "医生或护士已在服务中，\n请选择其他医生");
			}
			User patient = getUserByOrder(urlOrder);
			if (patient == null) {
				return resultMap.createResult(0, "patientId(UserId)不对哦");
			}
			String serviceStatus = doctorRealtimeInfoDao.getById(
					urlOrder.getDoctorId()).getServiceStatus();
			if (userDao.getById(urlOrder.getDoctorId()).getStatus()
					.equals(UserStatus.able)
					| serviceStatus.equals(DoctorStatus.service)
					| serviceStatus.equals(DoctorStatus.close)) {
				return resultMap.createResult(0, "医生已下线");
			}

			// 根据医生类型确定订单金额
			int doctorId = urlOrder.getDoctorId();

			Doctor doctor = doctorDao.getById(doctorId);
			String doctorType = doctor.getDoctorType();
			Double moneyToPay;
			switch (doctorType) {
			case DoctorType.doctor:
				urlOrder.setPatientPayMoney(ManageConstant.DocterPerServiceMoney);
				moneyToPay = ManageConstant.DocterPerServiceMoney;
				break;
			case DoctorType.expert:
				urlOrder.setPatientPayMoney(ManageConstant.ExpertPerServiceMoney);
				moneyToPay = ManageConstant.ExpertPerServiceMoney;
				break;
			default:
				moneyToPay = 1000000000.0;
				urlOrder.setPatientPayMoney(1000000.0); // 一个大数,如果跑到这一定会报错
			}
			double doctorGetMoney = urlOrder.getPatientPayMoney()
					* ManageConstant.DoctorGetPercent;
			urlOrder.setDoctorGetMoney(doctorGetMoney);
			// 创建订单
			urlOrder.setStatus(OrderStatus.unpaid);
			Integer orderId = (Integer) orderDao.save(urlOrder);
			logger.info("订单创建成功");
			HashMap<String, Object> result = new HashMap<String, Object>();

			result.put("code", 1);
			result.put("msg", "创建成功");
			result.put("orderId", orderId);
			result.put("moneyToPay", moneyToPay);

			return result;

		} catch (Exception e) {
			logger.error(this.getClass() + "", e);
			return resultMap.createResult(0, false);
		}

	}

	@Override
	public HashMap<String, Object> createOrder2(Order urlOrder) {
		Logger logger = Logger.getLogger(PatientBasicServiceImpl.class);
		try {
			if (doctorRealtimeInfoDao.getById(urlOrder.getDoctorId())
					.getServiceStatus().equals(DoctorStatus.close)
					| userDao.getById(urlOrder.getDoctorId()).equals(
							UserStatus.able)) {
				return resultMap.createResult(0, "医生或护士已下线，\n请选择其他医生");
			}

			String hql = "from Order where patientId='"
					+ urlOrder.getPatientId() + "'";
			List<Order> orderList = orderDao.findPage(hql, null);
			if (orderList.size() > 0) {
				for (Order order : orderList) {
					if (order.getStatus().equals(OrderStatus.unreceived)) {
						return resultMap.createResult(0, "请不要重复下单");
					}
					if (order.getStatus().equals(OrderStatus.received)) {
						return resultMap.createResult(0, "您的订单已提交，\n请耐心等待医生处理");
					}
					if (order.getStatus().equals(OrderStatus.finished)) {
						return resultMap.createResult(0, "订单已完成，请评价订单后再次下单");
					}
				}
			}
			if (doctorRealtimeInfoDao.getById(urlOrder.getDoctorId())
					.getServiceStatus().equals(DoctorStatus.service)) {
				return resultMap.createResult(0, "医生或护士已在服务中，\n请选择其他医生");
			}
			User patient = getUserByOrder(urlOrder);
			if (patient == null) {
				return resultMap.createResult(0, "patientId(UserId)不对哦");
			}
			String serviceStatus = doctorRealtimeInfoDao.getById(
					urlOrder.getDoctorId()).getServiceStatus();
			if (userDao.getById(urlOrder.getDoctorId()).getStatus()
					.equals(UserStatus.able)
					| serviceStatus.equals(DoctorStatus.service)
					| serviceStatus.equals(DoctorStatus.close)) {
				return resultMap.createResult(0, "医生已下线");
			}

			// 根据医生类型确定订单金额
			int doctorId = urlOrder.getDoctorId();

			Doctor doctor = doctorDao.getById(doctorId);
			String doctorType = doctor.getDoctorType();
			Double moneyToPay;
			switch (doctorType) {
			case DoctorType.doctor:
				urlOrder.setPatientPayMoney(ManageConstant.DocterPerServiceMoney);
				moneyToPay = ManageConstant.DocterPerServiceMoney;
				break;
			case DoctorType.expert:
				urlOrder.setPatientPayMoney(ManageConstant.ExpertPerServiceMoney);
				moneyToPay = ManageConstant.ExpertPerServiceMoney;
				break;
			default:
				moneyToPay = 1000000000.0;
				urlOrder.setPatientPayMoney(1000000.0); // 一个大数,如果跑到这一定会报错
			}
			double doctorGetMoney = urlOrder.getPatientPayMoney()
					* ManageConstant.DoctorGetPercent;
			urlOrder.setDoctorGetMoney(doctorGetMoney);
			// 创建订单
			urlOrder.setStatus(OrderStatus.unpaid);
			Integer orderId = (Integer) orderDao.save(urlOrder);
			logger.info("订单创建成功");
			HashMap<String, Object> result = new HashMap<String, Object>();

			result.put("code", 1);
			result.put("msg", "创建成功");
			result.put("orderId", orderId);
			result.put("moneyToPay", moneyToPay);

			return result;

		} catch (Exception e) {
			logger.error(this.getClass() + "", e);
			return resultMap.createResult(0, false);
		}

	}

	@Override
	public HashMap<String, Object> cancelOrder(int orderId) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			Order order = orderDao.getById(orderId);
			if (order.getStatus().equals(OrderStatus.finished)) {
				return resultMap.createResult(0, "订单状态已完成");
			}
			if (order.getStatus().equals(OrderStatus.evaluated)) {
				return resultMap.createResult(0, "订单已评价");
			}
			order.setStatus(OrderStatus.canceled);
			DoctorRealtimeInfo doctorRealtimeInfo = doctorRealtimeInfoDao
					.getById(order.getDoctorId());
			doctorRealtimeInfo.setServiceStatus(DoctorStatus.open);
			doctorRealtimeInfoDao.update(doctorRealtimeInfo);
			orderDao.update(order);

			Doctor doctor = doctorDao.getById(order.getDoctorId());
			User user = userDao.getById(doctor.getDoctorId());
			String deviceToken = user.getDeviceToken();
			String platform = user.getPlatform();
			Integer userId = user.getUserId();
			String userType = user.getUserType();
			String content = "您的病人已取消订单";
			SendNotification.sendNotifiction(deviceToken, platform, userId,
					content, userType, null);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			logger.error("cancelOrder", e);
			return resultMap.createResult(0, false);
		}

	}

	@Override
	public HashMap<String, Object> cancelOrder2(int orderId, String reason) {
		try {
			Order order = orderDao.getById(orderId);
			if (order.getStatus().equals(OrderStatus.finished)) {
				return resultMap.createResult(0, "订单状态已完成");
			}
			if (order.getStatus().equals(OrderStatus.evaluated)) {
				return resultMap.createResult(0, "订单已评价");
			}
			order.setStatus(OrderStatus.canceled);
			order.setReason(reason);
			DoctorRealtimeInfo doctorRealtimeInfo = doctorRealtimeInfoDao
					.getById(order.getDoctorId());
			doctorRealtimeInfo.setServiceStatus(DoctorStatus.open);
			doctorRealtimeInfoDao.update(doctorRealtimeInfo);
			orderDao.update(order);

			try {
				// 推送
				Integer doctorId = order.getDoctorId();
				User byId = userDao.getById(doctorId);
				String deviceToken = byId.getDeviceToken();
				SendNotification.sendNotifiction(deviceToken,
						byId.getPlatform(), byId.getUserId(), "您的病人已取消订单",
						byId.getUserType(), null);
				return resultMap.createResult(1, "取消订单成功");
			} catch (Exception e) {
				return resultMap.createResult(0, e.toString());
			}

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> payOrder(int orderId) {
		Logger logger = Logger.getLogger(PatientOrderServiceImpl.class);
		Order order = orderDao.getById(orderId);

		if (order == null) {
			return resultMap.createResult(0, "传入订单号不正确");
		}

		if (!order.getStatus().equals(OrderStatus.unpaid)) {
			return resultMap.createResult(0, "订单状态应该为未支付");
		}

		User patient = order.getPatient();

		if (patient == null) {
			return resultMap.createResult(0, "patientId(UserId)不对哦");
		}
		// 扣余额
		Account patientAccount = patient.getAccount();
		double currentBalance = patientAccount.getBalance();

		if (currentBalance < order.getPatientPayMoney()) {
			return resultMap.createResult(0, "余额不足请先充值");
		}

		patientAccount.setBalance(currentBalance - order.getPatientPayMoney());
		accountDao.update(patientAccount);
		// 记录
		AccountOperateHistory aAccountOperateHistory = new AccountOperateHistory(
				order.getPatientId(), AccountOperateType.callDoctor,
				order.getPatientPayMoney(), order.getOrderId(), new Date());
		accountOperateHistoryDao.save(aAccountOperateHistory);
		// 更新订单
		order.setStatus(OrderStatus.unreceived);
		orderDao.update(order);
		try {
			User user = userDao.getById(order.getDoctorId());
			String deviceToken = user.getDeviceToken();
			String platform = user.getPlatform();
			Integer userId = user.getUserId();
			String userType = user.getUserType();
			String content = "您有新的订单，请及时处理";
			SendNotification.sendNotifiction(deviceToken, platform, userId,
					content, userType, null);
			return resultMap.createResult(1, "下单成功");
		} catch (Exception e) {
			logger.error("payOrder", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> payOrder2(int orderId) {
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		resultsMap.put("orderId", orderId);
		Logger logger = Logger.getLogger(PatientOrderServiceImpl.class);
		Order order = orderDao.getById(orderId);

		if (order == null) {
			return resultMap.createResult(0, "传入订单号不正确");
		}

		if (!order.getStatus().equals(OrderStatus.unpaid)) {
			return resultMap.createResult(0, "订单状态应该为未支付");
		}

		User patient = userDao.getById(order.getPatientId());

		if (patient == null) {
			return resultMap.createResult(0, "patientId(UserId)不对哦");
		}
		// 扣余额
		Account patientAccount = patient.getAccount();
		double currentBalance = patientAccount.getBalance();

		if (currentBalance < order.getPatientPayMoney()) {
			return resultMap.createResult(0, "余额不足请先充值");
		}

		patientAccount.setBalance(currentBalance - order.getPatientPayMoney());
		accountDao.update(patientAccount);
		// 记录
		AccountOperateHistory aAccountOperateHistory = new AccountOperateHistory(
				order.getPatientId(), AccountOperateType.callDoctor,
				order.getPatientPayMoney(), order.getOrderId(), new Date());
		accountOperateHistoryDao.save(aAccountOperateHistory);
		// 更新订单
		order.setStatus(OrderStatus.unreceived);
		orderDao.update(order);
		try {
			User user = userDao.getById(order.getDoctorId());
			String deviceToken = user.getDeviceToken();
			String platform = user.getPlatform();
			Integer userId = user.getUserId();
			String userType = user.getUserType();
			String content = "您有新的订单，请及时处理";
			SendNotification.sendNotifiction(deviceToken, platform, userId,
					content, userType, null);
			return resultMap.createResult(1, resultsMap);
		} catch (Exception e) {
			logger.error("payOrder", e);
			return resultMap.createResult(0, false);
		}
	}

	//
	// @Override
	// public HashMap<String, Object> evaluateOrder(Order urlOrder, double
	// reviewstarLevel) {
	//
	// try {
	// if (reviewstarLevel > 5 | reviewstarLevel < 0) {
	// return resultMap.createResult(0, "星级大于0 小于5");
	// }
	//
	// String evalutaion = urlOrder.getEvaluation();
	// int orderId = urlOrder.getOrderId();
	//
	// Order order = orderDao.getById(orderId);
	// Evaluat evaluat=evaluatDao.getById(orderId);
	//
	// if (!order.getStatus().equals(OrderStatus.finished)) {
	// return resultMap.createResult(0, "订单状态应该为已完成");
	// }
	//
	// order.setEvaluation(evalutaion);
	// order.setStatus(OrderStatus.evaluated);
	// order.setStarLevel((int) (reviewstarLevel + 0.5));
	// orderDao.update(order);
	//
	// int doctorId = order.getDoctorId();
	// Doctor doctor = doctorDao.getById(doctorId);
	// double beforeStarLevel = doctor.getStarLevel();
	//
	// int serviceCount = doctor.getDoctorRealtimeInfo().getServiceCount();
	// double afterStarLevel = (beforeStarLevel * (serviceCount - 1) +
	// reviewstarLevel) / serviceCount;
	//
	// doctor.setStarLevel(afterStarLevel);
	//
	// return resultMap.createResult(1, "评价成功");
	//
	// } catch (Exception e) {
	// return resultMap.createResult(0, e.toString());
	// }
	// }

	@Override
	public HashMap<String, Object> evaluateOrder(int orderId,
			String evaluation, double reviewstarLevel) {
		Logger logger = Logger.getLogger(PatientOrderServiceImpl.class);

		try {
			if ((reviewstarLevel > 5 | reviewstarLevel < 0)) {
				return resultMap.createResult(0, "星级大于0 小于5");
			}
			Order order = orderDao.getById(orderId);
			Evaluat evaluat = evaluatDao.getByOrderId(orderId);
			if (evaluat == null) {
				evaluat = new Evaluat();
			}

			String hql = "from Evaluat where orderId='"
					+ (int) order.getOrderId() + "'";
			if (evaluatDao.findPage(hql, null).size() > 0) {
				return resultMap.createResult(0, "订单已评价");
			}

			if (!order.getStatus().equals(OrderStatus.finished)) {
				return resultMap.createResult(0, "订单状态应为已完成");
			}

			evaluat.setOrderId(order.getOrderId());
			evaluat.setDoctorId(order.getDoctorId());
			evaluat.setPatientId(order.getPatientId());
			evaluat.setPatientName(order.getPatient().getUserName());
			evaluat.setDoctorName(order.getDoctor().getUser().getUserName());
			evaluat.setEvaluation(evaluation);
			evaluat.setStarLevel((int) (reviewstarLevel + 0.5));
			evaluat.setCreateTime(new Date());
			evaluatDao.saveOrUpdate(evaluat);

			order.setStatus(OrderStatus.evaluated);
			orderDao.update(order);

			int doctorId = evaluat.getDoctorId();
			Doctor doctor = doctorDao.getById(doctorId);
			double beforeStarLevel = doctor.getStarLevel();

			int serviceCount = doctor.getDoctorRealtimeInfo().getServiceCount();
			double afterStarLevel;
			if (serviceCount == 0) {
				afterStarLevel = beforeStarLevel * (serviceCount - 1)
						+ reviewstarLevel;
			} else {
				afterStarLevel = (beforeStarLevel * (serviceCount - 1) + reviewstarLevel)
						/ serviceCount;
			}

			doctor.setStarLevel(afterStarLevel);
			doctorDao.saveOrUpdate(doctor);
			logger.info(evaluation);
			return resultMap.createResult(1, "评价成功");
		} catch (Exception e) {
			logger.error("evaluateOrder", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public List<Map<String, Object>> getCurrentOrder(int patientId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("patientId", patientId);
		String hql = "from Order where patientId=:patientId and status in ('unpaid','received','unreceived') order by createTime desc";
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

	@Override
	public List<Map<String, Object>> getCurrentOrder1(int patientId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patientId", patientId);
		String hql = "from Order where patientId=:patientId and status in ('unpaid','received','unreceived') order by createTime desc";
		List<OrderInfo> orderInfoList = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo Info : orderInfoList) {
			result = new HashMap<String, Object>();
			result.put("orderId", Info.getOrderId());
			result.put("status", Info.getStatus());
			result.put("serviceTime", Info.getServiceTime());
			result.put("createTime", Info.getCreateTime());
			result.put("finishTime", Info.getFinishTime());
			result.put("statusTime", Info.getStatusTime());
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

	@Override
	public List<Map<String, Object>> getFinishOrder(int patientId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("patientId", patientId);
		String hql = "from Order where patientId=:patientId and status in ('canceled','finished','evaluated') order by createTime desc";
		List<OrderInfo> orderInfoList = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo orderInfo : orderInfoList) {
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
			int patientInfoId = orderDao.getById(orderInfo.getOrderId())
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

	@Override
	public List<Map<String, Object>> getFinishOrder1(int patientId) {
		Logger logger = Logger.getLogger(PatientOrderServiceImpl.class);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patientId", patientId);
		String hql = "from Order where patientId=:patientId and status in ('canceled','finished','evaluated') order by createTime desc";
		List<OrderInfo> orderInfoList = orderDao.getOrderInfoList(hql, params);
		for (OrderInfo orderInfo : orderInfoList) {
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
			int patientInfoId = orderDao.getById(orderInfo.getOrderId())
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

	@Override
	public HashMap<String, Object> getOrderDetail(int orderId) {
		try {
			HashMap<String, Object> info = new HashMap<String, Object>();
			Evaluat evaluat = evaluatDao.getByOrderId(orderId);
			if (evaluat != null) {
				info.put("evaluation", evaluat.getEvaluation());
				info.put("starLevel", evaluat.getStarLevel());
				info.put("evaluatTime", evaluat.getCreateTime());
			}
			OrderInfo orderInfo = new OrderInfo(orderDao.getById(orderId));
			info.put("status", orderInfo.getStatus());
			info.put("serviceTime", orderInfo.getServiceTime());
			info.put("orderCreateTime", orderInfo.getCreateTime());
			info.put("doctorName", orderInfo.getDoctorName());
			info.put("doctorPhone", orderInfo.getDoctorPhone());
			info.put("PatientPayMoney", orderInfo.getPatientPayMoney());
			info.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			info.put("illnessDesc", orderInfo.getIllnessDesc());
			info.put("checkResult", orderInfo.getCheckResult());
			info.put("prescription", orderInfo.getPrescription());
			info.put("patientName", orderInfo.getPatientName());
			info.put("patientPhone", orderInfo.getPatientPhone());
			int patientInfoId = orderDao.getById(orderInfo.getOrderId())
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
				info.put("patientInfo", patientInfo);
			} else {
				info.put("patientInfo", null);
			}
			info.put("orderType", orderInfo.getOrderType());
			return resultMap.createResult(1, info);
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString()
					+ "如果是nullpointer 一般是订单Id不对");
		}
	}

	@Override
	public HashMap<String, Object> getOrderDetail1(int orderId) {
		try {
			HashMap<String, Object> info = new HashMap<String, Object>();
			Evaluat evaluat = evaluatDao.getByOrderId(orderId);
			if (evaluat != null) {
				info.put("evaluation", evaluat.getEvaluation());
				info.put("starLevel", evaluat.getStarLevel());
				info.put("evaluatTime", evaluat.getCreateTime());
			}
			OrderInfo orderInfo = new OrderInfo(orderDao.getById(orderId));
			info.put("orderId", orderId);
			info.put("status", orderInfo.getStatus());
			info.put("serviceTime", orderInfo.getServiceTime());
			info.put("orderCreateTime", orderInfo.getCreateTime());
			info.put("finishTime", orderInfo.getFinishTime());
			info.put("statusTime", orderInfo.getStatusTime());
			info.put("doctorName", orderInfo.getDoctorName());
			info.put("doctorPhone", orderInfo.getDoctorPhone());
			info.put("PatientPayMoney", orderInfo.getPatientPayMoney());
			info.put("doctorGetMoney", orderInfo.getDoctorGetMoney());
			info.put("illnessDesc", orderInfo.getIllnessDesc());
			info.put("checkResult", orderInfo.getCheckResult());
			info.put("prescription", orderInfo.getPrescription());
			info.put("patientName", orderInfo.getPatientName());
			info.put("patientPhone", orderInfo.getPatientPhone());
			int patientInfoId = orderDao.getById(orderInfo.getOrderId())
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
				info.put("patientInfo", patientInfo);
			} else {
				info.put("patientInfo", null);
			}
			info.put("orderType", orderInfo.getOrderType());

			return resultMap.createResult(1, info);
		} catch (Exception e) {
			return resultMap.createResult(0, "如果是nullpointer 一般是订单Id不对");
		}
	}

	private User getUserByOrder(Order urlOrder) {
		try {
			int patientId = urlOrder.getPatientId();
			User user = userDao.getById(patientId);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	// @Override
	// public List<Evaluat> getEvaluation(int doctorId) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("doctorId", doctorId);
	// String hql = "select * from Evaluat where doctorId=:doctorId";
	//
	// return evaluatDao.getEvaluatList(hql, params);
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getEvaluation(int doctorId, int orderId) {

		if (doctorId == 0) {
			return (List<Map<String, Object>>) resultMap.createResult(0,
					"doctorId 不能为空");
		}
		try {
			Logger logger = Logger.getLogger(PatientOrderServiceImpl.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("doctorId", doctorId);
			String hql = null;
			if (orderId != 0) {
				params.put("orderId", orderId);
				hql = "from Evaluat where doctorId=:doctorId and orderId=:orderId order by createTime desc";
			} else if (orderId == 0) {
				hql = "from Evaluat where doctorId=:doctorId order by createTime desc";
			}
			List<Evaluat> evaluatList = evaluatDao.getEvaluatList(hql, params);
			List<Map<String, Object>> evalList = new ArrayList<Map<String, Object>>();
			for (Evaluat evaluat : evaluatList) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("patientName", evaluat.getPatientName());
				result.put("starLevel", evaluat.getStarLevel());
				result.put("createTime", evaluat.getCreateTime());
				result.put("evaluation", evaluat.getEvaluation());
				evalList.add(result);
				logger.info(result);
			}
			logger.info("评论发送成功");
			return evalList;

		} catch (Exception e) {
			return (List<Map<String, Object>>) resultMap.createResult(0,
					e.toString());
		}

	}

	@Override
	public HashMap<String, Object> getEvaluation1(int doctorId, int orderId) {

		if (doctorId == 0) {
			return resultMap.createResult(0, "doctorId 不能为空");
		}
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("doctorId", doctorId);
			String hql = null;
			if (orderId != 0) {
				params.put("orderId", orderId);
				hql = "from Evaluat where doctorId=:doctorId and orderId=:orderId order by createTime desc";
			} else if (orderId == 0) {
				hql = "from Evaluat where doctorId=:doctorId order by createTime desc";
			}
			List<Evaluat> evaluatList = evaluatDao.getEvaluatList(hql, params);
			List<Map<String, Object>> evalList = new ArrayList<Map<String, Object>>();
			for (Evaluat evaluat : evaluatList) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				result.put("patientName", evaluat.getPatientName());
				result.put("starLevel", evaluat.getStarLevel());
				result.put("createTime", evaluat.getCreateTime());
				result.put("evaluation", evaluat.getEvaluation());
				evalList.add(result);
				logger.info(result);
			}
			logger.info("评论发送成功");
			resultsMap.put("evalList", evalList);
			return resultMap.createResult(1, resultsMap);
		} catch (Exception e) {
			logger.error("getEvaluation2", e);
			return resultMap.createResult(0, false);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getEvaluation2(int doctorId, int orderId,
			int pageNo, int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		if (doctorId == 0) {
			return resultMap.createResult(0, "doctorId 不能为空");
		}
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("doctorId", doctorId);
			String hql = null;
			if (orderId != 0) {
				params.put("orderId", orderId);
				hql = "from Evaluat where doctorId=:doctorId and orderId=:orderId order by createTime desc";
			} else if (orderId == 0) {
				hql = "from Evaluat where doctorId=:doctorId order by createTime desc";
			}
			HashMap<String, Object> evaluatMap = evaluatDao.findPage(hql,
					params, pageNo, pageSize);
			List<Evaluat> evaluatList = (List<Evaluat>) evaluatMap
					.get("result");
			List<Map<String, Object>> evalList = new ArrayList<Map<String, Object>>();
			for (Evaluat evaluat : evaluatList) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("patientName", evaluat.getPatientName());
				result.put("starLevel", evaluat.getStarLevel());
				result.put("createTime", evaluat.getCreateTime());
				result.put("evaluation", evaluat.getEvaluation());
				evalList.add(result);
			}
			resultsMap.put("evalList", evalList);
			resultsMap.put("pageCount", evaluatMap.get("pageCount"));
			return resultMap.createResult(1, resultsMap);
		} catch (Exception e) {
			logger.error("getEvaluation2", e);
			return resultMap.createResult(0, "请求失败");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getFinishOrder2(int patientId, int pageNo,
			int pageSize) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = null;
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("patientId", patientId);
		String hql = "from Order where patientId=:patientId and status in ('canceled','finished','evaluated') order by createTime desc";
		HashMap<String, Object> orderInfoMap = orderDao.findPage(hql, params,
				pageNo, pageSize);
		List<Order> orderList = (List<Order>) orderInfoMap.get("result");
		for (Order order : orderList) {
			OrderInfo orderInfo = new OrderInfo(order);
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
			int patientInfoId = orderDao.getById(orderInfo.getOrderId())
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
		resultsMap.put("orderInfoList", resultList);
		resultsMap.put("pageCount", orderInfoMap.get("pageCount"));
		return resultMap.createResult(1, resultsMap);
	}

	@Override
	public HashMap<String, Object> isHaveOrder(User user) {
		Integer userId = userDao.getByPhoneNo(user.getPhoneNo()).getUserId();
		try {
			String hql = "from Order where patientId='" + userId
					+ "' and status='" + OrderStatus.unreceived + "'";
			List<Order> orderList = orderDao.findPage(hql, null);
			if (orderList.size() > 0) {
				return resultMap.createResult(1, "true");
			} else {
				return resultMap.createResult(1, "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, "false");
		}
	}

	@Override
	public HashMap<String, Object> getMyOrder(String phoneNo) {
		Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
		String orderStatus = "'" + OrderStatus.unreceived + "','"
				+ OrderStatus.finished + "','" + OrderStatus.received + "'";
		String hql = "from Order where patientId='" + (int) userId
				+ "' and status in(" + orderStatus
				+ ") order by createTime desc";
		List<Order> myOrderList = orderDao.findPage(hql, null);
		if (myOrderList.size() == 0) {
			return resultMap.createResult(0, "您当前没有订单");
		} else {
			Order order = myOrderList.get(0);
			HashMap<String, Object> orderDetail = this.getOrderDetail1(order
					.getOrderId());
			return orderDetail;
		}
	}

}
