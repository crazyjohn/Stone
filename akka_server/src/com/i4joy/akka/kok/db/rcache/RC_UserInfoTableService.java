package com.i4joy.akka.kok.db.rcache;

import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.routing.RandomRouter;

import com.i4joy.akka.kok.LanguageProperties;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.WQ_userInfoGreate;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.WQ_userInfoUpdate;
import com.i4joy.akka.kok.db.wqueue.WQ_UserInfoTableService;
import com.ump.impl.JdbcUser_infoDAO;

public class RC_UserInfoTableService extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RC_UserInfoTableService.class, dataSource, DBName);
	}

	private static HashMap<String, String> user_password_cache = new HashMap<String, String>();
	private static JdbcUser_infoDAO dao;
	private final ActorRef userInfoGetWorker;
	private final ActorRef userInfoRegistWorker;
	private final ActorRef userInfoChangeWorker;
	private int nrOfWorkers = 16;// 初始路由工人数量
	public static final String topic = "user_info";// 表名字
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public RC_UserInfoTableService(DataSource dataSource, String DBName) {
		dao = new JdbcUser_infoDAO(dataSource);// 初始化处理DAO
		user_password_cache = dao.getALL();// 初始化
		userInfoGetWorker = getContext().actorOf(Props.create(UserInfoGetWorker.class).withRouter(new RandomRouter(nrOfWorkers)));// 多个处理
		userInfoRegistWorker = getContext().actorOf(Props.create(UserInfoRegistWorker.class));
		userInfoChangeWorker = getContext().actorOf(Props.create(UserInfoChangeWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof KOKDBPacket.RC_userInfoRegist) {// 收到帐号注册请求
			userInfoRegistWorker.tell(msg, getSender());
		} else if (msg instanceof KOKDBPacket.RC_userInfoGet) {// 收到帐号登录请求
			userInfoGetWorker.tell(msg, getSender());
		} else if (msg instanceof WQ_userInfoUpdate) {
			userInfoChangeWorker.tell(msg, getSender());
		}
	}

	public static class UserInfoRegistWorker extends UntypedActor {
		private HashMap<String, String> needSave = new HashMap<String, String>();// 等待注册的集合
		private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

		// 注册失败
		public void registFail(ActorRef sender, String userName, String password) {
			KOKDBPacket.WQ_userInfoGreate.Builder builder = KOKDBPacket.WQ_userInfoGreate.newBuilder();
			builder.setCreateOk(false);
			builder.setUserName(userName);
			builder.setPassword(password);
			sender.tell(builder.build(), getSelf());
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof KOKDBPacket.RC_userInfoRegist) {
				KOKDBPacket.RC_userInfoRegist regist = (KOKDBPacket.RC_userInfoRegist) msg;
				String userName = regist.getUsername();
				String password = regist.getPassword();
				if (user_password_cache.containsKey(userName))// 缓存里面已经有了
				{
					registFail(getSender(), userName, password);
				} else if (needSave.containsKey(userName))// 保存队列里已经有了
				{
					registFail(getSender(), userName, password);
				} else {
					needSave.put(userName, password);
					WQ_userInfoGreate.Builder create_builder = WQ_userInfoGreate.newBuilder();
					create_builder.setCreateOk(true);
					create_builder.setUserName(userName);
					create_builder.setPassword(password);
					mediator.tell(new Publish(WQ_UserInfoTableService.topic, create_builder.build()), getSelf());// 发送给wq
					getSender().tell(create_builder.build(), getSelf());
					user_password_cache.put(userName, password);// 添加到全局缓存
				}
			} else if (msg instanceof WQ_userInfoGreate)// 收到创建角色成功
			{
				WQ_userInfoGreate create = (WQ_userInfoGreate) msg;
				String username = create.getUserName();
				String password = create.getPassword();
				needSave.remove(username);// 从等待创建列表里删除
				// if(create.getCreateOk())
				// {
				// user_password_cache.put(username, password);//添加到全局缓存
				// }
			}
		}

	}

	public static class UserInfoGetWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof KOKDBPacket.RC_userInfoGet) {// 帐号登录请求
				KOKDBPacket.RC_userInfoGet login = (KOKDBPacket.RC_userInfoGet) msg;
				String userName = login.getUsername();
				String cachePassword = user_password_cache.get(userName);
				KOKDBPacket.RC_userInfoGet.Builder builder = KOKDBPacket.RC_userInfoGet.newBuilder();
				builder.setUsername(userName);
				if (cachePassword != null) {
					builder.setPassword(cachePassword);
				}
				getSender().tell(builder.build(), getSelf());
			}
		}

	}

	public static class UserInfoChangeWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof WQ_userInfoUpdate) {// 帐号登录请求
				WQ_userInfoUpdate login = (WQ_userInfoUpdate) msg;
				String userName = login.getUserName();
				String oldPassword = login.getOldPassword();
				String newPassword = login.getNewPassword();

				String cachePassword = user_password_cache.get(userName);
				if (cachePassword.equals(oldPassword)) {
					if (dao.upDate(userName, newPassword)) {
						getSender().tell(LanguageProperties.getText("XIUGAIMIMA"), getSender());
						user_password_cache.put(userName, newPassword);
					} else {
						getSender().tell(LanguageProperties.getText("MMCW"), getSender());
					}

				} else {
					getSender().tell(LanguageProperties.getText("MMCW"), getSender());
				}
			}
		}

	}

}