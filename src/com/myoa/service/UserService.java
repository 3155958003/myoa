package com.myoa.service;

import com.myoa.base.BaseDao;
import com.myoa.domain.User;

public interface UserService extends BaseDao<User>{

	User findByLoginNameAndPassword(String loginName, String password);

}
