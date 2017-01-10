package ylss.model.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_info", catalog = "ylss")
public class UserInfos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6268226417992868112L;
	private Integer userId;
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String sex;
	private User user;

	@Id
	@Column(name = "userId", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 经度
	 * 
	 * @return
	 */
	@Column(name = "longitude", precision = 10, scale = 6)
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 纬度
	 * 
	 * @return
	 */
	@Column(name = "latitude", precision = 10, scale = 6)
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name="sex")
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "UserId", updatable = false, nullable = false, insertable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInfos() {
		super();
	}

	public UserInfos(Integer userId) {
		this.userId = userId;
	}

	public UserInfos(Integer userId, Double longitude, Double latitude,
			String sex, User user) {
		super();
		this.userId = userId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sex = sex;
		this.user = user;
	}
}
