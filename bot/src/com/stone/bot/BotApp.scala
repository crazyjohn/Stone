package com.stone.bot

import com.stone.proto.Auths.Login
import org.slf4j.LoggerFactory
/**
 * BotApp;
 * @author crazyjohn
 */
object BotApp extends App {
  private val logger = LoggerFactory.getLogger("ClientIoHandler")
  val begin = 1
  val end = 5
  // create robot
  for (i <- begin to end) {
    val bot = new CrazyBot("bot" + i, "bot" + i)
    val connectFuture = bot.connect("0.0.0.0", 8081)
    connectFuture.awaitUninterruptibly();
    bot.start()
    bot.setSession(connectFuture.getSession)
    connectFuture.getSession().setAttribute("bot", bot)
    logger.info("Session opend: " + connectFuture.getSession)
    bot.doLogin()
  }

}