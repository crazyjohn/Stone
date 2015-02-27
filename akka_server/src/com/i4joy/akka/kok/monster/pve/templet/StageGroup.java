package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 关卡集合，包括同一关卡的3种不同难度
 * @author Administrator
 *
 */
public class StageGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3644821463057003114L;
	
	private int stageId;
	
	private Map<Integer, StageTemplet> sts=new HashMap<Integer, StageTemplet>();

	public Map<Integer, StageTemplet> getSts() {
		return sts;
	}

	public void setSts(Map<Integer, StageTemplet> sts) {
		this.sts = sts;
	}
	
	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public void addStage(StageTemplet st) throws Exception{
		if(this.sts.containsKey(st.getDifficulty())){
			throw new Exception("[关卡数据错误] [同一关卡难度重复] [关卡ID："+this.stageId+"]");
		}
		this.sts.put(st.getDifficulty(), st);
	}
	
	public StageTemplet getStageTemplet(int difficulty){
		return this.sts.get(difficulty);
	}

}
