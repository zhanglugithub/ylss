package ylss.service.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ecs.xhtml.object;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.CategoryT;
import ylss.model.table.Doctor;
import ylss.model.table.DoctorTypeT;
import ylss.model.table.HospitalT;
import ylss.model.table.KeShiT;

public interface AdminDoctorService {

	public HashMap<String, Object> getDoctorVerifyInfo(String phoneNo);

	public List<Doctor> listAllDoctorReview(int pageNo, int pageSize);

	public HashMap<String, Object> listAllDoctorReviewed(int pageNo,
			int pageSize);

	public HashMap<String, Object> reviewDoctorVerify(Doctor aDoctor);

	public HashMap<String, Object> rejectDoctorVerify(Doctor aDoctor);

	public Map<String, Object> removeVerified(Doctor doctor, String doctorName);

	public List<Doctor> listAllDoctor(int pageNo, int pageSize);

	public HashMap<String, Object> validatePassport(Integer doctorId);

	public HashMap<String, Object> submitVerifyImg(Doctor aDoctor,
			MultipartFile img0, MultipartFile img1, MultipartFile img2);

	public Map<String, Object> upload(Doctor doctor);

	public HashMap<String, Object> getDoctorInfo(int doctorId);

	public HashMap<String, Object> updateUser(String userName, Doctor aDoctor);

	public HashMap<String, Object> getHospital(int pageNo, int pageSize);

	public HashMap<String, Object> setHospital(HospitalT hosptal);

	public HashMap<String, Object> getDoctorType(int pageNo, int pageSize);

	public HashMap<String, Object> setDoctorType(DoctorTypeT doctorType);

	public HashMap<String, Object> getKeShi(int pageNo, int pageSize);

	public HashMap<String, Object> setKeShi(KeShiT keShi);

	public HashMap<String, Object> detailHospital(HospitalT hospital);

	public HashMap<String, Object> detailDoctorType(DoctorTypeT doctorType);

	public HashMap<String, Object> detailKeShi(KeShiT keShi);
	
//	public HashMap<String, object> getmet(int pager);

	public HashMap<String, Object> getCategory(int pageNo, int pageSize);

	public HashMap<String, Object> getDetailCategory(CategoryT category);

	public HashMap<String, Object> setCategory(CategoryT category);

}
