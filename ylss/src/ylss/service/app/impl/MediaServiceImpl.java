package ylss.service.app.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.MediaDao;
import ylss.model.table.Media;
import ylss.service.app.MediaService;
import ylss.utils.resultMap;

@Transactional
@Service
public class MediaServiceImpl implements MediaService {

	@Resource
	MediaDao mediaDao;

	@Override
	public HashMap<String, Object> getMedia(int pageNum) {
		return mediaDao.getMedia(pageNum);
	}

	@Override
	public HashMap<String, Object> setMedia(Media media) {
		try {
			Media media2 = new Media();
			media2.setTitle(media.getTitle());
			media2.setImage(media.getTitle());
			media2.setLink(media.getLink());
			mediaDao.save(media2);

			return resultMap.createResult(1, "添加成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	@Override
	public HashMap<String, Object> listMedia(int pageNo, int pageSize) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String hql = "from Media order by id desc";
		return mediaDao.findPage(hql, params, pageNo, pageSize);
	}

	@Override
	public HashMap<String, Object> delMedia(Media media) {
		try {
			mediaDao.delete(media);
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			return resultMap.createResult(0, e);
		}
	}

	@Override
	public HashMap<String, Object> updateMedia(Media media) {
		try {
			if (media.getTitle() == null || media.getTitle().equals("")) {
				return resultMap.createResult(0, "title 不能为空");
			}
			if (media.getLink() == null || media.getLink().equals("")) {
				return resultMap.createResult(0, "link 不能为空");
			}
			if (media.getImage() == null || media.getImage().equals("")) {
				return resultMap.createResult(0, "image 不能为空");
			}
			if (media.getId() == null) {
				mediaDao.save(media);
			} else {
				mediaDao.update(media);
			}
			return resultMap.createResult(1, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap.createResult(1, "updateMedia 失败");
		}
	}

	@Override
	public HashMap<String, Object> toUpdateMedia(Media media) {
		Media dbMedia = mediaDao.getById(media.getId());
		return resultMap.createResult(1, dbMedia);
	}

	@Override
	public HashMap<String, Object> getMedia2(int pageNum) {
		HashMap<String, Object> media = mediaDao.getMedia(pageNum);
		HashMap<String, Object> mediaMap = new HashMap<String, Object>();
		mediaMap.put("mediaList", media.get("result"));
		mediaMap.put("pageCount", media.get("pageCount"));
		return resultMap.createResult(1, mediaMap);
	}
}
