package com.myoa.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.ApplicationTemplate;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")

/**
 * 模板申请的action类
 * @author Administrator
 *
 */
public class ApplicationTemplateAction extends ModelDrivenBaseAction<ApplicationTemplate>{
	
	private File upload; //上传
	private FileInputStream inputStream;//下载
	
	/**
	 * 显示所有的申请模板
	 * @return
	 */
	public String list(){
		//准备数据   ----->查询所有的模板信息
		//显示在页面
		List<ApplicationTemplate> appList= applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList",appList);
		return "list";
	}
	
	/**
	 * 删除模板 并返回到页面
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String delete() throws UnsupportedEncodingException{
		applicationTemplateService.delete(model.getId());
		return "tolist";
	}
	
	/**
	 * 去到添加申请的页面
	 * @return
	 */
	public String addUI(){
		//准备数据   ----->  所有的部署流程信息
		//并显示在页面
		List<ProcessDefinition> processDefinitionList=processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList",processDefinitionList);
		return "addUI";
	}
	
	/**
	 * 添加申请
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		//封装
		String path=saveUploadFile(upload);
		model.setPath(path);
		//保存
		applicationTemplateService.save(model);
		return "tolist";
	}
	
	/**
	 * 去到修改申请的页面
	 * @return
	 */
	public String editUI(){
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		ApplicationTemplate applicationTemplate=applicationTemplateService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(applicationTemplate);
		return "addUI";
	}
	
	/**
	 * 修改申请
	 * @return
	 */
	public String edit(){
		//从DB中取出原对象
		ApplicationTemplate applicationTemplate=applicationTemplateService.findById(model.getId());
		//设置属性
		applicationTemplate.setName(model.getName());
		applicationTemplate.setProcessDefinitionKey(model.getProcessDefinitionKey());
		if(upload!=null){
			//删除老文件
			File file=new File(applicationTemplate.getPath());
			if(file.exists()){
				file.delete();
			}
			//保存新文件
			String path=saveUploadFile(upload);
			applicationTemplate.setPath(path);
		}
		//更新到DB
		applicationTemplateService.update(applicationTemplate);
		return "tolist";
	}
	
	/**
	 *下载申请模板
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String download() throws FileNotFoundException, UnsupportedEncodingException{
		//获取要下载的文件
		ApplicationTemplate applicationTemplate=applicationTemplateService.findById(model.getId());
		inputStream=new FileInputStream(applicationTemplate.getPath());
		//设置下载的文件名称
		String filename=URLEncoder.encode(applicationTemplate.getName(),"utf-8");
		ActionContext.getContext().put("fileName",filename);
		//下载
		return "download";
	}
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public FileInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
}
