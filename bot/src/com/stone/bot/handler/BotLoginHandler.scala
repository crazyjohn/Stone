package com.stone.bot.handler

import com.stone.core.msg.MessageType
import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot

object BotLoginHandler {
  
  Handlers.registHandler(MessageType.GC_PLAYER_LOGIN_RESULT, (msg:IMessage, bot:CrazyBot)=>{
    //val loginResult = msg.asInstanceOf[GCPlayerLoginResult]
  })
}