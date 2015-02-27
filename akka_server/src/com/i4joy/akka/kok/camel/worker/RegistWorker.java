package com.i4joy.akka.kok.camel.worker;

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
import com.i4joy.util.Tools;

public class RegistWorker extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props getProps() {
		return Props.create(RegistWorker.class);
	}

	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	@Override
	public void onReceive(Object msg) throws Exception {

		if (msg instanceof CamelMessage) {
			JSONObject responseJSon = new JSONObject();
			try {
				CamelMessage camelMessage = (CamelMessage) msg;
				Map<String, Object> map = camelMessage.getHeaders();
				String jsonStr = map.get("msg").toString();
				JSONObject requestJSON = JSONObject.fromObject(jsonStr);
				String password = requestJSON.getString("password");
				String username = requestJSON.getString("userName");
				String clientId = requestJSON.getString("clientId");
				String channelId = requestJSON.getString("channelId");
				
				RC_userInfoRegist.Builder builder = RC_userInfoRegist.newBuilder();
				builder.setPassword(password);
				builder.setUsername(username);
				Timeout timeout = new Timeout(Duration.create(5, "seconds"));
				Future<Object> future = Patterns.ask(mediator, new Publish(RC_UserInfoTableService.topic, builder.build()), timeout);
				WQ_userInfoGreate ret = (WQ_userInfoGreate) Await.result(future, timeout.duration());

				if (ret.getCreateOk() && ret.getUserName().equals(username))// 注册成功
				{
					responseJSon.put("retMsg",LanguageProperties.getText("ZCCG"));
					responseJSon.put("retCode", 0);
					QuickRegistWorker.addUserExtension(channelId, clientId, username, mediator, getSelf());
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

}
