package com.myoa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.Privilege;
import com.myoa.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService{

	
	public List<Privilege> findTopList() {
		// TODO Auto-generated method stub
		return getSession().createQuery("From Privilege where parent is NULL").list();
	}

	
	public List<String> getAllUrl() {
		// TODO Auto-generated method stub
		return getSession().createQuery("SELECT DISTINCT p.url from Privilege p where p.url is not NULL").list();
	}
}
