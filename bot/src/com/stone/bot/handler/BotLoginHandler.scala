package com.stone.bot.handler


import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot
import com.stone.proto.MessageTypes.MessageType

object BotLoginHandler {
  
  Handlers.registHandler(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, (msg:IMessage, bot:CrazyBot)=>{
    //val loginResult = msg.asInstanceOf[GCPlayerLoginResult]
  })
}