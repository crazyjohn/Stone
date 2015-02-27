/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;

/**
 * @author Administrator
 *
 */
public enum BuffTypeEnum {
		
		/**
		 * 2-攻击增加一定数值
		 */
		CHANGE_AD(2) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int)value);
			}
		},
		
		/**
		 * 3-外功防御增加一定数值
		 */
		CHANGE_AD_DEF(3) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int)value);
			}
		},
		
		/**
		 * 4-内功防御增加一定数值
		 */
		CHANGE_AP_DEF(4) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int)value);
			}
		},
		
		/**
		 * 6-攻击增加一定比例（%）
		 */
		CHANGE_AD_PERCENT(6) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyPercent(MercenaryPropertyEnum.AD, value);
			}
		},
		
		/**
		 * 7-外防增加一定比例（%）
		 */
		CHANGE_AD_DEF_PERCENT(7) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyPercent(MercenaryPropertyEnum.AD_DEF, value);
			}
		},
		
		/**
		 * 8-内防增加一定比例（%）
		 */
		CHANGE_AP_DEF_PERCENT(8) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyPercent(MercenaryPropertyEnum.AP_DEF, value);
			}
		},
		
		/**
		 * 9-暴击值增加一定数值
		 */
		CHANGE_CRITICAL(9) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.CRITICAL, (int)value);
			}
		},
		
		/**
		 * 10-韧性值增加一定数值
		 */
		CHANGE_RESILIENCE(10) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.RESILIENCE, (int)value);
			}
		},
		
		/**
		 * 11-闪避值增加一定数值
		 */
		CHANGE_DODGE(11) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.DODGE, (int)value);
			}
		},
		
		/**
		 * 12-命中值增加一定数值
		 */
		CHANGE_HIT_RATING(12) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.HIT_RATING, (int)value);
			}
		},
		
		/**
		 * 13-格挡值增加一定数值
		 */
		CHANGE_BLOCK(13) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.BLOCK, (int)value);
			}
		},
		
		/**
		 * 14-穿刺值增加一定数值
		 */
		CHANGE_PENETRATE(14) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.PENETRATE, (int)value);
			}
		},
		
		/**
		 * 31-生命值上限增加一定数值
		 */
		CHANGE_HP(31) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int)value);
			}
		},
		
		/**
		 * 32-生命值上限增加一定比例
		 */
		CHANGE_HP_PERCENT(32) {
			@Override
			public MercenaryProperty getMercenaryProperty(float value) {
				return new MercenaryPropertyPercent(MercenaryPropertyEnum.HP, value);
			}
		},
		
		
	;
	
	private final int index;
	
	private BuffTypeEnum(int index){
		this.index=index;
	}
	
	/**
	 * 只返回改变侠客属性的BUFF种类，其他在战斗中生效的BUFF类型，在此处会返回NULL
	 * @param index
	 * @return
	 */
	public static BuffTypeEnum getBuffTypeEnum(int index){
		BuffTypeEnum[] btes=values();
		for(BuffTypeEnum bte:btes){
			if(bte.index==index){
				return bte;
			}
		}
		return null;
	}
	
	public abstract MercenaryProperty getMercenaryProperty(float value);
}
