package ylss.model.table;

// Generated 2015-12-29 14:20:44 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * HospitalT generated by hbm2java
 */
@Entity
@Table(name = "hospital_t", catalog = "ylss", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class HospitalT implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3437092173427981255L;
	private Integer hoId;
	private String uid;
	private String hospital;
	private Date addTime;
	private String isDel;

	// private Set<CategoryT> categoryTs;

	public HospitalT() {
	}

	public HospitalT(String uid) {
		this.uid = uid;
	}

	public HospitalT(Integer hoId, String uid, String hospital, Date addTime,
			String isDel) {
		super();
		this.hoId = hoId;
		this.uid = uid;
		this.hospital = hospital;
		this.addTime = addTime;
		this.isDel = isDel;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hoId", length = 19)
	public Integer getHoId() {
		return this.hoId;
	}

	public void setHoId(Integer hoId) {
		this.hoId = hoId;
	}

	@Column(name = "uid", unique = true, nullable = false, length = 32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "hospital")
	public String getHospital() {
		return this.hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addTime", length = 19)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "isDel")
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	// @OneToMany(mappedBy = "parentId")
	// public Set<CategoryT> getCategoryTs() {
	// return this.categoryTs;
	// }
	//
	// public void setCategoryTs(Set<CategoryT> categoryTs) {
	// this.categoryTs = categoryTs;
	// }

}