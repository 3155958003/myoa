package com.myoa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.Application;
import com.myoa.domain.ApproveInfo;
import com.myoa.domain.TaskView;
import com.myoa.domain.User;
import com.myoa.service.ApplicationService;

/**
 * 部署流程服务类接口的实现类
 * @author Administrator
 *
 */
@Service
public class ApplicationServiceImpl extends BaseDaoImpl<Application> implements ApplicationService {

	@Resource
	private ProcessEngine processEngine;
	private SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	public void submit(Application application) {
		// 1，设置属性并保存application
		application.setApplyTime(new Date()); // 申请时间，当前时间
		application.setTitle(application.getApplicationTemplate().getName()+"_"+application.getApplicant().getName()+"_"+sFormat.format(application.getApplyTime()));//格式：模板名称_申请人_申请时间
		application.setStatus(Application.STATUS_RUNNING);//正在运行
		getSession().save(application); // 保存

		// 2，启动程程实例开始流转
		// >> 准备流程变量
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		variablesMap.put("application", application);
		// >> 启动程程实例
		String pdKey = application.getApplicationTemplate().getProcessDefinitionKey();
		ProcessInstance pi=processEngine.getExecutionService().startProcessInstanceByKey(pdKey, variablesMap);
		// >> 办理完第1个任务“提交申请”，并带上流程变量（当前的申 请信息）
		Task task =processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).uniqueResult();
		processEngine.getTaskService().completeTask(task.getId());
	}

	public List<TaskView> getMyTaskViewList(User currentUser) {
		// 查询我的任务列表
		String userId = currentUser.getLoginName(); // 约定使用loginName作为JBPM用的用户标识符
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks(userId);

		// 找出每个任务对应的申请信息
		List<TaskView> resultList = new ArrayList<TaskView>();
		for (Task task : taskList) {
			Application application = (Application) processEngine.getTaskService().getVariable(task.getId(), "application");
			resultList.add(new TaskView(task, application));
		}

		// 返回“任务--申请信息”的结果
		return resultList;
	}
/*
 * 保存审批信息
 * 完成任务
 * 维护审批状态
 */
	
	public void approval(ApproveInfo approveInfo,String taskId,String outcome) {
		getSession().save(approveInfo);
		Task task=processEngine.getTaskService().getTask(taskId);
		
		if(outcome==null){
			processEngine.getTaskService().completeTask(taskId);
		}else{
			processEngine.getTaskService().completeTask(taskId,outcome);
		}
		
		//维护审批状态
		Application application=approveInfo.getApplication();
		ProcessInstance pi=processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
		if(!approveInfo.isApproval()){
			if(pi!=null){
				processEngine.getExecutionService().endProcessInstance(pi.getId(),ProcessInstance.STATE_ENDED);
			}
			application.setStatus(Application.STATUS_REJECTED);
		}else{
			if(pi==null){
			    application.setStatus(Application.STATUS_APPROVED);
			}
		}
		// TODO Auto-generated method stub
	}

	
	public Set<String> getComes(String taskId) {
		// TODO Auto-generated method stub
		return processEngine.getTaskService().getOutcomes(taskId);
	}
	

}
