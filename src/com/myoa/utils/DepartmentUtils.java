package com.myoa.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.myoa.domain.Department;



public class DepartmentUtils {
	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> departmentlist=new ArrayList<Department>();
		walkDepartmentTrees(topList, "┣", departmentlist);
		return departmentlist;
	}
	private static void walkDepartmentTrees(Collection<Department> topList, String prefix, List<Department> list) {
		for(Department top:topList){
			Department copy = new Department(); // 原对象是在Session中的对象，是持久化状态，所以要使用副本。
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy);
			walkDepartmentTrees(top.getChildren(), "　" + prefix,list);
		}
	}
}
