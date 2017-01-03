package com.myoa.base;

import java.util.List;

import com.myoa.domain.PageBean;
import com.myoa.utils.HqlHelper;


public interface BaseDao<T>{
	/**
	 * 保存实体
	 * @param model
	 */
	void save(T model);
	
	/**
	 * 删除实体
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 更新实体
	 * @param model
	 */
	void update(T model);
	
	/**
	 * 查询所有的实体
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 根据id进行查询
	 * @param id
	 * @return
	 */
	T findById(Long id);
	
	/**
	 * 根据多个id进行查询
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);
	
	/**
	 * 公共的查询分页的方法
	 * @param pageNum
	 * @param hqlHelper
	 * @return
	 */
	PageBean getPageBean(int pageNum, HqlHelper hqlHelper);
}
