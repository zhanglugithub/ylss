package ylss.utils;

import java.util.HashMap;
import java.util.Map;

public class AddressRange {
	static double REARTH = 6378.1;

	public static Map<String, Object> getRange(double longitude, double latitude, double range) {

		Map<String, Object> result = new HashMap<String, Object>();

		double LaRange = range / (REARTH * Math.PI / 180);
		double LoRange = range * 360.0 / (Math.cos(Math.toRadians(latitude)) * REARTH * 2 * Math.PI);
		result.put("MaxLong", longitude + LoRange);
		result.put("MinLong", longitude - LoRange);
		result.put("MaxLati", latitude + LaRange);
		result.put("MinLati", latitude - LaRange);

		return result;

	}

}
