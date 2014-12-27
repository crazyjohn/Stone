package com.stone.aop.db;

import java.lang.reflect.Method;

import com.stone.aop.annotation.NotifyUpdate;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

/**
 * DB切面;
 * 
 * @author crazyjohn
 *
 */
public aspect DBServiceAspect {

	/**
	 * 拦截set方法;
	 */
	public pointcut interceptSetter():execution(void com.stone.aop.db.MockSetterEntity.set*(*));

	/**
	 * 拦截注解;
	 */
	public pointcut interceptAnnotation():execution(void com.stone.aop.db.MockAnnotationEntity.*(*))
		&& @annotation(com.stone.aop.annotation.NotifyUpdate);

	/**
	 * around 拦截注解实现;
	 */
	void around():interceptAnnotation() {
		proceed();
		Class<?> entityClass = thisJoinPoint.getTarget().getClass();
		if (entityClass == null) {
			return;
		}
		Method[] methods = entityClass.getMethods();
		for (Method eachMethod : methods) {
			NotifyUpdate annotation = eachMethod
					.getAnnotation(NotifyUpdate.class);
			if (annotation != null) {
				if (thisJoinPoint.getTarget() instanceof IEntity<?>) {
					@SuppressWarnings("rawtypes")
					IEntity entity = (IEntity) thisJoinPoint.getTarget();
					MockDBService.getInstance().update(entity, annotation.async());
				}
			}
		}
	}

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
			dbService.update(entity, false);
		}
	}
}
