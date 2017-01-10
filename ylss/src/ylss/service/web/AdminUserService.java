package ylss.service.web;

import java.util.Date;
import java.util.HashMap;

public interface AdminUserService {

	public String lockUser(String phoneNo, Date lockDate);

	public HashMap<String, Object> getCode(String phoneNo);

}
