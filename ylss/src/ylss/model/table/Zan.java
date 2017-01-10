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
@Table(name = "zan_t", catalog = "ylss")
public class Zan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3335050149675428608L;

	private Integer zanId;
	private Integer scId;
	private Integer userId;
	private Date createTime;
	private String isDelete = "1";

	private SufferersCircle sufferersCircle;

	public Zan() {
		super();
	}

	public Zan(Integer scId) {
		this.scId = scId;
	}

	public Zan(Integer zanId, Integer scId, Integer userId, Date createTime,
			String isDelete, SufferersCircle sufferersCircle) {
		super();
		this.zanId = zanId;
		this.scId = scId;
		this.userId = userId;
		this.createTime = createTime;
		this.isDelete = isDelete;
		this.sufferersCircle = sufferersCircle;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "zanId", unique = true, nullable = false)
	public Integer getZanId() {
		return zanId;
	}

	public void setZanId(Integer zanId) {
		this.zanId = zanId;
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

	@Column(name = "createTime", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
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
	@JoinColumn(name = "scId", nullable = false, updatable = false, insertable = false)
	public SufferersCircle getSufferersCircle() {
		return sufferersCircle;
	}

	public void setSufferersCircle(SufferersCircle sufferersCircle) {
		this.sufferersCircle = sufferersCircle;
	}

}
