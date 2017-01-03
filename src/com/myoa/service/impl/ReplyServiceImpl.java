package com.myoa.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.Forum;
import com.myoa.domain.Reply;
import com.myoa.domain.Topic;
import com.myoa.service.ReplyService;
@Service
public class ReplyServiceImpl extends BaseDaoImpl<Reply> implements ReplyService {

	
	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(//
				// 排序：最前面的是最早发表的回帖
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}

	@Override
	public void save(Reply reply) {
		// 保存到DB
		getSession().save(reply);

		// 维护相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();

		forum.setArticleCount(forum.getArticleCount() + 1); // 版块的文章数（主题+回复）
		topic.setReplyCount(topic.getReplyCount() + 1); // 主题的回复数
		topic.setLastReply(reply); // 主题的最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime()); // 主题的最后更新的时间（主题的发表时间或是最后回复的时间）

		getSession().update(topic);
		getSession().update(forum);
	}
	
}
