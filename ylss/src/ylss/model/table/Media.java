package ylss.model.table;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "media", catalog = "ylss")
public class Media implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8113833117935755185L;
	private Integer id;
	private String title;
	private String image;
	private String link;
	private Date createTime = new Date();

	public Media() {
		super();
	}

	public Media(Integer id) {
		super();
		this.id = id;
	}

	public Media(Integer id, String title, String image, String link, Date createTime) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.link = link;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", title=" + title + ", image=" + image + ", link=" + link + ", createTime="
				+ createTime + "]";
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title")
	@NotNull(message = "title 不能为空")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "image")
	@NotNull(message = "image 不能为空")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "link")
	@NotNull(message = "link 不能为空")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
