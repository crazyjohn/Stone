package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;

import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyPercent;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

public class MercenaryTalentTemplet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2189138801852670770L;

	private int talentId;//天赋ID
	
	private String talentName;//天赋名称
	
	/**
	 * 属性类型
	 */
	private int propType;
	
	/**
	 * 数值类型
	 */
	private int valueType;
	
	/**
	 * 数值
	 */
	private int value;
	
	private int skillId;
	
	private String describe;//描述

	public int getTalentId() {
		return talentId;
	}

	public void setTalentId(int talentId) {
		this.talentId = talentId;
	}

	public String getTalentName() {
		return talentName;
	}

	public void setTalentName(String talentName) {
		this.talentName = talentName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getPropType() {
		return propType;
	}

	public void setPropType(int propType) {
		this.propType = propType;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	
	public MercenaryProperty getMercenaryProperty(){
		MercenaryPropertyEnum mpe=MercenaryPropertyEnum.getMercenaryPropertyEnum(this.propType);
		if(mpe==null){
			return null;
		}
		if(this.valueType==1){
			return new MercenaryPropertyValue(mpe, value);
		}else{
			return new MercenaryPropertyPercent(mpe, value);
		}
	}
	
}
