package ylss.service.app;

import java.util.HashMap;

import ylss.model.table.Feedback;
import ylss.model.table.PatientInfo;

/**
 * 
 * @author JACK 病人除了关于订单的其他业务的接口
 */
public interface PatientBasicService {

	public HashMap<String, Object> getDoctorInfo(int doctorId);

	public HashMap<String, Object> getAroundDoctor(double longitude,
			double latitude, String phoneNo);

	public HashMap<String, Object> getAroundDoctor2(double longitude,
			double latitude, String phoneNo);

	public HashMap<String, Object> getAroundDoctor3(double longitude,
			double latitude, String phoneNo);

	public HashMap<String, Object> getAroundDoctorBy(double longitude,
			double latitude, String phoneNo, String condition);

	public HashMap<String, Object> getAllDoctor();

	public HashMap<String, Object> submitFeedback(Feedback aFeedback);

	public HashMap<String, Object> delPatientInfo(PatientInfo aPatientInfo);

	public HashMap<String, Object> addPatientInfo(PatientInfo aPatientInfo);

	public HashMap<String, Object> updatePatientInfo(PatientInfo aPatientInfo);

	public HashMap<String, Object> listPatientInfo(int userId);

	public HashMap<String, Object> getDoctorInfo2(int doctorId);

	public HashMap<String, Object> getDoctorStatus(int doctorId);

	public HashMap<String, Object> getAroundDoctor31(double longitude,
			double latitude, String phoneNo, int pageNo, int pageSize,
			String doctorType);

	public HashMap<String, Object> addPatientInfo2(PatientInfo aPatientInfo);

	public HashMap<String, Object> listPatientInfo2(int patientId);

	public HashMap<String, Object> setInfo(String address, String userName,
			String isDel, String phoneNo, Integer patientId);

}
