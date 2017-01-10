package ylss.service.app;

import java.util.HashMap;

import ylss.model.table.Media;

public interface MediaService {

	public HashMap<String, Object> getMedia(int pageNum);

	public HashMap<String, Object> setMedia(Media media);

	public HashMap<String, Object> listMedia(int pageNo, int pageSize);

	public HashMap<String, Object> delMedia(Media media);

	public HashMap<String, Object> toUpdateMedia(Media media);

	public HashMap<String, Object> updateMedia(Media media);

	public HashMap<String, Object> getMedia2(int pageNum);

}
