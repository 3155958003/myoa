package com.myoa.service.impl;

import java.util.List;



import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.Department;
import com.myoa.service.DepartmentService;
@Service
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends BaseDaoImpl<Department> implements DepartmentService {

	
	public List<Department> findTopList() {
		List<Department> departmentlist=getSession().createQuery("From Department d where d.parent IS NULL").list();
		return departmentlist;
	}
	
	public List<Department> findChildren(Long parentId) {
		List<Department> departmentlist=getSession().createQuery("From Department d where d.parent.id=?").setParameter(0, parentId).list();
		return departmentlist;
	}

}
