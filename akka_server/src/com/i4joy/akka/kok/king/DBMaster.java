package com.i4joy.akka.kok.king;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.routing.RandomRouter;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.camel.KOKEndpointManager;
import com.i4joy.akka.kok.camel.MyEndpoint;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_getAmuletId;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_getEquipmentId;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_getMercenaryId;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_heart;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_isAllReady;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_regist;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_select;
import com.i4joy.akka.kok.io.TcpServer;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.i4joy.akka.kok.protocol.PCreateDBDateSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ump.impl.JdbcDb_configDAO;
import com.ump.model.Db_config;

public class DBMaster extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private static HashMap<String, DBHeart> hm = new HashMap<String, DBHeart>();
	private ActorRef heartWorker;
	private ActorRef selectWorker;
	private ActorRef registWorker;
	private ActorRef getMercenaryId;
	private ActorRef getEquipmentId;
	private ActorRef getAmuletId;
	private Cancellable heart;// 心跳 清理缓存中超时的
	private int nrOfWorkers = 16;// 初始路由工人数量
	private int index = 0;// 优先级
	public static final int heartTime = 5;
	public static final String topic = "DBMaster";

	private static AtomicLong mercenaryMaxId = new AtomicLong(0);
	private static AtomicLong equipmentMaxId = new AtomicLong(0);
	private static AtomicLong amuletMaxId = new AtomicLong(0);
	private ArrayList<Db_config> dbList;
	private boolean isDBAllReady = false;

	public static Props getProps() {
		return Props.create(DBMaster.class);
	}

	public DBMaster() {
		PCreateDBDateSource pdbds_config = new PCreateDBDateSource(TextProperties.getText("DB_USER"), TextProperties.getText("DB_PASSWORD"), TextProperties.getText("DB_ADDRESS") + TextProperties.getText("DB_NAME_CONFIG") + "?rewriteBatchedStatements=true", TextProperties.getText("DB_DRIVER"),
				Integer.parseInt(TextProperties.getText("DB_INITPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MINPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MAXPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MAXSTATEMENTS")), Integer.parseInt(TextProperties
						.getText("DB_MAXIDLETIME")), TextProperties.getText("DB_NAME_CONFIG"));
		ComboPooledDataSource ds = KOKEndpointManager.getDBResource(pdbds_config);
		JdbcDb_configDAO dao = new JdbcDb_configDAO(ds);
		dbList = dao.getALL();
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
		heartWorker = getContext().actorOf(Props.create(HeartWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		selectWorker = getContext().actorOf(Props.create(SelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		registWorker = getContext().actorOf(Props.create(RegistWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		getMercenaryId = getContext().actorOf(Props.create(GetMercenaryId.class).withRouter(new RandomRouter(nrOfWorkers)));
		getAmuletId = getContext().actorOf(Props.create(GetAmuletId.class).withRouter(new RandomRouter(nrOfWorkers)));
		getEquipmentId = getContext().actorOf(Props.create(GetEquipmentId.class).withRouter(new RandomRouter(nrOfWorkers)));
		heart = getContext().system().scheduler().schedule(Duration.create(heartTime, "seconds"), Duration.create(heartTime, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());

	}

	public boolean findDBName(String name) {
		Db_config config;
		for (int i = 0; i < dbList.size(); i++) {
			config = dbList.get(i);
			if (config.getDb_name().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void preStart() throws Exception {
		super.preStart();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DB_regist) {// 数据库注册
			registWorker.tell(msg, getSender());
		} else if (msg instanceof DB_heart)// 收到数据库心跳
		{
			heartWorker.tell(msg, getSender());
		} else if (msg instanceof DB_select)// 路由数据库名字
		{
			selectWorker.tell(msg, getSender());
		} else if (msg instanceof DB_getMercenaryId) {// 获得侠客自增ID
			getMercenaryId.tell(msg, getSender());
		} else if (msg instanceof DB_getEquipmentId) {// 获得装备自增ID
			getEquipmentId.tell(msg, getSender());
		} else if (msg instanceof DB_getAmuletId) {// 获得装备自增ID
			getAmuletId.tell(msg, getSender());
		} else if (msg instanceof Heart) {
			index++;
			int len = hm.size();
			if (len > 0) {
				String[] keys = new String[len];
				hm.keySet().toArray(keys);
				boolean allReady = true;
				for (String string : keys) {
					DB_heart.Builder builder = DB_heart.newBuilder();
					builder.setDbName(string);
					builder.setPingTime(System.currentTimeMillis());
					builder.setPlayersNum(0);
					builder.setIndex(index);
					mediator.tell(new Publish(string, builder.build()), getSelf());// 发送给集群中的Player
					if (allReady) {
						allReady = findDBName(string);
					}
				}

				if (!isDBAllReady && allReady) {
					Thread.sleep(10000);
					isDBAllReady = allReady;
					DB_isAllReady.Builder builder = DB_isAllReady.newBuilder();
					builder.setIsAllReady(allReady);
					DB_isAllReady ready = builder.build();
					mediator.tell(new Publish(MyEndpoint.topic, ready), getSelf());// 发送给集群中的Player
					mediator.tell(new Publish(TcpServer.topic, ready), getSelf());// 发送给集群中的Player
				}
			}

		}
	}

	public static class RegistWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DB_regist)// 数据库注册
			{
				DB_regist regist = (DB_regist) msg;
				String db_name = regist.getDbName();
				DBHeart heart = new DBHeart();
				heart.dbName = db_name;
				hm.put(db_name, heart);
				if (mercenaryMaxId.get() < regist.getMercenaryMaxId()) {
					mercenaryMaxId = new AtomicLong(regist.getMercenaryMaxId());
				}
				if (equipmentMaxId.get() < regist.getEquipmentMaxId()) {
					equipmentMaxId = new AtomicLong(regist.getEquipmentMaxId());
				}
				if (amuletMaxId.get() < regist.getAmuletMaxId()) {
					amuletMaxId = new AtomicLong(regist.getAmuletMaxId());
				}
				getSender().tell(msg, getSelf());
			}
		}

	}

	public static class HeartWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DB_heart)// 收到数据库心跳
			{
				DB_heart dbHeart = (DB_heart) msg;
				DBHeart heart = new DBHeart();
				heart.dbName = dbHeart.getDbName();
				heart.pingTime = dbHeart.getPingTime();
				heart.playersNum = dbHeart.getPlayersNum();
				heart.index = dbHeart.getIndex();
				heart.lastTime = System.currentTimeMillis();
				hm.put(heart.dbName, heart);
			}
		}

	}

	public static class SelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DB_select)// 收到数据库心跳
			{
				int len = hm.size();
				DB_select.Builder builder = DB_select.newBuilder();
				if (len != 0) {
					DBHeart[] hearts = new DBHeart[len];
					hm.values().toArray(hearts);
					Arrays.sort(hearts);
					builder.setDbName(hearts[0].dbName);
				} else {
					builder.setDbName("no");
				}
				getSender().tell(builder.build(), getSelf());
			}
		}

	}

	public static class GetMercenaryId extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DB_getMercenaryId) {
				long mercenaryId = mercenaryMaxId.incrementAndGet();
				DB_getMercenaryId.Builder builder = DB_getMercenaryId.newBuilder();
				builder.setMercenaryId(mercenaryId);
				getSender().tell(builder.build(), getSelf());
			}
		}

	}

	public static class GetEquipmentId extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DB_getEquipmentId) {
				long id = equipmentMaxId.incrementAndGet();
				DB_getEquipmentId.Builder builder = DB_getEquipmentId.newBuilder();
				builder.setEquipmentId(id);
				getSender().tell(builder.build(), getSelf());
			}
		}
	}

	public static class GetAmuletId extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DB_getAmuletId) {
				long id = amuletMaxId.incrementAndGet();
				DB_getAmuletId.Builder builder = DB_getAmuletId.newBuilder();
				builder.setAmuletId(id);
				getSender().tell(builder.build(), getSelf());
			}
		}
	}

}

class DBHeart implements Comparable<DBHeart> {
	public String dbName;
	public long pingTime;
	public int playersNum;
	public int index;
	public long lastTime;

	@Override
	public int compareTo(DBHeart o) {
		return o.lastTime + DBMaster.heartTime * 1000 * o.index > lastTime + DBMaster.heartTime * 1000 * index ? 1 : -1;
	}

}
