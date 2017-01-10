package ylss.service.app.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.DoctorDao;
import ylss.dao.DoctorRealtimeInfoDao;
import ylss.dao.DoctoryTypeDao;
import ylss.dao.HospitalDao;
import ylss.dao.KeShiDao;
import ylss.model.logic.DoctorInfo;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorTypeT;
import ylss.model.table.HospitalT;
import ylss.model.table.KeShiT;
import ylss.service.app.SearchService;
import ylss.utils.Length;
import ylss.utils.Sort;
import ylss.utils.resultMap;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

	@Resource
	DoctorDao doctorDao;

	@Resource
	HospitalDao hospitalDao;

	@Resource
	DoctoryTypeDao doctoryTypeDao;

	@Resource
	KeShiDao keShiDao;

	@Resource
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	private String hqls = "select d from Doctor d,DoctorRealtimeInfo dri,User u where u.userId=d.doctorId and dri.serviceStatus='open' and d.status='verified' and d.doctorId=dri.doctorId ";

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> byIllness(String illness, String condition,
			Double latitude, Double longitude, int pageNo, int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String hql = null;
		if (illness == null || illness == "" || illness == "null") {
			if (condition == "" || condition == null || condition == "null") {
				hql = hqls;
			} else {
				hql = hqls + " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		} else {
			if (condition == "" || condition == null || condition == "null") {
				hql = "select d from Doctor d,DoctorRealtimeInfo dri,User u "
						+ "where u.userId=d.doctorId "
						+ "and d.specialty like '%"
						+ illness
						+ "%' "
						+ "and d.doctorId=dri.doctorId and dri.serviceStatus='open' ";
			} else {
				hql = "select d from Doctor d,DoctorRealtimeInfo dri,User u "
						+ "where u.userId=d.doctorId "
						+ "and d.specialty like '%"
						+ illness
						+ "%' "
						+ "and d.doctorId=dri.doctorId and dri.serviceStatus='open' "
						+ " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%')";
			}
		}

		try {
			HashMap<String, Object> findMap = null;
			findMap = doctorDao.findPage(hql, null, pageNo, pageSize);
			List<Doctor> doctorList = (List<Doctor>) findMap.get("result");
			List<HashMap<String, Object>> doctorsList = new ArrayList<HashMap<String, Object>>();
			for (Doctor doctor : doctorList) {
				HashMap<String, Object> doctorMap = new HashMap<String, Object>();
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
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
				doctorsList.add(doctorMap);
			}
			List<HashMap<String, Object>> sortList = Sort.sort(doctorsList);
			resultMap.put("pageCount", findMap.get("pageCount"));
			resultMap.put("doctorsList", sortList);
			resultMap.put("code", 1);
			resultMap.put("msg", "请求成功");
			return resultMap;
		} catch (Exception e) {
			logger.error("byIllness", e);
			return ylss.utils.resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> byDoctorType(String doctorType,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> doctorsMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> doctorsList = new ArrayList<HashMap<String, Object>>();
		String hql = null;
		if (doctorType == null || doctorType == "" || doctorType == "null") {
			if (condition == "" || condition == null || condition == "null") {
				hql = hqls + " and d.doctorType='doctor'";
			} else {
				hql = hqls
						+ " and d.doctorType='doctor' and( u.userName like '%"
						+ condition + "%'" + " or d.department like '%"
						+ condition + "%'" + " or d.hospital like '%"
						+ condition + "%'" + " or d.specialty like '%"
						+ condition + "%')";
			}
		} else {
			if (condition == "" || condition == null || condition == "null") {
				hql = "select d "
						+ "from DoctorTypeT dtt,Doctor d,DoctorRealtimeInfo dri,CategoryT ct "
						+ "where d.doctorId=dri.doctorId and dri.serviceStatus='open' "
						+ "and ct.childId=d.doctorId and ct.parentId=dtt.uid and d.doctorType='doctor'"
						+ "and dtt.doctorType like '%" + doctorType + "%' "
						+ "and d.status='verified' ";
			} else {
				hql = "select d "
						+ "from DoctorTypeT dtt,Doctor d,DoctorRealtimeInfo dri,CategoryT ct,User u "
						+ "where u.userId=d.doctorId and d.doctorId=dri.doctorId and dri.serviceStatus='open' "
						+ "and ct.childId=d.doctorId and ct.parentId=dtt.uid and d.doctorType='doctor'"
						+ "and dtt.doctorType like '%" + doctorType + "%' "
						+ "and d.status='verified' "
						+ " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		}
		try {
			HashMap<String, Object> findMap = null;
			findMap = doctorDao.findPage(hql, null, pageNo, pageSize);
			List<Doctor> doctorList = (List<Doctor>) findMap.get("result");
			for (Doctor doctor : doctorList) {
				HashMap<String, Object> doctorMap = new HashMap<String, Object>();
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
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
				doctorsList.add(doctorMap);
			}
			List<HashMap<String, Object>> sortList = Sort.sort(doctorsList);
			doctorsMap.put("pageCount", findMap.get("pageCount"));
			doctorsMap.put("doctorsList", sortList);
			doctorsMap.put("code", 1);
			doctorsMap.put("msg", "请求成功");
			return doctorsMap;
		} catch (Exception e) {
			logger.error("byDoctorType", e);
			return resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings("unused")
	private HashMap<String, Object> findDoctor(String hql,
			HashMap<String, Object> params, int pageNo, int pageSize) {
		return doctorDao.findPage(hql, params, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> byHospital(String hospital,
			String condition, Double latitude, Double longitude, int pageNo,
			int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> doctorsMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> doctorsList = new ArrayList<HashMap<String, Object>>();
		String hql = null;
		if (hospital == null || hospital == "" || hospital == "null") {
			if (condition == "" || condition == null || condition == "null") {
				hql = hqls;
			} else {
				hql = hqls + " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		} else {
			if (condition == "" || condition == null || condition == "null") {
				hql = "select d "
						+ "from HospitalT ht,Doctor d,DoctorRealtimeInfo dri,CategoryT ct "
						+ "where d.doctorId=dri.doctorId and dri.serviceStatus='open' "
						+ "and ct.childId=d.doctorId and ct.parentId=ht.uid "
						+ "and ht.hospital like '%" + hospital + "%' "
						+ "and d.status='verified' ";
			} else {
				hql = "select d "
						+ "from HospitalT ht,Doctor d,DoctorRealtimeInfo dri,CategoryT ct,User u "
						+ "where u.userId=d.doctorId and d.doctorId=dri.doctorId and dri.serviceStatus='open' "
						+ "and ct.childId=d.doctorId and ct.parentId=ht.uid "
						+ "and ht.hospital like '%" + hospital + "%' "
						+ "and d.status='verified' "
						+ " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		}
		try {
			HashMap<String, Object> findMap = null;
			findMap = doctorDao.findPage(hql, null, pageNo, pageSize);
			List<Doctor> doctorList = (List<Doctor>) findMap.get("result");
			for (Doctor doctor : doctorList) {
				HashMap<String, Object> doctorMap = new HashMap<String, Object>();
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
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
				doctorsList.add(doctorMap);
			}
			List<HashMap<String, Object>> sortList = Sort.sort(doctorsList);
			doctorsMap.put("pageCount", findMap.get("pageCount"));
			doctorsMap.put("doctorsList", sortList);
			doctorsMap.put("code", 1);
			doctorsMap.put("msg", "请求成功");
			return doctorsMap;
		} catch (Exception e) {
			logger.error("byHospital", e);
			return resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> byKeShi(String keShi, String condition,
			Double latitude, Double longitude, int pageNo, int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> doctorsMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> doctorsList = new ArrayList<HashMap<String, Object>>();
		String hql = null;
		if (keShi == null || keShi == "" || keShi == "null") {
			if (condition == "" || condition == null || condition == "null") {
				hql = hqls;
			} else {
				hql = hqls + " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		} else {
			if (condition == "" || condition == null || condition == "null") {
				hql = "select d "
						+ "from KeShiT kst,Doctor d,DoctorRealtimeInfo dri,CategoryT ct "
						+ "where d.doctorId=dri.doctorId and dri.serviceStatus='open' "
						+ "and ct.childId=d.doctorId and ct.parentId=kst.uid "
						+ "and kst.keShi like '%" + keShi + "%' "
						+ "and d.status='verified' ";
			} else {
				hql = "select d "
						+ "from KeShiT kst,Doctor d,DoctorRealtimeInfo dri,CategoryT ct,User u "
						+ "where d.doctorId=dri.doctorId and ct.childId=d.doctorId and ct.parentId=kst.uid and u.userId=d.doctorId "
						+ " and dri.serviceStatus='open' "
						+ "and kst.keShi like '%" + keShi + "%' "
						+ "and d.status='verified' "
						+ " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		}
		try {
			HashMap<String, Object> findMap = null;
			findMap = doctorDao.findPage(hql, null, pageNo, pageSize);
			List<Doctor> doctorList = (List<Doctor>) findMap.get("result");
			for (Doctor doctor : doctorList) {
				HashMap<String, Object> doctorMap = new HashMap<String, Object>();
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
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
				doctorsList.add(doctorMap);
			}
			List<HashMap<String, Object>> sortList = Sort.sort(doctorsList);
			doctorsMap.put("pageCount", findMap.get("pageCount"));
			doctorsMap.put("doctorsList", sortList);
			doctorsMap.put("code", 1);
			doctorsMap.put("msg", "请求成功");
			return doctorsMap;
		} catch (Exception e) {
			logger.error("byHospital", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> getDetailDoctor(int doctorId) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			Doctor doctor = doctorDao.getById(doctorId);
			HashMap<String, Object> doctorDetail = new HashMap<String, Object>();
			doctorDetail.put("doctorId", doctor.getDoctorId());
			doctorDetail.put("doctorType", doctor.getDoctorType());
			doctorDetail.put("starLevel", doctor.getStarLevel());
			doctorDetail.put("userName", doctor.getUser().getUserName());
			doctorDetail.put("haveIcon", doctor.getUser().getHaveIcon());
			doctorDetail.put("phoneNo", doctor.getUser().getPhoneNo());
			doctorDetail.put("serviceStatus", doctor.getDoctorRealtimeInfo()
					.getServiceStatus());
			doctorDetail.put("serviceCount", doctor.getDoctorRealtimeInfo()
					.getServiceCount());
			doctorDetail.put("status", doctor.getStatus());
			doctorDetail.put("birthday", doctor.getBirthday());
			doctorDetail.put("sex", doctor.getSex());
			doctorDetail.put("qualification", doctor.getQualification());
			doctorDetail.put("qualificationNo", doctor.getQualificationNo());
			doctorDetail.put("department", doctor.getDepartment());
			doctorDetail.put("hospital", doctor.getHospital());
			doctorDetail.put("specialty", doctor.getSpecialty());
			return resultMap.createResult(1, doctorDetail);
		} catch (Exception e) {
			logger.error("getDetailDoctor", e);
			return resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> byNurse(String nurse, String condition,
			Double latitude, Double longitude, int pageNo, int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		String hql = null;
		if (nurse == null || nurse == "" || nurse == "null") {
			if (condition == "" || condition == null || condition == "null") {
				hql = "select d "
						+ "from Doctor d,DoctorRealtimeInfo dri "
						+ "where d.doctorId=dri.doctorId and d.status='verified' and d.doctorType='expert' "
						+ "and dri.serviceStatus='open' order by dri.serviceCount desc";
			} else {
				hql = "select d "
						+ "from Doctor d,DoctorRealtimeInfo dri,User u "
						+ "where u.userId=d.doctorId and d.doctorId=dri.doctorId and d.status='verified' "
						+ "and d.doctorType='expert' and dri.serviceStatus='open' "
						+ " and( u.userName like '%" + condition + "%'"
						+ " or d.department like '%" + condition + "%'"
						+ " or d.hospital like '%" + condition + "%'"
						+ " or d.specialty like '%" + condition + "%')";
			}
		} else {
			hql = "";
			return resultMap.createResult(1, "此功能正在开发中");
		}
		try {
			HashMap<String, Object> doctorsMap = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> doctorsList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> findMap = null;
			findMap = doctorDao.findPage(hql, null, pageNo, pageSize);
			List<Doctor> doctorList = (List<Doctor>) findMap.get("result");
			for (Doctor doctor : doctorList) {
				HashMap<String, Object> doctorMap = new HashMap<String, Object>();
				DoctorInfo aDoctorInfo = new DoctorInfo(doctor);
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
				doctorsList.add(doctorMap);
			}
			List<HashMap<String, Object>> sortList = Sort.sort(doctorsList);
			doctorsMap.put("pageCount", findMap.get("pageCount"));
			doctorsMap.put("nurseList", sortList);
			doctorsMap.put("code", 1);
			doctorsMap.put("msg", "请求成功");
			return doctorsMap;
		} catch (Exception e) {
			logger.error("byNurse", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> getDoctorType() {
		Logger logger = Logger.getLogger(this.getClass());
		String hql = "from DoctorTypeT";
		try {
			List<DoctorTypeT> findDT = doctoryTypeDao.findPage(hql, null);
			ArrayList<HashMap<String, Object>> doctorTypes = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> results = new HashMap<String, Object>();
			for (DoctorTypeT doctorT : findDT) {
				HashMap<String, Object> doctorType = new HashMap<String, Object>();
				doctorType.put("dtId", doctorT.getDtId());
				doctorType.put("doctorType", doctorT.getDoctorType());
				doctorTypes.add(doctorType);
			}
			results.put("doctorType", doctorTypes);
			results.put("code", 1);
			results.put("msgs", true);
			return results;
		} catch (Exception e) {
			logger.error("getDoctorType", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> getHospital() {
		Logger logger = Logger.getLogger(this.getClass());
		String hql = "from HospitalT";
		try {
			List<HospitalT> hospital = hospitalDao.findPage(hql, null);
			ArrayList<HashMap<String, Object>> hospitals = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> results = new HashMap<String, Object>();
			for (HospitalT hospitalT : hospital) {
				HashMap<String, Object> hospitalMap = new HashMap<String, Object>();
				hospitalMap.put("hoId", hospitalT.getHoId());
				hospitalMap.put("hospital", hospitalT.getHospital());
				hospitalMap.put("addTime", hospitalT.getAddTime());
				hospitals.add(hospitalMap);
			}
			results.put("hospital", hospitals);
			results.put("code", 1);
			results.put("msgs", true);
			return results;
		} catch (Exception e) {
			logger.error("getHospital", e);
			return resultMap.createResult(0, false);
		}
	}

	@Override
	public HashMap<String, Object> getKeShi() {
		Logger logger = Logger.getLogger(this.getClass());
		String hql = "from KeShiT";
		try {
			List<KeShiT> keshi = keShiDao.findPage(hql, null);
			ArrayList<HashMap<String, Object>> keShis = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> results = new HashMap<String, Object>();
			for (KeShiT keShiT : keshi) {
				HashMap<String, Object> keShi = new HashMap<String, Object>();
				keShi.put("keShiId", keShiT.getKeShiId());
				keShi.put("keShi", keShiT.getKeShi());
				keShis.add(keShi);
			}
			results.put("keShi", keShis);
			results.put("code", 1);
			results.put("msg", true);
			return results;
		} catch (Exception e) {
			logger.error("getKeShi", e);
			return resultMap.createResult(0, false);
		}
	}
}
