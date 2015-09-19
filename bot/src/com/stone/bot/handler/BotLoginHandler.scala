package com.stone.bot.handler

import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot
import com.stone.proto.MessageTypes.MessageType
import com.stone.proto.Auths.LoginResult
import com.stone.proto.Auths.RoleList
import com.stone.proto.Auths.CreateRole
import com.stone.proto.Auths.SelectRole
import com.stone.proto.Auths.EnterScene
import com.stone.core.msg.ProtobufMessage
import com.stone.core.msg.IMessage
import com.stone.core.msg.ProtobufMessage
import com.stone.core.msg.IMessage
import com.stone.bot.BotState
/**
 * Bot login message handler;
 *
 * @author crazyjohn;
 */
object BotLoginHandler {

  /**
   *  Handle login result;
   */
  Handlers.registHandler(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val login = protobufMessage.getBuilder(LoginResult.newBuilder()).asInstanceOf[LoginResult.Builder];
    if (login.getSucceed) {
      println(String.format("Login success, puid: %s", bot.puid))
      bot.sendMessage(MessageType.CG_GET_ROLE_LIST_VALUE)
    }
  })

  /**
   * Handle role list;
   */
  Handlers.registHandler(MessageType.GC_GET_ROLE_LIST_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val roleList = protobufMessage.getBuilder(RoleList.newBuilder()).asInstanceOf[RoleList.Builder]
    if (roleList.getRoleListCount > 0) {
      val role = roleList.getRoleList(0)
      println(String.format("Select role, name: %s", role.getName))
      // get role where index = 0
      bot.sendMessage(MessageType.CG_SELECT_ROLE_VALUE, SelectRole.newBuilder().setRoleId(role.getRoleId))
    } else {
      println(String.format("CCCreate role, puid: %s", bot.puid))
      bot.sendMessage(MessageType.CG_CREATE_ROLE_VALUE, CreateRole.newBuilder().setTemplateId(1).setName(bot.puid + "_" + "role"))
    }
  })

  /**
   * Handle enter scene;
   */
  Handlers.registHandler(MessageType.GC_ENTER_SCENE_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val enterScene = protobufMessage.getBuilder(EnterScene.newBuilder()).asInstanceOf[EnterScene.Builder]
    bot.setId(enterScene.getHuman.getGuid)
    bot.botState = BotState.GAMEING
    println(String.format("%s enter scene", enterScene.getHuman.getName))
    // enter scene ready
    bot.sendMessage(MessageType.CG_ENTER_SCENE_READY_VALUE)
  })
}