/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;

import com.i4joy.akka.kok.monster.battle.BuffElementData;
import com.i4joy.akka.kok.monster.battle.SkillElementData;
import com.i4joy.akka.kok.monster.mercenary.MercenaryTempletManager;

/**
 * 技能模板
 * @author Administrator
 *
 */
public class MercenarySkillTemplet implements Serializable {
	
	private int id;
	
	private String name;
	
	private int skillType;
	
	private int damageType;
	
	private int rageCost;
	
	private String desc;
	
	private int minDamagePercent;
	
	private int maxDamagePercent;
	
	private int targetType;
	
	private int targetNum;
	
	private String buffInfo;
	
	private int effectId;
	
	private SkillElementData sed;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8131922668155864884L;

	/**
	 * 
	 */
	public MercenarySkillTemplet() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSkillType() {
		return skillType;
	}

	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

	public int getDamageType() {
		return damageType;
	}

	public void setDamageType(int damageType) {
		this.damageType = damageType;
	}

	public int getRageCost() {
		return rageCost;
	}

	public void setRageCost(int rageCost) {
		this.rageCost = rageCost;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getMinDamagePercent() {
		return minDamagePercent;
	}

	public void setMinDamagePercent(int minDamagePercent) {
		this.minDamagePercent = minDamagePercent;
	}

	public int getMaxDamagePercent() {
		return maxDamagePercent;
	}

	public void setMaxDamagePercent(int maxDamagePercent) {
		this.maxDamagePercent = maxDamagePercent;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public int getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(int targetNum) {
		this.targetNum = targetNum;
	}

	public String getBuffInfo() {
		return buffInfo;
	}

	public void setBuffInfo(String buffInfo) {
		this.buffInfo = buffInfo;
	}
	
	public int getEffectId() {
		return effectId;
	}

	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}

	public SkillElementData getSkillElementData(){
		if(this.sed==null){
			MercenaryTempletManager mtm=MercenaryTempletManager.getInstance();
			
			this.sed=new SkillElementData();
			this.sed.id=(short)this.getEffectId();
			this.sed.targetType=(byte)this.getTargetType();
			this.sed.targetCount=(byte)this.getTargetNum();
			this.sed.minDamage=(short)this.getMinDamagePercent();
			this.sed.maxDamage=(short)this.getMaxDamagePercent();
			this.sed.damageType=(byte)this.getDamageType();
			
			if(this.buffInfo!=null&&this.buffInfo.length()>0){
				String[] buffs=this.getBuffInfo().split("&");
				for(String buff:buffs){
					String[] ss=buff.split("_");
					if(ss.length!=7){
						continue;
					}
					//BuffID_成功率_目标_数量_持续回合数_触发条件_是否每回合累加（0、1）
					BuffElementData bed=new BuffElementData();
					MercenaryBuffTemplet mbt=mtm.getBuff(Integer.parseInt(ss[0]));
					bed.bufferID=(short)mbt.getBuffType();
					bed.bufferTrigger=Byte.parseByte(ss[5]);
					bed.bufferOdds=Byte.parseByte(ss[1]);
					bed.bufferRound=Byte.parseByte(ss[4]);
					bed.bufferMaxRound=bed.bufferRound;
					bed.bufferResult=mbt.getValue();
					bed.bufferIsOne=Byte.parseByte(ss[6]);
					bed.bufferTargetType=Byte.parseByte(ss[2]);
					bed.bufferRandCount=Byte.parseByte(ss[3]);
					bed.bufferEffectID=(short)mbt.getEffect();
					
					this.sed.buff.add(bed);
				}
			}
		}
		return this.sed;
	}
	

}
