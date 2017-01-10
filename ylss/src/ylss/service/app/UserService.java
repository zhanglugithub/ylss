/**
 * 
 */
package ylss.service.app;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ylss.model.table.Doctor;
import ylss.model.table.User;

/**
 * @author JACK
 *
 */
public interface UserService {

	public HashMap<String, Object> getValidateCode(String urlPhoneNo);

	public HashMap<String, Object> validateValidateCode(String urlValidateCode,
			String urlPhoneNo, String userType);

	public HashMap<String, Object> updatePasswordByToken(String urlPhoneNo,
			String urlPassword);

	public HashMap<String, Object> updatePasswordByOldPassword(
			String urlPhoneNo, String urlOldPassword, String urlNewPassword);

	public HashMap<String, Object> login2(String urlPhoneNo,
			String urlPassword, String platform, String deviceToken); // 为了适应app

	public HashMap<String, Object> login3(String urlPhoneNo,
			String urlPassword, String platform, String deviceToken); // 为了适应app

	public HashMap<String, Object> login(String urlPhoneNo, String urlPassword); // 为了适应app

	public HashMap<String, Object> logout(String urlPhoneNo);

	public HashMap<String, Object> updateUserName(String urlPhoneNo,
			String urlNewUserName);

	public HashMap<String, Object> updateHeadIcon(String urlPhoneNo,
			MultipartFile image, String folderName);

	public HashMap<String, Object> isUserExist(String phoneNo);

	public HashMap<String, Object> getDoctorGPS();

	public HashMap<String, Object> getUser(User user, int pageNo, int pageSize)
			throws Exception;

	public int getUserNo();

	public HashMap<String, Object> countDoctor(Doctor doctor, int pageNo,
			int pageSize);

	public List<Doctor> getDoctor();

	public User getUser(Doctor doctor);

	public HashMap<String, Object> updateInfo(String phoneNo, String userName,
			String sex);

	public HashMap<String, Object> updatePasswordByToken2(String phoneNo,
			String password);

	public HashMap<String, Object> reLogin(String phoneNo, String deviceToken,
			String platform);
}
