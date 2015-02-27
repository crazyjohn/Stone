package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class MercenaryGift implements Serializable{
	public static final String Path = "侠客系统\\情义培养\\礼品表.xlsx";
	
	private int id;//ID
	private String name;//名称
	private int starLevel;//星级
	private int experience;//经验
	private int shengJiProbability;//情义直接升级概率加成（%）
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getShengJiProbability() {
		return shengJiProbability;
	}
	public void setShengJiProbability(int shengJiProbability) {
		this.shengJiProbability = shengJiProbability;
	}
	public static String getPath() {
		return Path;
	}
	

}
