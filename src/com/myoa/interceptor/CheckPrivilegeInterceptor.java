package com.myoa.interceptor;

import com.myoa.domain.User;

//import oa.domain.Privilege;
//import oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckPrivilegeInterceptor implements Interceptor{

	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void init() {
		// TODO Auto-generated method stub
		this.getClass().getResource("/web/a.txt");
	}

	
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		//获取登录用户
		User user=(User) ActionContext.getContext().getSession().get("user");
		//获取访问的action
		String actionname=arg0.getProxy().getActionName();
		//System.out.println("action名称"+actionname);
		String privilegeUrl=actionname;
		if(user==null){
			if("userAction_login".equals(privilegeUrl)){
				return arg0.invoke();
			}
			else{
				return "loginUI";
			}
		}
		if(user.hasPrivilegeByUrl(privilegeUrl)){
			return arg0.invoke();
		}
		return "noPrivilegeError";
	}

}
