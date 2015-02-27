package com.i4joy.akka.kok.app;

import akka.actor.Address;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.MonsterStart;

public class StartMonster {
	public StartMonster() {
		Address address = new Address("akka.tcp", Property.SYSTEMNAME, TextProperties.getText("KINGIP"), Integer.parseInt(TextProperties.getText("KINGPORT")));
		MonsterStart.getInstance(address);
	}

	public static void main(String[] args) {
		new StartMonster();
	}
}
