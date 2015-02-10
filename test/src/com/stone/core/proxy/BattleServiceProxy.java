package com.stone.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BattleServiceProxy implements InvocationHandler {
	private Object target;

	@SuppressWarnings("unchecked")
	public <T>T createProxyService(Object target) {
		this.target = target;
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		System.out.println("Invoke begin");
		result = method.invoke(target, args);
		System.out.println("Invoke end");
		return result;
	}
}
