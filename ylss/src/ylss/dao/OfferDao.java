package ylss.dao;

import ylss.model.table.Offer;

public interface OfferDao extends BaseDao<Offer, Integer> {
	public Offer getByPhoneNo(String phoneNo);
}
