package ylss.model.logic;

import java.io.Serializable;
import java.util.Date;

import ylss.model.table.Order;
import ylss.model.table.PatientInfo;

public class OrderInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -360967581952262853L;

	@SuppressWarnings("unused")
	private Order order;

	private int orderId;

	private String status;
	private Date serviceTime;
	private Date createTime;
	private Date finishTime;
	private Date statusTime;

	private String patientName;
	private String doctorName;
	private String doctorPhone;
	private String patientPhone;

	private double doctorGetMoney;
	private double patientPayMoney;
	private String illnessDesc;
	private int orderType;

	private String checkResult;
	private String prescription;

	private String evaluation;
	private int starLevel;

	private PatientInfo patientInfo;

	public OrderInfo(Order order) {

		this.orderId = order.getOrderId();
		this.status = order.getStatus();
		this.serviceTime = order.getServiceTime();
		this.createTime = order.getCreateTime();
		this.finishTime = order.getFinishTime();
		this.statusTime = order.getStatusTime();

		this.doctorName = order.getDoctor().getUser().getUserName();
		this.doctorPhone = order.getDoctor().getUser().getPhoneNo();
		this.setPatientPayMoney(order.getPatientPayMoney());
		this.doctorGetMoney = order.getDoctorGetMoney();
		this.illnessDesc = order.getIllnessDesc();
		this.checkResult = order.getCheckResult();
		this.prescription = order.getPrescription();
		// this.evaluation = order.getEvaluation();
		this.patientName = order.getPatient().getUserName();
		this.patientPhone = order.getPatient().getPhoneNo();
		this.orderType = order.getOrderType();
		// if (order.getStarLevel()==null) {
		// this.starLevel=0;
		// } else {
		// this.starLevel=order.getStarLevel();
		// }

	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorPhone() {
		return doctorPhone;
	}

	public void setDoctorPhone(String doctorPhone) {
		this.doctorPhone = doctorPhone;
	}

	public String getIllnessDesc() {
		return illnessDesc;
	}

	public void setIllnessDesc(String illnessDesc) {
		this.illnessDesc = illnessDesc;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PatientInfo getPatientInfo() {
		return patientInfo;
	}

	public void setPatientInfo(PatientInfo patientInfo) {
		this.patientInfo = patientInfo;
	}

	public double getPatientPayMoney() {
		return patientPayMoney;
	}

	public void setPatientPayMoney(double patientPayMoney) {
		this.patientPayMoney = patientPayMoney;
	}

	public double getDoctorGetMoney() {
		return doctorGetMoney;
	}

	public void setDoctorGetMoney(double doctorGetMoney) {
		this.doctorGetMoney = doctorGetMoney;
	}

	public int getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

}
