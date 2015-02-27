package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class EquipmentIntroduce implements Serializable{
	public static final String Path = "装备系统\\装备介绍表.xlsx";
	
	private int equipmentId;//装备ID
	private String equipmentIntroduce;//装备介绍
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipmentIntroduce() {
		return equipmentIntroduce;
	}
	public void setEquipmentIntroduce(String equipmentIntroduce) {
		this.equipmentIntroduce = equipmentIntroduce;
	}
	public static String getPath() {
		return Path;
	}
	
}
