package com.myoa.service;

import com.myoa.base.BaseDao;
import com.myoa.domain.Forum;

public interface ForumService extends BaseDao<Forum>{
	void moveup(Long id);
	void movedown(Long id);
}
