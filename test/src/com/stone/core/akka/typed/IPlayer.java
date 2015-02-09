package com.stone.core.akka.typed;

import scala.concurrent.Future;
import akka.japi.Option;

public interface IPlayer {

	public String getName();

	public int getLevel();

	public void levelUpDontCare(int level);

	public Future<Integer> beatFuture(int damage);

	public Option<Integer> beatNow(int damage);
}
