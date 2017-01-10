package ylss.model.table;

import static javax.persistence.GenerationType.IDENTITY;

// Generated 2015-7-13 17:34:27 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AccountOperateHistory generated by hbm2java
 */
@Entity
@Table(name = "account_operate_history", catalog = "ylss")
public class AccountOperateHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3540877220872619834L;

	private Integer aohId;
	private Integer userId;
	private String operateType;
	private Double money;
	private Integer orderId;
	private Date createTime;

	public AccountOperateHistory() {
	}

	public AccountOperateHistory(Integer userId, String operateType, Double money, Integer orderId, Date createTime) {
		this.userId = userId;
		this.operateType = operateType;
		this.money = money;
		this.orderId = orderId;
		this.createTime = createTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "AohId", unique = true, nullable = false)
	public Integer getAohId() {
		return this.aohId;
	}

	public void setAohId(Integer aohId) {
		this.aohId = aohId;
	}

	@Column(name = "UserId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "OperateType", length = 10)
	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	@Column(name = "Money")
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "OrderId")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
