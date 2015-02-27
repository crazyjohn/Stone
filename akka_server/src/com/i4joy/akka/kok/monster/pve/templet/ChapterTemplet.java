/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import scala.Tuple2;

/**
 * 章节模板
 * @author Administrator
 *
 */
public class ChapterTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8315424570725377772L;
	
	private int id;
	
	private String title;
	
	private String desc;
	
	/**
	 * 奖励数组，第一位奖励ID，第二位所需星数
	 */
	private List<Tuple2<Integer, Integer>> rewardStarsPair=new ArrayList<Tuple2<Integer,Integer>>();

	/**
	 * 
	 */
	public ChapterTemplet() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Tuple2<Integer, Integer>> getRewardStarsPair() {
		return rewardStarsPair;
	}

	public void setRewardStarsPair(List<Tuple2<Integer, Integer>> rewardStarsPair) {
		this.rewardStarsPair = rewardStarsPair;
	}
	
	public void addReward(int rewardId,int stars){
		if(rewardId>0){
			this.rewardStarsPair.add(new Tuple2<Integer, Integer>(rewardId, stars));
		}
	}

}
