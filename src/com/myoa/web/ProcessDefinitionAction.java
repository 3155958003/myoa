package com.myoa.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")

/**
 * 这是流程部署的action类
 * @author Administrator
 *
 */
public class ProcessDefinitionAction extends BaseAction{
	
	private String id;
	private String key;
	private File upload;
	private InputStream inputstream;
	
	/**
	 * 查询所有的流程定义
	 * @return
	 */
	public String list(){
		List<ProcessDefinition> processDefinitionlist=
			processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionlist", processDefinitionlist);
		return "list";
	}
	
	/**
	 * 删除流程定义
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String delete() throws UnsupportedEncodingException{
		key = URLDecoder.decode(key, "utf-8"); // 自己再进行一次URL解码
		processDefinitionService.deleteByKey(key);
		return "tolist";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String addUI(){
		return "addUI";
	}
	
	/**
	 * 添加申请 	
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		ZipInputStream zipInputStream=new ZipInputStream(new FileInputStream(upload));
		processDefinitionService.deploy(zipInputStream);
		return "tolist";
	}
	
	/**
	 * 下载申请模板
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String downloadProcessImage() throws UnsupportedEncodingException{
		id = URLDecoder.decode(id, "utf-8"); // 自己再进行一次URL解码
		inputstream=processDefinitionService.getProcessImage(id);
		return "downloadProcessImage";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	public InputStream getInputstream() {
		return inputstream;
	}
	public void setInputstream(InputStream inputstream) {
		this.inputstream = inputstream;
	}
}
