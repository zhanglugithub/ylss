package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.DoctorGPSDao;
import ylss.model.table.DoctorRealtimeInfo;

@Repository
public class DoctorGPSDaoImpl extends BaseDaoImpl<DoctorRealtimeInfo, Integer>
		implements DoctorGPSDao {

	public DoctorGPSDaoImpl() {
		super(DoctorRealtimeInfo.class);
	}

}
