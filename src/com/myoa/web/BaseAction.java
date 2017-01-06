package com.myoa.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.myoa.domain.User;
import com.myoa.service.ApplicationService;
import com.myoa.service.ApplicationTemplateService;
import com.myoa.service.DepartmentService;
import com.myoa.service.ForumService;
import com.myoa.service.PrivilegeService;
import com.myoa.service.ProcessDefinitionService;
import com.myoa.service.ReplyService;
import com.myoa.service.RoleService;
import com.myoa.service.TopicService;
import com.myoa.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	@Resource
	protected RoleService roleService;
	
	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected UserService userService;
	
	@Resource
	protected PrivilegeService privilegeService;
	
	@Resource
	protected ForumService forumService;
	
	@Resource
	protected TopicService topicService;
	
	@Resource
	protected ReplyService replyService;
	
	@Resource
	protected ProcessDefinitionService processDefinitionService;
	
	@Resource
	protected ApplicationTemplateService applicationTemplateService;
	
	@Resource
	protected ApplicationService applicationService;
	
	public User getCurrentUser(){
		return (User) ActionContext.getContext().getSession().get("user");
	}
	protected int pageNum = 1;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	/**
	 * 文件上传的方法
	 * @param temp
	 * @return
	 */
	protected String saveUploadFile(File temp) {
		//设置路径
		String basePath=ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload/");
		//把日期类型格式化为   "yyyy/MM/dd/"
		SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
		String subpath=dateFormat.format(new Date());
		//路径处理
		File dir=new File(basePath+subpath);
		//如果文件夹不存，就创建一个文件夹
		if(!dir.exists()){
			dir.mkdirs();
		}
		//拼接文件路径
		//UUID.randomUUID().toString()  能够保证文件名字的唯一性	
		String path=basePath+subpath+UUID.randomUUID().toString();
		//移动文件
		temp.renameTo(new File(path));
		return path;
	}
}
