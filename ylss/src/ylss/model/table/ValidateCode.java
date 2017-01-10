package ylss.model.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ValidateCode_t", catalog = "ylss")
// @Table(name = "ValidateCode_t", catalog = "ylss", uniqueConstraints =
// @UniqueConstraint(columnNames = "PhoneNo"))
public class ValidateCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3347608445813368221L;
	private Integer id;
	private String phoneNo;
	private String validateCode;
	private Date createTime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PhoneNo", length = 11)
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "ValidateCode", length = 6)
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public ValidateCode() {
		super();
	}

	public ValidateCode(String phoneNo) {
		super();
		this.phoneNo = phoneNo;
	}

	public ValidateCode(String phoneNo, String validateCode, Date createTime) {
		super();
		this.phoneNo = phoneNo;
		this.validateCode = validateCode;
		this.createTime = createTime;
	}

}
