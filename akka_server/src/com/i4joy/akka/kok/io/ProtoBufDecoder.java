package com.i4joy.akka.kok.io;

import scala.concurrent.duration.Duration;
import worker.Behavior;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.io.Tcp.CommandFailed;
import akka.io.TcpMessage;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.camel.worker.LoginWorker;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGet;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.i4joy.akka.kok.overlord.protocol.ConnectionClose;
import com.i4joy.akka.kok.overlord.protocol.PlayerActorAddress;
import com.i4joy.akka.kok.overlord.protocol.PlayerCreate;
import com.i4joy.akka.kok.protobufs.KOKPacket.FirstIOReponse;
import com.i4joy.akka.kok.protobufs.KOKPacket.FirstIORequest;
import com.i4joy.akka.kok.protobufs.KOKPacket.PacketInfo;
import com.i4joy.akka.kok.protocol.PClosePlayerProxy;

public class ProtoBufDecoder extends UntypedActor {

	private ByteString byteStringBuffer = ByteString.empty();// 初始化ByteString缓存区
	private final ActorRef connection;// 连接
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private int playerId;// 唯一标识
	private String playerName;
	private String playerGroupName;
	private int serverId;
	private long lastRequest;
	private final Cancellable heart;// 心跳 清理缓存中超时的

	public static Props getProps(ActorRef connection) {
		return Props.create(ProtoBufDecoder.class, connection);
	}

	public ProtoBufDecoder(ActorRef connection) {
		this.connection = connection;
		heart = getContext().system().scheduler().schedule(Duration.create(60, "seconds"), Duration.create(60, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
	}

	@Override
	public void postStop() throws Exception {
		System.out.println("ProtoBufDecoder stop!");
		heart.cancel();
		if (playerGroupName != null) {
			ConnectionClose pk = new ConnectionClose();
			pk.playerId = playerId;
			mediator.tell(new Publish(Property.OVERLORDNAME, pk), getSelf());
			super.postStop();
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {

	}

	public void dealFirstIO(byte[] data, short pid) throws Exception {

		if (pid == 1001) {
			FirstIORequest fr = FirstIORequest.parseFrom(data);
			playerId = fr.getPlayerId();
			String userName = fr.getUserName();
			String passWord = fr.getPassword();
			playerName = fr.getPlayerName();
			if (LoginWorker.checkUserPassword(userName, passWord, mediator)) {
				RC_playerInfoGet playerInfo = LoginWorker.getPlayerInfo(playerName, mediator);
				if (playerInfo.getUserName().equals(userName)) {
					serverId = playerInfo.getServerId();

					createPlayerActor(fr.getPlayerId(), playerName, serverId, mediator, getSelf());
					getContext().become(waitPlayerCache);// 进入等待状态
				} else {
					connection.tell(TcpMessage.close(), getSelf());// 关闭连接
				}
			} else {
				playerId = 0;
				connection.tell(TcpMessage.close(), getSelf());// 关闭连接
			}
		}

	}

	public static void createPlayerActor(int playerId, String playerName, int serverId, ActorRef mediator, ActorRef self) {
		PlayerCreate pr = new PlayerCreate();
		pr.setPlayerId(playerId);
		pr.setPlayerName(playerName);
		pr.setServerId(serverId);
		String name = Property.OVERLORDNAME;
		mediator.tell(new Publish(name, pr), self);
	}

	public void reFirstIO() {
		FirstIOReponse.Builder firbuilder = FirstIOReponse.newBuilder();
		firbuilder.setRetMsg("成功!");
		byte[] data2 = firbuilder.build().toByteArray();
		ByteStringBuilder bb = new ByteStringBuilder();
		bb.putInt(data2.length + 2, java.nio.ByteOrder.BIG_ENDIAN);// 协议数据长度
		bb.putShort(1001, java.nio.ByteOrder.BIG_ENDIAN);
		bb.putBytes(data2);// 协议数据
		connection.tell(TcpMessage.write(bb.result()), getSelf());// 写给客户端
	}

	// 捕获第一个包状态
	private final Behavior cacheFirstIO = new Behavior() {
		public void apply(Object msg) {
			if (msg instanceof ByteString) {// 从ByteString缓冲区获得 读出数据
				lastRequest = System.currentTimeMillis();
				final ByteStringBuilder builder = new ByteStringBuilder();
				builder.append(byteStringBuffer);
				builder.append((ByteString) msg);
				byteStringBuffer = builder.result();
				final ByteIterator iter = byteStringBuffer.iterator();
				int dropLength = 0;
				while (true) {
					if (iter.len() > 4) {// 数据是否大于一个int
						final int packetLen = iter.getInt(java.nio.ByteOrder.LITTLE_ENDIAN);// 获得封包长度
						if (iter.len() >= packetLen) {// 数据大于封包的长度
							short pid = iter.getShort(java.nio.ByteOrder.LITTLE_ENDIAN);
							byte[] data = new byte[packetLen - 2];
							iter.getBytes(data);// 获取封包数据
							dropLength += 4 + packetLen;// 需要删除的长度
							try {
								dealFirstIO(data, pid);
							} catch (Exception e) {
								e.printStackTrace();
							}

						} else// 当前包内容长度比当前包长度短
						{
							break;
						}
					} else// 长度不够一个int
					{
						break;
					}
				}
				if (dropLength > 0) {
					byteStringBuffer = byteStringBuffer.drop(dropLength);
				}

			} else if (msg instanceof Heart) {
				if (System.currentTimeMillis() - lastRequest > Property.MINUTE) {
					connection.tell(TcpMessage.close(), getSelf());// 关闭连接
				}
			}
		}
	};

	{
		getContext().become(cacheFirstIO);
	}

	// 等待接收创建成功
	private final Behavior waitPlayerCache = new Behavior() {
		public void apply(Object msg) {
			if (msg instanceof PlayerActorAddress) {
				lastRequest = System.currentTimeMillis();
				getContext().become(doPlayerCache);// 进入广播状态
				getContext().parent().tell(msg, getSelf());// 通知packetBuffer创建成功
				reFirstIO();
			} else if (msg instanceof ByteString) {
				final ByteStringBuilder builder = new ByteStringBuilder();
				builder.append(byteStringBuffer);
				builder.append((ByteString) msg);
				byteStringBuffer = builder.result();// 添加到ByteString缓冲区
			} else if (msg instanceof Heart) {
				if (System.currentTimeMillis() - lastRequest > Property.MINUTE) {
					connection.tell(TcpMessage.close(), getSelf());// 关闭连接
				}
			}
		}
	};

	// 工作状态
	private final Behavior doPlayerCache = new Behavior() {
		public void apply(Object msg) {
			if (msg instanceof ByteString) {// 从ByteString缓冲区获得 读出数据
				final ByteStringBuilder builder = new ByteStringBuilder();
				builder.append(byteStringBuffer);
				builder.append((ByteString) msg);
				byteStringBuffer = builder.result();
				final ByteIterator iter = byteStringBuffer.iterator();
				int dropLength = 0;
				while (true) {
					if (iter.len() > 4) {// 数据是否大于一个int
						final int packetLen = iter.getInt(java.nio.ByteOrder.LITTLE_ENDIAN);// 获得封包长度
						if (iter.len() >= packetLen) {// 数据大于封包的长度
							byte[] data = new byte[packetLen];
							iter.getBytes(data);// 获取封包数据
							dropLength += 4 + packetLen;// 需要删除的长度
							// 发送给集群中的Player

							mediator.tell(new Publish("playerId" + playerId, data), getSelf());// 发送给集群中的Player
						} else// 当前包内容长度比当前包长度短
						{
							break;
						}
					} else// 长度不够一个int
					{
						break;
					}
				}
				byteStringBuffer = byteStringBuffer.drop(dropLength);
			} else if (msg instanceof PacketInfo)// 收到PlayerCache返回的协议
			{
				lastRequest = System.currentTimeMillis();
				PacketInfo p = (PacketInfo) msg;
				byte[] data = p.getData().toByteArray();
				ByteStringBuilder bb = new ByteStringBuilder();
				bb.putInt(data.length + 2, java.nio.ByteOrder.BIG_ENDIAN);// 协议数据长度
				bb.putShort(p.getId(), java.nio.ByteOrder.BIG_ENDIAN);
				bb.putBytes(data);// 协议数据
				connection.tell(TcpMessage.write(bb.result()), getSelf());// 写给客户端
			} else if (msg instanceof CommandFailed) {
				connection.tell(TcpMessage.resumeWriting(), getSelf());
			} else if (msg instanceof PClosePlayerProxy) {// 被挤掉连接
				connection.tell(TcpMessage.close(), getSelf());// 关闭连接
			} else if (msg instanceof Heart) {
				if (System.currentTimeMillis() - lastRequest > Property.MINUTE) {
					connection.tell(TcpMessage.close(), getSelf());// 关闭连接
				}
			} else {
				unhandled(msg);
			}
		}
	};

	public static void main(String[] args) {
		byte b1 = -23;
		byte b2 = 3;
		System.out.println((b2 << 8) + (b1 + 255));
	}

}
