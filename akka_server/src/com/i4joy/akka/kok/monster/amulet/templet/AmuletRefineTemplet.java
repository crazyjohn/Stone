package com.i4joy.akka.kok.monster.amulet.templet;

import java.util.ArrayList;
import java.util.List;

import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;

public class AmuletRefineTemplet {
	private int level;
	
	private int pay_money;
	
	private long[][] item_ids;
	
	private float[][] props;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPay_money() {
		return pay_money;
	}

	public void setPay_money(int pay_money) {
		this.pay_money = pay_money;
	}

	public long[][] getItem_ids() {
		return item_ids;
	}

	public void setItem_ids(long[][] item_ids) {
		this.item_ids = item_ids;
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

}
