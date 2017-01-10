package ylss.dao.impl;
import org.springframework.stereotype.Repository;

import ylss.dao.ApplyOfferDao;
import ylss.model.table.ApplyOffer;
@Repository
public class ApplyOfferDaoImpl extends BaseDaoImpl<ApplyOffer, Integer> implements ApplyOfferDao {
public ApplyOfferDaoImpl() {super(ApplyOffer.class);}}
