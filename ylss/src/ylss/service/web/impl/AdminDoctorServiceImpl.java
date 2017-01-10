package ylss.service.web.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.CategoryDao;
import ylss.dao.DoctorDao;
import ylss.dao.DoctorRealtimeInfoDao;
import ylss.dao.DoctoryTypeDao;
import ylss.dao.HospitalDao;
import ylss.dao.IllnessDao;
import ylss.dao.KeShiDao;
import ylss.dao.TableNameTDao;
import ylss.dao.UserDao;
import ylss.model.constant.databaseConstant.DoctorStatus;
import ylss.model.logic.DoctorInfo;
import ylss.model.table.CategoryT;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorRealtimeInfo;
import ylss.model.table.DoctorTypeT;
import ylss.model.table.HospitalT;
import ylss.model.table.IllnessT;
import ylss.model.table.KeShiT;
import ylss.model.table.TableNameT;
import ylss.model.table.User;
import ylss.service.web.AdminDoctorService;
import ylss.utils.FileTool;
import ylss.utils.PassportValidate;
import ylss.utils.resultMap;

@Service
@Transactional
public class AdminDoctorServiceImpl implements AdminDoctorService {

	@Resource
	@Autowired
	UserDao userDao;

	@Resource
	TableNameTDao tableNameTDao;

	@Resource
	@Autowired
	DoctorRealtimeInfoDao doctorRealtimeInfoDao;

	@Resource
	HospitalDao hospitalDao;

	@Resource
	CategoryDao categoryDao;

	@Resource
	KeShiDao keShiDao;

	@Resource
	DoctoryTypeDao doctoryTypeDao;

	@Resource
	IllnessDao illnessDao;

	@Resource
	@Autowired
	DoctorDao doctorDao;

	@Override
	public List<Doctor> listAllDoctor(int pageNo, int pageSize) {
		List<Doctor> verfyingDoctor = doctorDao.getAPage(pageNo, pageSize,
				"status", "'" + DoctorStatus.verifying + "'");
		return verfyingDoctor;
	}

	@Override
	public Map<String, Object> removeVerified(Doctor doctor, String doctorName) {
		try {
			Doctor dbDoctor = doctorDao.getById(doctor.getDoctorId());
			User user = userDao.getById(doctor.getDoctorId());
			if (!doctorName.equals("") || doctorName != null) {
				user.setUserName(doctorName);
				userDao.update(user);
			}
			DoctorRealtimeInfo doctorRealtimeInfo = doctorRealtimeInfoDao
					.getById(doctor.getDoctorId());
			doctorRealtimeInfo.setServiceStatus(DoctorStatus.close);
			doctorRealtimeInfoDao.update(doctorRealtimeInfo);
			dbDoctor.setBirthday(doctor.getBirthday());
			dbDoctor.setAlipayNo(doctor.getAlipayNo());
			dbDoctor.setDepartment(doctor.getDepartment());
			if (doctor.getDoctorType() != null) {
				dbDoctor.setDoctorType(doctor.getDoctorType());
			}
			dbDoctor.setHospital(doctor.getHospital());
			dbDoctor.setIdCard(doctor.getIdCard());
			dbDoctor.setQualificationNo(doctor.getQualificationNo());
			dbDoctor.setQualification(doctor.getQualification());
			if (doctor.getSex() != null) {
				dbDoctor.setSex(doctor.getSex());
			}
			dbDoctor.setSpecialty(doctor.getSpecialty());
			dbDoctor.setStarLevel(doctor.getStarLevel());
			doctorDao.saveOrUpdate(dbDoctor);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	@Override
	public HashMap<String, Object> getDoctorVerifyInfo(String phoneNo) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			int userId = userDao.getByPhoneNo(phoneNo).getUserId();
			Doctor aDoctor = doctorDao.getById(userId);
			if (aDoctor == null) {
				return resultMap.createResult(0, "尚未提交认证");
			}
			return resultMap.createResult(1, aDoctor);

		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public List<Doctor> listAllDoctorReview(int pageNo, int pageSize) {
		List<Doctor> doctorList = doctorDao.getAPage(pageNo, pageSize,
				"status", "'verifying'");
		return doctorList;
	}

	@Override
	public HashMap<String, Object> listAllDoctorReviewed(int pageNo,
			int pageSize) {
		String hql = "from Doctor where status=:verified order by doctorId desc";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("verified", "verified");
		HashMap<String, Object> doctorList = doctorDao.findPage(hql, params,
				pageNo, pageSize);

		return doctorList;
	}

	@Override
	public HashMap<String, Object> updateUser(String userName, Doctor aDoctor) {
		User user = userDao.getById(aDoctor.getDoctorId());
		try {
			user.setUserName(userName);
			userDao.update(user);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, "操作失败");
		}
	}

	@Override
	public HashMap<String, Object> reviewDoctorVerify(Doctor aDoctor) {
		try {
			Integer aDoctorId = aDoctor.getDoctorId();
			Doctor dbDoctor = doctorDao.getById(aDoctorId);
			if (aDoctor.getQualificationNo() != null) {
				dbDoctor.setQualificationNo(aDoctor.getQualificationNo());
			}
			if (aDoctor.getIdCard() != null) {
				dbDoctor.setIdCard(aDoctor.getIdCard());
			}
			if (aDoctor.getAlipayNo() != null) {
				dbDoctor.setAlipayNo(aDoctor.getAlipayNo());
			}
			if (aDoctor.getDepartment() != null) {
				dbDoctor.setDepartment(aDoctor.getDepartment());
			}
			if (aDoctor.getHospital() != null) {
				dbDoctor.setHospital(aDoctor.getHospital());
			}
			if (aDoctor.getSpecialty() != null) {
				dbDoctor.setSpecialty(aDoctor.getSpecialty());
			}
			dbDoctor.setSex(aDoctor.getSex());
			dbDoctor.setBirthday(aDoctor.getBirthday());
			dbDoctor.setQualificationNo(aDoctor.getQualificationNo());
			dbDoctor.setIdCard(aDoctor.getIdCard());
			dbDoctor.setAlipayNo(aDoctor.getAlipayNo());
			dbDoctor.setDepartment(aDoctor.getDepartment());
			dbDoctor.setHospital(aDoctor.getHospital());
			dbDoctor.setSpecialty(aDoctor.getSpecialty());
			dbDoctor.setDoctorType(aDoctor.getDoctorType());
			dbDoctor.setDoctorNumber(aDoctor.getDoctorNumber()
					+ aDoctorId.toString());
			dbDoctor.setStarLevel(aDoctor.getStarLevel());
			dbDoctor.setQualification(aDoctor.getQualification());
			dbDoctor.setStatus(DoctorStatus.verified);
			doctorDao.saveOrUpdate(dbDoctor);
			if (doctorRealtimeInfoDao.getById(aDoctorId) == null) {
				DoctorRealtimeInfo aDoctorRealtimeInfo = new DoctorRealtimeInfo(
						aDoctorId);
				doctorRealtimeInfoDao.save(aDoctorRealtimeInfo);
			}

			return resultMap.createResult(1, "审核通过");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> rejectDoctorVerify(Doctor aDoctor) {
		try {
			Integer aDoctorId = aDoctor.getDoctorId();
			Doctor dbDoctor = doctorDao.getById(aDoctorId);
			if (dbDoctor.getStatus().equals(DoctorStatus.verifying)) {
				dbDoctor.setStatus(DoctorStatus.notVerified);
				doctorDao.saveOrUpdate(dbDoctor);

				return resultMap.createResult(1, "成功拒绝该用户，请电话联系说明拒绝原因");
			} else {
				return resultMap.createResult(0, "该用户不处于审核中状态");
			}
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> validatePassport(Integer doctorId) {
		Doctor doctor = doctorDao.getById(doctorId);
		Logger logger = Logger.getLogger(this.getClass());
		String passport = doctor.getIdCard();
		PassportValidate passportValidate = new PassportValidate();
		String passportInfo = passportValidate.request(passport);
		try {
			HashMap<String, Object> result = new HashMap<String, Object>();
			JSONObject jsonString = new JSONObject(passportInfo);
			int errNum = (int) jsonString.get("errNum");
			String retMsg = (String) jsonString.get("retMsg");
			result.put("phoneNo", doctor.getUser().getPhoneNo());
			result.put("errNum", errNum);
			if (errNum != 0) {
				result.put("retMsg", retMsg);
				return resultMap.createResult(0, result);
			}
			JSONObject retData = jsonString.getJSONObject("retData");
			String address = (String) retData.get("address");
			String sex = (String) retData.get("sex");// M-男，F-女，N-未知
			if ("M".equalsIgnoreCase(sex)) {
				sex = "男";
			} else if ("F".equalsIgnoreCase(sex)) {
				sex = "女";
			} else {
				sex = "未知";
			}
			String birthday = (String) retData.get("birthday");
			result.put("doctorId", doctor.getDoctorId());
			result.put("doctorName", doctor.getUser().getUserName());
			result.put("address", address);
			result.put("sex", sex);
			result.put("birthday", birthday);
			result.put("idCard", passport);
			return resultMap.createResult(1, result);
		} catch (JSONException e) {
			logger.error("验证身份证出错", e);
			return resultMap.createResult(0, "验证身份证出错，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> submitVerifyImg(Doctor aDoctor,
			MultipartFile img0, MultipartFile img1, MultipartFile img2) {
		User user = userDao.getById(aDoctor.getDoctorId());
		FileTool.uploadImg(img0, user.getPhoneNo(), "idCard");
		FileTool.uploadImg(img1, user.getPhoneNo(), "allowWork");
		FileTool.uploadImg(img2, user.getPhoneNo(), "qulification");
		try {
			return resultMap.createResult(1, "提交成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}
	}

	@Override
	public Map<String, Object> upload(Doctor doctor) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<Object, Object> result = new HashMap<>();
		User user = userDao.getById(doctor.getDoctorId());
		String phoneNo = user.getPhoneNo();
		result.put("phoneNo", phoneNo);
		result.put("doctorId", doctor.getDoctorId());
		result.put("haveIcon", user.getHaveIcon());
		logger.info(user.getHaveIcon().toString());
		return resultMap.createResult(1, result);
	}

	@Override
	public HashMap<String, Object> getDoctorInfo(int doctorId) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Doctor doctor = doctorDao.getById(doctorId);
		DoctorInfo doctorInfo = new DoctorInfo(doctor);
		User user = userDao.getById(doctor.getDoctorId());
		Boolean haveIcon = user.getHaveIcon();
		resultMap.put("doctorInfo", doctorInfo);
		resultMap.put("haveIcon", haveIcon);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getHospital(int pageNo, int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		String hql = "from HospitalT where isDel='1' order by addTime desc";
		try {
			HashMap<String, Object> hospitalMap = hospitalDao.findPage(hql,
					null, pageNo, pageSize);
			List<HospitalT> hospitals = (List<HospitalT>) hospitalMap
					.get("result");
			resultsMap.put("hospitals", hospitals);
			resultsMap.put("pageCount", hospitalMap.get("pageCount"));
			resultsMap.put("code", 1);
			return resultsMap;
		} catch (Exception e) {
			logger.error("getHospital", e);
			return resultMap.createResult(0, "系统出错，请查看日志");
		}
	}

	@Override
	public HashMap<String, Object> setHospital(HospitalT hosptal) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			hosptal.setUid(UUID.randomUUID().toString());
			hosptal.setAddTime(new Date());
			hosptal.setIsDel("1");
			hospitalDao.save(hosptal);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			logger.error("setHospital", e);
			return resultMap.createResult(0, false);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public HashMap<String, Object> getDoctorType(int pageNo, int pageSize) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> results = new HashMap<String, Object>();
		String hql = "from DoctorTypeT where isDel='1'";
		try {
			HashMap<String, Object> doctorMap = doctorDao.findPage(hql, null,
					pageNo, pageSize);
			List<DoctorTypeT> doctorList = (List<DoctorTypeT>) doctorMap
					.get("result");
			results.put("pageCount", doctorMap.get("pageCount"));
			results.put("doctorList", doctorList);
			results.put("code", 1);
			return results;
		} catch (Exception e) {
			logger.error("getDoctorType", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> setDoctorType(DoctorTypeT doctorType) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			doctorType.setUid(UUID.randomUUID().toString());
			doctorType.setCreateTime(new Date());
			doctorType.setIsDel("1");
			doctoryTypeDao.save(doctorType);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			logger.error("setDoctor", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getKeShi(int pageNo, int pageSize) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> results = new HashMap<String, Object>();
		String hql = "from KeShiT where isDel='1' order by createTime desc";
		try {
			HashMap<String, Object> keShiMap = keShiDao.findPage(hql, null,
					pageNo, pageSize);
			List<KeShiT> keShiList = (List<KeShiT>) keShiMap.get("result");
			results.put("pageCount", keShiMap.get("pageCount"));
			results.put("keShiList", keShiList);
			results.put("code", 1);
			return results;
		} catch (Exception e) {
			logger.error("getKeShi", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> setKeShi(KeShiT keShi) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			keShi.setUid(UUID.randomUUID().toString());
			keShi.setCreateTime(new Date());
			keShi.setIsDel("1");
			keShiDao.save(keShi);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			logger.error("setKeShi", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> detailHospital(HospitalT hospital) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			if (hospital.getHospital() == null) {
				Integer hoId = hospital.getHoId();
				HospitalT hospitalT = hospitalDao.getById(hoId);
				results.put("hospital", hospitalT);
				results.put("code", 1);
				return results;
			} else {
				hospitalDao.update(hospital);
				return resultMap.createResult(1, "操作成功");
			}
		} catch (Exception e) {
			logger.error("detailHospital", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> detailDoctorType(DoctorTypeT doctorType) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			if (doctorType.getDoctorType() == null) {
				Integer dtId = doctorType.getDtId();
				DoctorTypeT typeT = doctoryTypeDao.getById(dtId);
				results.put("doctorType", typeT);
				results.put("code", 1);
				return results;
			} else {
				doctoryTypeDao.update(doctorType);
				return resultMap.createResult(1, "操作成功");
			}
		} catch (Exception e) {
			logger.error("detailDoctorType", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> detailKeShi(KeShiT keShi) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			if (keShi.getKeShi() == null) {
				KeShiT keShiT = keShiDao.getById(keShi.getKeShiId());
				results.put("keShi", keShiT);
				results.put("code", 1);
				return results;
			} else {
				keShiDao.update(keShi);
				return resultMap.createResult(1, "操作成功");
			}
		} catch (Exception e) {
			logger.error("detailKeShi", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getCategory(int pageNo, int pageSize) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		Logger logger = Logger.getLogger(this.getClass());
		String hql = "from CategoryT where isDel='1' order by createTime desc";
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		try {
			HashMap<String, Object> category = categoryDao.findPage(hql, null,
					pageNo, pageSize);
			List<CategoryT> cat = (List<CategoryT>) category.get("result");
			for (CategoryT categoryT : cat) {
				HashMap<String, Object> catMap = new HashMap<String, Object>();
				catMap.put("catId", categoryT.getCatId());
				catMap.put("category", categoryT.getCategory());
				catMap.put("doctorName", categoryT.getDoctor().getUser()
						.getUserName());
				if (categoryT.getCategory().equalsIgnoreCase("doctorType")) {
					DoctorTypeT doctorType = doctoryTypeDao.getByUid(categoryT
							.getParentId());
					catMap.put("doctorType", doctorType.getDoctorType());
				}
				if (categoryT.getCategory().equalsIgnoreCase("keShi")) {
					KeShiT keShi = keShiDao.getByUid(categoryT.getParentId());
					catMap.put("keShi", keShi.getKeShi());
				}
				if (categoryT.getCategory().equalsIgnoreCase("hospital")) {
					HospitalT hospitalT = hospitalDao.getByUid(categoryT
							.getParentId());
					catMap.put("hospital", hospitalT.getHospital());
				}
				if (categoryT.getCategory().equalsIgnoreCase("illness")) {
					IllnessT illness = illnessDao.getByUid(categoryT
							.getParentId());
					catMap.put("illness", illness.getIllness());
				}
				newList.add(catMap);
			}
			resultsMap.put("catList", newList);
			resultsMap.put("code", 1);
			resultsMap.put("pageCount", category.get("pageCount"));
			return resultsMap;
		} catch (Exception e) {
			logger.error("getCategory", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> getDetailCategory(CategoryT category) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			if (category.getCategory() == null) {
				CategoryT dbCategory = categoryDao.getById(category.getCatId());
				HashMap<String, Object> catMap = new HashMap<String, Object>();
				if (dbCategory.getCategory().equalsIgnoreCase("doctorType")) {
					DoctorTypeT doctorType = doctoryTypeDao.getByUid(dbCategory
							.getParentId());
					catMap.put("catDoctorType", doctorType.getDoctorType());
					catMap.put("uid", doctorType.getUid());
					catMap.put("doctorTypeList", doctoryTypeDao
							.getAll("from DoctorTypeT where isDel='1'"));
				}
				if (dbCategory.getCategory().equalsIgnoreCase("keShi")) {
					KeShiT keShi = keShiDao.getByUid(dbCategory.getParentId());
					catMap.put("catKeShi", keShi.getKeShi());
					catMap.put("uid", keShi.getUid());
					catMap.put("keShiList",
							keShiDao.getAll("from KeShiT where isDel='1'"));
				}
				if (dbCategory.getCategory().equalsIgnoreCase("hospital")) {
					HospitalT hospitalT = hospitalDao.getByUid(dbCategory
							.getParentId());
					catMap.put("catHospital", hospitalT.getHospital());
					catMap.put("uid", hospitalT.getUid());
					catMap.put("hospitalList", hospitalDao
							.getAll("from HospitalT where isDel='1'"));
				}
				if (dbCategory.getCategory().equalsIgnoreCase("illness")) {
					IllnessT illness = illnessDao.getByUid(dbCategory
							.getParentId());
					catMap.put("uid", illness.getUid());
					catMap.put("catIllness", illness.getIllness());
					catMap.put("illnessList",
							illnessDao.getAll("from IllnessT where isDel='1'"));
				}
				catMap.put("category", dbCategory.getCategory());
				catMap.put("catId", dbCategory.getCatId());
				catMap.put("doctorId", dbCategory.getChildId());
				catMap.put("createTime", dbCategory.getCreateTime());
				catMap.put("parentId", dbCategory.getParentId());
				catMap.put("doctorName", dbCategory.getDoctor().getUser()
						.getUserName());
				catMap.put("department", dbCategory.getDoctor().getDepartment());
				catMap.put("hospital", dbCategory.getDoctor().getHospital());
				catMap.put("specialty", dbCategory.getDoctor().getSpecialty());
				catMap.put("isDel", dbCategory.getIsDel());
				catMap.put("code", 1);
				return catMap;
			} else {
				categoryDao.update(category);
				return resultMap.createResult(1, "操作成功");
			}
		} catch (Exception e) {
			logger.error("getDetailCategory", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	@Override
	public HashMap<String, Object> setCategory(CategoryT category) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			if (category.getChildId() == null || category.getParentId() == null
					|| category.getCategory() == null) {
				HashMap<String, Object> results = new HashMap<String, Object>();
				String hql = "select d from Doctor d,User u "
						+ "where d.status='verified' and d.doctorId=u.userId order by u.createTime desc";
				List<Doctor> doctorList = doctorDao.getAll(hql);
				results.put("doctorTypeList", doctoryTypeDao
						.getAll("from DoctorTypeT where isDel='1'"));
				results.put("keShiList",
						keShiDao.getAll("from KeShiT where isDel='1'"));
				results.put("hospitalList",
						hospitalDao.getAll("from HospitalT where isDel='1'"));
				results.put("illnessList",
						illnessDao.getAll("from IllnessT where isDel='1'"));
				results.put("doctorList", doctorList);
				List<TableNameT> categorys = tableNameTDao
						.getAll("from TableNameT");
				results.put("categoryList", categorys);
				results.put("code", 1);
				return results;
			} else {
				String hql = "from CategoryT where category='"
						+ category.getCategory() + "' and childId="
						+ category.getChildId();
				List<CategoryT> catList = categoryDao.getAll(hql);
				if (catList.size() == 0) {
					category.setIsDel("1");
					category.setCreateTime(new Date());
					categoryDao.save(category);
					return resultMap.createResult(1, "操作成功");
				} else {
					HashMap<String, Object> errorMap = new HashMap<String, Object>();
					TableNameT tableName = tableNameTDao
							.getBytableName(category.getCategory());
					errorMap.put("code", 1);
					errorMap.put("error", userDao
							.getById(category.getChildId()).getUserName()
							+ "的"
							+ tableName.getTableValue() + "已添加");
					return errorMap;
				}
			}
		} catch (Exception e) {
			logger.error("setCategory", e);
			return resultMap.createResult(0, "系统错误，请联系管理员");
		}
	}

	public HashMap<Object, Object> getMap(Object keyValue, Object values) {
		HashMap<Object, Object> mapMap = new HashMap<Object, Object>();
		mapMap.put(keyValue, values);
		return mapMap;
	}

}
