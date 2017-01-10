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
@Table(name = "sufferers_circle", catalog = "ylss")
public class SufferersCircle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061775450111407839L;

	private Integer scId;
	private Integer userId;
	private String address;
	private String shuo;
	private String image;
	private Integer zanCount = 0;
	private Integer browseCount = 0;
	private Integer commentCount = 0;
	private Date createTime;
	private String isDelete = "1";

	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scId", unique = true, nullable = false)
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

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "shuo")
	public String getShuo() {
		return shuo;
	}

	public void setShuo(String shuo) {
		this.shuo = shuo;
	}

	@Column(name = "image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "zanCount")
	public Integer getZanCount() {
		return zanCount;
	}

	public void setZanCount(Integer zanCount) {
		this.zanCount = zanCount;
	}

	@Column(name = "browseCount")
	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	@Column(name = "commentCount")
	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	@Column(name = "createTime")
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
	@JoinColumn(name = "userId", referencedColumnName = "UserId", insertable = false, nullable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SufferersCircle() {
		super();
	}

	public SufferersCircle(int userId) {
		this.userId = userId;
	}

	public SufferersCircle(Integer scId, Integer userId, String address,
			String shuo, String image, Integer zanCount, Integer browseCount,
			Integer commentCount, Date createTime, String isDelete, User user) {
		super();
		this.scId = scId;
		this.userId = userId;
		this.address = address;
		this.shuo = shuo;
		this.image = image;
		this.zanCount = zanCount;
		this.browseCount = browseCount;
		this.commentCount = commentCount;
		this.createTime = createTime;
		this.isDelete = isDelete;
		this.user = user;
	}

}
