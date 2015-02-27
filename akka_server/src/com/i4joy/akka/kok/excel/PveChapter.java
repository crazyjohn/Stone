package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class PveChapter implements Serializable{
	public static final String Path = "PVE系统\\章节表.xlsx";
	
	private int chapterId;//章节ID
	private int guanQiaId;//关卡ID
	private String chapterTitle;//章节标题
	private String chapterDescribe;//章节描述
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getGuanQiaId() {
		return guanQiaId;
	}
	public void setGuanQiaId(int guanQiaId) {
		this.guanQiaId = guanQiaId;
	}
	public String getChapterTitle() {
		return chapterTitle;
	}
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	public String getChapterDescribe() {
		return chapterDescribe;
	}
	public void setChapterDescribe(String chapterDescribe) {
		this.chapterDescribe = chapterDescribe;
	}
	public static String getPath() {
		return Path;
	}

}
