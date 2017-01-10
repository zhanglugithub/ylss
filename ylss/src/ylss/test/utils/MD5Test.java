package ylss.test.utils;


import org.junit.Test;

import ylss.test.TestHead;
import ylss.utils.MD5;

public class MD5Test extends  TestHead{

	@Test
	public void testParseMD5() {
		
	    String MD5OfInput=	 MD5.parseMD5("123456");
		
		System.out.println(MD5OfInput);
		
		//3553be8b0b04cd39e250de6fcbcbf285
		//3553be8b0b04cd39e250de6fcbcbf285
//		String MD5GetOnline="3553be8b0b04cd39e250de6fcbcbf285";
		
//		assertEquals(MD5OfInput, MD5GetOnline);
	}

}
