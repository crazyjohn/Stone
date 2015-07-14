package com.stone.game.human.module;

import java.util.List;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.HumanItemEntity;
import com.stone.game.human.Human;

/**
 * The human item module;
 * 
 * @author crazyjohn
 *
 */
public class HumanItemModule extends BaseHumanModule {
	/** the item entities */
	protected List<HumanItemEntity> itemEntities;

	public HumanItemModule(Human human) {
		super(human);
	}

	@Override
	public void onLoad(HumanEntity entity) {
		// init entities
		
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
