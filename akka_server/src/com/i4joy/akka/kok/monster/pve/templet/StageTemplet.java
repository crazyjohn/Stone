/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;

/**
 * 关卡模板
 * @author Administrator
 *
 */
public class StageTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3987286716318469726L;
	
	/**
	 * 关卡ID
	 */
	private int stageId;
	
	/**
	 * 章节ID
	 */
	private int chapterId;
	
	/**
	 * 等级限制
	 */
	private int levelLimit;
	
	/**
	 * 每日挑战次数限制
	 */
	private int timesLimit;
	
	/**
	 * 关卡难度
	 */
	private int difficulty;
	
	/**
	 * 金钱奖励
	 */
	private int moneyReward;
	
	/**
	 * 阅历奖励
	 */
	private int mercenaryExpReward;
	
	/**
	 * 阵型ID
	 */
	private int lineupId;
	
	/**
	 * 剧情阵型ID
	 */
	private int storyLineupId;
	
	/**
	 * 奖励ID
	 */
	private int rewardsId;
	
	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 
	 */
	public StageTemplet() {
		
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public int getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public int getTimesLimit() {
		return timesLimit;
	}

	public void setTimesLimit(int timesLimit) {
		this.timesLimit = timesLimit;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getMoneyReward() {
		return moneyReward;
	}

	public void setMoneyReward(int moneyReward) {
		this.moneyReward = moneyReward;
	}

	public int getMercenaryExpReward() {
		return mercenaryExpReward;
	}

	public void setMercenaryExpReward(int mercenaryExpReward) {
		this.mercenaryExpReward = mercenaryExpReward;
	}

	public int getLineupId() {
		return lineupId;
	}

	public void setLineupId(int lineupId) {
		this.lineupId = lineupId;
	}

	public int getStoryLineupId() {
		return storyLineupId;
	}

	public void setStoryLineupId(int storyLineupId) {
		this.storyLineupId = storyLineupId;
	}

	public int getRewardsId() {
		return rewardsId;
	}

	public void setRewardsId(int rewardsId) {
		this.rewardsId = rewardsId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
