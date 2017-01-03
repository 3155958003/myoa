package com.myoa.service;

import java.util.List;

import com.myoa.base.BaseDao;
import com.myoa.domain.Forum;
import com.myoa.domain.Topic;


public interface TopicService extends BaseDao<Topic> {

	/**
	 * 查询指定版块中的主题列表，排序：所有置顶帖都在最上面，然后把最新状态的主题显示到前面。
	 * 
	 * @param forum
	 * @return
	 */
	List<Topic> findByForum(Forum forum);

}
