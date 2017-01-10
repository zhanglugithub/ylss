package ylss.service.app;

import java.util.HashMap;

public interface AroundPatientService {

	public HashMap<String, Object> getAroundPatient(double longitude,
			double latitude, String phoneNo, int pageNo, int pageSize);

}
