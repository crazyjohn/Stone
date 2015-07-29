package com.stone.actor.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Actor aspect;
 * 
 * @author crazyjohn
 *
 */
public aspect ActorAspect {
	/** logger */
	protected Logger logger = LoggerFactory.getLogger("Powerful Actor Aspect");
	/**
	 * Intercept the actor method, in this situation, customer wait a return
	 * value;
	 */
//	public pointcut interceptAskWay():execution(IActorFuture *(..))
//						&& @annotation(com.stone.actor.annotation.AskMethod);
//
//	/**
//	 * Intercept the actor method(fire and forget way);
//	 */
//	public pointcut interceptTellWay():execution(void *(..)) && @annotation(com.stone.actor.annotation.TellMethod);
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	IActorFuture around():interceptAskWay() {
//		// cast to actor
//		IActor actor = (IActor) thisJoinPoint.getTarget();
//		return actor.ask(new IActorCall() {
//			@Override
//			public Object execute() {
//				// get execute result
//				Object result = proceed();
//				return result;
//			}
//
//		});
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	void around():interceptTellWay() {
//		// cast to actor
//		IActor actor = (IActor) thisJoinPoint.getTarget();
//		logger.info(String.format("[Caller Thread - %s], [Call Method - %s]", Thread.currentThread().getName(), thisJoinPoint.getSignature()));
//		actor.tell(new IActorCall() {
//			@Override
//			public Object execute() {
//				logger.info(String.format("[Execute Thread - %s], [Execute Method - %s]", Thread.currentThread().getName(), thisJoinPoint.getSignature()));
//				proceed();
//				return null;
//			}
//
//		});
//	}

}
