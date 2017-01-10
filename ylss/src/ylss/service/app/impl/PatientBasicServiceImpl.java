package ylss.service.app.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.DoctorDao;
import ylss.dao.DoctorRealtimeInfoDao;
import ylss.dao.FeedBackDao;
import ylss.dao.PatientInfoDao;
import ylss.dao.UserDao;
import ylss.dao.UserInfosDao;
import ylss.model.constant.ManageConstant;
import ylss.model.constant.databaseConstant.DoctorServiceStatus;
import ylss.model.constant.databaseConstant.DoctorStatus;
import ylss.model.logic.DoctorInfo;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorRealtimeInfo;
import ylss.model.table.Feedback;
import ylss.model.table.PatientInfo;
import ylss.model.table.UserInfos;
//import ylss.service.PatientBasicService;
import ylss.service.app.PatientBasicService;
import ylss.utils.AddressRange;
import ylss.utils.Length;
import ylss.utils.Sort;
import ylss.utils.resultMap;

@Service
@Transactional
public class PatientBasicServiceImpl implements PatientBasicService {

	@Resource
	@Autowired
	DoctorDao doctorDao;

	@Resource
	@Autowired
	FeedBackDao feedBackDao;

	@Resource
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	@Autowired
	@Resource
	PatientInfoDao patientInfoDao;

	@Resource
	UserInfosDao userInfosDao;

	@Resource
	UserDao userDao;

	private Doctor dbDoctor;

	@Override
	public HashMap<String, Object> getDoctorInfo(int doctorId) {

		dbDoctor = doctorDao.getById(doctorId);
		if (dbDoctor == null) {
			return resultMap.createResult(0, "没有该医生");
		}
		if (!dbDoctor.getStatus().equals(DoctorStatus.verified)) {
			return resultMap.createResult(0, "该医生处于验证阶段");
		}

		DoctorInfo doctorInfo = new DoctorInfo(dbDoctor);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("doctorId", doctorInfo.getDoctorId());
		result.put("doctorNumber", doctorInfo.getDoctorNumber());
		result.put("doctorType", doctorInfo.getDoctorType());
		result.put("doctorPhone", doctorInfo.getDoctorPhone());
		result.put("birthday", doctorInfo.getBirthday());
		result.put("sex", doctorInfo.getSex());
		result.put("qualificationNo", doctorInfo.getQualificationNo());
		result.put("qualification", doctorInfo.getQualification());
		result.put("department", doctorInfo.getDepartment());
		result.put("starLevel", doctorInfo.getStarLevel());
		result.put("hospital", doctorInfo.getHospital());
		result.put("longitude", doctorInfo.getLongitude());
		result.put("latitude", doctorInfo.getLatitude());
		result.put("serviceTime", doctorInfo.getServiceTime());
		result.put("doctorName", doctorInfo.getDoctorName());
		result.put("haveIcon", doctorInfo.isHaveIcon());
		result.put("speciallist", doctorInfo.getSpecialty());
		result.put("serviceStatus", doctorInfo.getServiceStatus());
		return resultMap.createResult(1, result);

	}

	@Override
	public HashMap<String, Object> getDoctorInfo2(int doctorId) {

		dbDoctor = doctorDao.getById(doctorId);
		if (dbDoctor == null) {
			return resultMap.createResult(0, "没有该医生");
		}
		if (!dbDoctor.getStatus().equals(DoctorStatus.verified)) {
			return resultMap.createResult(0, "该医生处于验证阶段");
		}

		DoctorInfo doctorInfo = new DoctorInfo(dbDoctor);

		return resultMap.createResult(1, doctorInfo);

	}

	@Override
	public HashMap<String, Object> getAroundDoctor(double longitude,
			double latitude, String phoneNo) {
		Logger logger = Logger.getLogger(this.getClass());
		Map<String, Object> range;
		range = AddressRange.getRange(longitude, latitude,
				ManageConstant.PatientCanSee);
		Map<String, Object> result = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String hql = "from Doctor"
				+ " where "
				+ "doctorRealtimeInfo.longitude BETWEEN :MinLong and :MaxLong "
				+ " and doctorRealtimeInfo.latitude BETWEEN :MinLati and :MaxLati "
				+ " and doctorRealtimeInfo.serviceStatus in('"
				+ DoctorServiceStatus.open + "','"
				+ DoctorServiceStatus.service + "') order by starLevel desc";

		List<Doctor> doctorList = doctorDao.getAll(hql, range);
		List<DoctorInfo> doctorInfoList = new ArrayList<DoctorInfo>();
		for (Doctor doctor : doctorList) {
			DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
			doctorInfoList.add(aDoctorInfo);
		}
		for (DoctorInfo doctorInfo : doctorInfoList) {
			result = new HashMap<String, Object>();
			result.put("doctorId", doctorInfo.getDoctorId());
			result.put("doctorNumber", doctorInfo.getDoctorNumber());
			result.put("doctorType", doctorInfo.getDoctorType());
			result.put("doctorPhone", doctorInfo.getDoctorPhone());
			result.put("birthday", doctorInfo.getBirthday());
			result.put("sex", doctorInfo.getSex());
			result.put("qualificationNo", doctorInfo.getQualificationNo());
			result.put("qualification", doctorInfo.getQualification());
			result.put("department", doctorInfo.getDepartment());
			result.put("starLevel", doctorInfo.getStarLevel());
			result.put("hospital", doctorInfo.getHospital());
			result.put("longitude", doctorInfo.getLongitude());
			result.put("latitude", doctorInfo.getLatitude());
			result.put("serviceTime", doctorInfo.getServiceTime());
			result.put("doctorName", doctorInfo.getDoctorName());
			result.put("haveIcon", doctorInfo.isHaveIcon());
			result.put("speciallist", doctorInfo.getSpecialty());
			resultList.add(result);
		}
		if (phoneNo != null && !phoneNo.equalsIgnoreCase("null")
				&& !phoneNo.equals("")) {
			Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
			UserInfos userInfo = userInfosDao.getById(userId);
			userInfo.setLongitude(longitude);
			userInfo.setLatitude(latitude);
			userInfosDao.update(userInfo);
		}

		return resultMap.createResult(1, resultList);
	}

	@Override
	public HashMap<String, Object> getAroundDoctor2(double longitude,
			double latitude, String phoneNo) {
		Map<String, Object> range;
		range = AddressRange.getRange(longitude, latitude,
				ManageConstant.PatientCanSee);
		String hql = "select d from Doctor d,DoctorRealtimeInfo dri"
				+ " where  d.doctorId=dri.doctorId and "
				+ "dri.longitude BETWEEN :MinLong and :MaxLong "
				+ " and dri.latitude BETWEEN :MinLati and :MaxLati "
				+ " and dri.serviceStatus in('" + DoctorServiceStatus.open
				+ "','" + DoctorServiceStatus.service
				+ "') order by d.starLevel desc";

		List<Doctor> doctorList = doctorDao.getAll(hql, range);
		List<DoctorInfo> doctorInfoList = new ArrayList<DoctorInfo>();
		for (Doctor doctor : doctorList) {
			DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
			doctorInfoList.add(aDoctorInfo);
		}

		if (phoneNo != null && !phoneNo.equalsIgnoreCase("null")
				&& !phoneNo.equals("")) {
			Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
			UserInfos userInfo = userInfosDao.getById(userId);
			userInfo.setLongitude(longitude);
			userInfo.setLatitude(latitude);
			userInfosDao.update(userInfo);
		}

		return resultMap.createResult(1, doctorInfoList);
	}

	@Override
	public HashMap<String, Object> getAroundDoctor3(double longitude,
			double latitude, String phoneNo) {
		Logger logger = Logger.getLogger(this.getClass());
		Map<String, Object> range;
		range = AddressRange.getRange(longitude, latitude,
				ManageConstant.PatientCanSee);
		String hql = "select d from Doctor d,DoctorRealtimeInfo dri"
				+ " where d.doctorId=dri.doctorId and "
				+ "dri.longitude BETWEEN :MinLong and :MaxLong "
				+ " and dri.latitude BETWEEN :MinLati and :MaxLati "
				+ " and dri.serviceStatus in('" + DoctorServiceStatus.open
				+ "','" + DoctorServiceStatus.service
				+ "') order by d.starLevel desc";
		try {
			List<Doctor> doctorList = doctorDao.getAll(hql, range);
			HashMap<String, Object> doctorMap = null;
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			List<HashMap<String, Object>> juLiList = new ArrayList<HashMap<String, Object>>();
			for (Doctor doctor : doctorList) {
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
				doctorMap = new HashMap<String, Object>();
				doctorMap.put("doctorId", aDoctorInfo.getDoctorId());
				doctorMap.put("doctorType", aDoctorInfo.getDoctorType());
				doctorMap.put("doctorPhone", aDoctorInfo.getDoctorPhone());
				doctorMap.put("birthday", aDoctorInfo.getBirthday());
				doctorMap.put("sex", aDoctorInfo.getSex());
				doctorMap.put("qualification", aDoctorInfo.getQualification());
				doctorMap.put("department", aDoctorInfo.getDepartment());
				doctorMap.put("starLevel", aDoctorInfo.getStarLevel());
				doctorMap.put("hospital", aDoctorInfo.getHospital());
				doctorMap.put("longitude", aDoctorInfo.getLongitude());
				doctorMap.put("latitude", aDoctorInfo.getLatitude());
				doctorMap.put("speciallist", aDoctorInfo.getSpecialty());
				doctorMap.put("doctorName", aDoctorInfo.getDoctorName());
				doctorMap.put("haveIcon", aDoctorInfo.isHaveIcon());
				doctorMap.put("doctorNumber", aDoctorInfo.getDoctorNumber());
				doctorMap.put("serviceTime", aDoctorInfo.getServiceTime());
				doctorMap.put("qualificationNo",
						aDoctorInfo.getQualificationNo());
				doctorMap.put("serviceStatus", aDoctorInfo.getServiceStatus());
				double juLi = Length.gps2m(latitude, longitude,
						aDoctorInfo.getLatitude(), aDoctorInfo.getLongitude());
				int juLiM = (int) juLi;
				doctorMap.put("length", juLiM);
				juLiList.add(doctorMap);
			}
			List<HashMap<String, Object>> sortList = Sort.sort(juLiList);
			resultMap.put("sortList", sortList);
			if (phoneNo != null && !phoneNo.equalsIgnoreCase("null")
					&& !phoneNo.equals("")) {
				Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
				UserInfos userInfo = userInfosDao.getById(userId);
				userInfo.setLongitude(longitude);
				userInfo.setLatitude(latitude);
				userInfosDao.update(userInfo);
			}
			resultMap.put("code", 1);
			resultMap.put("msg", true);
			return resultMap;
		} catch (Exception e) {
			logger.error("getAroundDoctor3", e);
			return resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getAroundDoctor31(double longitude,
			double latitude, String phoneNo, int pageNo, int pageSize,
			String doctorType) {
		Logger logger = Logger.getLogger(this.getClass());
		String hql = null;
		if ("all".equals(doctorType)) {
			hql = "select d from Doctor d,DoctorRealtimeInfo dri"
					+ " where d.doctorId=dri.doctorId and "
					+ "dri.longitude BETWEEN :MinLong and :MaxLong "
					+ " and dri.latitude BETWEEN :MinLati and :MaxLati "
					+ " and dri.serviceStatus in('" + DoctorServiceStatus.open
					+ "','" + DoctorServiceStatus.service
					+ "') order by dri.serviceCount desc";
		} else if ("doctor".equals(doctorType) | "expert".equals(doctorType)) {
			hql = "select d from Doctor d,DoctorRealtimeInfo dri"
					+ " where d.doctorId=dri.doctorId and d.doctorType='"
					+ doctorType + "' and "
					+ "dri.longitude BETWEEN :MinLong and :MaxLong "
					+ " and dri.latitude BETWEEN :MinLati and :MaxLati "
					+ " and dri.serviceStatus in('" + DoctorServiceStatus.open
					+ "','" + DoctorServiceStatus.service
					+ "') order by dri.serviceCount desc";
		} else {
			return resultMap.createResult(0, "医生类型不正确");
		}
		Map<String, Object> range = AddressRange.getRange(longitude, latitude,
				ManageConstant.PatientCanSee);

		try {
			HashMap<String, Object> doctorsMap = doctorDao.findPage(hql, range,
					pageNo, pageSize);
			HashMap<String, Object> doctorMap = null;
			HashMap<String, Object> resultsMap = new HashMap<String, Object>();
			List<HashMap<String, Object>> juLiList = new ArrayList<HashMap<String, Object>>();
			List<Doctor> doctorList = (List<Doctor>) doctorsMap.get("result");
			for (Doctor doctor : doctorList) {
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
				doctorMap = new HashMap<String, Object>();
				doctorMap.put("doctorId", aDoctorInfo.getDoctorId());
				doctorMap.put("doctorType", aDoctorInfo.getDoctorType());
				doctorMap.put("doctorPhone", aDoctorInfo.getDoctorPhone());
				doctorMap.put("birthday", aDoctorInfo.getBirthday());
				doctorMap.put("sex", aDoctorInfo.getSex());
				doctorMap.put("qualification", aDoctorInfo.getQualification());
				doctorMap.put("department", aDoctorInfo.getDepartment());
				doctorMap.put("starLevel", aDoctorInfo.getStarLevel());
				doctorMap.put("hospital", aDoctorInfo.getHospital());
				doctorMap.put("longitude", aDoctorInfo.getLongitude());
				doctorMap.put("latitude", aDoctorInfo.getLatitude());
				doctorMap.put("speciallist", aDoctorInfo.getSpecialty());
				doctorMap.put("doctorName", aDoctorInfo.getDoctorName());
				doctorMap.put("haveIcon", aDoctorInfo.isHaveIcon());
				doctorMap.put("doctorNumber", aDoctorInfo.getDoctorNumber());
				doctorMap.put("serviceTime", aDoctorInfo.getServiceTime());
				doctorMap.put("qualificationNo",
						aDoctorInfo.getQualificationNo());
				doctorMap.put("serviceStatus", aDoctorInfo.getServiceStatus());
				double juLi = Length.gps2m(latitude, longitude,
						aDoctorInfo.getLatitude(), aDoctorInfo.getLongitude());
				int juLiM = (int) juLi;
				doctorMap.put("length", juLiM);
				juLiList.add(doctorMap);
			}
			resultsMap.put("orderList", juLiList);
			resultsMap.put("pageCount", doctorsMap.get("pageCount"));
			if (phoneNo != null && !phoneNo.equalsIgnoreCase("null")
					&& !phoneNo.equals("")) {
				Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
				UserInfos userInfo = userInfosDao.getById(userId);
				userInfo.setLongitude(longitude);
				userInfo.setLatitude(latitude);
				userInfosDao.update(userInfo);
			}

			return resultMap.createResult(1, resultsMap);
		} catch (Exception e) {
			logger.error("getAroundDoctor31", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> getAroundDoctorBy(double longitude,
			double latitude, String phoneNo, String condition) {
		Logger logger = Logger.getLogger(this.getClass());
		if (condition == null) {
			return resultMap.createResult(0,
					"condition 请求参数错误：serviceCount 或 qualification");
		} else if (!condition.equals("serviceCount")
				&& !condition.equals("qualification")) {
			return resultMap.createResult(0,
					"condition 请求参数错误：serviceCount 或 qualification");
		}
		Map<String, Object> range;
		range = AddressRange.getRange(longitude, latitude,
				ManageConstant.PatientCanSee);

		String hql = "from Doctor"
				+ " where "
				+ "doctorRealtimeInfo.longitude BETWEEN :MinLong and :MaxLong "
				+ " and doctorRealtimeInfo.latitude BETWEEN :MinLati and :MaxLati "
				+ " and doctorRealtimeInfo.serviceStatus in('"
				+ DoctorServiceStatus.open + "','"
				+ DoctorServiceStatus.service + "') order by " + condition
				+ " desc";

		try {
			List<Doctor> doctorMaps = doctorDao.getAll(hql, range);
			HashMap<String, Object> doctorMap = null;
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			List<HashMap<String, Object>> resultsList = new ArrayList<HashMap<String, Object>>();
			for (Doctor doctor : doctorMaps) {
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
				doctorMap = new HashMap<String, Object>();
				doctorMap.put("doctorId", aDoctorInfo.getDoctorId());
				doctorMap.put("doctorType", aDoctorInfo.getDoctorType());
				doctorMap.put("doctorPhone", aDoctorInfo.getDoctorPhone());
				doctorMap.put("birthday", aDoctorInfo.getBirthday());
				doctorMap.put("sex", aDoctorInfo.getSex());
				doctorMap.put("qualification", aDoctorInfo.getQualification());
				doctorMap.put("department", aDoctorInfo.getDepartment());
				doctorMap.put("starLevel", aDoctorInfo.getStarLevel());
				doctorMap.put("hospital", aDoctorInfo.getHospital());
				doctorMap.put("longitude", aDoctorInfo.getLongitude());
				doctorMap.put("latitude", aDoctorInfo.getLatitude());
				doctorMap.put("speciallist", aDoctorInfo.getSpecialty());
				doctorMap.put("doctorName", aDoctorInfo.getDoctorName());
				doctorMap.put("haveIcon", aDoctorInfo.isHaveIcon());
				doctorMap.put("doctorNumber", aDoctorInfo.getDoctorNumber());
				doctorMap.put("serviceTime", aDoctorInfo.getServiceTime());
				doctorMap.put("qualificationNo",
						aDoctorInfo.getQualificationNo());
				doctorMap.put("serviceStatus", aDoctorInfo.getServiceStatus());

				double juLi = Length.gps2m(latitude, longitude,
						aDoctorInfo.getLatitude(), aDoctorInfo.getLongitude());
				int juLiM = (int) juLi;
				doctorMap.put("length", juLiM);
				resultsList.add(doctorMap);
			}
			resultMap.put("sortList", resultsList);
			if (phoneNo != null && !phoneNo.equalsIgnoreCase("null")
					&& !phoneNo.equals("")) {
				Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
				UserInfos userInfo = userInfosDao.getById(userId);
				userInfo.setLongitude(longitude);
				userInfo.setLatitude(latitude);
				userInfosDao.update(userInfo);
			}
			resultMap.put("code", 1);
			resultMap.put("msg", true);
			return resultMap;
		} catch (Exception e) {
			logger.error("getAroundDoctorBy", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> submitFeedback(Feedback aFeedback) {

		feedBackDao.save(aFeedback);

		return resultMap.createResult(1, "提交成功");
	}

	@Override
	public HashMap<String, Object> addPatientInfo(PatientInfo aPatientInfo) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			Serializable save = patientInfoDao.save(aPatientInfo);
			logger.info(save);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> addPatientInfo2(PatientInfo aPatientInfo) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			patientInfoDao.save(aPatientInfo);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			logger.error("addPatientInfo2", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> updatePatientInfo(PatientInfo aPatientInfo) {

		try {
			int infoId = aPatientInfo.getInfoId();
			PatientInfo dbPatientInfo = patientInfoDao.getById(infoId);
			if (dbPatientInfo == null) {
				return resultMap.createResult(0, "并没有该Info");
			}
			dbPatientInfo.setThis(aPatientInfo);
			patientInfoDao.saveOrUpdate(dbPatientInfo);
			return resultMap.createResult(1, "操作成功");

		} catch (Exception e) {

			return resultMap.createResult(0, e.toString());
		}
	}

	@Override
	public HashMap<String, Object> listPatientInfo(int userId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("patientId", userId);
		params.put("isDelete", "1");
		HashMap<String, Object> resultsMap = null;
		String hql = "from PatientInfo where patientId=:patientId and isDelete=:isDelete order by addTime desc";
		List<PatientInfo> patientInfoList = patientInfoDao
				.findPage(hql, params);
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try {
			for (PatientInfo patientInfo : patientInfoList) {
				resultsMap = new HashMap<String, Object>();
				resultsMap.put("infoId", patientInfo.getInfoId());
				resultsMap.put("infoName", patientInfo.getInfoName());
				resultsMap.put("patientId", patientInfo.getPatientId());
				resultsMap.put("patientName", patientInfo.getPatientName());
				resultsMap.put("birthday", patientInfo.getBirthday());
				resultsMap.put("contactPhone", patientInfo.getContactPhone());
				resultsMap.put("address", patientInfo.getAddress());
				resultsMap.put("sex", patientInfo.getSex());
				resultList.add(resultsMap);
			}
			return resultMap.createResult(1, resultList);
		} catch (Exception e) {
			return resultMap.createResult(0, "服务器错误");
		}
	}

	@Override
	public HashMap<String, Object> delPatientInfo(PatientInfo aPatientInfo) {
		try {
			PatientInfo dbPatientInfo = patientInfoDao.getById(aPatientInfo
					.getInfoId());
			if (dbPatientInfo == null) {
				return resultMap.createResult(0, "并没有该Info");
			}
			dbPatientInfo.setIsDelete("0");
			patientInfoDao.update(dbPatientInfo);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	// 为了让老板看见所有的医生,所以加了这个接口
	@Override
	public HashMap<String, Object> getAllDoctor() {
		List<Doctor> doctorList = doctorDao
				.getAll("from Doctor where status='verified'");
		List<DoctorInfo> doctorInfoList = new ArrayList<DoctorInfo>();

		for (Doctor doctor : doctorList) {
			DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
			doctorInfoList.add(aDoctorInfo);
		}
		System.out.println("getAllDoctor_________________");
		return resultMap.createResult(1, doctorInfoList);
	}

	@Override
	public HashMap<String, Object> getDoctorStatus(int doctorId) {
		DoctorRealtimeInfo doctorStatus = doctorRealtimeInfoDao
				.getById(doctorId);
		return resultMap.createResult(1, doctorStatus.getServiceStatus());
	}

	@Override
	public HashMap<String, Object> listPatientInfo2(int userId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("patientId", userId);
		params.put("isDelete", "1");
		HashMap<String, Object> resultsMap = null;
		String hql = "from PatientInfo where patientId=:patientId and isDelete=:isDelete order by addTime desc";
		List<PatientInfo> patientInfoList = patientInfoDao
				.findPage(hql, params);
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try {
			for (PatientInfo patientInfo : patientInfoList) {
				resultsMap = new HashMap<String, Object>();
				resultsMap.put("infoId", patientInfo.getInfoId());
				resultsMap.put("infoName", patientInfo.getInfoName());
				resultsMap.put("patientId", patientInfo.getPatientId());
				resultsMap.put("patientName", patientInfo.getPatientName());
				resultsMap.put("birthday", patientInfo.getBirthday());
				resultsMap.put("contactPhone", patientInfo.getContactPhone());
				resultsMap.put("address", patientInfo.getAddress());
				resultsMap.put("sex", patientInfo.getSex());
				resultsMap.put("addTime", patientInfo.getAddTime());
				resultList.add(resultsMap);
			}
			return resultMap.createResult(1, resultList);
		} catch (Exception e) {
			return resultMap.createResult(0, "服务器错误");
		}
	}

	@Override
	public HashMap<String, Object> setInfo(String address, String userName,
			String isDel, String phoneNo, Integer patientId) {
		Date date = new Date();
		PatientInfo info = new PatientInfo();
		info.setAddress(address);
		info.setAddTime(date);
		info.setBirthday(date);
		info.setSex("nothing");
		info.setIsDelete(isDel);
		info.setContactPhone(phoneNo);
		info.setInfoName("");
		info.setPatientId(patientId);
		info.setPatientName(userName);
		patientInfoDao.save(info);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format.format(date);
		String hql = "from PatientInfo where addTime='" + dateTime + "'";
		List<PatientInfo> paitnetInfo = patientInfoDao.findPage(hql, null);
		PatientInfo patientInfo = paitnetInfo.get(0);
		return resultMap.createResult(1, patientInfo);
	}

}
