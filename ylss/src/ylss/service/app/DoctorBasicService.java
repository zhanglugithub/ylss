/**
 * 
 */
package ylss.service.app;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Doctor;
import ylss.model.table.Offer;

/**
 * @author JACK 病人除了关于订单的其他业务的接口
 */
public interface DoctorBasicService {

	public HashMap<String, Object> getVerifyInfo(String phoneNo);

	public HashMap<String, Object> submitVerify(Doctor aDoctor, String phoneNo,
			MultipartFile[] imgs);

	public HashMap<String, Object> submitVerifyIosDoctor(Doctor aDoctor,
			String phoneNo);

	public HashMap<String, Object> submitVerifyIos(Doctor aDoctor,
			String phoneNo, MultipartFile img0, MultipartFile img1,
			MultipartFile img2);

	public List<Offer> getOfferList();

	public HashMap<String, Object> setServiceState(String phoneNo, boolean state);

	public HashMap<String, Object> updateLocation(String phoneNo,
			double longitude, double latitude);

	public HashMap<String, Object> submitVerifyIos2(String phoneNo,
			MultipartFile[] imgs, Doctor aDoctor, String userName);

	public HashMap<String, Object> submitVerifyIosDoctor2(Doctor aDoctor,
			String phoneNo, String userName);

	public HashMap<String, Object> submitVerifyIosImg(String phoneNo,
			MultipartFile img, String imgName);

}
