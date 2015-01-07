package com.stone.bot.handler

import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot
import java.util.HashMap
import com.stone.core.msg.IProtobufMessage
/**
 * Handler static factory;
 */
object Handlers {
  private val handlers = new HashMap[Integer, (IProtobufMessage, CrazyBot) => Unit]()

  

  def handle(msg: IProtobufMessage, bot: CrazyBot) = {
    handlers.get(msg.getType) match {
      case null => 
        println("No handler for type: " + msg.getType)
      case handler => 
        handler(msg, bot) 
    }
  }
  
  def registHandler(msgType: Int, handler:(IProtobufMessage, CrazyBot)=>Unit) {
    handlers.put(msgType, handler)
  }
}