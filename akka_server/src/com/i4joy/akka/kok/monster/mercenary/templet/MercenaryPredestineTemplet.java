/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i4joy.akka.kok.monster.ThingsType;
import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;

/**
 * 配缘模板
 * @author Administrator
 *
 */
public class MercenaryPredestineTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6810168869130025906L;
	
	private int id;
	
	private String name;
	
	private Set<Long> elements=new HashSet<Long>();
	
	private float[][] props;
	
	private String desc;
	
	private String propsString;

	/**
	 * 
	 */
	public MercenaryPredestineTemplet() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Long> getElements() {
		return elements;
	}

	public void setElements(Set<Long> elements) {
		this.elements = elements;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void addElement(long id){
		this.elements.add(id);
	}
	
	public float[][] getProps() {
		return props;
	}

	public void setProps(float[][] props) {
		this.props = props;
	}
	
	public List<MercenaryProperty> getMercenaryPropertys(){
		List<MercenaryProperty> mps=new ArrayList<MercenaryProperty>();
		for(float[] prop:props){
			MercenaryProperty mp=MercenaryProperty.createMercenaryProperty(prop);
			if(mp.getMpe()!=MercenaryPropertyEnum.NONE){
				mps.add(mp);
			}
		}
		return mps;
	}

	public String getPropsString() {
		return propsString;
	}

	public void setPropsString(String propsString) {
		this.propsString = propsString;
	}

	/**
	 * 根据元素ID，获取是什么类型的配缘
	 * @return
	 */
	public ThingsType getType(){
		for(long id:this.elements){
			return ThingsType.getThingsType(id);
		}
		return null;
	}
	
	/**
	 * 是否激活
	 * @param ids
	 * @return
	 */
	public boolean isActive(long[] ids){
		int n=0;
		for(long element:this.elements){
			for(long id:ids){
				if(element==id){
					n++;
					break;
				}
			}
		}
		return n==this.elements.size();
	}
	
}
