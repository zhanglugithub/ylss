package ylss.service.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Feedback;

public interface AdminSystemService {
	public HashMap<String, Object> alterDoctorGetPercent(double percent);

	
	
	public HashMap<String, Object> alterLengthUserSee(double length);

	// OtherManage

	public HashMap<String, Object> alterStartPage(MultipartFile startPage);
	
	public  List<Feedback> listFeedback(int pageNo,int pageSize);
	
	public long getFeedBackTotalNo();

}
