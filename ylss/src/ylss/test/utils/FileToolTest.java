package ylss.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.UtilConstant;
import ylss.test.TestHead;
import ylss.utils.FileTool;

public class FileToolTest extends TestHead {
	
	String  phoneNo="15102271234";
	
	@Test
	public void testUpload() {
		File aFile = new File(UtilConstant.absoluteUploadPathWindows+"/HeadIcon/notImg.jpg");
		
		FileInputStream input;
		try {
			input = new FileInputStream(aFile);
		
		MultipartFile aMFile = new MockMultipartFile("not", input); // 此处a 应该无所谓吧；
		HashMap<String, Object> result= 	FileTool.uploadImg(aMFile, phoneNo, "headIcon");

		
		File upLoadFile=new File(UtilConstant.absoluteUploadPathWindows+"/HeadIcon/"+phoneNo+".jpg");
		
		System.out.println(result.get("msg"));
		assertEquals(0,result.get("code"));
		
		assertTrue(upLoadFile.exists());
		
		
		} catch (Exception e) {
			
			fail(e.toString()); 
			
		}
	}

}
