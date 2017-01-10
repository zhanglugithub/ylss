package ylss.model.table;

// Generated 2015-12-29 17:18:36 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * PrescriptionOrder generated by hbm2java
 */
@Entity
@Table(name = "ylss", catalog = "prescription_order", uniqueConstraints = @UniqueConstraint(columnNames = "orderId"))
public class PrescriptionOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6862945823937600981L;

	private Integer prId;
	private Integer userId;
	private User user;
	private Long orderId;
	private Integer infoId;
	private String address;
	private String receivingPeople;
	private Long transactionNumber;
	private Integer number;
	private String hosptal;
	private String expressNo;
	private String recipient;
	private String payment;
	private Float freight;
	private Double payMoney;
	private String medicineSingle;
	private String imgs;
	private String status;
	private Date finishTime;
	private Date createTime;
	private Date payTime;
	private String isDel;

	public PrescriptionOrder() {
	}

	public PrescriptionOrder(Integer prId) {
		this.prId = prId;
	}

	public PrescriptionOrder(Integer prId, Integer userId, User user,
			Long orderId, Integer infoId, String address,
			String receivingPeople, Long transactionNumber, Integer number,
			String hosptal, String expressNo, String recipient, String payment,
			Float freight, Double payMoney, String medicineSingle, String imgs,
			String status, Date finishTime, Date createTime, Date payTime,
			String isDel) {
		super();
		this.prId = prId;
		this.userId = userId;
		this.user = user;
		this.orderId = orderId;
		this.infoId = infoId;
		this.address = address;
		this.receivingPeople = receivingPeople;
		this.transactionNumber = transactionNumber;
		this.number = number;
		this.hosptal = hosptal;
		this.expressNo = expressNo;
		this.recipient = recipient;
		this.payment = payment;
		this.freight = freight;
		this.payMoney = payMoney;
		this.medicineSingle = medicineSingle;
		this.imgs = imgs;
		this.status = status;
		this.finishTime = finishTime;
		this.createTime = createTime;
		this.payTime = payTime;
		this.isDel = isDel;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prId", length = 11)
	public Integer getPrId() {
		return this.prId;
	}

	public void setPrId(Integer prId) {
		this.prId = prId;
	}

	@Column(name = "userId", length = 11)
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 用户Id
	 * 
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@ManyToOne
	@JoinColumn(name = "userId", columnDefinition = "UserId", insertable = false, updatable = false, nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "infoId", length = 11)
	public Integer getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	@Column(name = "address", length = 1024)
	public String getAddress() {
		return this.address;
	}

	/**
	 * 收货地址，完成订单后填充
	 * 
	 * @return
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "medicineSingle", length = 1024)
	public String getMedicineSingle() {
		return this.medicineSingle;
	}

	/**
	 * 药单详情
	 * 
	 * @param medicineSingle
	 */
	public void setMedicineSingle(String medicineSingle) {
		this.medicineSingle = medicineSingle;
	}

	@Column(name = "imgs", length = 1024)
	public String getImgs() {
		return this.imgs;
	}

	/**
	 * 药单图片
	 * 
	 * @param imgs
	 */
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 订单状态
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finishTime", length = 19)
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "isDel")
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Column(name = "orderId", unique = true, nullable = false, length = 20)
	@NotNull(message = "订单编号不能为空")
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 订单编号
	 * 
	 * @param orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "receivingPeople", length = 50)
	public String getReceivingPeople() {
		return receivingPeople;
	}

	/**
	 * 收货人,完成订单后填充
	 * 
	 * @param receivingPeople
	 */
	public void setReceivingPeople(String receivingPeople) {
		this.receivingPeople = receivingPeople;
	}

	@Column(name = "transactionNumber", length = 20)
	public Long getTransactionNumber() {
		return transactionNumber;
	}

	/**
	 * 交易流水号，由支付平台提供
	 * 
	 * @param transactionNumber
	 */
	public void setTransactionNumber(Long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	@Column(name = "number", length = 11)
	@NotNull(message = "数量 不能为空")
	public Integer getNumber() {
		return number;
	}

	/**
	 * 数量
	 * 
	 * @param number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "hosptal")
	public String getHosptal() {
		return hosptal;
	}

	/**
	 * 抓药医院
	 * 
	 * @param hosptal
	 */
	public void setHosptal(String hosptal) {
		this.hosptal = hosptal;
	}

	@Column(name = "expressNo")
	public String getExpressNo() {
		return expressNo;
	}

	/**
	 * 快递单号
	 * 
	 * @param expressNo
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	@Column(name = "recipient")
	public String getRecipient() {
		return recipient;
	}

	/**
	 * 收款方账号
	 * 
	 * @param recipient
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Column(name = "payment")
	public String getPayment() {
		return payment;
	}

	/**
	 * 支付方账号
	 * 
	 * @param payment
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Column(name = "freight")
	@NotNull(message = "运费不能为空")
	public Float getFreight() {
		return freight;
	}

	/**
	 * 运费
	 * 
	 * @param freight
	 */
	public void setFreight(Float freight) {
		this.freight = freight;
	}

	@Column(name = "payMoney", precision = 10, scale = 2)
	public Double getPayMoney() {
		return payMoney;
	}

	/**
	 * 支付金额（包含运费）
	 * 
	 * @param payMoney
	 */
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	@Column(name = "payTime", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * 支付时间
	 * 
	 * @param payTime
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

}