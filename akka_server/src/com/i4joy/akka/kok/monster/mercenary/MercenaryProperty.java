/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;

/**
 * @author Administrator
 *
 */
public class MercenaryProperty {
	
	private MercenaryPropertyEnum mpe;

	/**
	 * 
	 */
	public MercenaryProperty() {
		
	}
	
	public MercenaryProperty(MercenaryPropertyEnum mpe) {
		this.mpe=mpe;
	}

	public MercenaryPropertyEnum getMpe() {
		return mpe;
	}

	public void setMpe(MercenaryPropertyEnum mpe) {
		this.mpe = mpe;
	}
	
	public static MercenaryProperty createMercenaryProperty(float[] prop){
		if(prop.length==3){
			MercenaryPropertyEnum mpe=MercenaryPropertyEnum.getMercenaryPropertyEnum((int)prop[0]);
			if(mpe!=null && mpe!=MercenaryPropertyEnum.NONE){
				if(prop[1]==1){
					return new MercenaryPropertyValue(mpe, (int)prop[2]);
				}else{
					return new MercenaryPropertyPercent(mpe, prop[2]);
				}
			}
		}
		
		return new MercenaryProperty(MercenaryPropertyEnum.NONE);
	}

}
