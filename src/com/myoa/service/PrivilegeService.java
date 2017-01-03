package com.myoa.service;

import java.util.List;

import com.myoa.base.BaseDao;
import com.myoa.domain.Privilege;


public interface PrivilegeService extends BaseDao<Privilege>{
	List<Privilege> findTopList();

	List<String> getAllUrl();
}
