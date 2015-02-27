/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 侠客与配缘对应模板
 * @author Administrator
 *
 */
public class MercenaryPredestineAssociationTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350551406673360151L;
	
	private long mercenaryId;
	
	private Set<Integer> predestines=new HashSet<Integer>();

	/**
	 * 
	 */
	public MercenaryPredestineAssociationTemplet() {
		
	}

	public long getMercenaryId() {
		return mercenaryId;
	}

	public void setMercenaryId(long mercenaryId) {
		this.mercenaryId = mercenaryId;
	}

	public Set<Integer> getPredestines() {
		return predestines;
	}

	public void setPredestines(Set<Integer> predestines) {
		this.predestines = predestines;
	}
	
	public void addPredestine(int pid){
		if(pid>0){
			this.predestines.add(pid);
		}
	}

}
