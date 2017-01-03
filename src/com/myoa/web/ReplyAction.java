package com.myoa.web;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Reply;
import com.myoa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ReplyAction extends ModelDrivenBaseAction<Reply> {
	private Long topicId;
	/** 发表新回复页面 */
	public String addUI() throws Exception {
		Topic topic=topicService.findById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}

	/** 发表新回复 */
	public String add() throws Exception {
		// 1，封装（已经封装了title, content, faceIcon）
		model.setTopic(topicService.findById(topicId));

		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());

		// 2，保存
		replyService.save(model);

		return "toTopicShow"; // 转到新回复所属主题的显示页面
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
}
