package com.ump.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

import net.sf.json.JSONObject;

public class Equipment extends DBCache {

	private JSONObject json;

	public JSONObject getJson() {
		if (json == null) {
			json = JSONObject.fromObject(getEquipment_json());
		}
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public int getExtraHp() {
		if (getJson().containsKey("hp")) {
			return getJson().getInt("hp");
		}
		return 0;
	}

	public int getExtraAd() {
		if (getJson().containsKey("ad")) {
			return getJson().getInt("ad");
		}
		return 0;
	}

	public int getExtraAdDef() {
		if (getJson().containsKey("adDef")) {
			return getJson().getInt("adDef");
		}
		return 0;
	}

	public int getExtraPdDef() {
		if (getJson().containsKey("pdDef")) {
			return getJson().getInt("pdDef");
		}
		return 0;
	}

	public int getExtraCritical() {
		if (getJson().containsKey("critical")) {
			return getJson().getInt("critical");
		}
		return 0;
	}

	public int getExtraResilience() {
		if (getJson().containsKey("resilience")) {
			return getJson().getInt("resilience");
		}
		return 0;
	}

	public int getExtraHitRating() {
		if (getJson().containsKey("hitRating")) {
			return getJson().getInt("hitRating");
		}
		return 0;
	}

	public int getExtraDodge() {
		if (getJson().containsKey("dodge")) {
			return getJson().getInt("dodge");
		}
		return 0;
	}

	public int getExtraPenetrate() {
		if (getJson().containsKey("penetrate")) {
			return getJson().getInt("penetrate");
		}
		return 0;
	}

	public int getExtraBlock() {
		if (getJson().containsKey("block")) {
			return getJson().getInt("block");
		}
		return 0;
	}

	public void upDate() {
		setEquipment_json(getJson().toString());
	}

	public static final String tableName = "#equipment";
	private long equipment_id;
	private int base_id;
	private int level = 1;
	private int use_stone_num;
	private String equipment_json = "{}";
	private int owner_id;
	private Date created_time;

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public long getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(long equipment_id) {
		this.equipment_id = equipment_id;
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

	public int getUse_stone_num() {
		return use_stone_num;
	}

	public void setUse_stone_num(int use_stone_num) {
		this.use_stone_num = use_stone_num;
	}

	public String getEquipment_json() {
		return equipment_json;
	}

	public void setEquipment_json(String equipment_json) {
		this.equipment_json = equipment_json;
	}

	public Equipment() {

	}

	public Equipment(Equipment equipment, byte action) {
		setAction(action);
		this.equipment_id = equipment.getEquipment_id();
		this.base_id = equipment.getBase_id();
		this.level = equipment.getLevel();
		this.use_stone_num = equipment.getUse_stone_num();
		this.equipment_json = equipment.getEquipment_json();
		this.owner_id = equipment.getOwner_id();
		this.created_time = equipment.getCreated_time();
	}
	
	public List<MercenaryPropertyValue> getAllExtraProps(){
		List<MercenaryPropertyValue> mpvs=new ArrayList<MercenaryPropertyValue>();
		if(this.getExtraAd()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, this.getExtraAd()));
		}
		if(this.getExtraAdDef()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, this.getExtraAdDef()));
		}
		if(this.getExtraBlock()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.BLOCK, this.getExtraBlock()));
		}
		if(this.getExtraCritical()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.CRITICAL, this.getExtraCritical()));
		}
		if(this.getExtraDodge()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.DODGE, this.getExtraDodge()));
		}
		if(this.getExtraHitRating()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.HIT_RATING, this.getExtraHitRating()));
		}
		if(this.getExtraHp()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, this.getExtraHp()));
		}
		if(this.getExtraPdDef()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, this.getExtraPdDef()));
		}
		if(this.getExtraPenetrate()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.PENETRATE, this.getExtraPenetrate()));
		}
		if(this.getExtraResilience()>0){
			mpvs.add(new MercenaryPropertyValue(MercenaryPropertyEnum.RESILIENCE, this.getExtraResilience()));
		}
		return mpvs;
	}

}
