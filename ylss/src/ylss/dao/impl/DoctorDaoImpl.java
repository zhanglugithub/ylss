package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.DoctorDao;
import ylss.model.table.Doctor;

@Repository
public class DoctorDaoImpl extends BaseDaoImpl<Doctor, Integer> implements
		DoctorDao {

	public DoctorDaoImpl() {
		super(Doctor.class);
	}

}
