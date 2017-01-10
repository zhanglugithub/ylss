package ylss.service.utils.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;
import ylss.service.utils.UtilsService;
import ylss.utils.PhoneAttribution;

@Service
@Transactional
public class UtilsServiceImpl implements UtilsService {

	@Override
	public JSONObject phoneAttributes(String phoneNo) {
		return PhoneAttribution.getRequest1(phoneNo);
	}

}
