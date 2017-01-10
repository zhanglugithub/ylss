package ylss.model.table;

import static javax.persistence.GenerationType.IDENTITY;

// Generated 2015-7-23 13:44:09 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * PatientInfo generated by hbm2java
 */
@Entity
@Table(name = "patient_info", catalog = "ylss")
public class PatientInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2960947121268586585L;
	private Integer infoId;
	private String infoName;
	private Integer patientId;
	private String patientName;
	private Date birthday;
	private String contactPhone;
	private String address;
	private String sex;
	private String isDelete = "1";
	private Date addTime;

	private User user;

	public PatientInfo() {

	}

	public PatientInfo(Integer infoId) {
		this.infoId = infoId;
	}

	public PatientInfo(Integer infoId, String infoName, Integer patientId,
			String patientName, Date birthday, String contactPhone,
			String address, String sex, String isDelete, User user) {
		super();
		this.infoId = infoId;
		this.infoName = infoName;
		this.patientId = patientId;
		this.patientName = patientName;
		this.birthday = birthday;
		this.contactPhone = contactPhone;
		this.address = address;
		this.sex = sex;
		this.isDelete = isDelete;
		this.user = user;
	}

	@Column(name = "IsDelete")
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@ManyToOne
	@JoinColumn(name = "PatientId", referencedColumnName = "UserId", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "InfoId", unique = true, nullable = false)
	public Integer getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	@Column(name = "InfoName", length = 32)
	public String getInfoName() {
		return this.infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	@Column(name = "PatientId")
	public Integer getPatientId() {
		return this.patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	@Column(name = "PatientName", length = 16)
	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Birthday", length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "ContactPhone", length = 11)
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "Address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "Sex", length = 5)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setThis(PatientInfo aPatientInfo) {
		setAddress(aPatientInfo.getAddress());
		setBirthday(aPatientInfo.getBirthday());
		setContactPhone(aPatientInfo.getContactPhone());
		setInfoName(aPatientInfo.getInfoName());
		setPatientId(aPatientInfo.getPatientId());
		setPatientName(aPatientInfo.getPatientName());
		setSex(aPatientInfo.getSex());
		setIsDelete(aPatientInfo.getIsDelete());
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addTime", length = 19)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

}