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
public class ApplicationTemplateAction extends ModelDrivenBaseAction<ApplicationTemplate>{
	private File upload; //上传
	private FileInputStream inputStream;//下载
	public String list(){
		List<ApplicationTemplate> appList= applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList",appList);
		return "list";
	}
	public String delete() throws UnsupportedEncodingException{
		applicationTemplateService.delete(model.getId());
		return "tolist";
	}
	public String addUI(){
		List<ProcessDefinition> processDefinitionList=processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList",processDefinitionList);
		return "addUI";
	}
	public String add() throws Exception{
		//封装
		String path=saveUploadFile(upload);
		model.setPath(path);
		//保存
		applicationTemplateService.save(model);
		return "tolist";
	}
	public String editUI(){
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		ApplicationTemplate applicationTemplate=applicationTemplateService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(applicationTemplate);
		return "addUI";
	}
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
