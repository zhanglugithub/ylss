package ylss.model.table;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "evaluat", catalog = "ylss")
public class Evaluat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2566257282885990043L;

	private Integer evaluatId;
	private Integer orderId;
	private Integer patientId;
	private Integer doctorId;
	private String patientName;
	private String doctorName;
	private String evaluation;
	private Integer starLevel;
	private Date createTime;

	private Order order;
	private Doctor doctor;

	public Evaluat(Integer evaluatId, Integer orderId, Integer patientId,
			Integer doctorId, String patientName, String doctorName,
			String evaluation, Integer starLevel, Date createTime, Order order,
			Doctor doctor) {
		super();
		this.evaluatId = evaluatId;
		this.orderId = orderId;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.evaluation = evaluation;
		this.starLevel = starLevel;
		this.createTime = createTime;
		this.order = order;
		this.doctor = doctor;
	}

	// private User user;

	// private PhoneOrder phoneOrder;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Evaluat(Integer evaluatId) {
		this.evaluatId = evaluatId;
	}

	public Evaluat() {
	}

	@Id
	@Column(name = "EvaluatId", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	public Integer getEvaluatId() {
		return evaluatId;
	}

	public void setEvaluatId(Integer evaluatId) {
		this.evaluatId = evaluatId;
	}

	@Column(name = "OrderId")
	@NotNull(message = "OrderId 不能为空")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "PatientId")
	@NotNull(message = "PatientId 不能为空")
	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	@Column(name = "DoctorId")
	@NotNull(message = "DoctorId 不能为空")
	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	@ManyToOne
	@JoinColumn(name = "OrderId", nullable = false, insertable = false, updatable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne
	@JoinColumn(name = "DoctorId", nullable = false, insertable = false, updatable = false)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	// @ManyToOne
	// @JoinColumn(name = "PatientId", referencedColumnName = "UserId", nullable
	// = false, insertable = false, updatable = false)
	// public User getUser() {
	// return user;
	// }
	//
	// public void setUser(User user) {
	// this.user = user;
	// }

	// @ManyToOne
	// @JoinColumn(name = "OrderId", referencedColumnName = "phoneOrderId",
	// nullable = false, insertable = false, updatable = false)
	// public PhoneOrder getPhoneOrder() {
	// return phoneOrder;
	// }
	//
	// public void setPhoneOrder(PhoneOrder phoneOrder) {
	// this.phoneOrder = phoneOrder;
	// }

}
