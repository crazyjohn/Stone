package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class OtherMercenary implements Serializable{
	public static final String Path = "其他系统\\侠客招募\\侠客招募概率表.xlsx";
	
	private int mercenaryId;//侠客ID
	private int gailv;//招募概率
	public int getMercenaryId() {
		return mercenaryId;
	}
	public void setMercenaryId(int mercenaryId) {
		this.mercenaryId = mercenaryId;
	}
	public int getGailv() {
		return gailv;
	}
	public void setGailv(int gailv) {
		this.gailv = gailv;
	}
	public static String getPath() {
		return Path;
	}
	
}
