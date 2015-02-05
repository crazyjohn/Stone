package com.stone.core.enums;

import java.util.ArrayList;
import java.util.List;

import com.stone.core.constants.Loggers;
import com.stone.core.util.Assert;

/**
 * 从0开始的可索引枚举接口定义，实现此接口的枚举各个元素的index可以不连续，但此接口的实现类多为稀疏数组结构，保持index连续可以节省空间
 * 
 * 
 */
public interface IndexedEnum {

	/**
	 * 获取该枚举的索引值
	 * 
	 * @return 返回>=0的索引值
	 */
	public abstract int getIndex();

	public static class IndexedEnumUtil {
		
		/** 索引警戒上限，发现超过此值的索引可能存在较大的空间浪费*/
		private static final int WORNNING_MAX_INDEX = 1000;
		
		/**
		 * 将枚举中的元素放到一个List中，每个元素在list中的下表即为他的index，如果有不连续的index，则空缺的index用null填充
		 * 
		 * @param <E>
		 * @param enums
		 * @return
		 */
		public static <E extends IndexedEnum> List<E> toIndexes(E[] enums) {
			int maxIndex = Integer.MIN_VALUE;
			int curIdx = 0;
			// 找到最大index，此值+1就是结果list的size
			for (E enm : enums) {
				curIdx = enm.getIndex();
				// 索引不能为负
				Assert.isTrue(curIdx >= 0, String.format("枚举索引不能为负 index: %1$d type: %2$s ", curIdx, enums.getClass()
						.getComponentType().getName()));
				if (curIdx > maxIndex) {
					maxIndex = curIdx;
				}
			}
			if (maxIndex >= WORNNING_MAX_INDEX) {
				Loggers.GAME_LOGGER.warn(String.format("警告：枚举类%s中有索引超过%d的索引，如果有很多索引空缺，可能会造成空间浪费", enums.getClass()
						.getComponentType().getName(), WORNNING_MAX_INDEX));
			}
			List<E> instances = new ArrayList<E>(maxIndex + 1);
			// 先全用null填充
			for (int i = 0; i < maxIndex + 1; i++) {
				instances.add(null);
			}
			for (E enm : enums) {
				curIdx = enm.getIndex();
				// 索引必须唯一
				Assert.isTrue(instances.get(curIdx) == null, "枚举中有重复的index type= "
						+ enums.getClass().getComponentType().getName());
				instances.set(curIdx, enm);
			}
			return instances;
		}
	}
}