package com.i4joy.akka.kok.camel.worker;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.i4joy.akka.kok.LanguageProperties;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_userInfoRegist;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.WQ_userInfoGreate;
import com.i4joy.akka.kok.db.rcache.RC_UserInfoTableService;
import com.i4joy.akka.kok.db.rw.RW_User_extensionTableService;
import com.i4joy.util.Tools;
import com.ump.model.DBCache;
import com.ump.model.User_extension;

public class QuickRegistWorker extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public static Props getProps() {
		return Props.create(QuickRegistWorker.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof CamelMessage) {
			JSONObject responseJSon = new JSONObject();
			try {
				String username = Tools.getNano(6);
				CamelMessage camelMessage = (CamelMessage) msg;
				Map<String, Object> map = camelMessage.getHeaders();
				String jsonStr = map.get("msg").toString();
				JSONObject requestJSON = JSONObject.fromObject(jsonStr);
				String clientId = requestJSON.getString("clientId");
				String channelId = requestJSON.getString("channelId");

				addUserExtension(channelId, clientId, username, mediator, getSelf());

				String password = Tools.getNano(6);
				RC_userInfoRegist.Builder builder = RC_userInfoRegist.newBuilder();
				builder.setPassword(password);
				builder.setUsername(username);
				Timeout timeout = new Timeout(Duration.create(5, "seconds"));
				Future<Object> future = Patterns.ask(mediator, new Publish(RC_UserInfoTableService.topic, builder.build()), timeout);
				WQ_userInfoGreate ret = (WQ_userInfoGreate) Await.result(future, timeout.duration());

				if (ret.getUserName().equals(username))// 注册成功
				{
					responseJSon.put("retMsg", LanguageProperties.getText("ZCCG"));
					responseJSon.put("retCode", 0);
					responseJSon.put("user", username);
					responseJSon.put("pwd", password);
				} else {
					responseJSon.put("retMsg", LanguageProperties.getText("ZHCF"));
					responseJSon.put("retCode", 1);
				}
				getSender().tell(responseJSon.toString(), getSelf());
			} catch (Exception e) {
				Tools.printError(e, logger, null);
				responseJSon.put("retCode", 1);
				responseJSon.put("retMsg", LanguageProperties.getText("CSCW"));
				getSender().tell(responseJSon.toString(), getSelf());
			}
		}
	}

	public static void addUserExtension(String channelId, String clientId, String username, ActorRef mediator, ActorRef self) {
		User_extension ue = new User_extension();
		ue.setAction(DBCache.INSERT);
		ue.setChannel(channelId);
		ue.setClient_id(clientId);
		ue.setClient_ip(0);
		ue.setCreate_date(new Date(System.currentTimeMillis()));
		ue.setDevice("222");
		ue.setEmail("333");
		ue.setLast_login_date(new Date(System.currentTimeMillis()));
		ue.setMobile_id(0);
		ue.setUsername(username);
		mediator.tell(new Publish(RW_User_extensionTableService.topic, ue), self);
	}

}
