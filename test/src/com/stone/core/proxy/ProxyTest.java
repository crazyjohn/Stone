package com.stone.core.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * {@link Proxy#newProxyInstance(ClassLoader, Class[], java.lang.reflect.InvocationHandler)}
 * 只支持代理接口;<br>
 * 4. 我用Genarator生成了二进制到一个class文件，然后使用反编译去看生成的代码如下:<br>
 * 
 * <pre>
 * import com.stone.core.proxy.IBattleService;
 * import java.lang.reflect.InvocationHandler;
 * import java.lang.reflect.Method;
 * import java.lang.reflect.Proxy;
 * import java.lang.reflect.UndeclaredThrowableException;
 * 
 * public final class ProxyObject extends Proxy
 *   implements IBattleService
 * {
 *   private static Method m1;
 *   private static Method m3;
 *   private static Method m0;
 *   private static Method m2;
 * 
 *   public ProxyObject(InvocationHandler paramInvocationHandler)
 *     throws 
 *   {
 *     super(paramInvocationHandler);
 *   }
 * 
 *   public final boolean equals(Object paramObject)
 *     throws 
 *   {
 *     try
 *     {
 *       return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
 *     }
 *     catch (Error|RuntimeException localError)
 *     {
 *       throw localError;
 *     }
 *     catch (Throwable localThrowable)
 *     {
 *       throw new UndeclaredThrowableException(localThrowable);
 *     }
 *   }
 * 
 *   public final void fight()
 *     throws 
 *   {
 *     try
 *     {
 *       this.h.invoke(this, m3, null);
 *       return;
 *     }
 *     catch (Error|RuntimeException localError)
 *     {
 *       throw localError;
 *     }
 *     catch (Throwable localThrowable)
 *     {
 *       throw new UndeclaredThrowableException(localThrowable);
 *     }
 *   }
 * 
 *   public final int hashCode()
 *     throws 
 *   {
 *     try
 *     {
 *       return ((Integer)this.h.invoke(this, m0, null)).intValue();
 *     }
 *     catch (Error|RuntimeException localError)
 *     {
 *       throw localError;
 *     }
 *     catch (Throwable localThrowable)
 *     {
 *       throw new UndeclaredThrowableException(localThrowable);
 *     }
 *   }
 * 
 *   public final String toString()
 *     throws 
 *   {
 *     try
 *     {
 *       return (String)this.h.invoke(this, m2, null);
 *     }
 *     catch (Error|RuntimeException localError)
 *     {
 *       throw localError;
 *     }
 *     catch (Throwable localThrowable)
 *     {
 *       throw new UndeclaredThrowableException(localThrowable);
 *     }
 *   }
 * 
 *   static
 *   {
 *     try
 *     {
 *       m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
 *       m3 = Class.forName("com.stone.core.proxy.IBattleService").getMethod("fight", new Class[0]);
 *       m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
 *       m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
 *       return;
 *     }
 *     catch (NoSuchMethodException localNoSuchMethodException)
 *     {
 *       throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
 *     }
 *     catch (ClassNotFoundException localClassNotFoundException)
 *     {
 *       throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
 *     }
 *   }
 * }
 * </pre>
 * 
 * @author crazyjohn
 *
 */
public class ProxyTest {

	public static void main(String[] args) throws IOException {
		// new proxy
		BattleServiceProxy proxy = new BattleServiceProxy();
		// bind service
		IBattleService battleService = proxy.createProxyService(new BattleService());
		System.out.println(battleService.getClass().getName());
		battleService.fight();
		// proxy generator
		byte[] datas = ProxyGenerator.generateProxyClass("ProxyObject", new Class<?>[] { IBattleService.class });
		writeToClassFile("ProxyObject.class", datas);
	}

	private static void writeToClassFile(String className, byte[] datas) throws IOException {
		FileOutputStream out = new FileOutputStream(new File(className));
		out.write(datas);
		out.close();
	}

}
