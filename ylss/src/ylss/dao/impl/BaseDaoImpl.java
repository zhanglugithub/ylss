package ylss.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import ylss.dao.BaseDao;
import ylss.utils.PageUtils;

public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private SessionFactory sessionFactory;

	protected Class<T> type;

	public BaseDaoImpl(Class<T> type) {

		this.type = type;

	}

	private int PAGE_SIZE = 10;// 当前页显示条数
	private int PAGE_RECORD;

	@Resource
	private void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session session() {
		return sessionFactory.getCurrentSession();
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public PK save(T t) {
		return (PK) session().save(t);
	}

	@Override
	public void delete(T t) {

		session().delete(t);
	}

	@Override
	public void update(T t) {

		session().update(t);
	}

	@Override
	public void saveOrUpdate(T t) {

		session().saveOrUpdate(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(int id) {

		return (T) session().get(type, id);
	}

	@Override
	public T get(Object... queryField) {

		List<T> result = getAll(queryField);

		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}

	@Override
	public List<T> getAll(Object... queryField) {// 此处应该是偶数，如果是奇数会抛异常
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		String hql = " from " + type.getName().toString() + " where "; // TODO2
																		// buffer更好
		for (int i = 0; i < queryField.length; i = i + 2) {
			if (i != 0) {
				hql = hql + " and ";
			}
			if (queryField[i + 1].equals("")) {
				queryField[i + 1] = "''";
			}
			hql = hql + queryField[i] + " =  :" + queryField[i];
			params.put(queryField[i].toString(), queryField[i + 1]);
		}
		// hql=hql+" order by desc";
		return getAll(hql, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAPage(int pageNo, int pageSize, String... queryField) {
		Logger logger = Logger.getLogger(this.getClass());
		String hqlString = " from " + type.getName().toString() + " where "; // TODO2
																				// buffer更好
		for (int i = 0; i < queryField.length; i = i + 2) {
			if (i != 0) {
				hqlString = hqlString + " and ";
			}
			if (queryField[i + 1].equals("")) {
				queryField[i + 1] = "''";
			}
			hqlString = hqlString + queryField[i] + " = " + queryField[i + 1];
		}
		Query q = session().createQuery(hqlString);
		List<T> list = q.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		logger.info(list);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAPage(int pageNo, int pageSize) {

		Query q = session().createQuery(" from " + type.getName().toString());
		return q.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String hqlString) {
		
		return session().createQuery(hqlString).list();
	}

	@Override
	public int executehql(String hqlString) {

		Query q = session().createQuery(hqlString);
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql) {

		SQLQuery q = session().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public long countAll() {

		return (long) session().createCriteria(type)
				.setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String hql, Map<String, Object> params) {
		
		Query q = session().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> resultList = q.list();
		if (resultList == null || resultList.size() < 2) {
			return resultList;
		}
		return resultList;
		// TODO 暂时这样
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findPage(String hql, Map<String, Object> params) {
		
		// PageUtils page = new PageUtils();
		Query query = this.session().createQuery(hql);

		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		// page.setRecordCount(query.list().size());
		// page.setPageSize(PAGE_SIZE);
		// int pageCount = page.getPageCount();
		// if (pageNum > pageCount) {
		// PAGE_RECORD = pageCount;
		// } else {
		// PAGE_RECORD = pageNum;
		// }
		// query.setFirstResult((PAGE_RECORD - 1) * PAGE_SIZE);
		// query.setMaxResults(PAGE_SIZE);

		// System.out.println("总页数："+page.getPageCount());
		List<T> resultList = query.list();
		if (resultList == null || resultList.size() < 2) {
			return resultList;
		}
		return resultList;
	}

	@Override
	public HashMap<String, Object> findPage(String hql,
			Map<String, Object> params, int pageNum, int pageSize) {
		
		Logger logger = Logger.getLogger(BaseDaoImpl.class);
		HashMap<String, Object> result = new HashMap<String, Object>();
		PageUtils page = new PageUtils();
		Query query = this.session().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		page.setRecordCount(query.list().size());
		if (pageSize == 0) {
			page.setPageSize(PAGE_SIZE);
		} else {
			page.setPageSize(pageSize);
		}
		int pageCount = page.getPageCount();
		if (pageNum > pageCount) {
			PAGE_RECORD = pageCount;
		} else {
			PAGE_RECORD = pageNum;
		}
		result.put("results", query.list());// 未分页的结果集
		query.setFirstResult((PAGE_RECORD - 1) * pageSize).setMaxResults(
				pageSize);
		List<T> resultList = query.list();
		// if (resultList == null || resultList.size() < 2) {
		// return resultList;
		// }
		// logger.info(query.list());
		result.put("result", resultList);
		result.put("pageCount", pageCount);
		return result;
	}

}
