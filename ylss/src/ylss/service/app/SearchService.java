package ylss.service.app;

import java.util.HashMap;

public interface SearchService {

	HashMap<String, Object> byIllness(String illness, String condition,
			Double latitude, Double longitude, int pageNo, int pageSize);

	HashMap<String, Object> byDoctorType(String doctorType, String condition, Double latitude,
			Double longitude, int pageNo, int pageSize);

	HashMap<String, Object> byHospital(String hospital, String condition, Double latitude,
			Double longitude, int pageNo, int pageSize);

	HashMap<String, Object> byKeShi(String keShi, String condition, Double latitude,
			Double longitude, int pageNo, int pageSize);

	HashMap<String, Object> getDetailDoctor(int doctorId);

	HashMap<String, Object> byNurse(String nurse, String condition, Double latitude,
			Double longitude, int pageNo, int pageSize);

	HashMap<String, Object> getDoctorType();

	HashMap<String, Object> getHospital();

	HashMap<String, Object> getKeShi();

}
