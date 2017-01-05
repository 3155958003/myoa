package com.myoa.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;



import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;

import com.myoa.service.ProcessDefinitionService;

/**
 * 流程定义的服务类接口的实现类
 * @author Administrator
 *
 */
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

	@Resource
	private ProcessEngine processEngine;
	
	/**
	 * 查询所有流程的版本
	 */
	public List<ProcessDefinition> findAllLatestVersions() {
		// TODO Auto-generated method stub
		List<ProcessDefinition> all=processEngine.getRepositoryService()
		.createProcessDefinitionQuery()
		.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)
		.list();
		
		System.out.println(all);
		Map<String,ProcessDefinition> map=new HashMap<String,ProcessDefinition>();
		
		for(ProcessDefinition pd:all){
			map.put(pd.getKey(), pd);
		}
		return new ArrayList<ProcessDefinition>(map.values());
	}

	/**
	 * 根据key值删除流程
	 */
	public void deleteByKey(String key) {
		// TODO Auto-generated method stub
	List<ProcessDefinition> list=processEngine.getRepositoryService().createProcessDefinitionQuery()
		.processDefinitionKey(key)
		.list();
	
		for(ProcessDefinition pd:list){
			processEngine.getRepositoryService().deleteDeploymentCascade(pd.getDeploymentId());
		}
	}

	/**
	 * 根据ZipInputStream部署流程
	 */
	public void deploy(ZipInputStream zipInputStream) {
		// TODO Auto-generated method stub
		processEngine.getRepositoryService()
		.createDeployment()
		.addResourcesFromZipInputStream(zipInputStream) 
		.deploy();
	}

	/**
	 * 获取流程定义图
	 */
	public InputStream getProcessImage(String id) {
		// TODO Auto-generated method stub
		ProcessDefinition pd=processEngine.getRepositoryService()
		.createProcessDefinitionQuery()
		.processDefinitionId(id)
		.uniqueResult();
		return processEngine.getRepositoryService().getResourceAsStream(pd.getDeploymentId(),pd.getImageResourceName());
	}

}
