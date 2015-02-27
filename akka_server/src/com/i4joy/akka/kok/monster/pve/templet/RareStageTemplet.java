/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 精英任务模板
 * @author Administrator
 *
 */
public class RareStageTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2143529266904811033L;
	
	private int stageId;
	
	/**
	 * 前置普通关卡ID
	 */
	private int headStageId;
	
	/**
	 * 前置精英任务ID
	 */
	private int headRareStageId;
	
	/**
	 * 任务数据
	 */
	private StageTemplet st;

	/**
	 * 
	 */
	public RareStageTemplet() {
		
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public int getHeadStageId() {
		return headStageId;
	}

	public void setHeadStageId(int headStageId) {
		this.headStageId = headStageId;
	}

	public int getHeadRareStageId() {
		return headRareStageId;
	}

	public void setHeadRareStageId(int headRareStageId) {
		this.headRareStageId = headRareStageId;
	}

	public StageTemplet getSt() {
		return st;
	}

	public void setSt(StageTemplet st) {
		this.st = st;
	}
	

}
