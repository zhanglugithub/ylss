package ylss.test.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import ylss.controller.app.PatientOrderController;
import ylss.model.logic.OrderInfo;
import ylss.test.TestHead;

public class PatientOrderTest extends TestHead {

	@Resource
	PatientOrderController patientOrderController;

	@Test
	public void patientOrderControllerTest() {
		List<Map<String, Object>> currentOrder = patientOrderController
				.getCurrentOrder(336);

		for (Map<String, Object> orderInfo : currentOrder) {
			System.out.println(((OrderInfo) orderInfo).getOrderId());
		}
	}

}
