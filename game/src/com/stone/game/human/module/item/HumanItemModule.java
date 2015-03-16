package com.stone.game.human.module.item;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.db.entity.HumanEntity;
import com.stone.game.human.Human;
import com.stone.game.human.module.BaseHumanModule;
import com.stone.game.msg.ProtobufMessage;

/**
 * The human item module;
 * 
 * @author crazyjohn
 *
 */
public class HumanItemModule extends BaseHumanModule {

	public HumanItemModule(Human human) {
		super(human);
	}

	@Override
	public void onLoad(HumanEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPersistence(HumanEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInternalMessage(Object msg, ActorRef playerActor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		// TODO Auto-generated method stub
		
	}

}
