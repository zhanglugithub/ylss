package ylss.model.table;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "browse_t", catalog = "ylss")
public class Browse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2819763515660892034L;

	private Integer brId;
	private Integer scId;
	private Integer userId;
	private Date createTime;

	private User user;
	private SufferersCircle sc;

	@Id
	@Column(name = "brId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getBrId() {
		return brId;
	}

	public void setBrId(Integer brId) {
		this.brId = brId;
	}

	@Column(name = "userId")
	@NotNull(message = "userId 不能为空")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "scId")
	@NotNull(message = "scId 不能为空")
	public Integer getScId() {
		return scId;
	}

	public void setScId(Integer scId) {
		this.scId = scId;
	}

	@Column(name = "createTime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "UserId", insertable = false, updatable = false, nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "scId", insertable = false, updatable = false, nullable = false)
	public SufferersCircle getSc() {
		return sc;
	}

	public void setSc(SufferersCircle sc) {
		this.sc = sc;
	}

	public Browse() {
		super();
	}

	public Browse(int userId) {
		this.userId = userId;
	}
}
