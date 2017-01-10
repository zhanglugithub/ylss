package ylss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ylss.dao.EvaluatDao;
import ylss.model.table.Evaluat;

@Repository
public class EvaluatDaoImpl extends BaseDaoImpl<Evaluat, Integer> implements
		EvaluatDao {
	private final int pageSize = 10;

	public EvaluatDaoImpl() {
		super(Evaluat.class);
	}

	@Override
	public List<Evaluat> getEvaluatList(String hql, Map<String, Object> params) {
		List<Evaluat> evaluatList = super.findPage(hql, params);
		return evaluatList;
	}

	@Override
	public HashMap<String, Object> getEvaluatList(String hql,
			Map<String, Object> params, int pageNum) {
		HashMap<String, Object> evaluatList = super.findPage(hql, params,
				pageNum, pageSize);
		return evaluatList;
	}

	@Override
	public Evaluat getByOrderId(int orderId) {
		return super.get("orderId", orderId);
	}

}
