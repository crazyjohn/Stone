package com.i4joy.akka.kok.db.wqueue;

import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.WQ_userInfoGreate;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcUser_infoDAO;
import com.ump.model.User_info;

public class WQ_UserInfoTableService extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(WQ_UserInfoTableService.class, dataSource, DBName);
	}

	private static HashMap<String, User_info> createHM = new HashMap<String, User_info>();
	private final Cancellable heart;// 心跳 清理缓存中超时的
	private static JdbcUser_infoDAO dao;
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	public static  final String topic = "WQuser_info";// 表名字
	
	public WQ_UserInfoTableService(DataSource dataSource, String DBName) {
		dao = new JdbcUser_infoDAO(dataSource);// 初始化处理DAO
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof WQ_userInfoGreate) {//收到创建帐号请求
			WQ_userInfoGreate create = (WQ_userInfoGreate) msg;
			User_info ui = new User_info();
			ui.setPassword(create.getPassword());
			ui.setUsername(create.getUserName());
			WQ_userInfoGreate.Builder builder = WQ_userInfoGreate.newBuilder();
			builder.setPassword(create.getPassword());
			builder.setUserName(create.getUserName());

			if (!createHM.containsKey(create.getUserName())) {
				builder.setCreateOk(true);
				createHM.put(ui.getUsername(), ui);
			} else {
				builder.setCreateOk(false);
			}
			getSender().tell(builder.build(), getSelf());//返回创建成功
		}
		else if (msg instanceof Heart) {//心跳批量创建和更新
			int len = createHM.size();
			if(len > 0)
			{
				User_info[] uis = new User_info[len];
				createHM.values().toArray(uis);
				dao.addList(uis);
				createHM.clear();
			}
			
		}
	}
}
