package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class OtherShop implements Serializable{
	public static final String Path = "其他系统\\神秘商店\\魂玉商店道具表.xlsx";
	
	private int goodsId;//商品ID
	private int goodsType;//商品类型
	private String toolsName;//道具名称
	private int price;//魂玉价格
	private int gailv;//刷出概率
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	public String getToolsName() {
		return toolsName;
	}
	public void setToolsName(String toolsName) {
		this.toolsName = toolsName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
