package com.i4joy.akka.kok.camel.worker;

import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.i4joy.akka.kok.LanguageProperties;
import com.i4joy.akka.kok.db.wqueue.WQ_ClientTableService;
import com.i4joy.util.Tools;
import com.ump.model.Client;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;

public class UpdateWorker extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public static Props getProps() {
		return Props.create(UpdateWorker.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof CamelMessage) {
			JSONObject responseJSon = new JSONObject();
			try {
				CamelMessage camelMessage = (CamelMessage) msg;
				Map<String, Object> map = camelMessage.getHeaders();
				String jsonStr = map.get("msg").toString();
				JSONObject requestJSON = JSONObject.fromObject(jsonStr);
				String version = requestJSON.getString("version");
				String clientId = requestJSON.getString("clientId");
				String device = requestJSON.getString("device");
				String channelId = requestJSON.getString("channelId");
				String sourceVersion = requestJSON.getString("sourceVersion");
				if (clientId.equals("-1")) {
					clientId = UUID.randomUUID().toString();
					Client client = new Client();
					client.setChannel_id(channelId);
					client.setClient_id(clientId);
					client.setDevice(device);
					client.setSourceVersion(sourceVersion);
					client.setVersion(version);
					mediator.tell(new Publish(WQ_ClientTableService.topic, client), getSelf());
				}
				
				responseJSon.put("clientId", clientId);
				responseJSon.put("newVersion", "xxx");
				responseJSon.put("notice", "xxx");
				responseJSon.put("isForceUpdate", "1");
				responseJSon.put("downloadUrl", "http");
				responseJSon.put("loginAdds", "192.168.0.118:80");
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
