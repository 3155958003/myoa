package com.myoa.web;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Department;
import com.myoa.domain.Role;
import com.myoa.domain.User;
import com.myoa.utils.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")

public class UserAction extends ModelDrivenBaseAction<User>{
	
	private Long departmentId;
	private Long[] roleIds;
	
	/**
	 * 查询
	 * @return
	 */
	public String list(){
		List<User> list=userService.findAll();
		ActionContext.getContext().put("userlist",list);
		return "list";
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String delete(){
		userService.delete(model.getId());
		return "tolist";
 	}
	
	/**
	 * 去到修改页面  并回显数据
	 * @return
	 */
	public String editUI(){
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 准备数据：roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		// 准备回显的数据
		User user = userService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if(user.getDepartment()!=null){
			departmentId=user.getDepartment().getId();
		}
		if(user.getRoles().size()>0){
			roleIds=new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}
		return "editUI";
	}
	
	/**
	 * 初始化密码
	 * @return
	 */
	public String initPassword(){
		User user=userService.findById(model.getId());
		user.setPassword(DigestUtils.md5Hex("1234"));
		userService.update(user);
		return "tolist";
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		model.setDepartment(departmentService.findById(departmentId));
		model.setRoles(new HashSet<Role>(roleService.getByIds(roleIds)));
		//更新到数据库
		userService.update(model);
		return "tolist";
	}
	public String save(){
		model.setDepartment(departmentService.findById(departmentId));
		model.setRoles(new HashSet<Role>(roleService.getByIds(roleIds)));
		String passwdMD5 = DigestUtils.md5Hex("1234");
		model.setPassword(passwdMD5);
		userService.save(model);
		return "tolist";
	}
	
	/**
	 * 保存用户
	 * @return
	 */
	public String saveUI(){
		//准备数据  ---->  查询出所有的部门信息
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=DepartmentUtils.getAllDepartments(topList);
		
		//所有的权限
		List<Role> roleList=roleService.findAll();
		ActionContext.getContext().put("departmentList", departmentList);
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}
	
	public String loginUI(){
		return "loginUI";
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	public String login(){
		User user=userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if(user!=null){
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
		else{
			addFieldError("login", "用户名或密码错误");
			return "loginUI";
		}
	}
	
	/**
	 * 用户退出
	 * @return
	 */
	public String logout(){
		ActionContext.getContext().getSession().remove("user");
		return "logoutUI";
	}
	
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
}
