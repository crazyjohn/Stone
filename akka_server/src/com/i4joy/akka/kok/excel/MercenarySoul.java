package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class MercenarySoul implements Serializable{
	public static final String Path = "侠客系统\\侠客合成\\侠灵表.xlsx";
	
	private int mercenarySoulId;//侠灵ID
	private String mercenarySoulName;//侠灵名称
	private int sellPrice;//出售价格
	private int hunYuPrice;//魂玉价格
	public int getMercenarySoulId() {
		return mercenarySoulId;
	}
	public void setMercenarySoulId(int mercenarySoulId) {
		this.mercenarySoulId = mercenarySoulId;
	}
	public String getMercenarySoulName() {
		return mercenarySoulName;
	}
	public void setMercenarySoulName(String mercenarySoulName) {
		this.mercenarySoulName = mercenarySoulName;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int getHunYuPrice() {
		return hunYuPrice;
	}
	public void setHunYuPrice(int hunYuPrice) {
		this.hunYuPrice = hunYuPrice;
	}
	public static String getPath() {
		return Path;
	}
}
