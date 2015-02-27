package com.i4joy.akka.kok.db.rcache;

import java.util.ArrayList;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.routing.RandomRouter;

import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_servers;
import com.ump.impl.JdbcServersDAO;
import com.ump.model.Servers;

public class RC_ServersTableService extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RC_ServersTableService.class, dataSource, DBName);
	}

	public static final String topic = "servers";// 表名字
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private static JdbcServersDAO dao;
	private final Cancellable heart;// 心跳 清理缓存中超时的
	private static JSONObject json = new JSONObject();
	private ActorRef serversWorker;
	private int nrOfWorkers = 16;// 初始路由工人数量

	public RC_ServersTableService(DataSource dataSource, String DBName) {
		dao = new JdbcServersDAO(dataSource);// 初始化处理DAO
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), "heart", getContext().dispatcher(), getSelf());
		serversWorker = getContext().actorOf(Props.create(ServersWorker.class).withRouter(new RandomRouter(nrOfWorkers)));// 多个处理
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof RC_servers) {
			serversWorker.tell(msg, getSender());
		} else if (msg instanceof String) {
			ArrayList<Servers> list = dao.getList();
			JSONObject[] jsons = new JSONObject[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Servers servers = list.get(i);
				jsons[i] = new JSONObject();
				jsons[i].put("id", servers.getId());
				jsons[i].put("num", servers.getOnline_num());
				jsons[i].put("name", servers.getServer_name());
				jsons[i].put("status", servers.getStatus());
			}
			json.put("servers", jsons);
		}
	}

	public static class ServersWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof RC_servers) {
				RC_servers.Builder servers = RC_servers.newBuilder();
				servers.setServersJson(json.toString());
				getSender().tell(servers.build(), getSelf());
			}
		}

	}

}
