package ylss.model.logic;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import ylss.model.constant.UtilConstant;

public class UserUpload {
	
	@NotNull
	@Size(min=11,max=11,message="手机号长度11")
	private String phoneNo;
	

	@AssertFalse(message = "文件不能超过2MB")
	private boolean tooBig=false;

	private MultipartFile img;
	

	public MultipartFile getImg() {
		return img;
	}


	public void setImg(MultipartFile img) {
		this.img = img;
		tooBig= img.getSize()>UtilConstant.maxUploadSize;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
	public boolean isTooBig() {
		return img.getSize()>1024*1024*200;

	}
	public void setTooBig(boolean tooBig) {
		this.tooBig = tooBig;
	}

}
