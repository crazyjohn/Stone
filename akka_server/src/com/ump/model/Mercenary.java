package com.ump.model;

import java.util.Date;


public class Mercenary extends DBCache {

	private static final long serialVersionUID = 6853473932101357736L;
	public static final String tableName = "#mercenary";
	/**
	 * 唯一标识
	 */
	private long mercenary_id;

	/**
	 * 模板ID
	 */
	private int base_id;

	/**
	 * 进化等级
	 */
	private byte quality;

	/**
	 * 等级
	 */
	private byte level = 1;

	/**
	 * 经验
	 */
	private int exp;

	/**
	 * 好感度等级
	 */
	private int friendly_level;

	/**
	 * 好感度经验
	 */
	private int friendly_exp;

	/**
	 * 拥有者ID
	 */
	private long owner_id;

	/**
	 * 创建时间
	 */
	private Date created_time;

	/**
	 * 更新时间
	 */
	private Date update_time;

	private byte meridians_level;

	public byte getMeridians_level() {
		return meridians_level;
	}

	public void setMeridians_level(byte meridians_level) {
		this.meridians_level = meridians_level;
	}

	public long getMercenary_id() {
		return mercenary_id;
	}

	public void setMercenary_id(long mercenary_id) {
		this.mercenary_id = mercenary_id;
	}

	public int getBase_id() {
		return base_id;
	}

	public void setBase_id(int base_id) {
		this.base_id = base_id;
	}

	public byte getQuality() {
		return quality;
	}

	public void setQuality(byte quality) {
		this.quality = quality;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getFriendly_level() {
		return friendly_level;
	}

	public void setFriendly_level(int friendly_level) {
		this.friendly_level = friendly_level;
	}

	public int getFriendly_exp() {
		return friendly_exp;
	}

	public void setFriendly_exp(int friendly_exp) {
		this.friendly_exp = friendly_exp;
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

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Mercenary() {

	}

	public Mercenary(Mercenary m, byte action) {
		this.setAction(action);
		this.mercenary_id = m.getMercenary_id();
		this.base_id = m.getBase_id();
		this.quality = m.getQuality();
		this.level = m.getLevel();
		this.exp = m.getExp();
		this.friendly_level = m.getFriendly_level();
		this.friendly_exp = m.getFriendly_exp();
		this.owner_id = m.getOwner_id();
		this.created_time = m.getCreated_time();
		this.update_time = m.getUpdate_time();
	}

}
