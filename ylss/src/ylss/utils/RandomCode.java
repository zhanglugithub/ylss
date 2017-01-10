package ylss.utils;

import java.util.Random;

public class RandomCode {

	public static String getRandomCode(int length) {
		Random r = new Random();
		String RandomCode = "";
		for (int i = 0; i < length; i++) {
			int num = r.nextInt(10);
			RandomCode += num;
		}
		return RandomCode;
	}
}
