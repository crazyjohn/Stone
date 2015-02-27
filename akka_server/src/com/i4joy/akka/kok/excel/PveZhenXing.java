package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class PveZhenXing implements Serializable{
	public static final String Path = "PVE系统\\阵型表.xlsx";
	
	private int zhenXingId;//阵型ID
	private int part_1;//位置1
	private int part_2;//位置2
	private int part_3;//位置3
	private int part_4;//位置4
	private int part_5;//位置5
	private int part_6;//位置6
	private int part_7;//位置7
	private int part_8;//位置8
	public int getZhenXingId() {
		return zhenXingId;
	}
	public void setZhenXingId(int zhenXingId) {
		this.zhenXingId = zhenXingId;
	}
	public int getPart_1() {
		return part_1;
	}
	public void setPart_1(int part_1) {
		this.part_1 = part_1;
	}
	public int getPart_2() {
		return part_2;
	}
	public void setPart_2(int part_2) {
		this.part_2 = part_2;
	}
	public int getPart_3() {
		return part_3;
	}
	public void setPart_3(int part_3) {
		this.part_3 = part_3;
	}
	public int getPart_4() {
		return part_4;
	}
	public void setPart_4(int part_4) {
		this.part_4 = part_4;
	}
	public int getPart_5() {
		return part_5;
	}
	public void setPart_5(int part_5) {
		this.part_5 = part_5;
	}
	public int getPart_6() {
		return part_6;
	}
	public void setPart_6(int part_6) {
		this.part_6 = part_6;
	}
	public int getPart_7() {
		return part_7;
	}
	public void setPart_7(int part_7) {
		this.part_7 = part_7;
	}
	public int getPart_8() {
		return part_8;
	}
	public void setPart_8(int part_8) {
		this.part_8 = part_8;
	}
	public static String getPath() {
		return Path;
	}
	
}
