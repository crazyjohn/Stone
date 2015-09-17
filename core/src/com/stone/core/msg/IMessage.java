package com.stone.core.msg;

import org.apache.mina.core.buffer.IoBuffer;

import com.stone.core.constants.CommonErrorLogInfo;

/**
 * 消息的接口,消息数据包的格式 <len(2字节)>+<type(2字节)>+<body(len-4)>
 * 
 * @author crazyjohn;
 */
public interface IMessage {
	/** 最大消息长度为64K，可能超过这个长度的信息，需要自己打包分多次发送* */
	public static final int MAX_MESSAGE_LENGTH = 65535;

	/** 从客户端接收的消息估计长度,64字节,对于从客户端接收的数据来说，都是简单的命令，很少超过64B * */
	public static final int DECODE_MESSAGE_LENGTH = 64;

	/** 发送给客户端的消息长度,一般也少于512 * */
	public static final int ENCODE_MESSAGE_LENGTH = 512;

	/** 消息头的长度的字节数 {@value} */
	public static final int HEADER_LEN_BYTES = 2;

	/** 消息头的类型字节数 {@value} */
	public static final int HEADER_TYPE_BYTES = 4;

	/** 消息头的长度,4字节,length+type,{@value} */
	public static final int HEADER_SIZE = HEADER_LEN_BYTES + HEADER_TYPE_BYTES;

	/** 最小命令长度，只有消息头 * */
	public static final int MIN_MESSAGE_LENGTH = HEADER_SIZE;

	public static final int MIN_CACHE_MESSAGE_LENGTH = 6;

	/**
	 * 设置用于解析消息或者输出消息的ByteBuffer
	 * 
	 * @param buf
	 *            被操作的ByteBuffer对象,不能为空
	 */
	public void setBuffer(IoBuffer buf);

	/**
	 * 从由setBuffer设置的消息数据缓存中解析出该消息的数据
	 * 
	 * @return true,解析成功;false,解析失败
	 * @throws MessageParseException
	 */
	public boolean read() throws MessageParseException;

	/**
	 * 将该消息的数据写入到由setBuffer设置的消息数据缓存
	 * 
	 * @return true,写入成功;false,写入失败
	 * @throws MessageParseException
	 */
	public boolean write() throws MessageParseException;

	/**
	 * 取得该消息的类型
	 * 
	 * @return
	 */
	public int getType();

	/**
	 * 取得该消息的名称
	 * 
	 * @return
	 */
	public String getTypeName();

	/**
	 * 取得该消息的长度
	 * 
	 * @return
	 */
	public int getLength();

	/**
	 * 取得该类型消息缓存区的初始大小,该大小可能不是消息的实际大小,它是为了初始化用于存入该消息数据的ByteBuffer的大小,在具体的子类中
	 * 可以提供更为精确的缓存区初始化大小
	 * 
	 * @return
	 */
	public int getInitBufferLength();

	/**
	 * 执行消息的处理
	 * 
	 * @throws MessageParseException
	 */
	public abstract void execute() throws MessageParseException;

	/**
	 * 数据包解析工具类
	 * 
	 *
	 * 
	 */
	public static class Packet {
		/**
		 * 从buf中取出数据包的长度,buf的position属性保持不变,在调用此方法假定buf当前的位置就是长度字段的起始位置
		 * 
		 * @param buf
		 * @return -1,buf中没有足够的数据;>= 0
		 * @throws IllegalStateException
		 *             如果从buf中取得到包长度<
		 *             MIN_MESSAGE_LENGTH或者大于MAX_MESSAGE_LENGTH会抛出此异常
		 */
		public static int peekPacketLength(IoBuffer buf) {
			return seekLength(buf, true);
		}

		/**
		 * 从buf中取出数据包的长度,buf的position属性向前移动HEADER_LEN_BYTES个字节,
		 * 在调用此方法假定buf当前的位置就是长度字段的起始位置
		 * 
		 * @param buf
		 * @return -1,buf中没有足够的数据;>= 0
		 * @throws IllegalStateException
		 *             如果从buf中取得到包长度<
		 *             MIN_MESSAGE_LENGTH或者大于MAX_MESSAGE_LENGTH会抛出此异常
		 */
		public static int popPacketLength(IoBuffer buf) {
			return seekLength(buf, false);
		}

		/**
		 * 
		 * 从buf中取出数据包的类型,buf的position属性保持不变
		 * 
		 * @param buf
		 * @return -1,buf中没有足够的数据;>= 0
		 * @throws IllegalStateException
		 *             如果数据包中的类型小于0会抛出此异常
		 */
		public static short peekPacketType(IoBuffer buf) {
			final int _pos = buf.position();
			try {
				popPacketLength(buf);
				return popPacketType(buf);
			} finally {
				buf.position(_pos);
			}
		}

		/**
		 * 
		 * 从buf中取出数据包的类型,buf的position属性向前移动HEADER_TYPE_BYTES个字节,
		 * 在调用此方法假定buf当前的位置就是类型字段的起始位置
		 * 
		 * @return -1,buf中没有足够的数据;>= 0
		 * @throws IllegalStateException
		 *             如果数据包中的类型小于0会抛出此异常
		 */
		public static short popPacketType(IoBuffer buf) {
			return seekType(buf, false);
		}

		private static int seekLength(IoBuffer buf, boolean peek) {
			int _len = seekIntFromUnsignedShort(buf, peek);
			if (_len >= 0 && (_len < MIN_MESSAGE_LENGTH || _len > MAX_MESSAGE_LENGTH)) {
				throw new IllegalStateException(CommonErrorLogInfo.PACKET_BAD_HEADER_LEN);
			}
			return _len;
		}

		private static short seekType(IoBuffer buf, boolean peek) {
			short _type = seekShort(buf, peek);
			return _type;
		}

		private static short seekShort(IoBuffer buf, boolean peek) {
			if (buf.remaining() >= HEADER_LEN_BYTES) {
				int _op = buf.position();
				short _value = buf.getShort();
				if (peek) {
					buf.position(_op);
				}
				if (_value < 0) {
					throw new IllegalStateException(CommonErrorLogInfo.PACKET_BAD_HEADER_LEN);
				}
				return _value;
			} else {
				return -1;
			}
		}

		/**
		 * 从buf中获得一个16位的无符号的整数
		 * 
		 * @param buf
		 * @param peek
		 * @return
		 */
		protected static int seekIntFromUnsignedShort(IoBuffer buf, boolean peek) {
			if (buf.remaining() >= HEADER_LEN_BYTES) {
				int _op = buf.position();
				int _value = buf.getShort() << 16 >>> 16;
				if (peek) {
					buf.position(_op);
				}
				if (_value > MAX_MESSAGE_LENGTH) {
					throw new IllegalStateException(CommonErrorLogInfo.PACKET_BAD_HEADER_LEN);
				}
				return _value;
			} else {
				return -1;
			}
		}
	}
}
