package ylss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.PatientInfoDao;
import ylss.model.table.PatientInfo;

@Repository
@Transactional
public class PatientInfoDaoImpl extends BaseDaoImpl<PatientInfo, Integer>
		implements PatientInfoDao {
	public PatientInfoDaoImpl() {
		super(PatientInfo.class);
	}

	@Override
	public PatientInfo getBypatientId(Integer patientId) {
		return get("patientId", patientId);
	}
}
