package com.i4joy.akka.kok.db;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import javax.sql.DataSource;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;

import com.i4joy.akka.kok.db.rcache.RC_PlayerInfoTableService;
import com.i4joy.akka.kok.db.rcache.RC_ServersTableService;
import com.i4joy.akka.kok.db.rcache.RC_UserInfoTableService;
import com.i4joy.akka.kok.db.rw.RW_User_extensionTableService;
import com.i4joy.akka.kok.db.wqueue.WQ_ClientTableService;
import com.i4joy.akka.kok.db.wqueue.WQ_PlayerInfoTableService;
import com.i4joy.akka.kok.db.wqueue.WQ_UserInfoTableService;

public class DBDataSourceConfig extends UntypedActor {

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(DBDataSourceConfig.class, dataSource, DBName);
	}

	private final ActorRef rc_ServersTableService;// 用户信息表缓存

	private final ActorRef rc_UserInfoTableService;// 用户信息表缓存
	private final ActorRef rc_PlayerInfoTableService;// 玩家信息表缓存

	private final ActorRef wq_UserInfoTableService;// 用户信息写队列
	private final ActorRef wq_PlayerInfoTableService;// 玩家信息写队列
	private final ActorRef wq_ClientTableService;// 玩家安装游戏情况

	private final ActorRef rw_User_extensionTableService;// 用户扩展信息

	public DBDataSourceConfig(DataSource dataSource, String DBName) {

		rc_ServersTableService = getContext().actorOf(RC_ServersTableService.props(dataSource, DBName));// 创建用户信息表缓存

		rc_UserInfoTableService = getContext().actorOf(RC_UserInfoTableService.props(dataSource, DBName));// 创建用户信息表缓存
		rc_PlayerInfoTableService = getContext().actorOf(RC_PlayerInfoTableService.props(dataSource, DBName));

		wq_UserInfoTableService = getContext().actorOf(WQ_UserInfoTableService.props(dataSource, DBName));
		wq_PlayerInfoTableService = getContext().actorOf(WQ_PlayerInfoTableService.props(dataSource, DBName));
		wq_ClientTableService = getContext().actorOf(WQ_ClientTableService.props(dataSource, DBName));
		rw_User_extensionTableService = getContext().actorOf(RW_User_extensionTableService.props(dataSource, DBName));
	}

	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("10 second"), new Function<Throwable, Directive>() {
		public Directive apply(Throwable t) {
			if (t instanceof ArithmeticException) {
				return resume();
			} else if (t instanceof NullPointerException) {
				return restart();
			} else if (t instanceof IllegalArgumentException) {
				return stop();
			} else {
				return escalate();
			}
		}
	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {

	}

}
