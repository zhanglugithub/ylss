package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ylss.model.table.Order;
import ylss.service.app.PatientOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:springMVC-servlet.xml" })
public class PatientOrderServiceImplTest {

	private int pageNum;

	HashMap<String, Object> result;
	private int orderId = 1430;

	private int patientId = 2;
	@Resource
	PatientOrderService patientOrderService;

	@Test
	public void testOrder() {
		Map<String, Object> payOrder = patientOrderService.payOrder(orderId);
		System.out.println(payOrder);
	}

	@Test
	public void testCreateOrder() {
		Order urlOrder = new Order();

		urlOrder.setPatientId(336);
		urlOrder.setDoctorId(356);
		urlOrder.setPatientInfoId(312);
		urlOrder.setServiceTime(new Date());
		urlOrder.setIllnessDesc("公公偏头痛");
		result = patientOrderService.createOrder(urlOrder);
		System.out.println(result);

		assertEquals(1, result.get("code"));

	}

	@Test
	public void testCancelOrder() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPayOrder() {
		result = patientOrderService.payOrder(orderId);
		System.out.println(result.get("msg"));
		assertEquals(1, result.get("code"));

	}

	@Test
	public void testEvaluateOrder() {
		Order urlOrder = new Order();

		urlOrder.setOrderId(orderId);

		result = patientOrderService.evaluateOrder(urlOrder.getOrderId(),"还好吧啊啊", 4);
		System.out.println(result);
	}

	@Test
	public void testGetCurrentOrder() {
		int currentOrderSize = patientOrderService.getCurrentOrder(patientId).size();
		assertEquals(1, currentOrderSize);
	}

	@Test
	public void testGetFinishOrder() {
		int currentOrderSize = patientOrderService.getFinishOrder(patientId).size();
		assertEquals(2, currentOrderSize);
	}

	@Test
	public void testGetOrderDetail() {
//		result = patientOrderService.getOrderDetail(orderId);
//		assertEquals(1, result.get("code"));
	}

}
