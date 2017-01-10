package ylss.model.table;

// Generated 2015-7-23 13:44:09 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account", catalog = "ylss")
public class Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6413985689176510260L;
	private int userId;
	private Double balance;

	public Account() {
	}

	public Account(int userId) {
		this.userId = userId;
	}

	public Account(int userId, Double balance) {
		this.userId = userId;
		this.balance = balance;
	}

	@Id
	@Column(name = "UserId", unique = true, nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "Balance", precision = 20)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}