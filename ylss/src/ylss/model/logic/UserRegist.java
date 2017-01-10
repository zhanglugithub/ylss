package ylss.model.logic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegist {
	private String phoneNo;

	private String validateCode;

	private String userType;

	@NotNull
	@Size(min = 11, max = 11, message = "手机号长度11")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@NotNull
	@Size(min = 4, max = 6, message = "验证码长度6")
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
