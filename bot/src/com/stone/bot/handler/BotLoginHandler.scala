package com.stone.bot.handler

import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot
import com.stone.proto.MessageTypes.MessageType
import com.stone.game.msg.ProtobufMessage
import com.stone.proto.Auths.LoginResult
import com.stone.proto.Auths.GetRoleList

object BotLoginHandler {

  /**
   *  handle login result;
   */
  Handlers.registHandler(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val login = protobufMessage.parseBuilder(LoginResult.newBuilder());
    if (login.getSucceed) {
      bot.sendMessage(MessageType.CG_GET_ROLE_LIST_VALUE)
    }
  })
  
  
  Handlers.registHandler(MessageType.GC_GET_ROLE_LIST_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val roleList = protobufMessage.parseBuilder(GetRoleList.newBuilder());
    if (roleList.getRoleListCount > 0) {
      val role = roleList.getRoleList(0)
      println(String.format("Get role, name: %s", role.getName))
    }
  })
}