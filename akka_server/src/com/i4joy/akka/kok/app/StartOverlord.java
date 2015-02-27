package com.i4joy.akka.kok.app;

import akka.actor.Address;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.overlord.OverlordStart;

public class StartOverlord {

	public StartOverlord() {
		Address address = new Address("akka.tcp", Property.SYSTEMNAME, TextProperties.getText("KINGIP"),  Integer.parseInt(TextProperties.getText("KINGPORT")));//获得King的地址
		new OverlordStart(address, Property.OVERLORDNAME);//Overlord节点
	}

	public static void main(String[] args) {
		new StartOverlord();
	}
}
