package com.myoa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.myoa.base.BaseDaoImpl;
import com.myoa.domain.User;
import com.myoa.service.UserService;
import com.sun.mail.smtp.DigestMD5;


@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService {

	
	public User findByLoginNameAndPassword(String loginName, String password) {
		// TODO Auto-generated method stub
		User user=(User) getSession().createQuery("From User u where u.loginName=? and u.password=?").
		setParameter(0, loginName).setParameter(1, DigestUtils.md5Hex(password)).uniqueResult();
		return user;
	}

}
