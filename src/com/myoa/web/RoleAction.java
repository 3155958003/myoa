package com.myoa.web;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Privilege;
import com.myoa.domain.Role;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class RoleAction extends ModelDrivenBaseAction<Role>{
	private Long[] privilegeIds;
	public String list(){
		List<Role> roleList=roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "list";
	}
	public String save(){
		roleService.save(model);
		return "tolist";
	}
	public String saveUI(){
		return "saveUI";
	}
	public String editUI(){
		Role role=roleService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(role);
		return "editUI";
	}
	public String delete(){
		roleService.delete(model.getId());
		return "tolist";
	}
	public String update(){
		roleService.update(model);
		return "tolist";
	}
	public String setPrivilegeUI(){
		Role role=roleService.findById(model.getId());
		ActionContext.getContext().put("role", role);
		List<Privilege> toplist=null;
		toplist=privilegeService.findTopList();
		ActionContext.getContext().put("toplist", toplist);
		//准备回显选中的privilegeID
		privilegeIds=new Long[role.getPrivileges().size()];
		int index=0;
		for(Privilege privilege: role.getPrivileges()){
			privilegeIds[index++]=privilege.getId();
		}
		return "setPrivilegeUI";
	}
	public String setPrivilege(){
		Role role=roleService.findById(model.getId());
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		roleService.update(role);
		return "tolist";
	}
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
}
