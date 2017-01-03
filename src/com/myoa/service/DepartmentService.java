package com.myoa.service;


import java.util.List;

import com.myoa.base.BaseDao;
import com.myoa.domain.Department;


public interface DepartmentService extends BaseDao<Department>{

	List<Department> findTopList();

	List<Department> findChildren(Long parentId);
	
}
