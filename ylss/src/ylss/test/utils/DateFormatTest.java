package ylss.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateFormatTest {

	@Test
	public void DateFormatTests() {
		Date date = new Date();
		Long time = date.getTime() - 60 * 60 * 24*1000;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFormat.format(time);

		System.out.println(format);
	}
}
