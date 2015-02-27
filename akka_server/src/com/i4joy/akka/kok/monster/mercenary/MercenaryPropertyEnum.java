/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;

import com.i4joy.akka.kok.LanguageProperties;

/**
 * 侠客属性枚举
 * @author Administrator
 *
 */
public enum MercenaryPropertyEnum {
	
	NONE(0,""),
	/**
	 * 生命
	 */
	HP(1,LanguageProperties.getText("HP")),
	
	/**
	 * 攻击
	 */
	AD(2,LanguageProperties.getText("AD")),
	
	/**
	 * 物防
	 */
	AD_DEF(3,LanguageProperties.getText("AD_DEF")),
	
	/**
	 * 法防
	 */
	AP_DEF(4,LanguageProperties.getText("AP_DEF")),
	
	/**
	 * 暴击
	 */
	CRITICAL(5,LanguageProperties.getText("CRITICAL")),
	
	/**
	 * 韧性
	 */
	RESILIENCE(6,LanguageProperties.getText("RESILIENCE")),
	
	/**
	 * 命中
	 */
	HIT_RATING(7,LanguageProperties.getText("HIT_RATING")),
	
	/**
	 * 闪避
	 */
	DODGE(8,LanguageProperties.getText("DODGE")),
	
	/**
	 * 格挡
	 */
	BLOCK(9,LanguageProperties.getText("BLOCK")),
	
	/**
	 * 穿刺
	 */
	PENETRATE(10,LanguageProperties.getText("PENETRATE"))
	;
	
	final int index;
	
	final String name;
	
	private MercenaryPropertyEnum(int index,String name){
		this.index=index;
		this.name=name;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}
	
	public static MercenaryPropertyEnum getMercenaryPropertyEnum(int index){
		MercenaryPropertyEnum[] mpes=values();
		for(MercenaryPropertyEnum mpe:mpes){
			if(mpe.getIndex()==index){
				return mpe;
			}
		}
		
		return null;
	}

}
