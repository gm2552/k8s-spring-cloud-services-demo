package com.example.tanzu.atmloc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Component;

@Component
public class CaffeinePostProcessor implements BeanPostProcessor
{
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException 
	{
		/*
		 * With Spring 6.1, CaffeineCacheManager requires setAsyncCacheMode to be explicitly
		 * called when used with Web Flux
		 */
		if (bean instanceof CaffeineCacheManager)
			((CaffeineCacheManager)bean).setAsyncCacheMode(true);
		
		return bean;
	}
}
