package com.stone.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.stone.proto.MessageTypes.MessageType;

/**
 * 消息类型工具
 * 
 * @author crazyjohn
 * 
 */
public class MessageTypeUtil {
	/** Short类型到String的映射 */
	private static Map<Integer, String> intToNames = new HashMap<Integer, String>();

	static {
		for (Field field : MessageType.class.getFields()) {
			try {
				intToNames.put(field.getInt(field), field.getName());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据Short消息类型获取消息类型名称
	 * 
	 * @param messageType
	 *            short消息类型
	 * @return 如果系统中注册了此类型则返回对应的类型名;否则返回"";
	 */
	public static String getMessageTypeName(int messageType) {
		String result = "";
		if (intToNames.get(messageType) != null) {
			return intToNames.get(messageType);
		}
		return result;
	}

	/**
	 * 根据消息类型获取消息类型信息;可以用于日志信息记录;
	 * 
	 * @param messageType
	 * @return
	 */
	public static String getTypeInfo(short messageType) {
		return String.format("type: %d, typeName: %s", messageType,
				MessageTypeUtil.getMessageTypeName(messageType));
	}
}
