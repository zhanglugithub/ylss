package ylss.service.web;

import java.util.List;

import ylss.model.table.Order;

public interface AdminOrderService {
	public List<Order> listAllOrder(int pageNo, int pageSize); // 此处order 与 user的listAllOrder用相同model

	public int getOrderNo();
	
	public Order queryOrder(int orderId);
	
	public String cancelOrder(int orderId);
}
