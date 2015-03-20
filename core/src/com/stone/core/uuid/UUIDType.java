package com.stone.core.uuid;

import java.util.List;

import com.stone.core.enums.IndexedEnum;
import com.stone.core.util.EnumUtil;

/**
 * The uuid type;
 * 
 * @author crazyjohn
 *
 */
public enum UUIDType implements IndexedEnum {
	/** Player */
	PLAYER(0),
	/** Human */
	HUMAN(1),
	/** Item */
	ITEM(2);

	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<UUIDType> values = IndexedEnumUtil.toIndexes(UUIDType.values());

	/**
	 * 
	 * @param index
	 *            枚举的索引,从0开始
	 */
	private UUIDType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static UUIDType valueOf(final int index) {
		return EnumUtil.valueOf(values, index);
	}
}
