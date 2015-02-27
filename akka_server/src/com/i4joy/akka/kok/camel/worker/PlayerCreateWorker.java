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
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_select;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGreate;
import com.i4joy.akka.kok.db.rcache.RC_PlayerInfoTableService;
import com.i4joy.akka.kok.king.DBMaster;
import com.i4joy.util.Tools;
import com.ump.model.DBCache;
import com.ump.model.Player;
import com.ump.model.Player_avatar;

public class PlayerCreateWorker extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public static Props getProps() {
		return Props.create(PlayerCreateWorker.class);
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
				String userName = json.getString("userName");
				String pwd = json.getString("pwd");
				String name = json.getString("name");
				String serverId = json.getString("serverId");
				int sex = json.getInt("sex");
				int bodyId = json.getInt("bodyId");
				int bodyTexId = json.getInt("bodyTexId");
				int headId = json.getInt("headId");
				int headTexId = json.getInt("headTexId");
				int faceId = json.getInt("faceId");
				int faceTexId = json.getInt("faceTexId");
				int clothId = json.getInt("clothId");
				int clothTexId = json.getInt("clothTexId");
				int weaponId = json.getInt("weaponId");
				int weaponTexId = json.getInt("weaponTexId");
				if (LoginWorker.checkUserPassword(userName, pwd, mediator)) {

					String dbName = getDBName(mediator).getDbName();
					if (dbName.equals("no")) {
						responseJSon.put("retCode", 1);
						responseJSon.put("retMsg", LanguageProperties.getText("WJCJSB"));
					} else {

						RC_playerInfoGreate.Builder builder = RC_playerInfoGreate.newBuilder();
						builder.setPlayerName(name);
						builder.setServerId(Integer.parseInt(serverId));
						builder.setUserName(userName);
						builder.setDbId(Integer.parseInt(dbName.substring("user_million".length(), dbName.length())));

						Timeout timeout = new Timeout(Duration.create(5, "seconds"));
						Future<Object> future = Patterns.ask(mediator, new Publish(RC_PlayerInfoTableService.topic, builder.build()), timeout);
						RC_playerInfoGreate playerInfo = (RC_playerInfoGreate) Await.result(future, timeout.duration());

						if (playerInfo.getCreateOk())// 玩家创建成功
						{
							Player_avatar playerAvatar = new Player_avatar();
							playerAvatar.setPlayer_id(playerInfo.getPlayerId());
							playerAvatar.setAction(DBCache.INSERT);
							playerAvatar.setBody_id((short) bodyId);
							playerAvatar.setBody_tex_id((short) bodyTexId);
							playerAvatar.setCloth_id((short) clothId);
							playerAvatar.setCloth_tex_id((short) clothTexId);
							playerAvatar.setHead_id((short) headId);
							playerAvatar.setHead_tex_id((short) headTexId);
							playerAvatar.setFace_id((short) faceId);
							playerAvatar.setFace_tex_id((short) faceTexId);
							playerAvatar.setSex((byte) sex);
							playerAvatar.setWeapon_id((short) weaponId);
							playerAvatar.setWeapon_tex_id((short) weaponTexId);
							mediator.tell(new Publish(dbName + playerAvatar.tableName, playerAvatar), getSelf());// 保存
																														// avatar

							Player player = new Player();
							player.setPlayer_id(playerInfo.getPlayerId());
							player.setPlayer_name(name);
							player.setAction(DBCache.INSERT);
							if(playerAvatar.getSex() == 0)
							{
								player.setBase_id(1000001);	
							}else
							{
								player.setBase_id(1000002);
							}
							
							mediator.tell(new Publish(dbName + player.tableName, player), getSelf());// 保存
																											// player
							responseJSon.put("retMsg", LanguageProperties.getText("WJCJCG"));
							responseJSon.put("retCode", 0);
							responseJSon.put("IP", TextProperties.getText("IOIP"));
							responseJSon.put("PORT", "8000");
							responseJSon.put("playerId", playerInfo.getPlayerId());
						} else {
							responseJSon.put("retCode", 1);
							responseJSon.put("retMsg", LanguageProperties.getText("WJCJSB"));
						}
					}

					getSender().tell(responseJSon.toString(), getSelf());
				}
			} catch (Exception e) {
				Tools.printError(e, logger, null);
				responseJSon.put("retCode", 1);
				responseJSon.put("retMsg", "参数异常！");
				getSender().tell(responseJSon.toString(), getSelf());
			}
		}
	}

	public static DB_select getDBName(ActorRef mediator) throws Exception {
		DB_select.Builder builder = DB_select.newBuilder();
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(DBMaster.topic, builder.build()), timeout);
		return (DB_select) Await.result(future, timeout.duration());
	}
}
