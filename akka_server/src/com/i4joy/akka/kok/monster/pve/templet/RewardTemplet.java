/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 关卡奖励掉落模板
 * @author Administrator
 *
 */
public class RewardTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7116066556901928128L;
	
	private int id;
	
	private List<RewardCastingInfo> rcis=new ArrayList<RewardTemplet.RewardCastingInfo>();

	/**
	 * 
	 */
	public RewardTemplet() {
		
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public List<RewardCastingInfo> getRcis() {
		return rcis;
	}


	public void setRcis(List<RewardCastingInfo> rcis) {
		this.rcis = rcis;
	}
	
	public void addReward(long itemId,int num,int percent){
		this.rcis.add(new RewardCastingInfo(itemId, num, percent));
	}


	/**
	 * 掉落信息
	 * @author Administrator
	 *
	 */
	private class RewardCastingInfo implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5160068297575347534L;
		
		private long itemId;
		
		private int num;
		
		private int percent;
		
		public RewardCastingInfo(){
			
		}
		
		public RewardCastingInfo(long itemId,int num,int percent){
			this.itemId=itemId;
			this.num=num;
			this.percent=percent;
		}

		public long getItemId() {
			return itemId;
		}

		public int getNum() {
			return num;
		}

		public int getPercent() {
			return percent;
		}

		
	}

}
