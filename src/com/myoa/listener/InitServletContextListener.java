package com.myoa.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.myoa.domain.Privilege;
import com.myoa.service.PrivilegeService;

public class InitServletContextListener implements ServletContextListener{

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void contextInitialized(ServletContextEvent sce) {
		//获取容器相关的Service对象
		ServletContext application=sce.getServletContext();
		ApplicationContext ac =WebApplicationContextUtils.getWebApplicationContext(application);
		
		//准备数据：topPrivilegeList
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");
		List<Privilege> topPrivilegeList =privilegeService.findTopList();
		application.setAttribute("topPrivilegeList",topPrivilegeList);
		
		//准备数据：allPrivilegeUrls
		List<String> privilegeurllist=privilegeService.getAllUrl();
		application.setAttribute("privilegeurllist",privilegeurllist);
		System.out.println("-- 已准备好顶级权限的数据 --");
	}

}
