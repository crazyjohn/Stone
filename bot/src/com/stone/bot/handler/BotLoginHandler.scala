package com.stone.bot.handler


import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot
import com.stone.proto.MessageTypes.MessageType
import com.stone.game.msg.ProtobufMessage
import com.stone.proto.Auths.LoginResult

object BotLoginHandler {

  Handlers.registHandler(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val login = protobufMessage.parseBuilder(LoginResult.newBuilder());
    if (login.getSucceed) {
      System.out.println("Bot login succeed.")
    }
  })
}