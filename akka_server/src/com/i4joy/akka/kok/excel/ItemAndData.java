package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class ItemAndData implements Serializable{
	public static final String Path = "其他系统\\道具\\道具类型与数据表对应关系表.xlsx";
	
	private int toolsType;//道具类型
	private String dataName;//数据表名称
	
	public int getToolsType() {
		return toolsType;
	}
	public void setToolsType(int toolsType) {
		this.toolsType = toolsType;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public static String getPath() {
		return Path;
	}
	
}
