package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class Item implements Serializable{
	public static final String Path = "其他系统\\道具\\道具表.xlsx";
	
	private int toolsId;//道具ID
	private String toolsName;//道具名称
	private int toolsType;//道具类型
	private int useType;//使用类型
	private int sellPrice;//出售价格
	private String toolsDescribe;//道具描述
	public int getToolsId() {
		return toolsId;
	}
	public void setToolsId(int toolsId) {
		this.toolsId = toolsId;
	}
	public String getToolsName() {
		return toolsName;
	}
	public void setToolsName(String toolsName) {
		this.toolsName = toolsName;
	}
	public int getToolsType() {
		return toolsType;
	}
	public void setToolsType(int toolsType) {
		this.toolsType = toolsType;
	}
	public int getUseType() {
		return useType;
	}
	public void setUseType(int useType) {
		this.useType = useType;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getToolsDescribe() {
		return toolsDescribe;
	}
	public void setToolsDescribe(String toolsDescribe) {
		this.toolsDescribe = toolsDescribe;
	}
	public static String getPath() {
		return Path;
	}
	
}
