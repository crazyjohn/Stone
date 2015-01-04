package com.stone.bot

import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IoSession
import org.slf4j.LoggerFactory
/**
 * client ioHandler;
 * @author crazyjohn
 */
class ClientIoHandler extends IoHandlerAdapter {
  private val logger = LoggerFactory.getLogger("ClientIoHandler")
  override def sessionOpened(session: IoSession) {
    val bot = new CrazyBot()
    bot.setSession(session)
    session.setAttribute("bot", bot)
    logger.info("Session opend: " + session)
    bot.doLogin()
  }

  override def messageReceived(session: IoSession, message: Object) {
    val bot = session.getAttribute("bot").asInstanceOf[CrazyBot]
    bot ! message
  }

}