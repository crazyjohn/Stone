package com.i4joy.akka.kok.monster.equipment.templet;


public class EquipmentBasePropertyTemplet {
	private long eqipment_id;
	private String equipment_name;
	private byte startNum;
	private byte quality;
	private byte part;
	private int suit_id;
	private boolean is_xilian;
	private boolean is_thaw;
	private boolean is_sale;
	private int upgradPayId;
	private int xilian_id;
	private long[][] debirs;
	private int thaw_stone_num;
	private String desc;
	public long getEqipment_id() {
		return eqipment_id;
	}
	public void setEqipment_id(long eqipment_id) {
		this.eqipment_id = eqipment_id;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public byte getStartNum() {
		return startNum;
	}
	public void setStartNum(byte startNum) {
		this.startNum = startNum;
	}
	public byte getQuality() {
		return quality;
	}
	public void setQuality(byte quality) {
		this.quality = quality;
	}
	public byte getPart() {
		return part;
	}
	public void setPart(byte part) {
		this.part = part;
	}
	public int getSuit_id() {
		return suit_id;
	}
	public void setSuit_id(int suit_id) {
		this.suit_id = suit_id;
	}
	public boolean isIs_xilian() {
		return is_xilian;
	}
	public void setIs_xilian(boolean is_xilian) {
		this.is_xilian = is_xilian;
	}
	public boolean isIs_thaw() {
		return is_thaw;
	}
	public void setIs_thaw(boolean is_thaw) {
		this.is_thaw = is_thaw;
	}
	public boolean isIs_sale() {
		return is_sale;
	}
	public void setIs_sale(boolean is_sale) {
		this.is_sale = is_sale;
	}
	public int getUpgradPayId() {
		return upgradPayId;
	}
	public void setUpgradPayId(int upgradPayId) {
		this.upgradPayId = upgradPayId;
	}
	public int getXilian_id() {
		return xilian_id;
	}
	public void setXilian_id(int xilian_id) {
		this.xilian_id = xilian_id;
	}
	public long[][] getDebirs() {
		return debirs;
	}
	public void setDebirs(long[][] debirs) {
		this.debirs = debirs;
	}
	public int getThaw_stone_num() {
		return thaw_stone_num;
	}
	public void setThaw_stone_num(int thaw_stone_num) {
		this.thaw_stone_num = thaw_stone_num;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
