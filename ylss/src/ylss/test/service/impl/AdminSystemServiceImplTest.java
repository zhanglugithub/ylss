package ylss.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.UtilConstant;
import ylss.model.table.Feedback;
import ylss.service.web.AdminSystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
public class AdminSystemServiceImplTest {
	// @Autowired
	@Resource
	AdminSystemService adminSystemService;

	@Test
	public void testAlterPlatformGetPercent() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAlterLengthUserSee() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAlterStartPage() {


	
		try {
			File aFile = new File(UtilConstant.absoluteUploadPathWindows
					+ "/headIcon/a.jpg");
			FileInputStream input;
			input = new FileInputStream(aFile);

			MultipartFile aMFile = new MockMultipartFile("a", input);
			adminSystemService.alterStartPage(aMFile);
		} catch (Exception e) {
				fail(e.toString());
		}

	}

	@Test
	public void testGetFeedbackList() {

		List<Feedback> feedbacks = adminSystemService.listFeedback(1, 5);
		assertEquals("", feedbacks.get(4).getContactWay());
	}

}
