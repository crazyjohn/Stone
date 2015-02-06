package com.stone.game.aop;

import com.stone.actor.IActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.future.IActorFuture;

/**
 * Actor aspect;
 * 
 * @author crazyjohn
 *
 */
public aspect ActorAspect {
	/**
	 * Intercept the actor method;
	 */
	public pointcut interceptActorMethod():execution(IActorFuture *(..))
						&& @annotation(com.stone.actor.annotation.ActorMethod);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	IActorFuture around():interceptActorMethod() {
		IActor actor = (IActor) thisJoinPoint.getTarget();
		return actor.ask(new IActorCall() {
			@Override
			public Object execute() {
				return null;
			}

		});
	}

}
