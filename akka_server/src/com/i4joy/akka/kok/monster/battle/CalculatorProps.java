/**
 * 
 */
package com.i4joy.akka.kok.monster.battle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyPercent;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

/**
 * @author Administrator
 *
 */
public class CalculatorProps {
	
	private long mercenaryId;
	
	Map<MercenaryPropertyEnum, MercenaryPropertyValue> values=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();
	
	Map<MercenaryPropertyEnum, MercenaryPropertyPercent> percents=new HashMap<MercenaryPropertyEnum, MercenaryPropertyPercent>();
	
	List<Integer> buffIds=new ArrayList<Integer>();
	/**
	 * 
	 */
	public CalculatorProps() {
		
	}
	
	public void addValue(MercenaryPropertyEnum mpe,int value){
//		if(mpe==MercenaryPropertyEnum.HP){
//			System.out.println("ADD_"+mpe.getName()+":"+value);
//		}
		
		MercenaryPropertyValue mpv=this.values.get(mpe);
		if(mpv==null){
			mpv=new MercenaryPropertyValue(mpe, value);
			this.values.put(mpe, mpv);
		}else{
			mpv.setValue(mpv.getValue()+value);
		}
	}
	
	public void addValue(MercenaryPropertyValue mpv){
//		if(mpv.getMpe()==MercenaryPropertyEnum.HP){
//			System.out.println("ADD_"+mpv.getMpe().getName()+":"+mpv.getValue());
//		}
		
		MercenaryPropertyValue localMpv=this.values.get(mpv.getMpe());
		if(localMpv==null){
			localMpv=mpv.clone();
			this.values.put(localMpv.getMpe(), localMpv);
		}else{
			localMpv.setValue(localMpv.getValue()+mpv.getValue());
		}
	}
	
	public void addPercent(MercenaryPropertyEnum mpe,float percent){
		MercenaryPropertyPercent mpp=this.percents.get(mpe);
		if(mpp==null){
			mpp=new MercenaryPropertyPercent(mpe, percent);
			this.percents.put(mpe, mpp);
		}else{
			mpp.setValue(mpp.getValue()+percent);
		}
	}
	
	public void addPercent(MercenaryPropertyPercent mpp){
		MercenaryPropertyPercent localMpp=this.percents.get(mpp.getMpe());
		if(localMpp==null){
			localMpp=mpp.clone();
			this.percents.put(localMpp.getMpe(), localMpp);
		}else{
			localMpp.setValue(localMpp.getValue()+mpp.getValue());
		}
	}

	public long getMercenaryId() {
		return mercenaryId;
	}

	public void setMercenaryId(long mercenaryId) {
		this.mercenaryId = mercenaryId;
	}

	public Map<MercenaryPropertyEnum, MercenaryPropertyValue> getValues() {
		return values;
	}

	public void setValues(Map<MercenaryPropertyEnum, MercenaryPropertyValue> values) {
		this.values = values;
	}

	public Map<MercenaryPropertyEnum, MercenaryPropertyPercent> getPercents() {
		return percents;
	}

	public void setPercents(
			Map<MercenaryPropertyEnum, MercenaryPropertyPercent> percents) {
		this.percents = percents;
	}
	
	public void addAllValue(Collection<MercenaryPropertyValue> mpvs){
		for(MercenaryPropertyValue mpv:mpvs){
			this.addValue(mpv);
		}
	}
	
	public void addAllPercent(Collection<MercenaryPropertyPercent> mpps){
		for(MercenaryPropertyPercent mpp:mpps){
			this.addPercent(mpp);
		}
	}
	
	public void addBuffId(int buffId){
		this.buffIds.add(buffId);
	}
	
	public int getProps(MercenaryPropertyEnum mpe){
//		if(mpe==MercenaryPropertyEnum.AD){
//			System.out.println("GET_"+mpe.getName());
//		}
		
		float value=0;
		MercenaryPropertyValue mpv=this.values.get(mpe);
		if(mpv!=null){
			value=mpv.getValue();
		}
		MercenaryPropertyPercent mpp=this.percents.get(mpe);
		if(mpp!=null){
			value=value*(100f+mpp.getValue())/100f;
		}
		
		return (int)value;
	}
	
	public String getBuffString(){
		StringBuffer sb=new StringBuffer();
		for(int id:this.buffIds){
			sb.append(id);
			sb.append("_");
		}
		return sb.toString();
	}

}
