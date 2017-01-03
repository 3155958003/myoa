package com.myoa.web;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Application;
import com.myoa.domain.ApplicationTemplate;
import com.myoa.domain.ApproveInfo;
import com.myoa.domain.TaskView;
import com.myoa.utils.HqlHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class FlowAction extends BaseAction {
	
	private File upload; // 上传的文件
	private String taskId;
	private Long applicationId;
	private Long applicationTemplateId;
	private String applicationStatus;
	private String comment;
	private boolean approval;
	private String outcome;
	
	// ================================== 申请人有关

	/** 起草申请（模板列表） */
	public String applicationTemplateList() throws Exception {
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
		return "applicationTemplateList";
	}

	/** 提交申请页面 */
	public String submitUI() throws Exception {
//		ActionContext.getContext().getValueStack().push(applicationTemplateId);
		System.out.println(applicationTemplateId);
		return "submitUI";
	}

	/** 提交申请 */
	public String submit() throws Exception {
		// 封装申请信息
		Application application = new Application();

		application.setApplicant(getCurrentUser()); // 申请人，当前用户
		application.setPath(saveUploadFile(upload)); // 保存上传的文件并设置路径
		application.setApplicationTemplate(applicationTemplateService.findById(applicationTemplateId));

		// 调用业务方法（保存申请信息，并启动流程开始流转）
		
		applicationService.submit(application);

		return "toMyApplicationList"; // 成功后转到"我的申请查询"
	}

	/** 我的申请查询 */
	public String myApplicationList() throws Exception {
		new HqlHelper(Application.class,"a")
		.addCondition("a.applicant=?",getCurrentUser())
		.addCondition(applicationTemplateId!=null, "a.applicationTemplate.id=?", applicationTemplateId)
		.addCondition(applicationStatus!=null&&applicationStatus.length()>0,"a.status=?", applicationStatus)
		.addOrder("a.applyTime", false)
		.buildPageBeanForStruts2(pageNum, applicationService);
		List<ApplicationTemplate> applicationTemplateList=applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
		return "myApplicationList";
	}

	// ================================== 审批人有关

	/** 待我审批（我的任务列表） */
	public String myTaskList() throws Exception {
		List<TaskView> taskViewList = applicationService.getMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() throws Exception {
		Set<String> comes=applicationService.getComes(taskId);
		ActionContext.getContext().put("comes", comes);
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() throws Exception {
		//封装
	    ApproveInfo approveInfo=new ApproveInfo();
	    approveInfo.setApplication(applicationService.findById(applicationId));
	    approveInfo.setApproval(approval);
	    approveInfo.setApprover(getCurrentUser());
	    approveInfo.setApproveTime(new Date());
	    approveInfo.setComment(comment);
	    //调用业务方法
	    applicationService.approval(approveInfo,taskId,outcome);
		return "toMyTaskList"; // 成功后转到待我审批页面
	}

	/** 查看流转记录 */
	public String approveHistory() throws Exception {
		Application application=applicationService.findById(applicationId);
		Set<ApproveInfo> approveinfos=application.getApproveInfos();
		ActionContext.getContext().put("approveinfos", approveinfos);
		return "approveHistory";
	}

	// --------

	public Long getApplicationTemplateId() {
		return applicationTemplateId;
	}

	public void setApplicationTamplateId(Long applicationTemplateId) {
		this.applicationTemplateId = applicationTemplateId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationTemplateId(Long applicationTemplateId) {
		this.applicationTemplateId = applicationTemplateId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
}