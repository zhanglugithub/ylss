package ylss.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.UtilConstant;

public class FileTool {

	public static HashMap<String, Object> uploadImg(MultipartFile file,
			String fileName, String filePath) {
		try {

			if (file == null) {
				return resultMap.createResult(0, "没带上文件参数");
			}

			if (file.isEmpty()) {

				return resultMap.createResult(0, "传入文件为空");
			}

			if (file.getSize() > UtilConstant.maxUploadSize) {
				return resultMap.createResult(0, file + "超过2M");
			}

			String filePathToUse;

			byte[] bytes = file.getBytes();

			Path path = Paths.get(UtilConstant.absoluteUploadPathLinux);
			// Path path = Paths.get(UtilConstant.absoluteUploadPathWindows);
			if (path.toFile().exists()) {
				filePathToUse = UtilConstant.absoluteUploadPathLinux;
			} else {
				filePathToUse = UtilConstant.absoluteUploadPathWindows;
			}
			File dir = new File(filePathToUse + filePath + "/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String name = file.getName();
			String prefix = name.substring(name.indexOf(".") + 1);
			File newFile = null;
			File dbFile = null;

			newFile = new File(filePathToUse + filePath + "/" + fileName
					+ ".jpg");
			dbFile = new File(filePath + "/" + fileName + ".jpg");

			FileOutputStream out = new FileOutputStream(newFile);
			BufferedOutputStream stream = new BufferedOutputStream(out);

			stream.write(bytes);
			stream.close();
			if (!isImage(newFile)) {
				newFile.delete();
				return resultMap.createResult(0, "只能上传图片");
			}
			String localFile = dbFile.toString().replace("\\", "/");
			return resultMap.createResult(1, localFile);
		} catch (Exception e) {
			return resultMap.createResult(0, e.toString());
		}

	}

	public static final boolean isImage(File file) {
		boolean flag = false;
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static File parseToFile(MultipartFile multipartFile)
			throws IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
	}

	public static String getFileName(String phoneNo) {
		Date date = new Date();
		SimpleDateFormat file = new SimpleDateFormat("yyyyMMdd");
		String code = RandomCode.getRandomCode(6);
		String fileName = null;
		if (phoneNo == null) {
			fileName = file.format(date) + code;
		} else {
			fileName = file.format(date) + phoneNo + code;
		}

		return fileName;
	}

	public static String getDir(String dir) {
		Date date = new Date();
		SimpleDateFormat filePath = new SimpleDateFormat("yyyy/MM/dd");
		String dirName = filePath.format(date);
		if (dir == null) {
			return dirName;
		}
		return dirName + "/" + dir;
	}
}
