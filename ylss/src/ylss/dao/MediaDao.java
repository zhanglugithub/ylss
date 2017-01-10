package ylss.dao;

import java.util.HashMap;

import ylss.model.table.Media;

public interface MediaDao extends BaseDao<Media, Integer> {

	public HashMap<String, Object> getMedia(int pageNum);

}
