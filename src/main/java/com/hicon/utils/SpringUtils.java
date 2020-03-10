package com.hicon.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring Bean 管理工具
 * 
 * @author mayi
 * 
 *         2008-9-19 create
 */
public class SpringUtils implements ApplicationContextAware {

	/**
	 * 当前IOC
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 设置当前上下文环境，此方法由spring自动装配
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}

	/**
	 * 从当前IOC获取bean
	 * 
	 * @param id
	 *            bean的id
	 * @return
	 */
	public static Object getBean(String id) {
		Object object = null;
		object = applicationContext.getBean(id, Object.class);
		return object;
	}
	
	/**
	 * 从当前IOC获取bean
	 * 
	 * @param id
	 *            bean的id
	 * @return
	 */
	public static <T> T getBean(String id, Class<T> clazz) {
		T object = null;
		object = applicationContext.getBean(id, clazz);
		return object;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
