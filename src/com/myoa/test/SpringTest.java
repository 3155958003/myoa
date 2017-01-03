package com.myoa.test;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	private ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	
	@Test
	public void testFactory(){
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}
	
	@Test
	public void testjbpm(){
		ProcessEngine engine=(ProcessEngine) ac.getBean("processEngine");
		System.out.println(engine);
	}
}
