package com.i4joy.akka.kok.monster.equipment.templet;

import java.io.Serializable;

public class EquipmentdDebrisTemplet implements Serializable {
	private long debris_id;
	private long equipment_id;
	private int price;
	private String desc;

	public long getDebris_id() {
		return debris_id;
	}

	public void setDebris_id(long debris_id) {
		this.debris_id = debris_id;
	}

	public long getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(long equipment_id) {
		this.equipment_id = equipment_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "EquipmentdDebrisTemplet [debris_id=" + debris_id + ", equipment_id=" + equipment_id + ", price=" + price + ", desc=" + desc + "]";
	}

}
