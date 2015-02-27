package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class Dialog implements Serializable{
	public static final String Path = "PVE系统\\剧情对话表.xlsx";
	private int dialogId;// 对话ID
	private String context;// 对话文本

	public int getDialogId() {
		return dialogId;
	}

	public void setDialogId(int dialogId) {
		this.dialogId = dialogId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
