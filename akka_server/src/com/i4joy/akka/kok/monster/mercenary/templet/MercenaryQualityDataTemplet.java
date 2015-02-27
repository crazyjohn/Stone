/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.i4joy.akka.kok.monster.item.IdNumPair;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

/**
 * @author Administrator
 *
 */
public class MercenaryQualityDataTemplet implements Serializable {
	
	/**
	 * 进化等级
	 */
	private int level;
	
	/**
	 * 费用
	 */
	private int fee;
	
	/**
	 * 所需材料
	 */
	List<IdNumPair> materials=new ArrayList<IdNumPair>();
	
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 3976689389944053255L;

	/**
	 * 
	 */
	public MercenaryQualityDataTemplet() {
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public List<IdNumPair> getMaterials() {
		return materials;
	}

	public void setMaterials(List<IdNumPair> materials) {
		this.materials = materials;
	}

	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getPropsValue() {
		return propsValue;
	}

	public void setPropsValue(
			HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue) {
		this.propsValue = propsValue;
	}
	
	public void addMaterial(IdNumPair inp){
		this.materials.add(inp);
	}
	
	public void addProperty(MercenaryPropertyValue mpv){
		this.propsValue.put(mpv.getMpe(), mpv);
	}
	
	public Collection<MercenaryPropertyValue> getAllProps(){
		return this.propsValue.values();
	}

}
