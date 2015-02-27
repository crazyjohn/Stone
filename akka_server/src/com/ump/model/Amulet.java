package com.ump.model;

import java.util.Date;
import java.util.List;

import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletBasePropertyTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletRefineTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradeTemplet;
import com.i4joy.akka.kok.monster.battle.CalculatorProps;
import com.i4joy.akka.kok.monster.battle.RoleElementData;
import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyPercent;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;
import com.i4joy.akka.kok.monster.mercenary.MercenaryTempletManager;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenarySkillTemplet;

public class Amulet extends DBCache {
	public static final String tableName = "#amulet";
	private long amulet_id;
	private int base_id;
	private int level = 1;
	private int refine_level;
	private long owner_id;
	private Date created_time;
	private int exp;
	
	

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public long getAmulet_id() {
		return amulet_id;
	}

	public void setAmulet_id(long amulet_id) {
		this.amulet_id = amulet_id;
	}

	public int getBase_id() {
		return base_id;
	}

	public void setBase_id(int base_id) {
		this.base_id = base_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRefine_level() {
		return refine_level;
	}

	public void setRefine_level(int refine_level) {
		this.refine_level = refine_level;
	}

	public long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public Amulet() {

	}

	public Amulet(Amulet amulet, byte action) {
		setAction(action);
		this.amulet_id = amulet.getAmulet_id();
		this.base_id = amulet.getBase_id();
		this.level = amulet.getLevel();
		this.refine_level = amulet.getRefine_level();
		this.owner_id = amulet.getOwner_id();
		this.created_time = amulet.getCreated_time();
	}
	
	/**
	 * 计算此件宝物所能提供的属性
	 */
	public void calculatorProps(CalculatorProps cp,RoleElementData red,AmuletTempletManager atm,MercenaryTempletManager mtm){
		AmuletBasePropertyTemplet abpt=atm.getAmuletBasePropertyTemplet(this.getBase_id());
		AmuletUpGradeTemplet aut=atm.getAmuletUpGradeTemplet(this.getBase_id(), this.getLevel());
		AmuletRefineTemplet art=atm.getAmuletRefineTemplet(this.getBase_id(), this.getRefine_level());
		//20级的激活属性
		if(this.getLevel()>=20){
			List<MercenaryProperty> mps=abpt.getMercenaryPropertys20();
			this.addProps(cp, mps);
			
			//20级的技能
			if(red!=null){
				MercenarySkillTemplet mst=mtm.getMercenarySkillTemplet(abpt.getUnlockSkillId20());
				if(mst!=null){
					red.passiveSkill.add(mst.getSkillElementData());
				}
			}
		}
		//40级的激活属性
		if(this.getLevel()>=40){
			List<MercenaryProperty> mps=abpt.getMercenaryPropertys40();
			this.addProps(cp, mps);
			
			//40级的技能
			if(red!=null){
				MercenarySkillTemplet mst=mtm.getMercenarySkillTemplet(abpt.getUnlockSkillId40());
				if(mst!=null){
					red.passiveSkill.add(mst.getSkillElementData());
				}
			}
		}
		
		//强化属性
		this.addProps(cp, aut.getMercenaryPropertys());
		
		//进化属性
		this.addProps(cp, art.getMercenaryPropertys());
	}
	
	private void addProps(CalculatorProps cp,List<MercenaryProperty> mps){
		for(MercenaryProperty mp:mps){
			this.addProp(cp, mp);
		}
	}
	
	private void addProp(CalculatorProps cp,MercenaryProperty mp){
		if(mp!=null){
			if(mp instanceof MercenaryPropertyValue){
				cp.addValue((MercenaryPropertyValue)mp);
			}else if(mp instanceof MercenaryPropertyPercent){
				cp.addPercent((MercenaryPropertyPercent)mp);
			}
		}
	}

}
