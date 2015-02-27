/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

/**
 * 敌人模板
 * @author Administrator
 *
 */
public class EnemyTemplet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9089671013602260259L;
	
	private int enemyId;
	
	private long mercenaryId;
	
	private Map<MercenaryPropertyEnum,MercenaryPropertyValue> props=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	/**
	 * 
	 */
	public EnemyTemplet() {
		
	}

	public int getEnemyId() {
		return enemyId;
	}

	public void setEnemyId(int enemyId) {
		this.enemyId = enemyId;
	}

	public long getMercenaryId() {
		return mercenaryId;
	}

	public void setMercenaryId(long mercenaryId) {
		this.mercenaryId = mercenaryId;
	}

	public Map<MercenaryPropertyEnum, MercenaryPropertyValue> getProps() {
		return props;
	}

	public void setProps(Map<MercenaryPropertyEnum, MercenaryPropertyValue> props) {
		this.props = props;
	}
	
	public void addProperty(MercenaryPropertyValue mpv){
		this.props.put(mpv.getMpe(), mpv);
	}

}
