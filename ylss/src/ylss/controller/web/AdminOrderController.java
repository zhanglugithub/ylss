package ylss.controller.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ylss.model.table.Order;
import ylss.service.web.AdminOrderService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

	@Autowired
	@Resource
	AdminOrderService aAdminOrderService;

	@RequestMapping("/listAllOrder.do")
	public Object listAllOrder(ModelMap model,int pageNo,int pageSize) {
		List<Order> orderList =aAdminOrderService.listAllOrder(pageNo,pageSize);
		model.addAttribute("orderList",orderList);
		model.addAttribute("totalNo",aAdminOrderService.getOrderNo());
		return "listAllOrder";
	}

	@RequestMapping("/cancelOrder.do")
	public Object cancelOrder(int orderId, ModelMap model) {

		String msg=	 aAdminOrderService.cancelOrder(orderId);
		model.addAttribute("msg",msg);	
		return "cancelOrder";
	}
	
	@RequestMapping("queryOrder.do") 
	public Object queryOrder(int orderId,ModelMap model) {
		Order order=	aAdminOrderService.queryOrder(orderId);
		model.addAttribute("order", order);
		if (order==null) {
			model.addAttribute("msg","并没有该订单，订单号错了吧");			
		}
		return "queryOrder";
	}

}
