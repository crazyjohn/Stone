package com.i4joy.akka.kok.app;



public class OverlordApp {
	public static void main(String[] args) throws InterruptedException {
		new StartKing();
		Thread.sleep(1000);
		new StartOverlord();
		Thread.sleep(1000);
		new StartMonster();
		Thread.sleep(1000);
		new StartIO();
		Thread.sleep(1000);
		new StartDB();
		Thread.sleep(1000);
		new StartJettyCamel();
		Thread.sleep(1000);
		new StartSharding();
	}
}
