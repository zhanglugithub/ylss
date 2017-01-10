package ylss.service.web.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.OfferDao;
import ylss.dao.UserDao;
import ylss.model.table.Offer;
import ylss.service.web.AdminOfferService;
import ylss.utils.resultMap;

@Service
@Transactional
public class AdminOfferServiceImpl implements AdminOfferService {

	@Autowired
	@Resource
	OfferDao offerDao;

	@Resource
	UserDao userDao;
	
	@Override
	public List<Offer> listApplyOffer(int pageNo, int pageSize) {

		List<Offer> ListOffer = offerDao.getAPage(pageNo, pageSize);
		return ListOffer;
	}

	@Override
	public HashMap<String, Object> addOffer(Offer aOffer) {
		try {
			if (aOffer.getName().equals("") || aOffer.getName().equals(null)
					|| aOffer.getName().equals("null")) {
				return resultMap.createResult(0, "请填写姓名");
			}
			if (aOffer.getPhoneNo().equals("")
					|| aOffer.getPhoneNo().equals(null)
					|| aOffer.getPhoneNo().equals("null")) {
				return resultMap.createResult(0, "请填写手机号");
			}
			if (offerDao.getByPhoneNo(aOffer.getPhoneNo()) == null) {
			} else {
				return resultMap.createResult(0, "提交太频繁，请稍后再试");
			}
			if (userDao.getByPhoneNo(aOffer.getPhoneNo())==null) {
			}else{
				return resultMap.createResult(0, "医生已注册！");
			}
			if (aOffer.getPhoneNo().length() != 11) {
				return resultMap.createResult(0, "手机号填写有误");
			}
//			if (aOffer.getHospital().equals("")
//					|| aOffer.getHospital().equals(null)
//					|| aOffer.getHospital().equals("null")) {
//				return resultMap.createResult(0, "医院不能为空");
//			}
			offerDao.save(aOffer);
			return resultMap.createResult(1, "恭喜您,您已经提交成功，我们将在24小时内与你联系，\n帮助您上传审核相关材料请您保持手机畅通,医来伸手期待您的加入");
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, "操作失败，请稍后再试");
		}

	}

	@Override
	public long getApplyOfferTotalNo() {
		long allOffer = offerDao.countAll();
		return allOffer;
	}

}
