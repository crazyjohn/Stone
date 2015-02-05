package com.stone.core.orm.annotation;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;

import com.stone.core.annotation.IAnnotationFilter;
import com.stone.core.constants.Loggers;
import com.stone.core.entity.IEntity;
import com.stone.core.orm.annotation.exception.EntityPackageNotFoundException;

/**
 * IEntity实现类的扫描器。该扫描器默认扫描com.hifun.soul.gamedb.entity包内<br>
 * 所有IEntity的实现类。如果扫描其它包内的类，需要手动指定对应包的全限定名称.
 * <p>
 * 
 * <b>注意：该扫描器不会扫描指定包下的子包内的类。</b>
 * <p>
 * 
 * 使用方法：<br>
 * 1. 获取扫描器；
 * 
 * <pre>
 *   <code>scanner = EntityScanner.getDefaultScanner();</code>，获取默认包下的扫描器；
 *   <code>scanner = EntityScanner.getScanner("com.a.b");</code>，获取指定包下的扫描器；
 * </pre>
 * 
 * 2. 获取指定条件的所有Entity类;
 * 
 * <pre>
 *   <code>scanner.getEntityListByAnnotationFilter(EntityFilter);</code>
 * </pre>
 * 
 * 例如：获取需要自动创建BaseDao实例的所有Entity类；
 * 
 * <pre>
 *   <code>scanner.getEntityListByAnnotationFilter(new EntityFilter(){
 *   	public boolean accept(Class<? extends IEntity> entityClass){
 *   		return entityClass.getAnnotation(AutoCreate.class) != null;
 *   	}
 *   });</code>
 * </pre>
 * 
 * @see AnnotationFilter
 * 
 * @author crazyjohn
 */
public class EntityScanner {

	private final static Logger logger = Loggers.ENTITY_LOGGER;

	/** 默认的Entity包 */
	private final static String defaultEntityPackage = "com.hifun.soul.gamedb.entity";

	/** 管理Scanner实例的map */
	private static Map<String, EntityScanner> scannerMap = new HashMap<String, EntityScanner>();

	/**
	 * 获取默认包下的Entity扫描器
	 * 
	 * @return 默认Entity包下的扫描器
	 */
	public static EntityScanner getDefaultScanner() {
		return getScanner(defaultEntityPackage);
	}

	/**
	 * 获取指定包下的Entity扫描器
	 * 
	 * @param entityPackage
	 *            指定的Entity包
	 * 
	 * @return 指定的Entity包下的扫描器
	 */
	public static EntityScanner getScanner(String entityPackage) {
		EntityScanner scanner = scannerMap.get(entityPackage);
		if (scanner == null) {
			scanner = new EntityScanner(entityPackage);
			scannerMap.put(entityPackage, scanner);
		}
		return scanner;
	}

	private String entityPackage = "";

	/** 保存扫描结果的list */
	private List<Class<? extends IEntity<?>>> entityList = new ArrayList<Class<? extends IEntity<?>>>();;

	/**
	 * 构造函数
	 * <p>
	 * 在构造指定Package的扫描器时，对该package进行扫描。扫描只在创建scanner时执行一次。
	 * 
	 * @param entityPackage
	 */
	private EntityScanner(String entityPackage) {
		this.entityPackage = entityPackage;
		scanAllEntityClassInPackage();
	}

	/**
	 * 取得当前scanner实例对应的包
	 * 
	 * @return
	 */
	String getEntityPackge() {
		return this.entityPackage;
	}

	/**
	 * 根据指定的过滤器来获得带有指定条件的Entity类
	 * 
	 * @param filter
	 *            过滤器
	 * @return 符合指定注解条件的Entity List
	 */
	public List<Class<? extends IEntity<?>>> getEntityListByAnnotationFilter(IAnnotationFilter filter) {
		List<Class<? extends IEntity<?>>> result = new ArrayList<Class<? extends IEntity<?>>>();
		for (Class<? extends IEntity<?>> entityClass : entityList) {
			if (filter.accept(entityClass)) {
				result.add(entityClass);
			}
		}
		return result;
	}

	/**
	 * 扫描指定的包下所有IEntity的实现类。
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void scanAllEntityClassInPackage() {
		List<String> entityClassNameList = listAllClassNamesInPackage();
		for (String entityClassName : entityClassNameList) {
			try {
				Class<? extends IEntity<?>> entityClass = (Class<? extends IEntity<?>>) Class.forName(entityClassName);
				if (IEntity.class.isAssignableFrom(entityClass)) {
					entityList.add(entityClass);
					logger.info("[EntityAnnotationScanner#scanAllEntityClassInPackage]:Entity '" + entityClassName + "' loaded successfully!");
				}
			} catch (ClassNotFoundException e) {
				throw new NoClassDefFoundError(entityClassName + " not found!");
			}
		}
	}

	/**
	 * 获取指定包下的所有类。
	 * <p>
	 * <b>注意：该方法不会获取指定包下子包内的类。</b>
	 * 
	 * @return
	 */
	private List<String> listAllClassNamesInPackage() {
		List<String> list = new ArrayList<String>();
		URL entityPackageURL = Thread.currentThread().getContextClassLoader().getResource(convertPackageToFolderPath(entityPackage));
		if (entityPackageURL == null) {
			logger.warn("[EntityAnnotationScanner#listAllClassNamesInPackage]:" + "Scanner did not locate the entity package!");
			return list;
		}
		if ("file".equals(entityPackageURL.getProtocol())) {
			File packageFolder = new File(entityPackageURL.getFile());
			File[] entityFiles = packageFolder.listFiles();
			for (File entityFile : entityFiles) {
				if (entityFile.isFile() && entityFile.getName().endsWith(".class")) {
					String entityClassName = entityPackage + "." + entityFile.getName().substring(0, entityFile.getName().length() - 6);
					list.add(entityClassName);
					logger.info("[EntityAnnotationScanner#listAllClassNamesInPackage]:" + entityClassName + " found!");
				}
			}
		} else if ("jar".equals(entityPackageURL.getProtocol())) {
			// 路径中含有!的替选方案，但如果路径中出现!的话，LogServer和DataServer也将无法启动。所以该方案不太需要的感觉！
			// String jarPath = entityPackageURL.getPath().substring(5,
			// entityPackageURL.getPath().indexOf("jar!") + 3);
			String jarPath = entityPackageURL.getPath().substring(5, entityPackageURL.getPath().indexOf("!"));
			try {
				// 将路径中出现的+号提前过滤掉
				jarPath = jarPath.replaceAll("[+]", "%2b");
				JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					String entryName = entries.nextElement().getName();
					if (entryName.startsWith(convertPackageToFolderPath(entityPackage)) && entryName.endsWith(".class")) {
						list.add(convertFilePathToClassName(entryName));
						logger.info("[EntityAnnotationScanner#listAllClassNamesInPackage]:" + entryName + " found!");
					}
				}
				jarFile.close();
			} catch (Exception e) {
				throw new EntityPackageNotFoundException(e);
			}
		} else {
			throw new UnsupportedOperationException("[EntityAnnotationScanner#listAllClassNamesInPackage]:" + "Scanner can not handle the protocol of  the entity package!");
		}
		return list;
	}

	private String convertPackageToFolderPath(String entityPackage) {
		return entityPackage.replaceAll("[.]", "/");
	}

	private String convertFilePathToClassName(String filePath) {
		filePath = filePath.substring(0, filePath.length() - 6);
		return filePath.replaceAll("[/]", ".");
	}

}
