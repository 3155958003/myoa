package com.myoa.domain;

/**
 * 回复
 * @author Administrator
 *
 */
public class Reply extends Article {
	private Topic topic;// 所属的主题

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
