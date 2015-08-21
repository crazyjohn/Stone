package com.stone.bot

import com.stone.proto.Auths.Login
import org.slf4j.LoggerFactory
import org.apache.mina.core.future.IoFutureListener
import org.apache.mina.core.future.ConnectFuture
/**
 * BotApp;
 * @author crazyjohn
 */
object BotApp extends App {
  private val logger = LoggerFactory.getLogger("ClientIoHandler")
  val begin = 0
  val end = 5
  // create robot
  for (i <- begin to end) {
    val bot = new CrazyBot("bot" + i)
    // 203.195.218.172
    val connectFuture = bot.connect("127.0.0.1", 8081)
    connectFuture.awaitUninterruptibly()
    connectFuture.addListener(new ConnectListener(bot))
  }

  /**
   * Connect future listener;
   */
  class ConnectListener(bot: CrazyBot) extends IoFutureListener[ConnectFuture] {
    override def operationComplete(connectFuture: ConnectFuture) {
      bot.start()
      bot.setSession(connectFuture.getSession)
      connectFuture.getSession().setAttribute("bot", bot)
      logger.info("Session opend: " + connectFuture.getSession)
      bot.doLogin()
    }
  }

}