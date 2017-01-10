package ylss.utils;

import java.util.HashMap;

public  class resultMap {
	
	public static HashMap<String , Object> createResult(int code,Object msg) {
		
		if(code!=1&code!=0) {
			System.out.println("return code  竟然 不是 0或者 1");
		}
		
		HashMap<String , Object> result=new HashMap<String, Object>();
		result.put("code", code);
		result.put("msg",msg);
		return result;
		
	}
	

}
