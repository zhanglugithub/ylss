package ylss.dao;

import ylss.model.table.ValidateCode;

public interface ValidateCodeDao extends BaseDao<ValidateCode, String> {
	
	public ValidateCode getByPhoneNo(String phoneNo);//手机号找验证码对象
	
	public ValidateCode getByPhoneNo(ValidateCode vCode);
}
