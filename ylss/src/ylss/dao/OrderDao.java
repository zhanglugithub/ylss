package ylss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ylss.model.logic.OrderInfo;
import ylss.model.table.Order;	

public interface OrderDao extends BaseDao<Order, Integer> {

	public List<OrderInfo> getOrderInfoList(String hql,
			Map<String, Object> params);

	public List<OrderInfo> getOrderInfoList(List<Order> orderList);

	public HashMap<String, Object> getOrderInfoList(String hql,
			Map<String, Object> params, int pageNum);

	public HashMap<String, Object> getOrderInfoList2(String hql,
			Map<String, Object> params, int pageNo, int pageSize);

}
