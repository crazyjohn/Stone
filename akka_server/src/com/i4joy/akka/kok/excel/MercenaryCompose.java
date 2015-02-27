package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class MercenaryCompose implements Serializable{
	public static final String Path = "侠客系统\\侠客合成\\侠客合成表.xlsx";
	
	private int mercenaryId;//侠客ID
	private int xiaLingId;//侠灵ID
	private int composeNum;//合成数量
	public int getMercenaryId() {
		return mercenaryId;
	}
	public void setMercenaryId(int mercenaryId) {
		this.mercenaryId = mercenaryId;
	}
	public int getXiaLingId() {
		return xiaLingId;
	}
	public void setXiaLingId(int xiaLingId) {
		this.xiaLingId = xiaLingId;
	}
	public int getComposeNum() {
		return composeNum;
	}
	public void setComposeNum(int composeNum) {
		this.composeNum = composeNum;
	}
	public static String getPath() {
		return Path;
	}
}
