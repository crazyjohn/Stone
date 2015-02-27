package com.i4joy.akka.kok.monster.equipment.templet;

import com.i4joy.util.Tools;

public class EquipmentRefineTemplete {

	public int getHp(byte type) {
		if (hp_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, hp_refine_normal);
		case 1:
			return Tools.getRandomNum(0, hp_refine_medium);
		case 2:
			return Tools.getRandomNum(0, hp_refine_high);
		default:
			return -1;
		}
	}
	
	public int getAd(byte type) {
		if (ad_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, ad_refine_normal);
		case 1:
			return Tools.getRandomNum(0, ad_refine_medium);
		case 2:
			return Tools.getRandomNum(0, ad_refine_high);
		default:
			return -1;
		}
	}
	
	public int getAdDef(byte type) {
		if (adDef_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, adDef_refine_normal);
		case 1:
			return Tools.getRandomNum(0, adDef_refine_medium);
		case 2:
			return Tools.getRandomNum(0, adDef_refine_high);
		default:
			return -1;
		}
	}
	
	public int getPdDef(byte type) {
		if (pdDef_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, pdDef_refine_normal);
		case 1:
			return Tools.getRandomNum(0, pdDef_refine_medium);
		case 2:
			return Tools.getRandomNum(0, pdDef_refine_high);
		default:
			return -1;
		}
	}
	
	public int getCritical(byte type) {
		if (critical_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, critical_refine_normal);
		case 1:
			return Tools.getRandomNum(0, critical_refine_medium);
		case 2:
			return Tools.getRandomNum(0, critical_refine_high);
		default:
			return -1;
		}
	}
	
	public int getResilience(byte type) {
		if (resilience_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, resilience_normal);
		case 1:
			return Tools.getRandomNum(0, resilience_medium);
		case 2:
			return Tools.getRandomNum(0, resilience_high);
		default:
			return -1;
		}
	}
	
	public int getHitRating(byte type) {
		if (hit_rating_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, hit_rating_refine_normal);
		case 1:
			return Tools.getRandomNum(0, hit_rating_refine_medium);
		case 2:
			return Tools.getRandomNum(0, hit_rating_refine_high);
		default:
			return -1;
		}
	}
	
	public int getDodge(byte type) {
		if (dodge_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, dodge_normal);
		case 1:
			return Tools.getRandomNum(0, dodge_medium);
		case 2:
			return Tools.getRandomNum(0, dodge_high);
		default:
			return -1;
		}
	}
	
	public int getPenetrate(byte type) {
		if (penetrate_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, penetrate_refine_normal);
		case 1:
			return Tools.getRandomNum(0, penetrate_refine_medium);
		case 2:
			return Tools.getRandomNum(0, penetrate_refine_high);
		default:
			return -1;
		}
	}
	
	public int getBlock(byte type) {
		if (block_refine_limit == -1) {
			return -1;
		}
		switch (type) {
		case 0:
			return Tools.getRandomNum(0, block_refine_normal);
		case 1:
			return Tools.getRandomNum(0, block_refine_medium);
		case 2:
			return Tools.getRandomNum(0, block_refine_high);
		default:
			return -1;
		}
	}

	private int level;
	private int hp_refine_limit;
	private int hp_refine_normal;
	private int hp_refine_medium;
	private int hp_refine_high;
	private int ad_refine_limit;
	private int ad_refine_normal;
	private int ad_refine_medium;
	private int ad_refine_high;
	private int adDef_refine_limit;
	private int adDef_refine_normal;
	private int adDef_refine_medium;
	private int adDef_refine_high;
	private int pdDef_refine_limit;
	private int pdDef_refine_normal;
	private int pdDef_refine_medium;
	private int pdDef_refine_high;
	private int critical_refine_limit;
	private int critical_refine_normal;
	private int critical_refine_medium;
	private int critical_refine_high;
	private int resilience_refine_limit;
	private int resilience_normal;
	private int resilience_medium;
	private int resilience_high;
	private int hit_rating_refine_limit;
	private int hit_rating_refine_normal;
	private int hit_rating_refine_medium;
	private int hit_rating_refine_high;
	private int dodge_refine_limit;
	private int dodge_normal;
	private int dodge_medium;
	private int dodge_high;
	private int penetrate_refine_limit;
	private int penetrate_refine_normal;
	private int penetrate_refine_medium;
	private int penetrate_refine_high;
	private int block_refine_limit;
	private int block_refine_normal;
	private int block_refine_medium;
	private int block_refine_high;

	@Override
	public String toString() {
		return "EquipmentRefineTemplete [level=" + level + ", hp_refine_limit=" + hp_refine_limit + ", hp_refine_normal=" + hp_refine_normal + ", hp_refine_medium=" + hp_refine_medium + ", hp_refine_high=" + hp_refine_high + ", ad_refine_limit=" + ad_refine_limit + ", ad_refine_normal="
				+ ad_refine_normal + ", ad_refine_medium=" + ad_refine_medium + ", ad_refine_high=" + ad_refine_high + ", adDef_refine_limit=" + adDef_refine_limit + ", adDef_refine_normal=" + adDef_refine_normal + ", adDef_refine_medium=" + adDef_refine_medium + ", adDef_refine_high="
				+ adDef_refine_high + ", pdDef_refine_limit=" + pdDef_refine_limit + ", pdDef_refine_normal=" + pdDef_refine_normal + ", pdDef_refine_medium=" + pdDef_refine_medium + ", pdDef_refine_high=" + pdDef_refine_high + ", critical_refine_limit=" + critical_refine_limit
				+ ", critical_refine_normal=" + critical_refine_normal + ", critical_refine_medium=" + critical_refine_medium + ", critical_refine_high=" + critical_refine_high + ", resilience_refine_limit=" + resilience_refine_limit + ", resilience_normal=" + resilience_normal
				+ ", resilience_medium=" + resilience_medium + ", resilience_high=" + resilience_high + ", hit_rating_refine_limit=" + hit_rating_refine_limit + ", hit_rating_refine_normal=" + hit_rating_refine_normal + ", hit_rating_refine_medium=" + hit_rating_refine_medium
				+ ", hit_rating_refine_high=" + hit_rating_refine_high + ", dodge_refine_limit=" + dodge_refine_limit + ", dodge_normal=" + dodge_normal + ", dodge_medium=" + dodge_medium + ", dodge_high=" + dodge_high + ", penetrate_refine_limit=" + penetrate_refine_limit
				+ ", penetrate_refine_normal=" + penetrate_refine_normal + ", penetrate_refine_medium=" + penetrate_refine_medium + ", penetrate_refine_high=" + penetrate_refine_high + ", block_refine_limit=" + block_refine_limit + ", block_refine_normal=" + block_refine_normal
				+ ", block_refine_medium=" + block_refine_medium + ", block_refine_high=" + block_refine_high + "]";
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHp_refine_limit() {
		return hp_refine_limit;
	}

	public void setHp_refine_limit(int hp_refine_limit) {
		this.hp_refine_limit = hp_refine_limit;
	}

	public int getHp_refine_normal() {
		return hp_refine_normal;
	}

	public void setHp_refine_normal(int hp_refine_normal) {
		this.hp_refine_normal = hp_refine_normal;
	}

	public int getHp_refine_medium() {
		return hp_refine_medium;
	}

	public void setHp_refine_medium(int hp_refine_medium) {
		this.hp_refine_medium = hp_refine_medium;
	}

	public int getHp_refine_high() {
		return hp_refine_high;
	}

	public void setHp_refine_high(int hp_refine_high) {
		this.hp_refine_high = hp_refine_high;
	}

	public int getAd_refine_limit() {
		return ad_refine_limit;
	}

	public void setAd_refine_limit(int ad_refine_limit) {
		this.ad_refine_limit = ad_refine_limit;
	}

	public int getAd_refine_normal() {
		return ad_refine_normal;
	}

	public void setAd_refine_normal(int ad_refine_normal) {
		this.ad_refine_normal = ad_refine_normal;
	}

	public int getAd_refine_medium() {
		return ad_refine_medium;
	}

	public void setAd_refine_medium(int ad_refine_medium) {
		this.ad_refine_medium = ad_refine_medium;
	}

	public int getAd_refine_high() {
		return ad_refine_high;
	}

	public void setAd_refine_high(int ad_refine_high) {
		this.ad_refine_high = ad_refine_high;
	}

	public int getAdDef_refine_limit() {
		return adDef_refine_limit;
	}

	public void setAdDef_refine_limit(int adDef_refine_limit) {
		this.adDef_refine_limit = adDef_refine_limit;
	}

	public int getAdDef_refine_normal() {
		return adDef_refine_normal;
	}

	public void setAdDef_refine_normal(int adDef_refine_normal) {
		this.adDef_refine_normal = adDef_refine_normal;
	}

	public int getAdDef_refine_medium() {
		return adDef_refine_medium;
	}

	public void setAdDef_refine_medium(int adDef_refine_medium) {
		this.adDef_refine_medium = adDef_refine_medium;
	}

	public int getAdDef_refine_high() {
		return adDef_refine_high;
	}

	public void setAdDef_refine_high(int adDef_refine_high) {
		this.adDef_refine_high = adDef_refine_high;
	}

	public int getPdDef_refine_limit() {
		return pdDef_refine_limit;
	}

	public void setPdDef_refine_limit(int pdDef_refine_limit) {
		this.pdDef_refine_limit = pdDef_refine_limit;
	}

	public int getPdDef_refine_normal() {
		return pdDef_refine_normal;
	}

	public void setPdDef_refine_normal(int pdDef_refine_normal) {
		this.pdDef_refine_normal = pdDef_refine_normal;
	}

	public int getPdDef_refine_medium() {
		return pdDef_refine_medium;
	}

	public void setPdDef_refine_medium(int pdDef_refine_medium) {
		this.pdDef_refine_medium = pdDef_refine_medium;
	}

	public int getPdDef_refine_high() {
		return pdDef_refine_high;
	}

	public void setPdDef_refine_high(int pdDef_refine_high) {
		this.pdDef_refine_high = pdDef_refine_high;
	}

	public int getCritical_refine_limit() {
		return critical_refine_limit;
	}

	public void setCritical_refine_limit(int critical_refine_limit) {
		this.critical_refine_limit = critical_refine_limit;
	}

	public int getCritical_refine_normal() {
		return critical_refine_normal;
	}

	public void setCritical_refine_normal(int critical_refine_normal) {
		this.critical_refine_normal = critical_refine_normal;
	}

	public int getCritical_refine_medium() {
		return critical_refine_medium;
	}

	public void setCritical_refine_medium(int critical_refine_medium) {
		this.critical_refine_medium = critical_refine_medium;
	}

	public int getCritical_refine_high() {
		return critical_refine_high;
	}

	public void setCritical_refine_high(int critical_refine_high) {
		this.critical_refine_high = critical_refine_high;
	}

	public int getResilience_refine_limit() {
		return resilience_refine_limit;
	}

	public void setResilience_refine_limit(int resilience_refine_limit) {
		this.resilience_refine_limit = resilience_refine_limit;
	}

	public int getResilience_normal() {
		return resilience_normal;
	}

	public void setResilience_normal(int resilience_normal) {
		this.resilience_normal = resilience_normal;
	}

	public int getResilience_medium() {
		return resilience_medium;
	}

	public void setResilience_medium(int resilience_medium) {
		this.resilience_medium = resilience_medium;
	}

	public int getResilience_high() {
		return resilience_high;
	}

	public void setResilience_high(int resilience_high) {
		this.resilience_high = resilience_high;
	}

	public int getHit_rating_refine_limit() {
		return hit_rating_refine_limit;
	}

	public void setHit_rating_refine_limit(int hit_rating_refine_limit) {
		this.hit_rating_refine_limit = hit_rating_refine_limit;
	}

	public int getHit_rating_refine_normal() {
		return hit_rating_refine_normal;
	}

	public void setHit_rating_refine_normal(int hit_rating_refine_normal) {
		this.hit_rating_refine_normal = hit_rating_refine_normal;
	}

	public int getHit_rating_refine_medium() {
		return hit_rating_refine_medium;
	}

	public void setHit_rating_refine_medium(int hit_rating_refine_medium) {
		this.hit_rating_refine_medium = hit_rating_refine_medium;
	}

	public int getHit_rating_refine_high() {
		return hit_rating_refine_high;
	}

	public void setHit_rating_refine_high(int hit_rating_refine_high) {
		this.hit_rating_refine_high = hit_rating_refine_high;
	}

	public int getDodge_refine_limit() {
		return dodge_refine_limit;
	}

	public void setDodge_refine_limit(int dodge_refine_limit) {
		this.dodge_refine_limit = dodge_refine_limit;
	}

	public int getDodge_normal() {
		return dodge_normal;
	}

	public void setDodge_normal(int dodge_normal) {
		this.dodge_normal = dodge_normal;
	}

	public int getDodge_medium() {
		return dodge_medium;
	}

	public void setDodge_medium(int dodge_medium) {
		this.dodge_medium = dodge_medium;
	}

	public int getDodge_high() {
		return dodge_high;
	}

	public void setDodge_high(int dodge_high) {
		this.dodge_high = dodge_high;
	}

	public int getPenetrate_refine_limit() {
		return penetrate_refine_limit;
	}

	public void setPenetrate_refine_limit(int penetrate_refine_limit) {
		this.penetrate_refine_limit = penetrate_refine_limit;
	}

	public int getPenetrate_refine_normal() {
		return penetrate_refine_normal;
	}

	public void setPenetrate_refine_normal(int penetrate_refine_normal) {
		this.penetrate_refine_normal = penetrate_refine_normal;
	}

	public int getPenetrate_refine_medium() {
		return penetrate_refine_medium;
	}

	public void setPenetrate_refine_medium(int penetrate_refine_medium) {
		this.penetrate_refine_medium = penetrate_refine_medium;
	}

	public int getPenetrate_refine_high() {
		return penetrate_refine_high;
	}

	public void setPenetrate_refine_high(int penetrate_refine_high) {
		this.penetrate_refine_high = penetrate_refine_high;
	}

	public int getBlock_refine_limit() {
		return block_refine_limit;
	}

	public void setBlock_refine_limit(int block_refine_limit) {
		this.block_refine_limit = block_refine_limit;
	}

	public int getBlock_refine_normal() {
		return block_refine_normal;
	}

	public void setBlock_refine_normal(int block_refine_normal) {
		this.block_refine_normal = block_refine_normal;
	}

	public int getBlock_refine_medium() {
		return block_refine_medium;
	}

	public void setBlock_refine_medium(int block_refine_medium) {
		this.block_refine_medium = block_refine_medium;
	}

	public int getBlock_refine_high() {
		return block_refine_high;
	}

	public void setBlock_refine_high(int block_refine_high) {
		this.block_refine_high = block_refine_high;
	}

}
