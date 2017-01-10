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
@Table(name = "comment_t", catalog = "ylss")
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4582199622011133500L;

	private Integer coId;
	private Integer scId;
	private Integer userId;
	private String comment;
	private Date createTime;
	private String isDelete = "1";

	private User user;
	private SufferersCircle sc;

	@Id
	@Column(name = "coId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCoId() {
		return coId;
	}

	public void setCoId(Integer coId) {
		this.coId = coId;
	}

	@Column(name = "scId")
	@NotNull(message = "scId 不能为空")
	public Integer getScId() {
		return scId;
	}

	public void setScId(Integer scId) {
		this.scId = scId;
	}

	@Column(name = "userId")
	@NotNull(message = "userId 不能为空")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "isDelete")
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "UserId", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "scId", nullable = false, insertable = false, updatable = false)
	public SufferersCircle getSc() {
		return sc;
	}

	public void setSc(SufferersCircle sc) {
		this.sc = sc;
	}

	public Comment() {
		super();
	}

	public Comment(Integer coId, Integer scId, Integer userId, String comment,
			Date createTime, String isDelete, User user) {
		super();
		this.coId = coId;
		this.scId = scId;
		this.userId = userId;
		this.comment = comment;
		this.createTime = createTime;
		this.isDelete = isDelete;
		this.user = user;
	}

}
