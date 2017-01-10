package ylss.test.utils;


import org.junit.Assert;
import org.junit.Test;

import ylss.utils.RandomCode;

public class RandomCodeTest {

	@Test
	public void testGetRandomCode() {
		
		System.out.println("randomCode is "+RandomCode.getRandomCode(6) );
		Assert.assertEquals(RandomCode.getRandomCode(6).length(), 6);
	
	}

}
