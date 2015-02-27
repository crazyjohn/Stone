/**
 * 
 */
package com.i4joy.akka.kok.monster;

/**
 * 游戏内所有物品的类型枚举
 * @author Administrator
 *
 */
public enum ThingsType {
		MERCENARY(1),
		MERCENARY_PIECE(2),
		EQUIPMENT(3),
		EQUIPMENT_PIECE(4),
		AMULET(5),
		AMULET_PIECE(6),
		ITEM(7),
		GIFT(8)
	;
	
	private int index;
	
	private ThingsType(int index){
		this.index=index;
	}
	
	public static ThingsType getThingsType(long id){
		int type = (int) id / 1000000;
		ThingsType[] tts=values();
		for(ThingsType tt:tts){
			if(tt.index==type){
				return tt;
			}
		}
		return null;
	}
}
