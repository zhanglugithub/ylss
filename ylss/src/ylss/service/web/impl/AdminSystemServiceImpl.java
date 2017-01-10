package ylss.service.web.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.FeedBackDao;
import ylss.model.constant.ManageConstant;
import ylss.model.table.Feedback;
import ylss.service.web.AdminSystemService;
import ylss.utils.FileTool;
import ylss.utils.resultMap;

@Service
@Transactional
public class AdminSystemServiceImpl implements AdminSystemService {

	@Resource
	FeedBackDao feedBackDao;

	@Override
	public HashMap<String, Object> alterDoctorGetPercent(double percent) {

		ManageConstant.DoctorGetPercent = percent;

		return resultMap.createResult(1, "操作成功");
	}

	@Override
	public HashMap<String, Object> alterLengthUserSee(double length) {

		ManageConstant.PatientCanSee = length;

		return resultMap.createResult(1, "操作成功");
	}

	@Override
	public HashMap<String, Object> alterStartPage(MultipartFile startPage) {
		if (startPage == null) {
			return resultMap.createResult(0, "图片为空");
		}

		if (startPage.getSize() <= 0) {
			return resultMap.createResult(0, "size不对");
		}
		FileTool.uploadImg(startPage, "startPage","startPage");

		return resultMap.createResult(1, "操作成功");
	}

	@Override
	public List<Feedback> listFeedback(int pageNo, int pageSize) {

		return feedBackDao.getAPage(pageNo, pageSize);
	}

	@Override
	public long getFeedBackTotalNo() {

		return feedBackDao.countAll();

	}

}
