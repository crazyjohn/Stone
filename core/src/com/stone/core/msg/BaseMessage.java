package com.stone.core.msg;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.mina.core.buffer.IoBuffer;

import com.stone.core.constants.Loggers;
import com.stone.core.constants.SharedConstants;
import com.stone.core.util.MessageTypeUtil;

/**
 * IMessageBase的基本实现
 * 
 * @author crazyjohn;
 */

public abstract class BaseMessage implements IMessage {

	/** 与该消息绑定的ByteBuffer,用于read或者write方法的操作对象 * */
	protected IoBuffer buf;

	/** 消息的长度 * */
	protected int messageLength;

	/** 消息的类型 * */
	protected short type;

	/** 消息的名称 * */
	protected String typeName;

	protected BaseMessage() {
	}

	/**
	 * 将该消息的属性写入到当前设置的ByteBuffer中,按以下顺序写入:
	 * 
	 * <pre>
	 * 1.消息长度(占位符0,实际的长度当writeImpl写完之后再确定)
	 * 2.消息类型
	 * 3.消息体 
	 * 4.如果第3步成功,则修正消息长度,并返回true 
	 * 5.如果第3步失败,则返回flase
	 * </pre>
	 * 
	 * @return true,写入成功;false,写入失败
	 * @throws MessageParseException
	 *             在写入的过程中发生错误时抛出此异常
	 */
	public boolean write() throws MessageParseException {
		try {
			int _op = buf.position();
			writeShort(0);
			writeShort(getType());
			boolean b = writeBody();
			if (!b) {
				return false;
			}
			// 消息体写完之后,修正消息头中的长度字段
			messageLength = buf.position() - _op;
			if (messageLength > MAX_MESSAGE_LENGTH) {
				throw new IllegalArgumentException(
						"The message length is not invalid,value ["
								+ messageLength + "],maybe it's too long?"
								+ this.getTypeName());
			}
			if (messageLength > MAX_MESSAGE_LENGTH / 2) {
				Loggers.MSG_LOGGER.warn("[#message too big]"
						+ this.getTypeName() + "|" + getType());
			}
			buf.putShort(_op, (short) messageLength);
			return true;
		} catch (Exception e) {
			throw new MessageParseException(e);
		}
	}

	/**
	 * 将当前buf中的数据读取到IBaseMessage对象对应的属性中
	 * 
	 * @return true,读取成功;false,读取失败
	 * @throws MessageParseException
	 *             在读取过程发生错误的抛出此异常
	 */
	public boolean read() throws MessageParseException {
		try {
			messageLength = IMessage.Packet
					.seekIntFromUnsignedShort(buf, false);
			type = readShort();
			// 统计消息数据; 暂时注释掉;
			// StatisticsLoggerHelper.logMessageRecived(this);
			return readBody();
		} catch (Exception e) {
			throw new MessageParseException(String.format(
					" Type: %d, typeName: %s", this.type,
					MessageTypeUtil.getMessageTypeName(this.type)), e);
		}
	}

	/**
	 * 取得消息的当前长度
	 * 
	 * @return -1,该消息还未与ByteBuffer绑定;否则返回ByteBuffer的位置
	 */
	public final int getLength() {
		return this.messageLength;
	}

	protected final void setLength(int length) {
		this.messageLength = length;
	}

	@Override
	public short getType() {
		return this.type;
	}

	protected void setType(short type) {
		this.type = type;
	}

	@Override
	public String getTypeName() {
		if (typeName == null) {
			return this.getClass().getSimpleName();
		}
		return this.typeName;
	}

	protected void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 使用IMessageBase.ENCODE_MESSAGE_LENGTH作为缓存区初始化的大小
	 */
	@Override
	public int getInitBufferLength() {
		return IMessage.ENCODE_MESSAGE_LENGTH;
	}

	protected void writeByte(int data) {
		buf.put((byte) data);
	}

	protected void writeShort(int data) {
		buf.putShort((short) data);
	}

	protected void writeInt(int data) {
		buf.putInt(data);
	}

	protected void writeInteger(int data) {
		buf.putInt(data);
	}

	protected void writeInt(float data) {
		buf.putInt((int) (data + 0.5));
	}

	protected void writeLong(long data) {
		buf.putLong(data);
	}

	protected void writeDate(Date data) {
		if (data != null) {
			buf.putLong(data.getTime());
		} else {
			buf.putLong(0L);
		}

	}

	protected void writeFloat(float data) {
		buf.putFloat(data);
	}

	protected void writeDouble(double data) {
		buf.putDouble(data);
	}

	protected void writeBytes(byte[] data) {
		buf.put(data);
	}

	protected void writeBoolean(boolean data) {
		buf.put(data ? (byte) 1 : (byte) 0);
	}

	protected void writeString(String str) {
		writeString(str, SharedConstants.DEFAULT_CHARSET);
	}

	protected void writeString(String str, String charset) {
		try {
			if (str == null) {
				buf.putShort((short) 0);
				return;
			}
			byte[] bytes = str.getBytes(charset);
			buf.putShort((short) bytes.length);
			buf.put(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将msg的数据写入到该消息的ByteBuffer数据缓存中
	 * 
	 * @param msg
	 * @throws MessageParseException
	 */
	protected void writeMessage(IMessage msg) throws MessageParseException {
		IoBuffer _buf = IoBuffer.allocate(msg.getInitBufferLength());
		_buf.setAutoExpand(true);
		msg.setBuffer(_buf);
		msg.write();
		_buf.flip();
		this.buf.put(_buf);
		msg.setBuffer(null);
		_buf.free();
	}

	/**
	 * 将子消息的数据写入当前消息的数据缓存中
	 * 
	 * @param msg
	 * @throws MessageParseException
	 */
	protected void writeSubMessage(BaseMessage msg) {
		msg.setBuffer(this.buf);
		msg.writeBody();
	}

	/**
	 * 将msg除去消息头以外的,消息包体数据写入的该消息的ByteBuffer数据缓存中
	 * 
	 * @param msg
	 * @throws MessageParseException
	 */
	protected void writeMessageWithoutHead(IMessage msg)
			throws MessageParseException {
		IoBuffer _buf = IoBuffer.allocate(msg.getInitBufferLength());
		_buf.setAutoExpand(true);
		msg.setBuffer(_buf);
		msg.write();
		_buf.flip();
		_buf.position(HEADER_SIZE);
		_buf.compact();
		_buf.flip();
		this.buf.put(_buf);
		msg.setBuffer(null);
		_buf.free();
	}

	/**
	 * 
	 * @param length
	 * @param type
	 * @param msg
	 * @throws MessageParseException
	 */
	protected void readMessage(IMessage msg) throws MessageParseException {
		int _length = Packet.popPacketLength(this.buf);
		short _type = Packet.popPacketType(this.buf);
		IoBuffer _buf = IoBuffer.allocate(_length);
		_buf.putShort((short) _length);
		_buf.putShort(_type);
		byte[] bytes = new byte[_length - 4];
		readBytes(bytes);
		_buf.put(bytes);
		_buf.flip();
		msg.setBuffer(_buf);
		msg.read();
		msg.setBuffer(null);
		_buf.free();
	}

	protected byte readByte() {
		return buf.get();
	}

	protected short readShort() {
		return buf.getShort();
	}

	protected int readInt() {
		return buf.getInt();
	}

	protected int readInteger() {
		return buf.getInt();
	}

	protected long readLong() {
		return buf.getLong();
	}

	protected Date readDate() {
		long time = buf.getLong();
		if (time > 0) {
			return new Date(time);
		} else {
			return null;
		}
	}

	protected float readFloat() {
		return buf.getFloat();
	}

	protected double readDouble() {
		return buf.getDouble();
	}

	protected boolean readBoolean() {
		return buf.get() == 1 ? true : false;
	}

	protected void readBytes(byte[] bytes) {
		buf.get(bytes);
	}

	/**
	 * 读byte数组：用于blob传输
	 * 
	 * 1.读长度 2.读内容
	 * 
	 * @return
	 */
	protected byte[] readByteArray() {
		byte[] _data = null;
		int _length = readInt();
		if (_length > 0) {
			_data = new byte[_length];
			for (int i = 0; i < _length; i++) {
				_data[i] = buf.get();
			}
		}
		return _data;
	}

	/**
	 * 写byte数组：用于blob传输
	 * 
	 * 1.写长度 2.写内容
	 * 
	 * @return
	 */
	protected void writeByteArray(byte[] datas) {
		if (datas != null && datas.length > 0) {
			int _length = datas.length;
			writeInt(_length);
			for (int i = 0; i < _length; i++) {
				writeByte(datas[i]);
			}
		} else {
			writeInt(0);
		}
	}

	protected String readString() {
		return readString(SharedConstants.DEFAULT_CHARSET);
	}

	protected String readString(String charset) {
		short len = buf.getShort();
		byte[] bytes = new byte[len];
		buf.get(bytes);
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 根据当前buf的位置,修正消息头的长度字段
	 * 
	 * @throws IllegalArgumentException
	 *             如果消息的长度超出了short的最大值(65535),或者为0则会抛出此异常
	 */
	protected void fixHeaderLength() {
	}

	/**
	 * 设置该消息当前操作的ByteBuffer,即BaseMessage的read是从buf中读取数据,而write是将数据写入到buf中
	 * 
	 * @param buf
	 */
	public void setBuffer(IoBuffer buf) {
		this.buf = buf;
	}

	/**
	 * 获取当前Buff，用于在其他Util类中直接对Buff进行写操作
	 * 
	 * @param buf
	 */
	public IoBuffer getBuffer() {
		return this.buf;
	}

	/**
	 * 读取buffer中的数据到消息中对应的属性中
	 * 
	 * @return true,读取成功;false,读取失败
	 */
	protected abstract boolean readBody();

	/**
	 * 将消息的属性按照消息格式写入到buffer中
	 * 
	 * @return true,写入成功;false,写入失败
	 */
	protected abstract boolean writeBody();

	private final static String[] toStringExcludeFields = { "buf",
			"messageLength", "type", "typeName", "session" };

	/**
	 * 此方法效率较低，应只在debug模式下调用
	 */
	@Override
	public String toString() {
		ReflectionToStringBuilder _builder = new BaseToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE, toStringExcludeFields);
		return _builder.toString() + getType();
	}

}