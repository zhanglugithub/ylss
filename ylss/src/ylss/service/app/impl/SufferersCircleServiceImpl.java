package ylss.service.app.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.BrowseDao;
import ylss.dao.CommentDao;
import ylss.dao.SufferersCircleDao;
import ylss.dao.UserDao;
import ylss.dao.ZanDao;
import ylss.model.table.Browse;
import ylss.model.table.Comment;
import ylss.model.table.SufferersCircle;
import ylss.model.table.User;
import ylss.model.table.Zan;
import ylss.service.app.SufferersCircleService;
import ylss.utils.FileTool;
import ylss.utils.resultMap;

@Service
@Transactional
public class SufferersCircleServiceImpl implements SufferersCircleService {

	@Resource
	UserDao userDao;

	@Resource
	SufferersCircleDao sufferersCircleDao;

	@Resource
	CommentDao commentDao;

	@Resource
	BrowseDao browseDao;

	@Resource
	ZanDao zanDao;

	@Override
	public HashMap<String, Object> setShuo(String phoneNo, String address,
			String shuo, MultipartFile[] imgs) {
		Logger logger = Logger.getLogger(this.getClass());

		User dbUser = userDao.getByPhoneNo(phoneNo);
		String results = null;
		List<String> imgList = new ArrayList<String>();
		try {
			SufferersCircle sufferersCircle = new SufferersCircle();
			if (imgs.length != 0) {
				for (int i = 0; i < imgs.length; i++) {
					// 上传图片
					FileTool fileTool = new FileTool();
					String fileName = fileTool.getFileName(null) + "_" + i;
					if (dbUser == null) {
						results = (String) FileTool.uploadImg(imgs[i],
								fileName, fileTool.getDir(null)).get("msg");
					} else {
						results = (String) FileTool.uploadImg(imgs[i],
								fileName, fileTool.getDir(phoneNo)).get("msg");
					}
					imgList.add(results);
				}
				sufferersCircle.setImage(imgList.toString());
			} else {
				sufferersCircle.setImage("0");
			}

			sufferersCircle.setUserId(dbUser.getUserId());
			sufferersCircle.setAddress(address);
			sufferersCircle.setShuo(shuo);
			sufferersCircle.setCreateTime(new Date());
			sufferersCircle.setIsDelete("1");
			sufferersCircleDao.save(sufferersCircle);

			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			logger.info("setShuo错误" + e);
			return resultMap.createResult(0, "操作失败");
		}
	}

	@Override
	public HashMap<String, Object> uploadImg(User user, int shuoId,
			MultipartFile[] imgs) {
		Logger logger = Logger.getLogger(this.getClass());
		int userId = user.getUserId();
		User dbUser = userDao.getById(userId);
		String results = null;
		List<String> luJing = new ArrayList<String>();
		try {
			for (int i = 0; i < imgs.length; i++) {
				// 上传图片
				FileTool fileTool = new FileTool();
				String fileName = fileTool.getFileName(null) + "_" + i;
				if (dbUser == null) {
					results = (String) FileTool.uploadImg(imgs[i], fileName,
							fileTool.getDir(null)).get("msg");
				} else {
					results = (String) FileTool.uploadImg(imgs[i], fileName,
							fileTool.getDir(dbUser.getPhoneNo())).get("msg");
				}
				luJing.add(results);
				SufferersCircle sufferersCircle = sufferersCircleDao
						.getByUserId(userId);
				sufferersCircle.setImage(luJing.toString());
				sufferersCircleDao.saveOrUpdate(sufferersCircle);
			}
			return resultMap.createResult(1, luJing);
		} catch (Exception e) {
			logger.info("uploadImg:" + e);
			return resultMap.createResult(0, "操作失败");
		}
	}

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> getShuo(int shuoId, int userId,
			String phoneNo, int pageNo) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		int pageSize = 10;
		Logger logger = Logger.getLogger(this.getClass());

		String hql = "";
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> result = null;
		HashMap<String, Object> results = null;
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try {
			if (shuoId == 0 && userId != 0) {
				// 获取用户的所有说说
				results = new HashMap<String, Object>();
				params.put("userId", userId);
				hql = "from SufferersCircle where userId=:userId order by createTime desc";
				HashMap<String, Object> scResult = sufferersCircleDao.findPage(
						hql, params, pageNo, pageSize);
				List<SufferersCircle> scList = (List<SufferersCircle>) scResult
						.get("result");
				for (SufferersCircle sc : scList) {
					result = new HashMap<String, Object>();
					result.put("scId", sc.getScId());
					result.put("userId", sc.getUserId());
					result.put("userName", sc.getUser().getUserName());
					result.put("address", sc.getAddress());
					result.put("shuo", sc.getShuo());
					if (!sc.getImage().equals("0")) {
						String images = sc.getImage();
						String replace = images.replace("[", "");
						String replace2 = replace.replace("]", "");
						String[] split = replace2.split(", ");
						result.put("imgCount", split.length);
					} else if (sc.getImage().equals("[]")) {
						result.put("imgCount", 0);
					} else {
						result.put("imgCount", 0);
					}
					Integer myUserId = userDao.getByPhoneNo(phoneNo)
							.getUserId();
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("scId", sc.getScId());
					param.put("userId", myUserId);
					result.put("zanCount", sc.getZanCount());
					if (sc.getZanCount() > 0) {
						List<Zan> zanList = zanDao.findPage(
								"from Zan where userId=:userId and scId=:scId",
								param);
						if (zanList.size() == 0) {
							result.put("myZan", false);
						} else {
							result.put("myZan", true);
						}
					} else {
						result.put("myZan", false);
					}
					result.put("browseCount", sc.getBrowseCount());
					if (sc.getBrowseCount() > 0) {
						List<Browse> brList = browseDao
								.findPage(
										"from Browse where userId=:userId and scId=:scId",
										param);
						if (brList.size() == 0) {
							result.put("myBrowse", false);
						} else {
							result.put("myBrowse", true);
						}
					} else {
						result.put("myBrowse", false);
					}
					result.put("commentCount", sc.getCommentCount());
					if (sc.getCommentCount() > 0) {
						List<Comment> comList = commentDao
								.findPage(
										"from Comment where userId=:userId and scId=:scId",
										param);
						if (comList.size() == 0) {
							result.put("myComment", false);
						} else {
							result.put("myComment", true);
						}
					} else {
						result.put("myComment", false);
					}
					result.put("image", sc.getImage());
					result.put("zanCount", sc.getZanCount());
					result.put("browseCount", sc.getBrowseCount());
					result.put("commentCount", sc.getCommentCount());
					result.put("createTime", sc.getCreateTime());
					User user = sc.getUser();
					result.put("userId", user.getUserId());
					result.put("phoneNo", user.getPhoneNo());
					result.put("haveIcon", user.getHaveIcon());
					resultList.add(result);
				}
				results.put("code", 1);
				results.put("msg", "操作成功");
				results.put("sufferersCircle", resultList);
				results.put("pageCount", (int) scResult.get("pageCount"));
				return results;
			} else if (shuoId == 0 && userId == 0) {
				// 获取我的个人说说
				Integer dbUserId = userDao.getByPhoneNo(phoneNo).getUserId();
				results = new HashMap<String, Object>();
				params.put("userId", dbUserId);
				hql = "from SufferersCircle where userId=:userId order by createTime desc";
				HashMap<String, Object> scResult = sufferersCircleDao.findPage(
						hql, params, pageNo, pageSize);
				List<SufferersCircle> scList = (List<SufferersCircle>) scResult
						.get("result");
				for (SufferersCircle sc : scList) {
					result = new HashMap<String, Object>();
					result.put("scId", sc.getScId());
					result.put("userId", sc.getUserId());
					result.put("userName", sc.getUser().getUserName());
					result.put("address", sc.getAddress());
					result.put("shuo", sc.getShuo());
					if (!sc.getImage().equals("0")) {
						String images = sc.getImage();
						String replace = images.replace("[", "");
						String replace2 = replace.replace("]", "");
						String[] split = replace2.split(", ");
						result.put("imgCount", split.length);
					} else if (sc.getImage().equals("[]")) {
						result.put("imgCount", 0);
					} else {
						result.put("imgCount", 0);
					}
					result.put("image", sc.getImage());
					result.put("zanCount", sc.getZanCount());
					result.put("browseCount", sc.getBrowseCount());
					result.put("commentCount", sc.getCommentCount());
					result.put("createTime", sc.getCreateTime());
					User user = sc.getUser();
					result.put("userId", user.getUserId());
					result.put("phoneNo", user.getPhoneNo());
					result.put("haveIcon", user.getHaveIcon());
					resultList.add(result);
				}
				results.put("code", 1);
				results.put("msg", "操作成功");
				results.put("sufferersCircle", resultList);
				results.put("pageCount", (int) scResult.get("pageCount"));
				return results;
			} else {
				// 获取说说及评论
				results = new HashMap<String, Object>();
				SufferersCircle sufferer = sufferersCircleDao.getById(shuoId);
				params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("scId", sufferer.getScId());
				results.put("commentCount", sufferer.getCommentCount());
				List<Comment> comList = commentDao
						.findPage(
								"from Comment where userId=:userId and scId=:scId and isDelete=1",
								params);
				if (comList.size() == 0) {
					results.put("myComment", false);
				} else {
					results.put("myComment", true);
				}
				List<Zan> zanList = zanDao
						.findPage(
								"from Zan where userId=:userId and scId=:scId and isDelete=1",
								params);
				results.put("zanCount", sufferer.getZanCount());
				if (zanList.size() == 0) {
					results.put("myZan", false);
				} else {
					results.put("myZan", true);
				}
				results.put("zanCount", sufferer.getZanCount());
				// if (sufferer.getBrowseCount() > 0) {
				List<Browse> brList = browseDao.findPage(
						"from Browse where userId=:userId and scId=:scId",
						params);
				results.put("browseCount", sufferer.getBrowseCount());
				if (brList.size() == 0) {
					Browse browse = new Browse();
					browse.setScId(sufferer.getScId());
					browse.setUserId(userId);
					browse.setCreateTime(new Date());
					HashMap<String, Object> historyBrowse = this.setBrowse(
							browse, phoneNo);
					Boolean isBrowse = (Boolean) historyBrowse.get("myBrowse");
					results.put("myBrowse", isBrowse);
				} else {
					results.put("myBrowse", true);
				}
				// }
				// if (sufferer.getCommentCount() > 0) {
				HashMap<String, Object> coParam = new HashMap<String, Object>();
				coParam.put("scId", shuoId);
				coParam.put("isDelete", "1");
				hql = "from Comment where scId=:scId and isDelete=:isDelete order by createTime desc";
				HashMap<String, Object> commentResult = commentDao.findPage(
						hql, coParam, pageNo, pageSize);
				List<Comment> commentList = (List<Comment>) commentResult
						.get("result");
				for (Comment comment : commentList) {
					result = new HashMap<String, Object>();
					result.put("coId", comment.getCoId());
					result.put("scId", comment.getUserId());
					result.put("userId", comment.getScId());
					result.put("comment", comment.getComment());
					result.put("createTime", comment.getCreateTime());
					result.put("haveIcon", comment.getUser().getHaveIcon());
					result.put("userName", comment.getUser().getUserName());
					result.put("phoneNo", comment.getUser().getPhoneNo());
					resultList.add(result);
				}
				int countNo = (int) commentResult.get("pageCount");
				results.put("pageCount", countNo);
				results.put("comments", resultList);
				results.put("code", 1);
				results.put("msg", "操作成功");
				return results;
			}
		} catch (Exception e) {
			logger.error("getShuo" + e);
			return resultMap.createResult(0, "getShuo 获取失败");
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public HashMap<String, Object> getShuoAll(int userId, int pageNo,
			int pageSize) {
		// 获取所有说说
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		HashMap<String, Object> results = null;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("isDelete", "1");
		String hql = "from SufferersCircle where isDelete=:isDelete order by createTime desc";
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> result = sufferersCircleDao.findPage(hql,
				params, pageNo, pageSize);
		List<SufferersCircle> scList = (List<SufferersCircle>) result
				.get("result");

		for (SufferersCircle sc : scList) {
			results = new HashMap<String, Object>();
			results.put("scId", sc.getScId());
			results.put("userId", sc.getUserId());
			results.put("userName", sc.getUser().getUserName());
			results.put("address", sc.getAddress());
			results.put("shuo", sc.getShuo());
			String images = sc.getImage();
			String replace = images.replace("[", "");
			String replace2 = replace.replace("]", "");
			String[] split = replace2.split(", ");
			results.put("imgCount", split.length);
			results.put("image", sc.getImage());
			results.put("zanCount", sc.getZanCount());
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("scId", sc.getScId());
			param.put("userId", userId);
			if (sc.getZanCount() > 0) {
				List<Zan> zanList = zanDao.findPage(
						"from Zan where userId=:userId and scId=:scId", param);
				if (zanList.size() == 0) {
					results.put("myZan", false);
				} else {
					results.put("myZan", true);
				}
			} else {
				results.put("myZan", false);
			}
			results.put("browseCount", sc.getBrowseCount());
			if (sc.getBrowseCount() > 0) {
				List<Browse> brList = browseDao.findPage(
						"from Browse where userId=:userId and scId=:scId",
						param);
				if (brList.size() == 0) {
					results.put("myBrowse", false);
				} else {
					results.put("myBrowse", true);
				}
			} else {
				results.put("myBrowse", false);
			}
			results.put("commentCount", sc.getCommentCount());
			if (sc.getCommentCount() > 0) {
				List<Comment> comList = commentDao.findPage(
						"from Comment where userId=:userId and scId=:scId",
						param);
				if (comList.size() == 0) {
					results.put("myComment", false);
				} else {
					results.put("myComment", true);
				}
			} else {
				results.put("myComment", false);
			}
			results.put("createTime", sc.getCreateTime());
			User user = sc.getUser();
			results.put("phoneNo", user.getPhoneNo());
			results.put("haveIcon", user.getHaveIcon());
			resultList.add(results);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageCount", result.get("pageCount"));
		resultMap.put("sufferersCircle", resultList);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> setComment(String phoneNo, Comment comment) {
		try {
			Integer scId = comment.getScId();
			String urlComent = comment.getComment();
			Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
			Comment dbComment = new Comment();
			dbComment.setScId(scId);
			dbComment.setUserId(userId);
			dbComment.setComment(urlComent);
			dbComment.setCreateTime(new Date());
			dbComment.setIsDelete("1");
			commentDao.save(dbComment);
			SufferersCircle sufferersCircle = sufferersCircleDao.getById(scId);
			int oldCommentCount = sufferersCircle.getCommentCount();
			int newCommentCount = oldCommentCount + 1;
			sufferersCircle.setCommentCount(newCommentCount);
			sufferersCircleDao.update(sufferersCircle);
			return resultMap.createResult(1, "评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(0, "操作失败");
		}
	}

	@Override
	public HashMap<String, Object> setBrowse(Browse urlBrowse, String phoneNo) {
		try {
			Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
			Integer scId = urlBrowse.getScId();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("scId", scId);
			params.put("userId", userId);
			String hql = "from Browse where userId=:userId and scId=:scId";
			Browse dbBrowse = null;

			List<Browse> resultBrowse = browseDao.findPage(hql, params);
			if (resultBrowse.size() == 0) {
			} else {
				dbBrowse = resultBrowse.get(0);
			}
			if (dbBrowse == null) {
				Browse browse = new Browse();
				browse.setScId(scId);
				browse.setUserId(userId);
				browse.setCreateTime(new Date());
				browseDao.save(browse);
				SufferersCircle sc = sufferersCircleDao.getById(scId);
				Integer oldBrowseCount = sc.getBrowseCount();
				int newBrowseCount = oldBrowseCount + 1;
				sc.setBrowseCount(newBrowseCount);
				sufferersCircleDao.update(sc);
			}
			HashMap<String, Object> resultsMap = new HashMap<String, Object>();
			resultsMap.put("code", 1);
			resultsMap.put("myBrowse", false);
			return resultsMap;
		} catch (Exception e) {
			HashMap<String, Object> resultsMap = new HashMap<String, Object>();
			resultsMap.put("code", 0);
			resultsMap.put("myBrowse", false);
			e.printStackTrace();
			return resultsMap;
		}
	}

	@Override
	public HashMap<String, Object> setZan(Zan urlZan, String phoneNo) {
		try {
			Integer scId = urlZan.getScId();
			Integer userId = userDao.getByPhoneNo(phoneNo).getUserId();
			SufferersCircle sc = sufferersCircleDao.getById(scId);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("scId", scId);
			params.put("userId", userId);
			String hql = "from Zan where scId=:scId and userId=:userId";
			List<Zan> dbZans = zanDao.findPage(hql, params);
			Zan dbZan = null;
			if (dbZans.size() != 0) {
				dbZan = dbZans.get(0);
			}
			if (dbZan == null) {
				Zan zan = new Zan();
				zan.setScId(scId);
				zan.setUserId(userId);
				zan.setCreateTime(new Date());
				zan.setIsDelete("1");
				zanDao.save(zan);
				Integer oldZanCount = sc.getZanCount();
				int newZanCount = oldZanCount + 1;
				sc.setZanCount(newZanCount);
				sufferersCircleDao.update(sc);
				HashMap<String, Object> resultsMap = new HashMap<String, Object>();
				SufferersCircle sufferer = sufferersCircleDao.getById(scId);
				resultsMap.put("zanCount", sufferer.getZanCount());
				resultsMap.put("code", 1);
				resultsMap.put("myZan", true);
				return resultsMap;
			} else {
				if (dbZan.getIsDelete().equalsIgnoreCase("1")) {
					dbZan.setIsDelete("0");
					zanDao.update(dbZan);
					Integer oldZanCount = sc.getZanCount();
					int newZanCount = oldZanCount - 1;
					sc.setZanCount(newZanCount);
					sufferersCircleDao.update(sc);
					HashMap<String, Object> resultsMap = new HashMap<String, Object>();
					SufferersCircle sufferer = sufferersCircleDao.getById(scId);
					resultsMap.put("zanCount", sufferer.getZanCount());
					resultsMap.put("code", 1);
					resultsMap.put("myZan", false);
					return resultsMap;
				} else {
					dbZan.setIsDelete("1");
					zanDao.update(dbZan);
					Integer oldZanCount = sc.getZanCount();
					int newZanCount = oldZanCount + 1;
					sc.setZanCount(newZanCount);
					sufferersCircleDao.update(sc);
					HashMap<String, Object> resultsMap = new HashMap<String, Object>();
					SufferersCircle sufferer = sufferersCircleDao.getById(scId);
					resultsMap.put("zanCount", sufferer.getZanCount());
					resultsMap.put("code", 1);
					resultsMap.put("myZan", true);
					return resultsMap;
				}
			}
		} catch (Exception e) {
			HashMap<String, Object> resultsMap = new HashMap<String, Object>();
			resultsMap.put("code", 0);
			resultsMap.put("myZan", false);
			e.printStackTrace();
			return resultsMap;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getBrowse(int shuoId, int pageNo,
			int pageSize) {
		Logger logger = Logger.getLogger(this.getClass());
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
		}
		try {
			// if
			// (sufferersCircleDao.getById(shuoId).getBrowseCount().equals(0)) {
			// return resultMap.createResult(1, "暂无浏览记录");
			// }
			HashMap<String, Object> params = new HashMap<String, Object>();
			HashMap<String, Object> result = new HashMap<String, Object>();
			HashMap<String, Object> results = null;
			List<HashMap<String, Object>> resultsList = new ArrayList<HashMap<String, Object>>();
			params.put("scId", shuoId);
			String hql = "from Browse where scId=:scId order by createTime desc";
			HashMap<String, Object> resultBrowse = browseDao.findPage(hql,
					params, pageNo, pageSize);
			List<Browse> browseList = (List<Browse>) resultBrowse.get("result");
			for (Browse browse : browseList) {
				results = new HashMap<String, Object>();
				results.put("userId", browse.getUserId());
				results.put("PhoneNo", browse.getUser().getPhoneNo());
				results.put("haveIcon", browse.getUser().getHaveIcon());
				results.put("userName", browse.getUser().getUserName());
				results.put("createTime", browse.getCreateTime());
				resultsList.add(results);
			}
			result.put("pageCount", resultBrowse.get("pageCount"));
			result.put("browseList", resultsList);
			return resultMap.createResult(1, result);
		} catch (Exception e) {
			logger.error("getBrowse 失败", e);
			return resultMap.createResult(0, "操作失败");
		}
	}

	@Override
	public HashMap<String, Object> getZan(String phoneNo, int scId) {
		Logger logger = Logger.getLogger(this.getClass());
		HashMap<String, Object> params = new HashMap<String, Object>();
		String hql = "from Zan where userId=:userId and scId=:scId and isDelete=1";
		params.put("userId", userDao.getByPhoneNo(phoneNo).getUserId());
		params.put("scId", scId);
		try {
			List<Zan> zanList = zanDao.findPage(hql, params);
			if (zanList.get(0) == null) {
				return resultMap.createResult(1, false);
			} else {
				return resultMap.createResult(1, true);
			}
		} catch (Exception e) {
			logger.error("getZan", e);
			return resultMap.createResult(0, false);
		}
	}
}