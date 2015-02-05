package com.stone.core.uuid;

import java.util.List;

import com.stone.core.enums.IndexedEnum;
import com.stone.core.util.EnumUtil;

/**
 * 游戏中的UUID类型
 *
 * 
 */
public enum UUIDType implements IndexedEnum {
	/** 玩家角色的UUID */
	HUMAN(0),
	/** 已发送邮件的UUID */
    SENTMAIL(1),
	/** 接收邮件的UUID */
    RECEIVEDMAIL(2),
	/** gm问答 */
    GMQUESTION(3),
    /** 账号 */
    ACCOUNT(4),
     /** 邮件草稿箱 */
    MAILDRAFT(5)
	;
	
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
