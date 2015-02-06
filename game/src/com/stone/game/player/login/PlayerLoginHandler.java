package com.stone.game.player.login;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.IActor;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.listener.IActorFutureListener;
import com.stone.core.annotation.MessageHandler;
import com.stone.core.data.IDataService;
import com.stone.core.msg.MessageParseException;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.query.DBQueryConstants;
import com.stone.game.data.DataService;
import com.stone.game.mock.MockActor;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.msg.handler.BaseProtobufMessageHandler;
import com.stone.game.player.Player;
import com.stone.proto.Auths.Login;
import com.stone.proto.MessageTypes.MessageType;

/**
 * player login handler;
 * 
 * @author crazyjohn
 *
 */
@MessageHandler
public class PlayerLoginHandler extends BaseProtobufMessageHandler {
	private Logger logger = LoggerFactory.getLogger(PlayerLoginHandler.class);
	private IDataService dataService = new DataService();

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN_VALUE;
	}

	@Override
	public void execute(ProtobufMessage msg) throws MessageParseException {
		final Login.Builder login = msg.parseBuilder(Login.newBuilder());
		final Player player = msg.getPlayer();
		// call data rpc service;
		IActorFuture<List<PlayerEntity>> future = dataService.queryByNameAndParams(player, DBQueryConstants.QUERY_PLAYER_BY_NAME_AND_PASSWORD, new String[] { "userName", "password" }, new Object[] {
				login.getUserName(), login.getPassword() });
		// add future listener
		future.addListener(new IActorFutureListener<List<PlayerEntity>>() {

			@Override
			public void onComplete(IActorFuture<List<PlayerEntity>> future) {
				if (future.getResult().size() > 0) {
					logger.info(String.format("Player login succeed, userName: %s", future.getResult().get(0).getUserName()));
				} else {
					logger.info(String.format("Player login failed, userName: %s", login.getUserName()));
				}

			}

			@Override
			public IActor getTarget() {
				return player;
			}

		});
		// test mock actor
		MockActor mockActor = MockActor.createMockActor();
		mockActor.testActorTell();
		// oh baby, a better way, sync easy to understand, but use DSL to change it real works use async way
		// IActorFuture<List<PlayerEntity>> betterFuture =
		// dataService.queryByNameAndParams(player,
		// DBQueryConstants.QUERY_PLAYER_BY_NAME_AND_PASSWORD, new String[] {
		// "userName", "password" }, new Object[] {
		// login.getUserName(), login.getPassword() });
		// betterFuture.awaitResult();
		// if (betterFuture.getResult().size() > 0) {
		// logger.info(String.format("Player login succeed, userName: %s",
		// future.getResult().get(0).getUserName()));
		// } else {
		// logger.info(String.format("Player login failed, userName: %s",
		// login.getUserName()));
		// }

		logger.info("PlayerLoginHandler execute end.");
	}

}
