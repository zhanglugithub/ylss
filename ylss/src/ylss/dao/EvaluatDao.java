package ylss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ylss.model.table.Evaluat;

public interface EvaluatDao extends BaseDao<Evaluat, Integer> {

	public List<Evaluat> getEvaluatList(String hql, Map<String, Object> params);

	public HashMap<String, Object> getEvaluatList(String hql, Map<String, Object> params, int pageNum);

	public Evaluat getByOrderId(int orderId);
}
