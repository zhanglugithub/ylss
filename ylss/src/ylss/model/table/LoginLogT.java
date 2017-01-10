package ylss.model.table;

// Generated 2016-1-8 14:02:50 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 登陆日志表
 */

@Entity
@Table(name = "login_log_t", catalog = "ylss")
public class LoginLogT implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3005989371583706912L;
	private Integer logId;
	private String phoneNo;
	private Date loginTime;
	private String deviceToken;
	private String deviceType;
	private String isDel;

	public LoginLogT() {
	}

	public LoginLogT(int logId) {
		this.logId = logId;
	}

	public LoginLogT(Integer logId, String phoneNo, Date loginTime,
			String deviceToken, String deviceType, String isDel) {
		this.logId = logId;
		this.phoneNo = phoneNo;
		this.loginTime = loginTime;
		this.deviceToken = deviceToken;
		this.deviceType = deviceType;
		this.isDel = isDel;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "logId", nullable = false, unique = true)
	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	@Column(name = "phoneNo", length = 11)
	@NotNull(message = "手机号不能为空")
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "loginTime", length = 19)
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "deviceToken")
	public String getDeviceToken() {
		return this.deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Column(name = "deviceType")
	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "isDel")
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

}
