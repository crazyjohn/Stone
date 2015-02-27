/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 天赋侠客关系表
 * @author Administrator
 *
 */
public class MercenaryTalentAssociationTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2286458708128133810L;
	
	private long mercenaryId;
	
	/**
	 * 天赋激活信息
	 * KEY:激活等级，VALUE:天赋ID
	 */
	private Map<Integer, Integer> talentActivated=new HashMap<Integer, Integer>();

	/**
	 * 
	 */
	public MercenaryTalentAssociationTemplet() {
		
	}

	public long getMercenaryId() {
		return mercenaryId;
	}

	public void setMercenaryId(long mercenaryId) {
		this.mercenaryId = mercenaryId;
	}

	public Map<Integer, Integer> getTalentActivated() {
		return talentActivated;
	}

	public void setTalentActivated(Map<Integer, Integer> talentActivated) {
		this.talentActivated = talentActivated;
	}
	
	public void addAssociation(int talentId,int level){
		if(talentId>0){
			this.talentActivated.put(level, talentId);
		}
	}

}
