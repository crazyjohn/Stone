package com.stone.game.human;

import java.util.LinkedHashMap;
import java.util.Map;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.game.module.human.IHumanModule;
import com.stone.game.module.human.item.HumanItemModule;
import com.stone.game.module.human.scene.HumanSceneModule;
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
	/** modules */
	private Map<Class<? extends IHumanModule>, IHumanModule> modules;
	/** itemModule */
	private IHumanModule itemModule;
	/** scenModule */
	private IHumanModule sceneModule;
	private int level;

	public Human(Player player) {
		this.player = player;
		// init
		initModule();
	}

	private void initModule() {
		modules = new LinkedHashMap<Class<? extends IHumanModule>, IHumanModule>();
		// scene module
		sceneModule = new HumanSceneModule(this);
		modules.put(sceneModule.getClass(), sceneModule);
		// init item module
		itemModule = new HumanItemModule(this);
		modules.put(itemModule.getClass(), itemModule);
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
		for (IHumanModule module : modules.values()) {
			if (!module.isOpen()) {
				continue;
			}
			module.onInternalMessage(message, playerActor);
		}
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
		for (IHumanModule module : modules.values()) {
			if (!module.isOpen()) {
				continue;
			}
			module.onExternalMessage(msg, playerActor, dbMaster);
		}
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
		this.level = humanEntity.getLevel();
		// load module datas
		for (IHumanModule module : modules.values()) {
			if (!module.isOpen()) {
				continue;
			}
			module.onLoad(humanEntity);
		}
	}

	public int getLevel() {
		return level;
	}

	public int getSceneId() {
		// TODO Auto-generated method stub
		return 1;
	}
}
