package ylss.test.utils;



import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ylss.utils.Token;

public class TokenTest {

	@Test
	public void test() {
		
		  String aToken= Token.getToken("356");
		 assertEquals(68,aToken.length());
	} 
}
