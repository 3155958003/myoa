package com.myoa.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.Forum;
import com.myoa.service.ForumService;
@Service
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends BaseDaoImpl<Forum> implements ForumService{

	
	@Override
	public List<Forum> findAll() {
		return getSession().createQuery("FROM Forum f ORDER BY f.position ASC").list();
	}
	@Override
	public void save(Forum model) {
		getSession().save(model);
		model.setPosition(model.getId());
	}
	
	public void moveup(Long id) {
		// TODO Auto-generated method stub
		Forum forum=findById(id);
		Forum other=(Forum)getSession().createQuery("From Forum where position<? order by position DESC")
		.setParameter(0,forum.getPosition())
		.setFirstResult(0)
		.setMaxResults(1)
		.uniqueResult();
		if(other==null){
			return;
		}
		Long temp=forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
	}

	
	public void movedown(Long id) {
		// TODO Auto-generated method stub
		Forum forum=findById(id);
		Forum other=(Forum) getSession().createQuery(// 我下面的那个Forum
		"FROM Forum f WHERE f.position>? ORDER BY f.position ASC")//
		.setParameter(0, forum.getPosition())//
		.setFirstResult(0)//
		.setMaxResults(1)//
		.uniqueResult();

		if(other==null){
			return;
		}
		Long temp=forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
	}

}
