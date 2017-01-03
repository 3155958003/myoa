package com.myoa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.myoa.cfg.Configuration;
import com.myoa.domain.PageBean;
import com.myoa.utils.HqlHelper;

//@Transactional注解可以被继承
//@Transactional注解对父类中声明的方法无效
@Transactional
@SuppressWarnings("unchecked")

public class BaseDaoImpl<T> implements BaseDao<T>{
	
	@Resource
	private SessionFactory sessionFactory;	
	protected Class<T> clazz; 
	
	//使用反射技术
	public BaseDaoImpl() {
		ParameterizedType pt =(ParameterizedType) this.getClass()
				.getGenericSuperclass();//获取当前new对象的泛型父类的类型
		this.clazz=(Class) pt.getActualTypeArguments()[0];//获取第一个参数的真实类型
	}
	
	/**
	 *保存
	 */
	public void save(T model) {
		getSession().save(model);
	}
	
	/**
	 *删除
	 */
	public void delete(Long id) {
		Object obj=getSession().get(clazz, id);
		getSession().delete(obj);
	}
	
	/**
	 * 查询全部的信息
	 */
	public List<T> findAll() {
		List<T> list=getSession().createQuery("From "+clazz.getSimpleName()).list();
		return list;
	}
	
	/**
	 * 根据id查询
	 */
	public T findById(Long id) {
		if(id==null){
			return null;
		}
		return (T) getSession().get(clazz, id);
	}
	/**
	 * 根据多个id进行查询
	 */
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}
		
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")//
				.setParameterList("ids", ids)//
				.list();
	}
	
	/**
	 * 修改
	 */
	public void update(T model) {
		getSession().update(model);
	}
	
	/**
	 * 获取当前可用的Session
	 * @return
	 */
	protected Session getSession() {
		//SpringTest test=new SpringTest();
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 公共查询分页的方法
	 */
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = hqlHelper.getParameters();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数
		Query countQuery = getSession().createQuery(hqlHelper.getQueryCountHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}
}
