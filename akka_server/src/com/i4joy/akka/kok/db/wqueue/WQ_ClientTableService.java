package com.i4joy.akka.kok.db.wqueue;

import java.util.ArrayList;
import java.util.Date;

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

import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcClientDAO;
import com.ump.model.Client;

public class WQ_ClientTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(WQ_ClientTableService.class, dataSource, DBName);
	}

	private final Cancellable heart;// 心跳 清理缓存中超时的
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	public final static String topic = "WQclient";// 表名字
	private JdbcClientDAO dao;
	private ArrayList<Client> clientList = new ArrayList<Client>();

	public WQ_ClientTableService(DataSource dataSource, String DBName) {
		dao = new JdbcClientDAO(dataSource);
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
		if (msg instanceof Client) {
			Client client = (Client) msg;
			client.setCreate_date(new Date(System.currentTimeMillis()));
			clientList.add(client);
		} else if (msg instanceof Heart) {
			int len = clientList.size();
			if (len > 0) {
				Client[] clients = new Client[len];
				clientList.toArray(clients);
				dao.addList(clients);
				clientList.clear();
			}
		}
	}

}
