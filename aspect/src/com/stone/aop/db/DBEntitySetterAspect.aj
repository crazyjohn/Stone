package com.stone.aop.db;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

/**
 * 拦截DB实体所有setter的切面;
 * 
 * @author crazyjohn
 *
 */
public aspect DBEntitySetterAspect {
	/**
	 * 拦截set方法;
	 */
	public pointcut interceptSetter():execution(void com.stone.core.entity.IEntity+.set*(*));

	/**
	 * around 拦截setter实现;
	 */
	void around():interceptSetter() {
		// call method
		proceed();
		IDBService dbService = MockDBService.getInstance();
		if (thisJoinPoint.getTarget() instanceof IEntity<?>) {
			@SuppressWarnings("rawtypes")
			IEntity entity = (IEntity) thisJoinPoint.getTarget();
			dbService.update(entity, true);
		}
	}
}
