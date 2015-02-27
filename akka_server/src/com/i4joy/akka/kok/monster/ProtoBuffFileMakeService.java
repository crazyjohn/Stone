/**
 * 
 */
package com.i4joy.akka.kok.monster;

import java.util.ArrayList;
import java.util.List;

/**
 * 将数据文件生成客户端使用的protobuff数据文件
 * 数据模板管理器应该注册到此service中
 * @author Administrator
 *
 */
public class ProtoBuffFileMakeService {
	
	private static List<ProtoBuffFileMaker> pbfms=new ArrayList<ProtoBuffFileMaker>();

	/**
	 * 
	 */
	public ProtoBuffFileMakeService() {
		
	}
	
	public static void addMaker(ProtoBuffFileMaker pbm){
		pbfms.add(pbm);
	}
	
	public static void makeFile() throws Exception{
		for(ProtoBuffFileMaker pbfm:pbfms){
			pbfm.makeFile();
		}
	}

}
