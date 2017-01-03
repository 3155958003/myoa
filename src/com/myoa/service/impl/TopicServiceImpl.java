package com.myoa.service.impl;

import java.util.List;



import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.Forum;
import com.myoa.domain.Topic;
import com.myoa.service.TopicService;
@Service
public class TopicServiceImpl extends BaseDaoImpl<Topic> implements TopicService {

	
	public List<Topic> findByForum(Forum forum) {
		// TODO Auto-generated method stub
		return getSession().createQuery("From Topic t where t.forum=? order by (CASE t.type when 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC").
		setParameter(0, forum).list();
	}
	public void save(Topic topic){
		topic.setLastUpdateTime(topic.getPostTime());
		getSession().save(topic);
		Forum forum=topic.getForum();
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(topic);
		forum.setTopicCount(forum.getTopicCount()+1);
		getSession().update(forum);
	}
}
