package com.i4joy.akka.kok.monster;

import java.util.UUID;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.equipment.EquipmentTempletManager;
import com.i4joy.akka.kok.monster.item.ItemTempletManager;
import com.i4joy.akka.kok.monster.mercenary.MercenaryTempletManager;
import com.i4joy.akka.kok.monster.player.PlayerInitDataTempletManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MonsterStart {
	private static MonsterStart instance;
	private final ActorSystem system;
	private boolean noExcel = false;
	private boolean noDataFile=true;

	public MonsterStart(Address joinAddress) {
		Config config = ConfigFactory.load().getConfig("MONSTER");
		system = ActorSystem.create(Property.SYSTEMNAME, config);
		Cluster.get(system).join(joinAddress);// 系统加入集群
		String name = config.getString("akka.remote.netty.tcp.hostname") + ":" + config.getString("akka.remote.netty.tcp.port");// 获得monster的名字
		name.replace(".", "&");
		system.actorOf(PlayersGroup.props(name), UUID.randomUUID().toString());// 创建Monster
		if (!noExcel) {
			try {
				PlayerInitDataTempletManager pidtm = new PlayerInitDataTempletManager();
				pidtm.init();
				MercenaryTempletManager mtm = new MercenaryTempletManager();
				mtm.init();
				ItemTempletManager itm=new ItemTempletManager();
				itm.init();
				//TODO 数据不全，临时注释掉
//				PveTempletManager ptm=new PveTempletManager();
//				ptm.init();
				EquipmentTempletManager etm=new EquipmentTempletManager();
				etm.init();
				AmuletTempletManager atm=new AmuletTempletManager();
				atm.init();
				

				// 此函数必须在所有数据初始化完毕后调用，进行跨模块数据正确性的验证
				DataCheckService.check();
				if(!noDataFile){
					//此函数必须在校验完模块数据正确性后调用，生成客户端使用的protobuff数据文件
					ProtoBuffFileMakeService.makeFile();
				}
			} catch (Exception e) {
				e.printStackTrace();
				system.shutdown();
				throw new RuntimeException(e);
			}
		}

	}

	public static MonsterStart getInstance(Address joinAddress) {
		if (instance == null) {
			instance = new MonsterStart(joinAddress);
		}
		return instance;
	}
}
