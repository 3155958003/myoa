package com.myoa.web;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.myoa.domain.Forum;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumManageAction extends ModelDrivenBaseAction<Forum>{
	
	public String list(){
		List<Forum> forumlist=forumService.findAll();
		ActionContext.getContext().put("forumlist", forumlist);
		return "list";
	}
	public String delete(){
		forumService.delete(model.getId());
		return "tolist";
	}
	public String saveUI(){
		return "saveUI";
	}
	public String save(){
		forumService.save(model);
		return "tolist";
	}
	public String editUI(){
		Forum forum=forumService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	public String edit(){
		Forum forum=forumService.findById(model.getId());
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		forumService.update(forum);
		return "tolist";
	}
	/** 上移 */
	public String moveUp() throws Exception {
		forumService.moveup(model.getId());
		return "tolist";
	}

	/** 下移 */
	public String moveDown() throws Exception {
		forumService.movedown(model.getId());
		return "tolist";
	}

}
