package com.stone.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Message;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.stone.core.entity.IProtobufEntity;

/**
 * The mongo db util;
 * 
 * @author crazyjohn
 *
 */
public class MongodbUtil {

	@SuppressWarnings("unchecked")
	public static DBObject buildDBObject(Message msg) {
		BasicDBObject obj = new BasicDBObject();
		for (Map.Entry<FieldDescriptor, Object> entry : msg.getAllFields().entrySet()) {
			FieldDescriptor field = entry.getKey();
			String fieldName = field.getName();
			switch (field.getType()) {
			case MESSAGE:
				if (field.isRepeated()) {
					BasicDBList list = new BasicDBList();
					obj.put(fieldName, list);
					List<Message> msgList = (List<Message>) entry.getValue();
					for (Message subMsg : msgList) {
						list.add(buildDBObject(subMsg));
					}
				} else {
					obj.put(fieldName, buildDBObject((Message) entry.getValue()));
				}
				break;

			case ENUM:
				obj.put(fieldName, ((EnumValueDescriptor) entry.getValue()).getName());

			default:
				obj.put(fieldName, entry.getValue());
			}
		}
		return obj;
	}

	public static DBObject buildDBObject(Object obj) {
		return BasicDBObjectBuilder.start(new BeanMap(obj)).get();
	}

	public static void convert(DBObject obj, Object bean) throws IllegalAccessException, InvocationTargetException {
		if (bean instanceof IProtobufEntity) {
			convert(obj, ((IProtobufEntity) bean).getBuilder());
		} else {
			BeanUtils.populate(bean, obj.toMap());
		}
	}

	public static void convert(DBObject obj, Message.Builder builder) {
		for (FieldDescriptor fd : builder.getDescriptorForType().getFields()) {
			Object value = obj.get(fd.getName());
			if (value == null) {
				if (fd.isRequired()) {
					throw new IllegalArgumentException(String.format("Field [%s] is expected of obj", fd.getName()));
				}
				continue;
			}
			switch (fd.getType()) {
			case MESSAGE:
				if (fd.isRepeated()) {
					BasicDBList list = (BasicDBList) value;
					for (Object elem : list) {
						Message.Builder fieldBuilder = builder.newBuilderForField(fd);
						convert((DBObject) elem, fieldBuilder);
						builder.addRepeatedField(fd, fieldBuilder.build());
					}
				} else {
					Message.Builder fieldBuilder = builder.newBuilderForField(fd);
					convert((DBObject) value, fieldBuilder);
					builder.setField(fd, fieldBuilder.build());
				}
				break;

			case ENUM:
				int number = (Integer) value;
				builder.setField(fd, fd.getEnumType().findValueByNumber(number));
				break;

			default:
				if (value instanceof Double) {
					Double d = (Double) value;
					if (fd.getJavaType() == JavaType.INT) {
						value = d.intValue();
					} else if (fd.getJavaType() == JavaType.LONG) {
						value = d.longValue();
					}
				}
				builder.setField(fd, value);
				break;
			}
		}
	}
}
