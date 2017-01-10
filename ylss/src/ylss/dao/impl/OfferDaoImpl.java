package ylss.dao.impl;

import org.springframework.stereotype.Repository;

import ylss.dao.OfferDao;
import ylss.model.table.Offer;

@Repository
public class OfferDaoImpl extends BaseDaoImpl<Offer, Integer> implements
		OfferDao {
	public OfferDaoImpl() {
		super(Offer.class);
	}

	@Override
	public Offer getByPhoneNo(String phoneNo) {
		return super.get("phoneNo", phoneNo);
	}
}
