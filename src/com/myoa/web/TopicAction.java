package com.myoa.web;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Reply;
import com.myoa.domain.Topic;
import com.myoa.utils.HqlHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TopicAction extends ModelDrivenBaseAction<Topic> {

	private Long forumId;

	/** 显示单个主题（主帖 + 回帖列表） */
	public String show() throws Exception {
		Topic topic=topicService.findById(model.getId());
		ActionContext.getContext().put("topic", topic);
		new HqlHelper(Reply.class, "r")//
		.addCondition("r.topic=?", topic)//
		.addOrder("r.postTime", true)//
		.buildPageBeanForStruts2(pageNum, replyService);
        return "show";
	}

	/** 发表新主题页面 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/** 发表新主题 */
	public String add() throws Exception {
		//表单中的字段, 已经封装了 title, content, faceIcon
		model.setForum(forumService.findById(forumId));
		//当前可以获取的信息
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		//放到业务方法的信息
//		model.setLastReply(lastReply);
//		model.setLastUpdateTime(lastUpdateTime);
//		model.setReplies(replies);
//		model.setReplyCount(replyCount);
//		model.setType(type);
		topicService.save(model);
		return "toShow"; // 转到新主题的显示页面
	}

	// -----------------------------

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
}
