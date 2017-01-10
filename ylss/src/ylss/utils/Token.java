package ylss.utils;

import java.util.UUID;

public  class Token {
	
	public  static String   getToken(String userId) {  
		
		UUID aUuid=UUID.randomUUID();
		
	    String  token = aUuid.toString()+MD5.parseMD5(userId);
		return token;
	}
	public static void main(String[] args) {
		UUID aUuid = UUID.randomUUID();
	    String  token = aUuid.toString()+MD5.parseMD5("356");			
		System.out.println(aUuid);
	}
}
