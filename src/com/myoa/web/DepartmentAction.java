package com.myoa.web;

import java.util.List;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Department;
import com.myoa.utils.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
public class DepartmentAction  extends ModelDrivenBaseAction<Department>{
	
	private Long parentId;
	
	/**
	 * 遍历所有的部门信息
	 * @return
	 */
	public String list(){
		List<Department> departmentList=null;
		if(parentId==null){//如果父id为空则是顶级部门
			departmentList=departmentService.findTopList();
		}else{//否则则为子部门
			departmentList=departmentService.findChildren(parentId);
			Department parent = departmentService.findById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String save(){
		//封装数据到对象中
		model.setParent(departmentService.findById(parentId));
		//保存到数据库
		departmentService.save(model);
		return "toList";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String saveUI(){
		//准备数据,departmentList 
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}
	
	/**
	 * 修该部门信息
	 * @return
	 */
	public String editUI(){
		//准备数据，department
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		//准备回显的数据	
		Department department=departmentService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		if (department.getParent() != null) {
			parentId = department.getParent().getId();
		}
		return "editUI";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		departmentService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		//从数据库取出要修改的数据
		model.setParent(departmentService.findById(parentId));
		//更新到数据库
		departmentService.update(model);
		return "toList";
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
