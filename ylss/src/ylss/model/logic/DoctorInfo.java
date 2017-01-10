package ylss.model.logic;

import java.util.Date;

import ylss.model.table.Doctor;

/**
 * 病人查看医生详细信息时的模型
 */
public class DoctorInfo {

	@SuppressWarnings("unused")
	private Doctor aDoctor;

	private int doctorId;
	private String doctorNumber;
	private String doctorType;
	private String doctorPhone;

	private Date birthday;
	private String sex;
	private String qualificationNo;
	private Integer qualification;// 临床经验

	private String department;// 科室
	private double starLevel;
	private String hospital;
	private Double longitude;
	private Double latitude;
	private String specialty;
	private int serviceTime;
	private String doctorName;
	private boolean haveIcon;
	private String serviceStatus;
	private int serviceCount;
	private String idCard;
	private String alipayNo;

	public DoctorInfo(Doctor aDoctor) {
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
		this.specialty = aDoctor.getSpecialty();
		this.starLevel = aDoctor.getStarLevel();
		this.serviceTime = aDoctor.getDoctorRealtimeInfo().getServiceCount();
		this.setDoctorPhone(aDoctor.getUser().getPhoneNo());
		this.department = aDoctor.getDepartment();
		this.latitude = aDoctor.getDoctorRealtimeInfo().getLatitude();
		this.longitude = aDoctor.getDoctorRealtimeInfo().getLongitude();
		this.serviceStatus = aDoctor.getDoctorRealtimeInfo().getServiceStatus();
		this.serviceCount = aDoctor.getDoctorRealtimeInfo().getServiceCount();
		this.idCard = aDoctor.getIdCard();
		this.alipayNo = aDoctor.getAlipayNo();
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

	public String getDoctorPhone() {
		return doctorPhone;
	}

	public void setDoctorPhone(String doctorPhone) {
		this.doctorPhone = doctorPhone;
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

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public boolean isHaveIcon() {
		return haveIcon;
	}

	public void setHaveIcon(boolean haveIcon) {
		this.haveIcon = haveIcon;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

}
