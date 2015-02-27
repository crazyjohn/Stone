/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;



/**
 * @author Administrator
 *
 */
public class MercenaryManage {
	
	static MercenaryManage self;

	/**
	 * 
	 */
	public MercenaryManage() {
		
	}
	
	public void init(){
		MercenaryManage.self=this;
	}
	
	public static MercenaryManage getInstance(){
		return MercenaryManage.self;
	}
	
	

}
