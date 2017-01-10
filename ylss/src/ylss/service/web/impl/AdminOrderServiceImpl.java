package ylss.service.web.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.AccountDao;
import ylss.dao.OrderDao;
import ylss.model.constant.databaseConstant.OrderStatus;
import ylss.model.table.Order;
import ylss.service.web.AdminOrderService;

@Service
@Transactional
public class AdminOrderServiceImpl implements AdminOrderService {

	@Resource
	OrderDao orderDao;
	@Resource
	AccountDao accountDao;
	
	@Override
	public List<Order> listAllOrder(int pageNo, int pageSize) {
		
		return orderDao.getAPage(pageNo, pageSize);
	}

	@Override
	public String cancelOrder(int orderId) { 
		
		Order order=orderDao.getById(orderId);
		if (order==null) {
			return "查无此订单";
		}
		switch (order.getStatus()) {
		case OrderStatus.unreceived:
			return cancelUnreceivedOrder(order);
		case OrderStatus.received:
			return cancelReceivedOrder(order);
		case OrderStatus.finished:
			return cancelFinishOrder(order);
		default:
			return "只处理未接单，未完成，未评价的订单 ";
		}
		
	}
	
	private 	String  cancelUnreceivedOrder(Order order) {
		int userId= order.getPatient().getUserId();
		accountDao.addMoney(userId, order.getPatientPayMoney());
		order.setStatus(OrderStatus.sCanceled);
		orderDao.update(order);
		return "取消成功";
	}
	private String  cancelReceivedOrder(Order order) {

		int userId= order.getPatient().getUserId();
		accountDao.addMoney(userId, order.getPatientPayMoney());
		order.setStatus(OrderStatus.sCanceled);
		orderDao.update(order);
		return "取消成功";
	}
	private String  cancelFinishOrder(Order order) {
	
		int doctorId=	 order.getDoctor().getDoctorId();
		int userId= order.getPatient().getUserId();
		accountDao.addMoney(userId, order.getPatientPayMoney());
		accountDao.subMoney(doctorId, order.getDoctorGetMoney());
		order.setStatus(OrderStatus.sCanceled);
		orderDao.update(order);
		return "取消成功";
	}

	@Override
	public Order queryOrder(int orderId) {
		return orderDao.getById(orderId);
	}

	@Override
	public int getOrderNo() {
		return (int)orderDao.countAll();
	}
}
