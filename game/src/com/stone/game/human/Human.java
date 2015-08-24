package com.stone.game.human;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.game.module.human.IHumanModule;
import com.stone.game.module.human.item.HumanItemModule;
import com.stone.game.module.human.item.HumanSceneModule;
import com.stone.game.module.player.Player;

/**
 * The human;
 * 
 * @author crazyjohn
 *
 */
public class Human {
	/** human guid */
	private long guid;
	private String name;
	/** player */
	private final Player player;
	/** itemModule */
	private IHumanModule itemModule;
	/** scenModule */
	private IHumanModule sceneModule;
	private HumanEntity humanEntity;

	public Human(Player player) {
		this.player = player;
		// init
		initModule();
	}

	private void initModule() {
		// scene module
		sceneModule = new HumanSceneModule(this);
		// init item module
		itemModule = new HumanItemModule(this);
	}

	public long getGuid() {
		return guid;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Handle the system internal message;
	 * 
	 * @param message
	 * @param playerActor
	 */
	public void onInternalMessage(Object message, ActorRef playerActor) {
		// dispatch internal message
		this.sceneModule.onInternalMessage(message, playerActor);
		this.itemModule.onInternalMessage(message, playerActor);
	}

	/**
	 * Handle the net external message;
	 * 
	 * @param msg
	 * @param playerActor
	 * @param dbMaster
	 * @throws MessageParseException
	 */
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		// dispatch external message
		this.sceneModule.onExternalMessage(msg, playerActor, dbMaster);
		this.itemModule.onExternalMessage(msg, playerActor, dbMaster);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Init from human entity;
	 * 
	 * @param humanEntity
	 */
	public void onLoad(HumanEntity humanEntity) {
		this.name = humanEntity.getName();
		this.guid = humanEntity.getGuid();
		this.humanEntity = humanEntity;
		// load module datas
		this.sceneModule.onLoad(humanEntity);
		this.itemModule.onLoad(humanEntity);
	}

	public int getLevel() {
		return humanEntity.getLevel();
	}

	public int getSceneId() {
		// TODO Auto-generated method stub
		return 1;
	}
}
