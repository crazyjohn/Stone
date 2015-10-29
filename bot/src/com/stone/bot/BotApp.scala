package com.stone.bot

import com.stone.proto.Auths.Login
import org.slf4j.LoggerFactory
import org.apache.mina.core.future.IoFutureListener
import org.apache.mina.core.future.ConnectFuture
import com.stone.proto.MessageTypes.MessageType
import com.stone.proto.Syncs.Move
import com.stone.proto.Humans.SceneObjectType
import com.stone.bot.task.LoopTask
/**
 * BotApp;
 * @author crazyjohn
 */
object BotApp extends App {
  private val logger = LoggerFactory.getLogger("ClientIoHandler")
  val begin = 0
  val end = 10
  // create robot
  for (i <- begin to end) {
    val bot = new CrazyBot("bot" + i)
    // remote: 203.195.218.172
    // local: 127.0.0.1
    val connectFuture = bot.connect("127.0.0.1", 8080)
    connectFuture.awaitUninterruptibly()
    connectFuture.addListener(new ConnectListener(bot))
    // add task
    addTask(bot)
  }
  
  private def addTask(bot:CrazyBot) = {
    // add move task
    bot.addTask(new LoopTask(1000 * 5){
      override def runOnceTime(bot:CrazyBot):Unit = {
        if (bot.botState != BotState.GAMEING) {
          return
        }
        val move = Move.newBuilder()
        move.setId(bot.id).setObjectType(SceneObjectType.HUMAN).setSceneId(1).setX(500).setY(300)
        bot.sendMessage(MessageType.CG_REQUEST_MOVE_VALUE, move)
      }
    })
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