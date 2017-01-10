package ylss.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseDao<T, ID extends Serializable> {

	public Serializable save(T t);

	public void delete(T t);

	public void update(T t);

	public void saveOrUpdate(T t);

	public T getById(int id);

	public T get(Object... queryField);

	public List<T> getAll(Object... queryField);

	public List<T> getAPage(int pageNo, int pageSize);

	public List<T> getAll(String hql);

	public List<T> getAPage(int pageNo, int pageSize, String... queryField);

	public List<T> getAll(String hql, Map<String, Object> params);

	public long countAll();

	public int executehql(String hqlString);

	public int executeSql(String sql);

	public List<T> findPage(String hql, Map<String, Object> params);

	public HashMap<String, Object> findPage(String hql, Map<String, Object> params, int pageNum, int pageSize);

}
