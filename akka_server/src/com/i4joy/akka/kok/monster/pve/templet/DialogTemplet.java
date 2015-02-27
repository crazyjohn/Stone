/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import scala.Tuple2;

/**
 * @author Administrator
 *
 */
public class DialogTemplet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8876230929518954724L;

	/**
	 * 关卡ID
	 */
	private int stageId;
	
	/**
	 * 进入关卡前的对话，第一位侠客ID，第二位对话ID
	 */
	private List<Tuple2<Long, Integer>> dialogBeforeEnter=new ArrayList<Tuple2<Long,Integer>>();
	
	/**
	 * 战斗前的对话，第一位侠客ID，第二位对话ID
	 */
	private List<Tuple2<Long, Integer>> dialogBeforeFight=new ArrayList<Tuple2<Long,Integer>>();
	
	/**
	 * 战斗后的对话，第一位侠客ID，第二位对话ID
	 */
	private List<Tuple2<Long, Integer>> dialogAfterFight=new ArrayList<Tuple2<Long,Integer>>();

	/**
	 * 
	 */
	public DialogTemplet() {
		
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public List<Tuple2<Long, Integer>> getDialogBeforeEnter() {
		return dialogBeforeEnter;
	}

	public void setDialogBeforeEnter(List<Tuple2<Long, Integer>> dialogBeforeEnter) {
		this.dialogBeforeEnter = dialogBeforeEnter;
	}

	public List<Tuple2<Long, Integer>> getDialogBeforeFight() {
		return dialogBeforeFight;
	}

	public void setDialogBeforeFight(List<Tuple2<Long, Integer>> dialogBeforeFight) {
		this.dialogBeforeFight = dialogBeforeFight;
	}

	public List<Tuple2<Long, Integer>> getDialogAfterFight() {
		return dialogAfterFight;
	}

	public void setDialogAfterFight(List<Tuple2<Long, Integer>> dialogAfterFight) {
		this.dialogAfterFight = dialogAfterFight;
	}
	
	public void addDialogBeforeEnter(long mercenaryId,int dialogId){
		this.dialogBeforeEnter.add(new Tuple2<Long, Integer>(mercenaryId, dialogId));
	}
	
	public void addDialogBeforeFight(long mercenaryId,int dialogId){
		this.dialogBeforeFight.add(new Tuple2<Long, Integer>(mercenaryId, dialogId));
	}
	
	public void addDialogAfterFight(long mercenaryId,int dialogId){
		this.dialogAfterFight.add(new Tuple2<Long, Integer>(mercenaryId, dialogId));
	}

}
