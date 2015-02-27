package com.i4joy.akka.kok.app;

public class StartDB_HTTP_IO {
	public static void main(String[] args) throws InterruptedException {
		new StartIO();
		Thread.sleep(1000);
		new StartDB();
		Thread.sleep(1000);
		new StartJettyCamel();
	}
}
