package ylss.service.web;

import java.util.HashMap;
import java.util.List;

import ylss.model.table.Offer;

public interface AdminOfferService {
	public  List<Offer> listApplyOffer(int pageNo,int pageSize); 
	
	public long getApplyOfferTotalNo();
	
	public HashMap<String, Object> addOffer(Offer aOffer);

}
