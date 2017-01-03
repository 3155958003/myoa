package com.myoa.web;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Forum;
import com.myoa.domain.Topic;
import com.myoa.utils.HqlHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends ModelDrivenBaseAction<Forum> {

	/**
	 * 0 表示全部主题 <br>
	 * 1 表示只看精华帖
	 */
	private int viewType = 0;

	/**
	 * 0 代表默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 代表只按最后更新时间排序<br>
	 * 2 代表只按主题发表时间排序<br>
	 * 3 代表只按回复数量排序
	 */
	private int orderBy = 0;

	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;

	/** 版块列表 */
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}

	/** 显示单个版块（主题列表） */
	public String show() throws Exception {
		Forum forum=forumService.findById(model.getId());
		ActionContext.getContext().put("forum", forum);
		new HqlHelper(Topic.class,"t").addCondition("t.forum=?", forum)
		.addCondition(viewType == 1, "t.type=?", Topic.TYPE_BEST) // 1表示只看精华帖
				.addOrder(orderBy == 1, "t.lastUpdateTime", asc) // 1 代表只按最后更新时间排序
				.addOrder(orderBy == 2, "t.postTime", asc) // 2 代表只按主题发表时间排序
				.addOrder(orderBy == 3, "t.replyCount", asc) // 3 代表只按回复数量排序
				.addOrder(orderBy == 0, "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
				.addOrder(orderBy == 0, "t.lastUpdateTime", false) // 0 代表默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
				.buildPageBeanForStruts2(pageNum, replyService);
		return "show";
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
