package com.stone.db.cache.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stone.core.annotation.IAnnotationFilter;
import com.stone.core.entity.IEntity;
import com.stone.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.stone.core.orm.annotation.EntityScanner;

/**
 * 根据Entity上的<tt>AutoCreateHumanEntityHolder</tt>注释自动创建EntityHolder实例的类。
 * 
 * @author crazyjohn
 * 
 */
public class HumanEntityHolderCreater {

	private static HumanEntityHolderCreater creater = null;

	private static final IAnnotationFilter filter = new IAnnotationFilter() {
		@Override
		public boolean accept(Class<?> entityClass) {
			return entityClass.getAnnotation(AutoCreateHumanEntityHolder.class) != null;
		}
	};

	private Map<Class<? extends IEntity<?>>, Class<IEntityHolder<? extends IEntity<?>>>> entityHolderClassMap = new HashMap<Class<? extends IEntity<?>>, Class<IEntityHolder<? extends IEntity<?>>>>();

	private static Object initLock = new Object();

	/**
	 * 初始化自动创建EntityHolder的creater，并且执行check。<br>
	 * 该方法在DataServer初始化的时候调用。
	 * 
	 * @param scanner
	 */
	public static void init(EntityScanner scanner) {
		synchronized (initLock) {
			if (creater == null) {
				synchronized (initLock) {
					creater = new HumanEntityHolderCreater(scanner);
					creater.checkEntityHolders();
				}
			}
		}
	}

	/**
	 * 为每一个标记<tt>AutoCreateHumanEntityHolder</tt>的Entity创建对应的EntityHolder
	 * 
	 * @return
	 */
	public static Map<Class<? extends IEntity<?>>, IEntityHolder<? extends IEntity<?>>> getHumanEntityHolder() {
		initDefaultIfCreaterIsNull();
		Map<Class<? extends IEntity<?>>, IEntityHolder<? extends IEntity<?>>> map = new HashMap<Class<? extends IEntity<?>>, IEntityHolder<? extends IEntity<?>>>();
		for (Class<? extends IEntity<?>> key : creater.entityHolderClassMap.keySet()) {
			try {
				map.put(key, creater.entityHolderClassMap.get(key).newInstance());
			} catch (Exception e) {
				throw new RuntimeException("Entity Holder:" + creater.entityHolderClassMap.get(key).getName() + " can not be created!");
			}
		}
		return map;
	}

	private static void initDefaultIfCreaterIsNull() {
		init(EntityScanner.getDefaultScanner());
	}

	/**
	 * 获取所有扫描到的角色子实体的Entity类
	 * 
	 * @return
	 */
	public static Class<?>[] getAllHumanSubEntityClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>(creater.entityHolderClassMap.keySet());
		return classList.toArray(new Class[0]);
	}

	/**
	 * 重新初始化自动创建EntityHolder的creater
	 * 
	 * @param scanner
	 */
	public static void reset(EntityScanner scanner) {
		creater = null;
		init(scanner);
	}

	/**
	 * 使用指定的扫描器创建EntityHolder的Creater。
	 * 
	 * @param scanner
	 * @throws NoClassDefFoundError
	 * @throws ClassCastException
	 */
	@SuppressWarnings("unchecked")
	private HumanEntityHolderCreater(EntityScanner scanner) {
		List<Class<? extends IEntity<?>>> list = scanner.getEntityListByAnnotationFilter(filter);
		for (Class<? extends IEntity<?>> entityClass : list) {
			AutoCreateHumanEntityHolder annotation = entityClass.getAnnotation(AutoCreateHumanEntityHolder.class);
			Class<IEntityHolder<? extends IEntity<?>>> holderClass = null;
			try {
				holderClass = (Class<IEntityHolder<? extends IEntity<?>>>) Class.forName(annotation.EntityHolderClass());
			} catch (ClassNotFoundException e) {
				try {
					holderClass = (Class<IEntityHolder<? extends IEntity<?>>>) Class.forName("com.hifun.soul.gamedb.cache.holder." + annotation.EntityHolderClass());
				} catch (ClassNotFoundException e1) {
					throw new NoClassDefFoundError("Entity Holder:" + annotation.EntityHolderClass() + " not found!");
				}
			}
			if (!IEntityHolder.class.isAssignableFrom(holderClass)) {
				throw new ClassCastException("The Entity Holder:" + annotation.EntityHolderClass() + " is not implemetation of IEntityHolder!");
			}
			entityHolderClassMap.put(entityClass, holderClass);
		}
	}

	/**
	 * 初始化时检测指定的EntityHolder是否包含公开的并且无参的构造方法。
	 * 
	 * @throws RuntimeException
	 */
	private void checkEntityHolders() {
		Collection<Class<IEntityHolder<? extends IEntity<?>>>> entityHolderClasses = entityHolderClassMap.values();
		for (Class<IEntityHolder<? extends IEntity<?>>> entityHolderClass : entityHolderClasses) {
			try {
				if (entityHolderClass.getConstructor().isAccessible())
					throw new Exception();
			} catch (Exception e) {
				throw new RuntimeException("Entity Holder Check Failed! " + entityHolderClass.getName() + " can not be created!");
			}
		}
	}
}
