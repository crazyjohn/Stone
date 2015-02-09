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
	 * Intercept the actor method, in this situation, customer wait a return
	 * value;
	 */
	public pointcut interceptAskWay():execution(IActorFuture *(..))
						&& @annotation(com.stone.actor.annotation.AskMethod);

	/**
	 * Intercept the actor method(fire and forget way);
	 */
	public pointcut interceptTellWay():execution(void *(..)) && @annotation(com.stone.actor.annotation.TellMethod);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	IActorFuture around():interceptAskWay() {
		// cast to actor
		IActor actor = (IActor) thisJoinPoint.getTarget();
		return actor.ask(new IActorCall() {
			@Override
			public Object execute() {
				// get execute result
				Object result = proceed();
				return result;
			}

		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void around():interceptTellWay() {
		// cast to actor
		IActor actor = (IActor) thisJoinPoint.getTarget();
		actor.tell(new IActorCall() {
			@Override
			public Object execute() {
				proceed();
				return null;
			}

		});
	}

}
