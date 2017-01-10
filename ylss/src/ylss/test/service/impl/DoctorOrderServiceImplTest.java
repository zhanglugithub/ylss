package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.model.logic.SickHistory;
import ylss.model.table.Doctor;
import ylss.model.table.Order;
import ylss.service.app.DoctorOrderService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class DoctorOrderServiceImplTest {
	
	private int pageNum;
	
	@Resource
	DoctorOrderService doctorOrderService;

	Doctor aDoctor;
	int doctorId=62;
	int patientId=2;
	HashMap<String, Object> result;

	int orderId=33;
	
	
	@Test
	public void testGetSickHistory() {
		List<SickHistory> sickHistories=	doctorOrderService.getSickHistory(patientId);
		assertEquals(2,sickHistories.size());
	}

	@Test
	public void testReceiveOrder() {
		
		
		result=doctorOrderService.receiveOrder(orderId);
		assertEquals(1, result.get("code"));
	}

	@Test
	public void testFinishOrder() {
		Order urlOrder =new Order(orderId);
		urlOrder.setPrescription("呵呵呵呵呵呵呵呵 还是去医院把....");
		result=	doctorOrderService.finishOrder(urlOrder);
		assertEquals(1, result.get("code"));
		
	}

	@Test
	public void testGetCurrentOrder() {
////	 List<OrderInfo> orders= doctorOrderService.getCurrentOrder(2);
//	 assertNotNull(orders);
	}

	@Test
	public void testGetFinishOrder() {
//		 List<OrderInfo> orders= doctorOrderService.getFinishOrder(doctorId);
//		 assertEquals(2, orders.size());
	}

	@Test
	public void testGetOrderDetail() {
		result= doctorOrderService.getOrderDetail(orderId);
		assertEquals(1, result.get("code"));
	
	}

}
