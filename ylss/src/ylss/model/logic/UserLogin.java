package ylss.model.logic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLogin {
	
	private String phoneNo;
	
	private String password;
	
	@NotNull
	@Size(min=11,max=11,message="手机号长度11")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	@NotNull
	@Size(min=6,max=15,message="密码长度大于5位")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
