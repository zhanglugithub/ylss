package ylss.service.app.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.DoctorDao;
import ylss.dao.UserDao;
import ylss.dao.UserInfosDao;
import ylss.model.constant.ManageConstant;
import ylss.model.table.User;
import ylss.model.table.UserInfos;
import ylss.service.app.AroundPatientService;
import ylss.utils.AddressRange;
import ylss.utils.Length;
import ylss.utils.Sort;

@Service
@Transactional
public class AroundPatientServiceImpl implements AroundPatientService {

	@Resource
	DoctorDao doctorDao;

	@Resource
	UserDao userDao;

	@Resource
	UserInfosDao userInfosDao;

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getAroundPatient(double longitude,
			double latitude, String phoneNo, int pageNo, int pageSize) {
		Map<String, Object> range;
		range = AddressRange.getRange(longitude, latitude,
				ManageConstant.PatientCanSeeOther);
		String hql = "select user from User user,UserInfos ui"
				+ " where "
				+ " ui.longitude BETWEEN :MinLong and :MaxLong "
				+ " and ui.latitude BETWEEN :MinLati and :MaxLati "
				+ " and user.status in('online') and ui.longitude is not null and ui.latitude is not null and user.userId=ui.userId";
		HashMap<String, Object> doctorsMap = userDao.findPage(hql, range,
				pageNo, pageSize);
		List<User> doctorList = (List<User>) doctorsMap.get("result");
		HashMap<String, Object> userMap = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> juLiList = new ArrayList<HashMap<String, Object>>();
		for (User user : doctorList) {
			UserInfos userInfos = userInfosDao.getById(user.getUserId());
			if (userInfos != null) {
				if (userInfos.getLatitude() != null) {
					if (userInfos.getLongitude() != null) {
						userMap = new HashMap<String, Object>();
						userMap.put("userId", user.getUserId());
						userMap.put("haveIcon", user.getHaveIcon());
						userMap.put("userName", user.getUserName());
						userMap.put("phoneNo", user.getPhoneNo());

						double juLi = Length.gps2m(latitude, longitude,
								userInfos.getLatitude(),
								userInfos.getLongitude());
						int juLiM = (int) juLi;
						userMap.put("length", juLiM);
						juLiList.add(userMap);
					}
				}
			}
		}

		List<HashMap<String, Object>> resultList = Sort.sort(juLiList);
		resultMap.put("juLiList", resultList);
		resultMap.put("pageCount", doctorsMap.get("pageCount"));
		if (phoneNo != null && !phoneNo.equalsIgnoreCase("null")
				&& !phoneNo.equals("")) {
			Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
			UserInfos userInfo = userInfosDao.getById(userId);
			if (userInfo == null) {
				UserInfos userinfos = new UserInfos();
				userinfos.setUserId(userId);
				userinfos.setLongitude(longitude);
				userinfos.setLatitude(latitude);
				userInfosDao.save(userinfos);
			} else {
				userInfo.setLongitude(longitude);
				userInfo.setLatitude(latitude);
				userInfosDao.update(userInfo);
			}
		}
		return ylss.utils.resultMap.createResult(1, resultMap);
	}
}
