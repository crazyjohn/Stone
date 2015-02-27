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
import com.i4joy.akka.kok.camel.KOKEndpointManager;
import com.i4joy.util.Tools;

public class RandomNameWorker extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public static Props getProps() {
		return Props.create(RandomNameWorker.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {

		if (msg instanceof CamelMessage) {
			JSONObject responseJSon = new JSONObject();
			try {
				CamelMessage camelMessage = (CamelMessage) msg;
				Map<String, Object> map = camelMessage.getHeaders();
				String jsonStr = map.get("msg").toString();
				JSONObject json = JSONObject.fromObject(jsonStr);
				int sex = json.getInt("sex");
				String userName = json.getString("userName");
				String password = json.getString("password");
				if (LoginWorker.checkUserPassword(userName, password, mediator))// 帐号密码相同
				{
					String name = "";
					if(sex == 0)
					{
						name = KOKEndpointManager.girlNames.get(Tools.getRandomNum(0, KOKEndpointManager.girlNames.size()));
					}else
					{
						name = KOKEndpointManager.boyNames.get(Tools.getRandomNum(0, KOKEndpointManager.boyNames.size()));
					}
					responseJSon.put("retMsg", "成功！");
					responseJSon.put("name", name);
					responseJSon.put("retCode", 0);
				} else {
					responseJSon.put("retCode", 1);
					responseJSon.put("retMsg",LanguageProperties.getText("ZHMMCW"));
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

	public static String getRandomName(int sex, ActorRef mediator) throws Exception {
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish("random_name", new Byte((byte)sex)), timeout);
		String result = (String) Await.result(future, timeout.duration());
		return result;
	}

}
