package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.PrescriptionOrderDao;
import ylss.model.table.PrescriptionOrder;

@Repository
public class PrescriptionOrderDaoImpl extends
		BaseDaoImpl<PrescriptionOrder, Integer> implements PrescriptionOrderDao {

	public PrescriptionOrderDaoImpl() {
		super(PrescriptionOrder.class);
	}

}
