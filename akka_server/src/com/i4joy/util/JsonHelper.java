package com.i4joy.util;


import java.util.Collection;
import java.util.Map;

import com.i4joy.akka.kok.annotation.JsonIgnore;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author tao.yang1
 *
 */
public class JsonHelper {

	/**
	 * 
	 */
	public JsonHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取默认jsonconfig，只设置了JsonIgnore的ignoreFieldAnnotation
	 * 进行json串型化时，如果某些属性不想被串型化，就在这个属性的get方法
	 * 前面加上JsonIgnore注释即可
	 * @return
	 */
	public static JsonConfig getDefaultJsonConfig(){
		JsonConfig jc=new JsonConfig();
		jc.addIgnoreFieldAnnotation(JsonIgnore.class);
		return jc;
	}
	
	/**
	 * 根据json串，返回一个指定类的类数组
	 * @param <T>
	 * @param arrayData json字符串，必须是array格式
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] getObjectArrayFromJson(String arrayData,Class<T> clazz){
		JSONArray jsar=JSONArray.fromObject(arrayData);
		if(clazz == Long.class){
			return (T[])JSONArray.toList(jsar, clazz).toArray(new Long[0]);
		}
		return (T[])JSONArray.toArray(jsar, clazz);
	}
	
	/**
	 * 根据json串，返回一个指定类的类数组
	 * @param <T>
	 * @param arrayData json字符串，必须是array格式
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T[] getObjectArrayFromJson(String arrayData,Class<T> clazz,Map<String, Class<?>> classMap){
		JSONArray jsar=JSONArray.fromObject(arrayData);
		if(clazz == Long.class){
			return (T[])JSONArray.toList(jsar, clazz).toArray(new Long[0]);
		}
		return (T[])JSONArray.toArray(jsar, clazz,classMap);
	}
	
	/**
	 * 根据一个集合返回json串
	 * @param c
	 * @return
	 */
	public static String getJsonStringFromCollection(Collection<?> c){
		JSONArray jsar=JSONArray.fromObject(c,getDefaultJsonConfig());
		return jsar.toString();
	}
	
	/**
	 * 返回指定对象的json串
	 * @param obj
	 * @return
	 */
	public static String getJsonStringFromObject(Object obj){
		JSONObject jsob=JSONObject.fromObject(obj, getDefaultJsonConfig());
		return jsob.toString();
	}
	
	/**
	 * 根据给定的json串返回类实体
	 * @param <T>
	 * @param json json数据
	 * @param clazz 指定类
	 * @param classMap 用于json深层转化
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromJson(String json,Class<T> clazz,Map<String, Class<?>> classMap){
		JSONObject jsob=JSONObject.fromObject(json);
		T t=(T)JSONObject.toBean(jsob, clazz,classMap);
		return t;
	}

}
