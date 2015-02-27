package com.i4joy.akka.kok.monster.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;

import com.i4joy.akka.kok.monster.equipment.templet.EquipmentBasePropertyTemplet;
import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.akka.kok.protobufs.KOKPacket.Secret;

public class EquipmentService {
	protected final Log logger = LogFactory.getLog(getClass());

	public static boolean give(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, int amount, EquipmentBasePropertyTemplet ebp) {
		boolean b = true;
		return b;
	}
}
