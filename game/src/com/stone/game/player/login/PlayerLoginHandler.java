package com.stone.game.player.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.IActorId;
import com.stone.actor.listener.IActorFutureListener;
import com.stone.core.annotation.MessageHandler;
import com.stone.core.data.IDataService;
import com.stone.core.msg.MessageParseException;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.query.DBQueryConstants;
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
	private IDataService dataService;

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN_VALUE;
	}

	@Override
	public void execute(ProtobufMessage msg) throws MessageParseException {
		Login.Builder login = msg.parseBuilder(Login.newBuilder());
		logger.info(String.format("Player login, userName: %s", login.getUserName()));
		final Player player = msg.getPlayer();
		IActorFuture<PlayerEntity> future = dataService.queryByNameAndParams(DBQueryConstants.QUERY_PLAYER_BY_NAME_AND_PASSWORD, new String[] { "userName", "password" },
				new Object[] { login.getUserName(), login.getPassword() });
		future.addListener(new IActorFutureListener<PlayerEntity>() {

			@Override
			public void onComplete(IActorFuture<PlayerEntity> future) {
				// TODO Auto-generated method stub

			}

			@Override
			public IActorId getTarget() {
				return player.getActorId();
			}
		});
	}

}
