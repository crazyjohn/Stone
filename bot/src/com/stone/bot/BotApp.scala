package com.stone.bot

import com.stone.core.msg.MessageType
import com.stone.proto.Auths.Login
/**
 * BotApp;
 * @author crazyjohn
 */
object BotApp extends App {
  val bot = new CrazyBot()
  bot.connect("0.0.0.0", 8081)
}