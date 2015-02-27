package com.i4joy.akka.kok.db.rw;

import java.util.ArrayList;

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
import com.ump.impl.JdbcChatsDAO;
import com.ump.model.Chats;

public class RW_ChatsTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_ChatsTableService.class, dataSource, DBName);
	}

	private ArrayList<Chats> createList = new ArrayList<Chats>();
	private Cancellable heart;// 心跳 清理缓存中超时的
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private final String tableName = "chats";// 表名字
	private String DBName;

	public RW_ChatsTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + "#" + tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Chats) {
			Chats chats = (Chats) msg;
			createList.add(chats);
		} else if (msg instanceof Heart) {
			JdbcChatsDAO.getInstance().addList(createList);
			createList.clear();
		}
	}

}
