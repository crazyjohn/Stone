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
import com.stone.core.msg.MessageType
import com.stone.game.msg.ProtobufMessage
import com.stone.game.msg.ProtobufMessageFactory
import com.stone.proto.Auths.Login
import com.stone.bot.handler.Handlers

/**
 * bot Actor;
 * @author crazyjohn
 */
class CrazyBot extends Actor {
  private val tasks = new MutableList[BotTask]
  private var session: IoSession = null

  def setSession(session: IoSession) {
    this.session = session
  }
  /**
   * add task;
   * @param task;
   */
  def addTask(task: BotTask) {
    tasks += task
  }

  def connect(serverIp: String, port: Int) {
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
          tasks.foreach(task => { task.runOnceTime(this) })
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
  
  def doLogin() {
    sendMessage(MessageType.CG_PLAYER_LOGIN, Login.newBuilder().setUserName("bot").setPassword("bot"))
  }

}