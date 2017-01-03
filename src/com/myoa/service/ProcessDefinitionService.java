package com.myoa.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;

/**
 * 流程定义的服务类接口
 * @author Administrator
 *
 */
public interface ProcessDefinitionService {
	
	/**
	 * 查询所有的版本
	 * @return
	 */
	List<ProcessDefinition> findAllLatestVersions();
	
	/**
	 * 根据pdkey删除
	 * @param key
	 */
	void deleteByKey(String key);
	
	/**
	 * 根据ZipInputStream部署流程
	 * @param zipInputStream
	 */
	void deploy(ZipInputStream zipInputStream);
	
	/**
	 * 获取流程部署图
	 * @param id
	 * @return
	 */
	InputStream getProcessImage(String id);

}
