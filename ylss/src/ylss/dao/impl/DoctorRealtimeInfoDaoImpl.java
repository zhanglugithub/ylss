package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.DoctorRealtimeInfoDao;
import ylss.model.table.DoctorRealtimeInfo;

@Repository
public class DoctorRealtimeInfoDaoImpl extends BaseDaoImpl<DoctorRealtimeInfo, Integer>
		implements DoctorRealtimeInfoDao {
	public DoctorRealtimeInfoDaoImpl() {
		super(DoctorRealtimeInfo.class);
	}

}
