package com.myoa.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户
 * @author Administrator
 *
 */
public class User implements java.io.Serializable {
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明


	/**
	 * 是否是超级管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}
	/*
	 * 判断是否有权限查看
	 */
	public boolean hasPrivilegeByName(String privilegename){
		if(isAdmin()){
			return true;
		}
		else{
			for(Role role:this.getRoles()){
				for(Privilege privilege:role.getPrivileges()){
					if(privilege.getName().equals(privilegename)){
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean hasPrivilegeByUrl(String privilegeUrl){
		if(isAdmin()){
			return true;
		}
		int pos=privilegeUrl.indexOf("?");
		if(pos>-1){
			privilegeUrl=privilegeUrl.substring(0,pos);
		}
		if(privilegeUrl.endsWith("UI")){
			privilegeUrl=privilegeUrl.substring(0,privilegeUrl.length()-2);
		}
		List<Privilege> privilegeurllist=(List<Privilege>) ActionContext.getContext().getApplication().get("privilegeurllist");
		if(!privilegeurllist.contains(privilegeUrl)){
			return true;
		}
		for(Role role:this.getRoles()){
			for(Privilege privilege:role.getPrivileges()){
					if(privilegeUrl.equals(privilege.getUrl())){
						return true;
					}
			}
		}
		return false;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
