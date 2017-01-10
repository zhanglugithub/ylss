package ylss.model.logic;

import java.util.Date;

import ylss.model.table.Order;

public class SickHistory {

	public Date serviceTime;
	public String illDesc;
	public String prescription;

	public SickHistory(Order order) {

		this.serviceTime = order.getServiceTime();
		this.illDesc =order.getIllnessDesc();
		this.prescription = order.getPrescription();

	}

}
