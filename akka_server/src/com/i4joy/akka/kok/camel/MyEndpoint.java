package com.i4joy.akka.kok.camel;

import static akka.actor.SupervisorStrategy.restart;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.japi.Function;
import akka.routing.RoundRobinRouter;

import com.i4joy.akka.kok.LanguageProperties;
import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.camel.worker.ChangePWDWorker;
import com.i4joy.akka.kok.camel.worker.LoginWorker;
import com.i4joy.akka.kok.camel.worker.PlayerCreateWorker;
import com.i4joy.akka.kok.camel.worker.PlayerLoginWorker;
import com.i4joy.akka.kok.camel.worker.QuickRegistWorker;
import com.i4joy.akka.kok.camel.worker.RandomNameWorker;
import com.i4joy.akka.kok.camel.worker.RegistWorker;
import com.i4joy.akka.kok.camel.worker.UpdateWorker;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_isAllReady;
import com.i4joy.util.Tools;

/**
 * 
 * @author DongLei
 * 
 */
public class MyEndpoint extends UntypedConsumerActor {
	public static boolean isAllReady = false;
	protected final Log logger = LogFactory.getLog(getClass());
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	public static final String LOGIN = "doLogin";
	public static final String REGISTE = "doReg";
	public static final String PLAYERCREATE = "doCreatPlayer";
	public static final String PLAYERLOGIN = "playerLogin";
	public static final String UPDATE = "doUpdate";
	public static final String QUICKREGIST = "doQuickReg";
	public static final String CHANGEPWD = "doCPwd";
	public static final String GETRANDOMNAME = "doGetName";
	public static final String topic = "MyEndpoint";

	public static Props getProps(String url, String type) {
		return Props.create(MyEndpoint.class, url, type);
	}

	private final String uri;// 地址
	private final ActorRef routers;// 处理路由

	public MyEndpoint(String uri, String type) {
		this.uri = uri;
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
		if (type.equals(LOGIN)) {
			routers = getContext().actorOf(LoginWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else if (type.equals(REGISTE)) {
			routers = getContext().actorOf(RegistWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else if (type.equals(PLAYERCREATE)) {
			routers = getContext().actorOf(PlayerCreateWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else if (type.equals(UPDATE)) {
			routers = getContext().actorOf(UpdateWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else if (type.equals(QUICKREGIST)) {
			routers = getContext().actorOf(QuickRegistWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else if (type.equals(CHANGEPWD)) {
			routers = getContext().actorOf(ChangePWDWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else if (type.equals(GETRANDOMNAME)) {
			routers = getContext().actorOf(RandomNameWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		} else {
			routers = getContext().actorOf(PlayerLoginWorker.getProps().withRouter(new RoundRobinRouter(Property.EndpointNum)));
		}
	}

	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("10 second"), new Function<Throwable, Directive>() {
		public Directive apply(Throwable t) {
			return restart();// 如果处理异常直接重启
		}
	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	@Override
	public String getEndpointUri() {
		return uri;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof CamelMessage) {// camel message 通知
			if (isAllReady) {
				CamelMessage camelMessage = (CamelMessage) message;
				routers.tell(camelMessage, getSender());// 发给 处理路由
			} else {
				JSONObject responseJSon = new JSONObject();
				responseJSon.put("retCode", 1);
				responseJSon.put("retMsg", LanguageProperties.getText("WEIHU"));
				getSender().tell(responseJSon.toString(), getSelf());
			}
		} else if (message instanceof DB_isAllReady) {
			DB_isAllReady db = (DB_isAllReady) message;
			isAllReady = db.getIsAllReady();
			Tools.printlnInfo("MyEndpoint DB allReady", logger);
		} else {
			unhandled(message);
		}
	}

}
