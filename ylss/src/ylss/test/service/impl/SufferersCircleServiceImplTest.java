package ylss.test.service.impl;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ylss.dao.UserDao;
import ylss.model.constant.UtilConstant;
import ylss.model.table.User;
import ylss.service.app.SufferersCircleService;
import ylss.test.TestHead;

public class SufferersCircleServiceImplTest extends TestHead {

	@Resource
	SufferersCircleService sufferersCircleService;

	@Resource
	UserDao userDao;

	@Test
	public void uploadImgTest() throws Exception {
		Logger logger = Logger.getLogger(SufferersCircleServiceImplTest.class);
		File file = new File(UtilConstant.absoluteUploadPathWindows
				+ "/startPage/startPage.jpg");
		FileInputStream inputStream = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile(file.getName(),
				inputStream);

		MultipartFile filter[] = new MultipartFile[8];
		for (int i = 0; i < filter.length; i++) {
			filter[i] = multipartFile;
		}
		int userId = 578;
		User user = new User();
		user.setUserId(userId);
		// HashMap<String, Object> result = sufferersCircleService.setShuo(578,
		// "北极光", "打发无聊客户斯蒂芬妮", filter);
		// logger.info(result);
	}

	@Test
	public void getShuoTest() {
		// HashMap<String, Object> shuo = sufferersCircleService
		// .getShuo(0, 578, 0);
		// System.out.println(shuo);
	}
}
