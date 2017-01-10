package ylss.service.app.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.DoctorDao;
import ylss.dao.DoctorRealtimeInfoDao;
import ylss.dao.OfferDao;
import ylss.dao.UserDao;
import ylss.model.constant.databaseConstant.DoctorServiceStatus;
import ylss.model.constant.databaseConstant.DoctorStatus;
import ylss.model.logic.DoctorVerifyInfo;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorRealtimeInfo;
import ylss.model.table.Offer;
import ylss.model.table.User;
import ylss.service.app.DoctorBasicService;
import ylss.utils.FileTool;
import ylss.utils.RedisUtil;
import ylss.utils.resultMap;

@Service
@Transactional
public class DoctorBasicServiceImpl implements DoctorBasicService {

	@Resource
	@Autowired
	UserDao userDao;

	@Resource
	@Autowired
	DoctorDao doctorDao;

	@Resource
	OfferDao offerDao;

	@Resource
	@Autowired
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	//
	// @Resource
	// ApplyOfferDao applyOfferDao;

	// @Override
	// public HashMap<String, Object> applyOffer(ApplyOffer applyOffer) {
	//
	// Integer doctorId = applyOffer.getDoctorId();
	// Integer offerId = applyOffer.getOfferId();
	// ApplyOffer aApply = applyOfferDao.get("doctorId", doctorId, "offerId",
	// offerId);
	//
	// if (aApply != null) {
	// return resultMap.createResult(0, "请勿重复提交");
	// }
	// applyOfferDao.save(applyOffer);
	// return resultMap.createResult(1, "提交成功，稍后我们将与您电话联系");
	// }

	@Override
	public List<Offer> getOfferList() {

		String hqlString = "from Offer where endTime>now() "; //
		List<Offer> offerList = offerDao.getAll(hqlString);

		return offerList;
	}

	@Override
	public HashMap<String, Object> setServiceState(String phoneNo, boolean state) {

		try {
			int doctorId = userDao.getByPhoneNo(phoneNo).getUserId();

			DoctorRealtimeInfo aDoctorRealtimeInfo = doctorRealtimeInfoDao
					.getById(doctorId);
			if (state) {
				aDoctorRealtimeInfo.setServiceStatus(DoctorServiceStatus.open);
				doctorRealtimeInfoDao.update(aDoctorRealtimeInfo);

				return resultMap.createResult(1, "开启成功");

			} else {
				aDoctorRealtimeInfo.setServiceStatus(DoctorServiceStatus.close);
				doctorRealtimeInfoDao.update(aDoctorRealtimeInfo);
				return resultMap.createResult(1, "关闭成功");
			}

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> updateLocation(String phoneNo,
			double longitude, double latitude) {
		try {
			int userId = userDao.getByPhoneNo(phoneNo).getUserId();
			Doctor aDoctor = doctorDao.getById(userId);

			DoctorRealtimeInfo aDoctorRealtimeInfo = aDoctor
					.getDoctorRealtimeInfo();
			aDoctorRealtimeInfo.setLatitude(latitude);
			aDoctorRealtimeInfo.setLongitude(longitude);
			RedisUtil.setLat(phoneNo, latitude, longitude);
			doctorRealtimeInfoDao.update(aDoctorRealtimeInfo);

			return resultMap.createResult(1, "更新位置成功");

		} catch (Exception e) {
			return resultMap.createResult(0, e);

		}

	}

	@Override
	public HashMap<String, Object> getVerifyInfo(String phoneNo) {
		try {
			int userId = userDao.getByPhoneNo(phoneNo).getUserId();
			Doctor aDoctor = doctorDao.getById(userId);
			if (aDoctor == null) {
				return resultMap.createResult(0, "尚未提交认证");
			}
			DoctorVerifyInfo aDoctorInfo = new DoctorVerifyInfo(aDoctor);
			HashMap<String, Object> doctorInfoMap = new HashMap<String, Object>();
			doctorInfoMap.put("doctorId", aDoctorInfo.getDoctorId());
			doctorInfoMap.put("doctorNumber", aDoctorInfo.getDoctorNumber());
			doctorInfoMap.put("doctorType", aDoctorInfo.getDoctorType());
			doctorInfoMap.put("birthday", aDoctorInfo.getBirthday());
			doctorInfoMap.put("sex", aDoctorInfo.getSex());
			doctorInfoMap.put("qualificationNo",
					aDoctorInfo.getQualificationNo());
			doctorInfoMap.put("qualification", aDoctorInfo.getQualification()
					+ "");
			doctorInfoMap.put("department", aDoctorInfo.getDepartment());
			doctorInfoMap.put("starLevel", aDoctorInfo.getStarLevel());
			doctorInfoMap.put("hospital", aDoctorInfo.getHospital());
			doctorInfoMap.put("specialty", aDoctorInfo.getSpecialty());
			doctorInfoMap.put("alipayNo", aDoctorInfo.getAlipayNo());
			doctorInfoMap.put("idCard", aDoctorInfo.getIdCard());
			doctorInfoMap.put("status", aDoctorInfo.getStatus());
			doctorInfoMap.put("serviceTime", aDoctorInfo.getServiceTime());
			doctorInfoMap.put("doctorName", aDoctorInfo.getDoctorName());
			doctorInfoMap.put("haveIcon", aDoctorInfo.isHaveIcon());
			return resultMap.createResult(1, doctorInfoMap);

		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, false);
		}

	}

	@Override
	public HashMap<String, Object> submitVerify(Doctor aDoctor, String phoneNo,
			MultipartFile[] imgs) {

		FileTool.uploadImg(imgs[0], phoneNo, "idCard");
		FileTool.uploadImg(imgs[1], phoneNo, "allowWork");
		FileTool.uploadImg(imgs[2], phoneNo, "qulification");
		try {
			int userId = userDao.getByPhoneNo(phoneNo).getUserId();
			// Doctor dbDoctor = doctorDao.getById(userId);
			// if (dbDoctor != null) {
			// if (!dbDoctor.getStatus().equals(DoctorStatus.notVerified))
			// return resultMap.createResult(0, "请勿重复提交");
			// }
			//
			aDoctor.setStatus(DoctorStatus.verifying);
			aDoctor.setDoctorId(userId);
			doctorDao.saveOrUpdate(aDoctor);
			return resultMap.createResult(1, "提交成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	@Override
	public HashMap<String, Object> submitVerifyIos(Doctor aDoctor,
			String phoneNo, MultipartFile img0, MultipartFile img1,
			MultipartFile img2) {
		FileTool.uploadImg(img0, phoneNo, "idCard");
		FileTool.uploadImg(img1, phoneNo, "allowWork");
		FileTool.uploadImg(img2, phoneNo, "qulification");
		try {
			int userId = userDao.getByPhoneNo(phoneNo).getUserId();

			aDoctor.setStatus(DoctorStatus.verifying);
			aDoctor.setDoctorId(userId);
			doctorDao.saveOrUpdate(aDoctor);
			return resultMap.createResult(1, "提交成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	// public void submitVerifyIosImg(MultipartFile img0,String phoneNo,String
	// folderName) {
	// FileTool.uploadImg(img0, phoneNo, fol);
	// }
	public HashMap<String, Object> submitVerifyIosDoctor(Doctor aDoctor,
			String phoneNo) {
		try {
			int userId = userDao.getByPhoneNo(phoneNo).getUserId();

			aDoctor.setStatus(DoctorStatus.verifying);
			aDoctor.setDoctorId(userId);
			doctorDao.saveOrUpdate(aDoctor);
			return resultMap.createResult(1, "提交成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	@Override
	public HashMap<String, Object> submitVerifyIos2(String phoneNo,
			MultipartFile[] imgs, Doctor aDoctor, String userName) {
		Logger logger = Logger.getLogger(this.getClass());
		FileTool.uploadImg(imgs[0], phoneNo, "idCard");
		FileTool.uploadImg(imgs[1], phoneNo, "allowWork");
		FileTool.uploadImg(imgs[2], phoneNo, "qulification");
		FileTool.uploadImg(imgs[3], phoneNo, "headIcon");
		try {
			User user = userDao.getByPhoneNo(phoneNo);
			user.setHaveIcon(true);
			user.setUserName(userName);
			userDao.update(user);
			aDoctor.setStatus(DoctorStatus.verifying);
			aDoctor.setDoctorId(user.getUserId());
			doctorDao.saveOrUpdate(aDoctor);
			return resultMap.createResult(1, "提交成功");
		} catch (Exception e) {
			logger.error("submitVerifyIos2", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> submitVerifyIosDoctor2(Doctor aDoctor,
			String phoneNo, String userName) {
		try {
			User user = userDao.getByPhoneNo(phoneNo);
			user.setUserName(userName);
			userDao.update(user);
			aDoctor.setStatus(DoctorStatus.verifying);
			aDoctor.setDoctorId(user.getUserId());
			doctorDao.saveOrUpdate(aDoctor);
			return resultMap.createResult(1, "提交成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	@Override
	public HashMap<String, Object> submitVerifyIosImg(String phoneNo,
			MultipartFile img, String imgName) {
		User user = userDao.getByPhoneNo(phoneNo);
		user.setHaveIcon(true);
		userDao.update(user);
		String folderName = imgName;
		return FileTool.uploadImg(img, phoneNo, folderName);
	}
}
