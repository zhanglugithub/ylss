package ylss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.ValidateCodeDao;
import ylss.model.table.ValidateCode;

@Repository
@Transactional
public class ValidateCodeDaoImpl extends BaseDaoImpl<ValidateCode, String> implements ValidateCodeDao {

	public ValidateCodeDaoImpl() {
		super(ValidateCode.class);
	}

	@Override
	public ValidateCode getByPhoneNo(String phoneNo) {
		return get("phoneNo",phoneNo);
	}

	@Override
	public ValidateCode getByPhoneNo(ValidateCode vCode) {
		return get("phoneNo",vCode.getPhoneNo());
	}

}
