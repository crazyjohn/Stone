package com.i4joy.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static ApplicationContext s_context;
	
	public static ApplicationContext getContext()
	{
		if(s_context == null)
		{
			s_context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		}
		return s_context;
	}
}
