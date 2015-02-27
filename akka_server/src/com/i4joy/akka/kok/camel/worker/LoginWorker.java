package com.i4joy.akka.kok.camel.worker;

import java.util.Map;

import net.sf.json.JSONArray;
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
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGet;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_servers;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_userInfoGet;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_userPlayersGet;
import com.i4joy.akka.kok.db.rcache.RC_PlayerInfoTableService;
import com.i4joy.akka.kok.db.rcache.RC_ServersTableService;
import com.i4joy.akka.kok.db.rcache.RC_UserInfoTableService;
import com.i4joy.util.Tools;

public class LoginWorker extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public static Props getProps() {
		return Props.create(LoginWorker.class);
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
				String password = json.getString("password");
				String username = json.getString("userName");
				String channelId = json.getString("channelId");
				if (checkUserPassword(username, password, mediator))// 帐号密码相同
				{
					responseJSon.put("retMsg", LanguageProperties.getText("DLCG"));
					responseJSon.put("retCode", 0);
					JSONObject allPlayers = LoginWorker.getUserPlayers(username, mediator);

					JSONArray o = (JSONArray) LoginWorker.getServers(mediator).get("servers");
					// JSONObject[] serversJSON = (JSONObject[])
					// LoginWorker.getServers(mediator).get("servers");
					JSONObject[] players = new JSONObject[0];
					if (allPlayers.containsKey("players")) {
						JSONArray playersJSON = (JSONArray) LoginWorker.getUserPlayers(username, mediator).get("players");
						players = new JSONObject[playersJSON.size()];
						for (int i = 0; i < playersJSON.size(); i++) {
							JSONObject jp = (JSONObject) playersJSON.get(i);
							for (int j = 0; j < o.size(); j++) {
								JSONObject s = (JSONObject) o.get(j);
								if (s.getString("id").equals(jp.getString("serverId"))) {
									players[i] = new JSONObject();
									players[i].put("name", s.getString("name"));
									players[i].put("id", s.getString("id"));
									players[i].put("status", s.getString("status"));
									players[i].put("playerName", jp.getString("name"));
									players[i].put("playerId", jp.getString("id"));
									break;
								}
							}
						}
					}
					responseJSon.put("servers", o);
					responseJSon.put("players", players);
				} else {
					responseJSon.put("retCode", 1);
					responseJSon.put("retMsg", LanguageProperties.getText("ZHMMCW"));
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

	public static boolean checkUserPassword(String username, String password, ActorRef mediator) throws Exception {
		RC_userInfoGet.Builder rcBuilder = RC_userInfoGet.newBuilder();
		rcBuilder.setPassword(password);
		rcBuilder.setUsername(username);
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(RC_UserInfoTableService.topic, rcBuilder.build()), timeout);
		RC_userInfoGet userInfo = (RC_userInfoGet) Await.result(future, timeout.duration());
		return userInfo.getPassword().equals(password) && userInfo.getUsername().equals(username);
	}

	public static RC_playerInfoGet getPlayerInfo(String playerName, ActorRef mediator) throws Exception {
		RC_playerInfoGet.Builder playerInfoBuilder = RC_playerInfoGet.newBuilder();
		playerInfoBuilder.setPlayerName(playerName);
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(RC_PlayerInfoTableService.topic, playerInfoBuilder.build()), timeout);
		return (RC_playerInfoGet) Await.result(future, timeout.duration());
	}

	public static JSONObject getServers(ActorRef mediator) throws Exception {
		RC_servers.Builder builder = RC_servers.newBuilder();
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(RC_ServersTableService.topic, builder.build()), timeout);
		RC_servers ret = (RC_servers) Await.result(future, timeout.duration());
		if (ret.getServersJson().length() > 0) {
			return JSONObject.fromObject(ret.getServersJson());
		}
		return new JSONObject();
	}

	public static JSONObject getUserPlayers(String username, ActorRef mediator) throws Exception {
		RC_userPlayersGet.Builder builder = RC_userPlayersGet.newBuilder();
		builder.setUsername(username);
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(RC_PlayerInfoTableService.topic, builder.build()), timeout);
		RC_userPlayersGet ret = (RC_userPlayersGet) Await.result(future, timeout.duration());
		if (ret.getUserPlayers().length() > 0) {
			return JSONObject.fromObject(ret.getUserPlayers());
		}
		return new JSONObject();
	}
}
