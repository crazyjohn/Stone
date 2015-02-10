package com.stone.core.proxy;

import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * 动态代理测试;
 * <p>
 * 测试目的：研究动态代理实现<br>
 * java动态代理:<br>
 * 1. 使用基于反射的技术实现，需要为接口动态的生成实现类，通过
 * {@link ProxyGenerator#generateProxyClass(String, Class[])};<br>
 * 2. 上述生成的二进制会被写入到class然后再进行class文件的加载，链接，初始化等<br>
 * 3. 这种实现方式一定会有效率上的消耗，而且目前
 * {@link Proxy#newProxyInstance(ClassLoader, Class[], java.lang.reflect.InvocationHandler)}只支持代理接口;
 * 
 * @author crazyjohn
 *
 */
public class ProxyTest {

	public static void main(String[] args) {
		// new proxy
		BattleServiceProxy proxy = new BattleServiceProxy();
		// bind service
		IBattleService battleService = (IBattleService) proxy.bind(new BattleService());
		battleService.fight();
	}

}
