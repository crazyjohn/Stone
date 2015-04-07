package com.stone.core.actor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.stone.core.annotation.ActorMethod;

import akka.actor.UntypedActor;

/**
 * The annotated untypedActor;
 * <p>
 * This kind of untypedActor support to add {@link ActorMethod} annotation with
 * it's method, and when message received, it will dispatched to the target
 * actorMethod;
 * <p>
 * The actor method skeleton is :
 * 
 * <pre>
 * 
 * annotation marked:ActorMethod(messageClassType = Integer.class)
 * protected void handleMethod(Object msg) {
 * 		// business code
 * }
 * </pre>
 * 
 * @author crazyjohn
 *
 */
public class AnnotatedUntypedActor extends UntypedActor {
	/** registed actor method */
	private Map<Class<?>, Method> actorMethods = new HashMap<Class<?>, Method>();

	public AnnotatedUntypedActor() {
		// init the actor methods
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method eachMethod : methods) {
			if (eachMethod.getAnnotation(ActorMethod.class) == null) {
				continue;
			}
			// register method
			ActorMethod actorMethod = eachMethod.getAnnotation(ActorMethod.class);
			eachMethod.setAccessible(true);
			actorMethods.put(actorMethod.messageClassType(), eachMethod);
		}
	}

	@Override
	public final void onReceive(Object msg) throws Exception {
		Class<?> msgClassType = msg.getClass();
		Method method = actorMethods.get(msgClassType);
		if (method == null) {
			return;
		}
		// invoke the method
		method.invoke(this, msg);
	}

}
