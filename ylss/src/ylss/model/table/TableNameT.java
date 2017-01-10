package ylss.model.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table_name_t", catalog = "ylss")
public class TableNameT implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5377487636768683729L;

	private Integer taId;
	private String tableName;
	private String tableValue;

	public TableNameT() {
		super();
	}

	public TableNameT(Integer taId) {
		super();
		this.taId = taId;
	}

	public TableNameT(Integer taId, String tableName, String tableValue) {
		super();
		this.taId = taId;
		this.tableName = tableName;
		this.tableValue = tableValue;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taId", length = 11, nullable = false, unique = true)
	public Integer getTaId() {
		return taId;
	}

	public void setTaId(Integer taId) {
		this.taId = taId;
	}

	@Column(name = "tableName")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "tableValue")
	public String getTableValue() {
		return tableValue;
	}

	public void setTableValue(String tableValue) {
		this.tableValue = tableValue;
	}
}
