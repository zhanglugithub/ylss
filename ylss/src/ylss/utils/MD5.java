package ylss.utils;

import org.apache.commons.codec.digest.DigestUtils;

import ylss.model.constant.UtilConstant;

public class MD5 {

	public static String parseMD5(String inputString) {

		inputString = inputString + UtilConstant.magic; // magicString

		return DigestUtils.md5Hex(inputString);
	}

}
