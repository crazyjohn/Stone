package com.i4joy.akka.kok.monster.amulet.templet;

import java.util.ArrayList;
import java.util.List;

import com.i4joy.akka.kok.monster.battle.SkillElementData;
import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;

public class AmuletBasePropertyTemplet {

	private long id;
	
	private String name;
	
	private int type;
	
	private int start;
	
	private int quality;
	
	private boolean upGrade;
	
	private boolean decomposing;
	
	private boolean refine;
	
	private float[][] unlockProps20;
	
	private float[][] unlockProps40;
	
	private int unlockSkillId20;
	
	private int unlockSkillId40;
	
	private long[][] debris_ids;
	
	private boolean consumables;
	
	private int upGradePayId;
	
	private String desc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public boolean isUpGrade() {
		return upGrade;
	}

	public void setUpGrade(boolean upGrade) {
		this.upGrade = upGrade;
	}

	public boolean isDecomposing() {
		return decomposing;
	}

	public void setDecomposing(boolean decomposing) {
		this.decomposing = decomposing;
	}

	public boolean isRefine() {
		return refine;
	}

	public void setRefine(boolean refine) {
		this.refine = refine;
	}

	public boolean isConsumables() {
		return consumables;
	}

	public void setConsumables(boolean consumables) {
		this.consumables = consumables;
	}

	public int getUpGradePayId() {
		return upGradePayId;
	}

	public void setUpGradePayId(int upGradePayId) {
		this.upGradePayId = upGradePayId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float[][] getUnlockProps20() {
		return unlockProps20;
	}

	public void setUnlockProps20(float[][] unlockProps20) {
		this.unlockProps20 = unlockProps20;
	}

	public float[][] getUnlockProps40() {
		return unlockProps40;
	}

	public void setUnlockProps40(float[][] unlockProps40) {
		this.unlockProps40 = unlockProps40;
	}

	public int getUnlockSkillId20() {
		return unlockSkillId20;
	}

	public void setUnlockSkillId20(int unlockSkillId20) {
		this.unlockSkillId20 = unlockSkillId20;
	}

	public int getUnlockSkillId40() {
		return unlockSkillId40;
	}

	public void setUnlockSkillId40(int unlockSkillId40) {
		this.unlockSkillId40 = unlockSkillId40;
	}

	public long[][] getDebris_ids() {
		return debris_ids;
	}

	public void setDebris_ids(long[][] debris_ids) {
		this.debris_ids = debris_ids;
	}

	/**
	 * 根据BUFF数据创建战斗所需的SkillElementData
	 * @return
	 */
	public SkillElementData createSkillElementData(){
		SkillElementData sed=new SkillElementData();
		return sed;
	}
	
	public List<MercenaryProperty> getMercenaryPropertys20(){
		List<MercenaryProperty> mps=new ArrayList<MercenaryProperty>();
		for(float[] prop:this.unlockProps20){
			MercenaryProperty mp=MercenaryProperty.createMercenaryProperty(prop);
			if(mp.getMpe()!=MercenaryPropertyEnum.NONE){
				mps.add(mp);
			}
		}
		return mps;
	}
	
	public List<MercenaryProperty> getMercenaryPropertys40(){
		List<MercenaryProperty> mps=new ArrayList<MercenaryProperty>();
		for(float[] prop:this.unlockProps40){
			MercenaryProperty mp=MercenaryProperty.createMercenaryProperty(prop);
			if(mp.getMpe()!=MercenaryPropertyEnum.NONE){
				mps.add(mp);
			}
		}
		return mps;
	}

}
