package com.i4joy.akka.kok.monster.amulet.templet;

import java.util.ArrayList;
import java.util.List;

import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;

public class AmuletUpGradeTemplet {
	private int level;
	
	private float[][] props;
	
	private int skill;
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public float[][] getProps() {
		return props;
	}

	public void setProps(float[][] props) {
		this.props = props;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}
	
	public List<MercenaryProperty> getMercenaryPropertys(){
		List<MercenaryProperty> mps=new ArrayList<MercenaryProperty>();
		for(float[] prop:this.props){
			MercenaryProperty mp=MercenaryProperty.createMercenaryProperty(prop);
			if(mp.getMpe()!=MercenaryPropertyEnum.NONE){
				mps.add(mp);
			}
		}
		return mps;
	}
	
}
