package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class EquipmentHeCheng implements Serializable{
	public static final String Path = "装备系统\\装备合成表.xlsx";
	
	private int equipmentId;//装备ID
	private String equipmentName;//装备名称
	private int equipmentPatchId;//装备碎片ID
	private String equipmentPatchName;//装备碎片名称
	private int equipmentPatchNum;//装备碎片数量
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public int getEquipmentPatchId() {
		return equipmentPatchId;
	}
	public void setEquipmentPatchId(int equipmentPatchId) {
		this.equipmentPatchId = equipmentPatchId;
	}
	
	public String getEquipmentPatchName() {
		return equipmentPatchName;
	}
	public void setEquipmentPatchName(String equipmentPatchName) {
		this.equipmentPatchName = equipmentPatchName;
	}
	public int getEquipmentPatchNum() {
		return equipmentPatchNum;
	}
	public void setEquipmentPatchNum(int equipmentPatchNum) {
		this.equipmentPatchNum = equipmentPatchNum;
	}
	public static String getPath() {
		return Path;
	}
	
}
