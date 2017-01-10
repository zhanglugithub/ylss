package ylss.dao;

import ylss.model.table.PatientInfo;

public interface PatientInfoDao extends BaseDao<PatientInfo, Integer> {
	public PatientInfo getBypatientId(Integer patientId);
}
