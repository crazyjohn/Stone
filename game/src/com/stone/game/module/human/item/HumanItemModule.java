package com.stone.game.module.human.item;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.HumanItemEntity;
import com.stone.game.human.Human;
import com.stone.game.module.human.BaseHumanModule;
import com.stone.proto.entity.Entities.HumanItemData;

/**
 * The human item module;
 * 
 * @author crazyjohn
 *
 */
public class HumanItemModule extends BaseHumanModule {
	/** the item entities */
	protected List<HumanItemEntity> itemEntities = new ArrayList<HumanItemEntity>();

	public HumanItemModule(Human human) {
		super(human);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// init entities
		for (HumanItemData eachItemData : humanEntity.getBuilder()
				.getHumanItemsList()) {
			itemEntities.add(new HumanItemEntity(eachItemData.toBuilder()));
		}

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
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor,
			ActorRef dbMaster) throws MessageParseException {
		// TODO Auto-generated method stub

	}

}
