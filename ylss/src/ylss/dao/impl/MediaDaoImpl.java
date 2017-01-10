package ylss.dao.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ylss.dao.MediaDao;
import ylss.model.table.Media;

@Repository
public class MediaDaoImpl extends BaseDaoImpl<Media, Integer> implements
		MediaDao {
	private final int pageSize = 10;

	public MediaDaoImpl() {
		super(Media.class);
	}

	@Override
	public HashMap<String, Object> getMedia(int pageNum) {
		Logger logger = Logger.getLogger(this.getClass());
		if (pageNum == 0) {
			pageNum = 1;
		}
		String hql = "from Media order by createTime desc";
		HashMap<String, Object> mediaList = super.findPage(hql, null, pageNum,
				pageSize);
		return mediaList;
	}

}
