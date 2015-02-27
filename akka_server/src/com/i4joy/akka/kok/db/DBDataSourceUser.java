package com.i4joy.akka.kok.db;

import static akka.actor.SupervisorStrategy.restart;

import javax.sql.DataSource;

import scala.concurrent.duration.Duration;
import worker.Behavior;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.SupervisorStrategy.Directive;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.io.TcpMessage;
import akka.japi.Function;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_heart;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_regist;
import com.i4joy.akka.kok.db.rw.RW_AmuletTableService;
import com.i4joy.akka.kok.db.rw.RW_Amulet_extraTableService;
import com.i4joy.akka.kok.db.rw.RW_ChatsTableService;
import com.i4joy.akka.kok.db.rw.RW_EquipmentTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_amulets_debrisTableService;
import com.i4joy.akka.kok.db.rw.RW_Equipment_extraTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_mercenary_giftsTableService;
import com.i4joy.akka.kok.db.rw.RW_MercenaryTableService;
import com.i4joy.akka.kok.db.rw.RW_Mercenary_extraTableService;
import com.i4joy.akka.kok.db.rw.RW_PlayerTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_activity_battleTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_amuletsTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_avatarTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_elite_battleTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_emailsTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_equipment_debrisTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_equipmentsTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_friendsTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_friends_giveTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_mercenary_debirsTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_giftTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_gift_checkinTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_gift_kaifuTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_gift_levelTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_goldTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_itemsTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_mercenary_recordTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_mercenarysTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_nomal_battleTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_recruitTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_starTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_teamTableService;
import com.i4joy.akka.kok.db.rw.RW_Player_tianmingTableService;
import com.i4joy.akka.kok.db.rw.RW_TeamTableService;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcAmuletDAO;
import com.ump.impl.JdbcEquipmentDAO;
import com.ump.impl.JdbcMercenaryDAO;

public class DBDataSourceUser extends UntypedActor {

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(DBDataSourceUser.class, dataSource, DBName);
	}

	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	public static DataSource dataSource;// 数据源
	private final String DBName;// 数据库名字
	private final ActorRef rw_amulet_extra;
	private final ActorRef rw_amulet;
	private final ActorRef rw_chats;
	private final ActorRef rw_player_amulet_debris;
	private final ActorRef rw_equipment_extra;
	private final ActorRef rw_equipment;
	private final ActorRef rw_item;
	private final ActorRef rw_mercenary_extra;
	private final ActorRef rw_mercenary;
	private final ActorRef rw_player_activity_battle;
	private final ActorRef rw_player_amulets;
	private final ActorRef rw_player_elite_battle;
	private final ActorRef rw_player_emails;
	private final ActorRef rw_player_equipment_debris;
	private final ActorRef rw_player_equipments;
	private final ActorRef rw_player_friends_give;
	private final ActorRef rw_player_friends;
	private final ActorRef rw_player_ghosts;
	private final ActorRef rw_player_gift_checkin;
	private final ActorRef rw_player_gift_kaifu;
	private final ActorRef rw_player_gift_level;
	private final ActorRef rw_player_gift;
	private final ActorRef rw_player_gold;
	private final ActorRef rw_player_items;
	private final ActorRef rw_player_mercenarys;
	private final ActorRef rw_player_normal_battle;
	private final ActorRef rw_player_recurit;
	private final ActorRef rw_player_star;
	private final ActorRef rw_player_team;
	private final ActorRef rw_player_tianming;
	private final ActorRef rw_team;
	private final ActorRef rw_player_avatar;
	private final ActorRef rw_player_mercenary_record;

	private final ActorRef rw_player;// 用户信息表缓存

	private Cancellable heart;// 心跳 清理缓存中超时的

	public DBDataSourceUser(DataSource dataSource, String DBName) {
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName, getSelf()), getSelf());
		DBDataSourceUser.dataSource = dataSource;
		this.DBName = DBName;
		rw_player = getContext().actorOf(RW_PlayerTableService.props(dataSource, DBName));
		rw_amulet_extra = getContext().actorOf(RW_Amulet_extraTableService.props(dataSource, DBName));
		rw_amulet = getContext().actorOf(RW_AmuletTableService.props(dataSource, DBName));
		rw_chats = getContext().actorOf(RW_ChatsTableService.props(dataSource, DBName));
		rw_player_amulet_debris = getContext().actorOf(RW_Player_amulets_debrisTableService.props(dataSource, DBName));
		rw_equipment_extra = getContext().actorOf(RW_Equipment_extraTableService.props(dataSource, DBName));
		rw_equipment = getContext().actorOf(RW_EquipmentTableService.props(dataSource, DBName));
		rw_item = getContext().actorOf(RW_Player_mercenary_giftsTableService.props(dataSource, DBName));
		rw_mercenary_extra = getContext().actorOf(RW_Mercenary_extraTableService.props(dataSource, DBName));
		rw_mercenary = getContext().actorOf(RW_MercenaryTableService.props(dataSource, DBName));
		rw_player_activity_battle = getContext().actorOf(RW_Player_activity_battleTableService.props(dataSource, DBName));
		rw_player_amulets = getContext().actorOf(RW_Player_amuletsTableService.props(dataSource, DBName));
		rw_player_elite_battle = getContext().actorOf(RW_Player_elite_battleTableService.props(dataSource, DBName));
		rw_player_emails = getContext().actorOf(RW_Player_emailsTableService.props(dataSource, DBName));
		rw_player_equipment_debris = getContext().actorOf(RW_Player_equipment_debrisTableService.props(dataSource, DBName));
		rw_player_equipments = getContext().actorOf(RW_Player_equipmentsTableService.props(dataSource, DBName));
		rw_player_friends_give = getContext().actorOf(RW_Player_friends_giveTableService.props(dataSource, DBName));
		rw_player_friends = getContext().actorOf(RW_Player_friendsTableService.props(dataSource, DBName));
		rw_player_ghosts = getContext().actorOf(RW_Player_mercenary_debirsTableService.props(dataSource, DBName));
		rw_player_gift_checkin = getContext().actorOf(RW_Player_gift_checkinTableService.props(dataSource, DBName));
		rw_player_gift_kaifu = getContext().actorOf(RW_Player_gift_kaifuTableService.props(dataSource, DBName));
		rw_player_gift_level = getContext().actorOf(RW_Player_gift_levelTableService.props(dataSource, DBName));
		rw_player_gift = getContext().actorOf(RW_Player_giftTableService.props(dataSource, DBName));
		rw_player_gold = getContext().actorOf(RW_Player_goldTableService.props(dataSource, DBName));
		rw_player_items = getContext().actorOf(RW_Player_itemsTableService.props(dataSource, DBName));
		rw_player_mercenarys = getContext().actorOf(RW_Player_mercenarysTableService.props(dataSource, DBName));
		rw_player_normal_battle = getContext().actorOf(RW_Player_nomal_battleTableService.props(dataSource, DBName));
		rw_player_recurit = getContext().actorOf(RW_Player_recruitTableService.props(dataSource, DBName));
		rw_player_star = getContext().actorOf(RW_Player_starTableService.props(dataSource, DBName));
		rw_player_team = getContext().actorOf(RW_Player_teamTableService.props(dataSource, DBName));
		rw_player_tianming = getContext().actorOf(RW_Player_tianmingTableService.props(dataSource, DBName));
		rw_team = getContext().actorOf(RW_TeamTableService.props(dataSource, DBName));
		rw_player_avatar = getContext().actorOf(RW_Player_avatarTableService.props(dataSource, DBName));
		rw_player_mercenary_record = getContext().actorOf(RW_Player_mercenary_recordTableService.props(dataSource, DBName));
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {

	}
	
	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("10 second"), new Function<Throwable, Directive>() {
		public Directive apply(Throwable t) {
			return restart();// 如果处理异常直接重启
		}
	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	// 启动注册
	private final Behavior doRegist = new Behavior() {
		public void apply(Object msg) {
			if (msg instanceof Heart) {
				DB_regist.Builder builder = DB_regist.newBuilder();
				builder.setDbName(DBName);
				builder.setMercenaryMaxId(JdbcMercenaryDAO.getInstance().getMaxId());
				builder.setEquipmentMaxId(JdbcEquipmentDAO.getInstance().getMaxId());
				builder.setAmuletMaxId(JdbcAmuletDAO.getInstance().getMaxId());
				mediator.tell(new Publish("DBMaster", builder.build()), getSelf());// 发送给集群中的Player
			} else if (msg instanceof DB_regist) {
				getContext().become(doHeart);
			}
		}
	};

	// 心跳
	private final Behavior doHeart = new Behavior() {
		public void apply(Object msg) {
			if (msg instanceof DB_heart) {
				DB_heart heart = (DB_heart) msg;
				DB_heart.Builder builder = DB_heart.newBuilder();
				builder.setDbName(DBName);
				builder.setPingTime(System.currentTimeMillis() - heart.getPingTime());
				builder.setPlayersNum(0);
				builder.setIndex(heart.getIndex());
				getSender().tell(builder.build(), getSender());
			}
		}
	};

	{
		getContext().become(doRegist);
	}

}
