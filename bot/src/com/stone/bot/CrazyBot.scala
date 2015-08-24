package com.stone.bot

import java.net.InetSocketAddress
import scala.actors.Actor
import scala.collection.mutable.MutableList
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.nio.NioSocketConnector
import com.google.protobuf.Message.Builder
import com.stone.core.codec.GameCodecFactory
import com.stone.core.msg.IProtobufMessage
import com.stone.proto.Auths.Login
import com.stone.bot.handler.Handlers
import com.stone.proto.MessageTypes.MessageType
import org.apache.mina.core.future.ConnectFuture
import com.stone.core.codec.GameCodecFactory
import com.stone.core.msg.IProtobufMessage
import com.stone.core.msg.ProtobufMessage
import com.stone.core.msg.ProtobufMessageFactory
import com.stone.core.codec.GameCodecFactory
import com.stone.core.msg.IProtobufMessage
import com.stone.core.msg.ProtobufMessage
import com.stone.core.msg.ProtobufMessageFactory
import scala.collection.mutable.ListBuffer
import com.stone.bot.task.OnceTask
import com.stone.bot.task.LoopTask

/**
 * bot Actor;
 * @author crazyjohn
 */
class CrazyBot(platformId: String) extends Actor {
  private val tasks = new ListBuffer[BotTask]
  private var session: IoSession = null
  val puid: String = platformId
  var id: Long = 0
  var botState = BotState.INIT

  def setSession(session: IoSession) {
    this.session = session
  }

  def setId(id: Long) = {
    this.id = id
  }
  /**
   * add task;
   * @param task;
   */
  def addTask(task: BotTask) {
    tasks += task
  }

  def connect(serverIp: String, port: Int): ConnectFuture = {
    val connector: NioSocketConnector = new NioSocketConnector()
    connector.setHandler(new ClientIoHandler())
    connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GameCodecFactory(new ProtobufMessageFactory())))
    connector.connect(new InetSocketAddress(serverIp, port))
  }

  /**
   * act;
   */
  override def act() {
    loop {
      // 1. run tasks
      try {
        if (!tasks.isEmpty) {
          tasks.foreach { task =>
            {
              if (task.isInstanceOf[OnceTask]) {
                task.runOnceTime(this)
                tasks -= task
              } else if (task.isInstanceOf[LoopTask]) {
                var loopTask = task.asInstanceOf[LoopTask]
                if ((System.currentTimeMillis() - loopTask.lastRunTime) >= loopTask.interval) {
                  loopTask.runOnceTime(this)
                  loopTask.lastRunTime = System.currentTimeMillis()
                }
              }

            }
          }
        }
      } catch {
        case e: Exception => {
          println(String.format("Task run error: %s", e.getStackTrace()))
        }
      }
      // 2. react
      react {
        case msg if msg.isInstanceOf[IProtobufMessage] =>
          Handlers.handle(msg.asInstanceOf[IProtobufMessage], this)
      }
    }
  }

  /**
   * send msg;
   */
  def sendMessage(messageType: Short, builder: Builder) {
    val msg = new ProtobufMessage(messageType)
    msg.setBuilder(builder)
    session.write(msg)
  }

  def sendMessage(messageType: Short) {
    val msg = new ProtobufMessage(messageType)
    session.write(msg)
  }

  def doLogin() {
    sendMessage(MessageType.CG_PLAYER_LOGIN_VALUE, Login.newBuilder().setPuid(puid))
  }

}