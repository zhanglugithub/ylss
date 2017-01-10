package ylss.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ylss.dao.OrderDao;
import ylss.model.logic.OrderInfo;
import ylss.model.table.Order;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, Integer> implements
		OrderDao {
	private int pageSize = 10;

	public OrderDaoImpl() {
		super(Order.class);
	}

	@Override
	public HashMap<String, Object> getOrderInfoList(String hql,
			Map<String, Object> params, int pageNum) {
		Logger logger = Logger.getLogger(OrderDaoImpl.class);

		HashMap<String, Object> orderList = super.findPage(hql, params,
				pageNum, pageSize);
		return orderList;
	}

	@Override
	public List<OrderInfo> getOrderInfoList(String hql,
			Map<String, Object> params) {
		List<Order> orderList = super.findPage(hql, params);

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (Order order : orderList) {
			orderInfoList.add(new OrderInfo(order));
		}
		return orderInfoList;
	}

	@Override
	public List<OrderInfo> getOrderInfoList(List<Order> orderList) {

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (Order order : orderList) {
			orderInfoList.add(new OrderInfo(order));
		}
		return orderInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getOrderInfoList2(String hql,
			Map<String, Object> params, int pageNo, int pageSize) {
		HashMap<String, Object> orderMap = super.findPage(hql, params, pageNo,
				pageSize);
		HashMap<String, Object> resutMap = new HashMap<String, Object>();
		List<Order> orderList = (List<Order>) orderMap.get("result");
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (Order order : orderList) {
			orderInfoList.add(new OrderInfo(order));
		}
		resutMap.put("result", orderInfoList);
		resutMap.put("pageCount", orderMap.get("pageCount"));
		return resutMap;
	}
}
