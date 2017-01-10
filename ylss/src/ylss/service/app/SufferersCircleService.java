package ylss.service.app;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Browse;
import ylss.model.table.Comment;
import ylss.model.table.User;
import ylss.model.table.Zan;

public interface SufferersCircleService {

	public HashMap<String, Object> uploadImg(User user, int shuoId,
			MultipartFile[] imgs);

	public HashMap<String, Object> getShuo(int shuoId, int userId,
			String phoneNo, int pageNo);

	public HashMap<String, Object> getShuoAll(int userId, int pageNo,
			int pageSize);

	public HashMap<String, Object> setShuo(String phoneNo, String address,
			String shuo, MultipartFile[] imgs);

	public HashMap<String, Object> setBrowse(Browse browse, String phoneNo);

	public HashMap<String, Object> setComment(String phoneNo, Comment comment);

	public HashMap<String, Object> setZan(Zan zan, String phoneNo);

	public HashMap<String, Object> getBrowse(int shuoId, int pageNo,
			int pageSize);

	public HashMap<String, Object> getZan(String phoneNo, int scId);

}
