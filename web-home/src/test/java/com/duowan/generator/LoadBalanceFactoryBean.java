package com.duowan.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

public class LoadBalanceFactoryBean {

	public List<String> getServiceUrls(Class serviceInterface) {
		return null;
	}
	
	public void test() {
		Class serviceInterface;
		Map map = new HashMap();
		
	}
	
	public void serviceRegiste(Class serviceInterface,int version) {
		
	}
	
	public Object serviceReference(String id,Class serviceInterface,int timeout,int retryTimes,boolean check) {
		List<String> urls = getServiceUrls(serviceInterface);
		
		Map<String,Object> instances = new HashMap<String,Object>();
		for(String serviceUrl : getServiceUrls(serviceInterface)) {
			HttpInvokerProxyFactoryBean f = new  HttpInvokerProxyFactoryBean();
			f.setServiceUrl(serviceUrl);
			f.setServiceInterface(serviceInterface);
			f.afterPropertiesSet();
			Object instance = f.getObject();
			instances.put(serviceUrl, instance);
		};
		ProxyFactory pf = new ProxyFactory();
//		pf.setTarget(target);
		return instances;
	}
}