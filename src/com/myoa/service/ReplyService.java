package com.myoa.service;

import java.util.List;

import com.myoa.base.BaseDao;
import com.myoa.domain.Reply;
import com.myoa.domain.Topic;


public interface ReplyService extends BaseDao<Reply> {

	/**
	 * 查询指定主题中所有的回复列表，排序：最前面的是最早发表的回帖
	 * 
	 * @param topic
	 * @return
	 */
	List<Reply> findByTopic(Topic topic);

}
