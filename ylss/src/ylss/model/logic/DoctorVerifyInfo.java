package ylss.model.logic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

//import java.util.Set;
import ylss.model.table.Doctor;

//import ylss.model.table.Order;

/**
 * 医生要认证时的模型
 */
public class DoctorVerifyInfo {

	@SuppressWarnings("unused")
	private Doctor aDoctor;

	private int doctorId;
	private String doctorNumber;
	private String doctorType;

	private Date birthday;
	private String sex;
	private String qualificationNo;
	private Integer qualification;

	private String department;
	private double starLevel;
	private String hospital;

	private String specialty;

	private String alipayNo;
	private String idCard;
	private String status;

	// status idCard AlipayNo status

	private int serviceTime;

	private String doctorName;
	private boolean haveIcon;

	public interface WithVerifyInfo {
	};

	// private Set<Order> orderList;

	public DoctorVerifyInfo(Doctor aDoctor) {
		this.aDoctor = aDoctor;
		this.doctorId = aDoctor.getDoctorId();
		this.doctorNumber = aDoctor.getDoctorNumber();
		this.birthday = aDoctor.getBirthday();
		this.doctorName = aDoctor.getUser().getUserName();
		this.haveIcon = aDoctor.getUser().getHaveIcon();
		this.doctorType = aDoctor.getDoctorType();
		this.hospital = aDoctor.getHospital();
		this.qualificationNo = aDoctor.getQualificationNo(); // 证书编号
		this.qualification = aDoctor.getQualification(); // 临床经验
		this.sex = aDoctor.getSex();
		
		
		
		this.setSpecialty(aDoctor.getSpecialty());
		this.starLevel = aDoctor.getStarLevel();

		if (aDoctor.getDoctorRealtimeInfo() == null) {
			this.serviceTime = 0;

		} else {
			this.serviceTime = aDoctor.getDoctorRealtimeInfo()
					.getServiceCount();
		}
		this.department = aDoctor.getDepartment();
		this.setAlipayNo(aDoctor.getAlipayNo());
		this.status = aDoctor.getStatus();
		this.setIdCard(aDoctor.getIdCard());

		// this.orderList=aDoctor.getOrderList();
	}

	public void setaDoctor(Doctor aDoctor) {
		this.aDoctor = aDoctor;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorNumber() {
		return doctorNumber;
	}

	public void setDoctorNumber(String doctorNumber) {
		this.doctorNumber = doctorNumber;
	}

	public String getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(String doctorType) {
		this.doctorType = doctorType;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getQualificationNo() {
		return qualificationNo;
	}

	public void setQualificationNo(String qualificationNo) {
		this.qualificationNo = qualificationNo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(double starLevel) {
		this.starLevel = starLevel;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public boolean isHaveIcon() {
		return haveIcon;
	}

	public void setHaveIcon(boolean haveIcon) {
		this.haveIcon = haveIcon;
	}

	@JsonView(WithVerifyInfo.class)
	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	// public Set<Order> getOrderList() {
	// return orderList;
	// }
	// public void setOrderList(Set<Order> orderList) {
	// this.orderList = orderList;
	// }

}
