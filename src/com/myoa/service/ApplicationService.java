package com.myoa.service;

import java.util.List;
import java.util.Set;

import com.myoa.base.BaseDao;
import com.myoa.domain.Application;
import com.myoa.domain.ApproveInfo;
import com.myoa.domain.TaskView;
import com.myoa.domain.User;

/**
 * 部署流程定义的服务类接口
 * @author Administrator
 *
 */
public interface ApplicationService extends BaseDao<Application> {

	/**
	 * 提交申请：
	 * 
	 * 1，保存申请信息
	 * 
	 * 2，启动流程开始流转
	 * 
	 * @param application
	 */
	void submit(Application application);

	/**
	 * 查询我的任务信息列表
	 * 
	 * @param currentUser
	 * @return
	 */
	List<TaskView> getMyTaskViewList(User currentUser);

	void approval(ApproveInfo approveInfo,String taskId, String outcome);

	Set<String> getComes(String taskId);

}
