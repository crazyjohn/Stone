package com.stone.core.processor;

/**
 * 消息类型枚举;
 * 
 * @author crazyjohn
 *
 */
public enum MessageType {
	/** 客户端请求装备技能 */
	CG_EQUIP_SKILL(1001),;
	
	private int type;
	
	MessageType(int type) {
		this.setType(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
