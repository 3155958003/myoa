package com.myoa.service.impl;

import java.io.File;
import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.ApplicationTemplate;
import com.myoa.service.ApplicationTemplateService;

/**
 * 申请模板的服务类接口的实现类
 * @author Administrator
 *
 */
@Service
public class ApplicationTemplateServiceImpl extends BaseDaoImpl<ApplicationTemplate> implements ApplicationTemplateService{
	
	@Override
	public void delete(Long id) {
		//删除数据库记录
		ApplicationTemplate applicationTemplate=findById(id);
		getSession().delete(applicationTemplate);
		//删除文件
		File file=new File(applicationTemplate.getPath());
		if(file.exists()){
			file.delete();
		}
		return;
	}
}
