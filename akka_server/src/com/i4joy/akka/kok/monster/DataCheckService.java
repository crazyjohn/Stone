/**
 * 
 */
package com.i4joy.akka.kok.monster;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据检查服务
 * 在所有数据加载完成后调用
 * @author Administrator
 *
 */
public class DataCheckService {
	
	static List<DataCheckListener> dcls=new ArrayList<DataCheckListener>();
	
	/**
	 * 
	 */
	public DataCheckService() {
		
	}
	
	public static void addListener(DataCheckListener dcl){
		dcls.add(dcl);
	}
	
	/**
	 * 检查数据，在所有数据加载完毕后调用
	 * @throws Exception 
	 */
	public static void check() throws Exception{
		for(DataCheckListener dcl:dcls){
			dcl.dataCheck();
		}
	}

}
