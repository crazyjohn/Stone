package com.i4joy.akka.kok.protobufs.deal;

import akka.actor.ActorRef;

import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.akka.kok.protobufs.KOKPacket.HeartReponse;
import com.i4joy.akka.kok.protobufs.KOKPacket.HeartRequest;
import com.i4joy.akka.kok.protobufs.KOKPacket.PacketInfo;

public class PHeart {
	public static void deal(PlayerEntity player, HeartRequest request,ActorRef mediator,ActorRef sender,ActorRef self) {
		try
		{
			PacketInfo.Builder packetBuilder = PacketInfo.newBuilder();
			HeartReponse.Builder reponse = HeartReponse.newBuilder();
			reponse.setMsg(request.getMsg());
			reponse.setTime(request.getTime());
			packetBuilder.setId(1002);
			byte[] data = reponse.build().toByteArray();
			packetBuilder.setData(com.google.protobuf.ByteString.copyFrom(data));
//			System.out.println("player heart "+player.hashCode());
//			final PProtocolPacket.msgInfo.Builder builder = PProtocolPacket.msgInfo.newBuilder();
//			builder.setWriteData(com.google.protobuf.ByteString.copyFrom(Tools.packageBytes(reponse.build().toByteArray())));
//			return builder.build();
			
//			return packetBuilder.build();
			sender.tell(packetBuilder.build(), self);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
//		return null;
	}
}
