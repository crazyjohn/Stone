package com.stone.core.akka.typed;

import scala.concurrent.Future;
import akka.dispatch.Futures;
import akka.japi.*;

public class PlayerTester implements IPlayer {
	private String name;
	private int level;

	public PlayerTester(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void levelUpDontCare(int level) {
		this.level += level;
	}

	@Override
	public Future<Integer> beatFuture(int damage) {
		return Futures.successful(damage);
	}

	@Override
	public Option<Integer> beatNow(int damage) {
		return Option.some(damage);
	}

	@Override
	public int getLevel() {
		return level;
	}

}
