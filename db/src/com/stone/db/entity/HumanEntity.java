package com.stone.db.entity;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mina.core.buffer.IoBuffer;

import com.stone.core.entity.BaseProtobufEntity;
import com.stone.proto.entity.Entities.HumanData;
import com.stone.proto.entity.Entities.HumanData.Builder;
import com.stone.proto.entity.Entities.HumanItemData;

/**
 * The human db entity;
 * 
 * @author crazyjohn
 *
 */
@Entity
@Table(name = "human")
public class HumanEntity extends BaseProtobufEntity<HumanData.Builder> {

	@Id
	@Column(name = "guid")
	private long guid;
	@Column(name = "playerId")
	private long playerId;
	@Column(name = "name")
	private String name;
	@Column(name = "level")
	private int level;

	protected HumanEntity(Builder builder) {
		super(builder);
	}

	public HumanEntity() {
		this(HumanData.newBuilder());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public Long getId() {
		return guid;
	}

	public long getGuid() {
		return guid;
	}

	public void setGuid(long guid) {
		this.guid = guid;
	}

	private IoBuffer allocateByteBuffer(int size) {
		return IoBuffer.allocate(size).setAutoExpand(true);
	}

	private static final int DEFAULT_BUFFER_SIZE = 64;

	private IoBuffer allocateByteBuffer() {
		return allocateByteBuffer(DEFAULT_BUFFER_SIZE);
	}

	@Column
	public byte[] getHumanItems() throws IOException {
		IoBuffer buffer = allocateByteBuffer();
		for (HumanItemData.Builder item : this.builder.getHumanItemsBuilderList()) {
			HumanItemEntity itemEntity = new HumanItemEntity(item);
			itemEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanItems(byte[] items) throws IOException {
		if (items != null) {
			IoBuffer buffer = IoBuffer.wrap(items);
			while (buffer.hasRemaining()) {
				HumanItemEntity entity = new HumanItemEntity();
				entity.read(buffer);
				this.builder.addHumanItems(entity.getBuilder());
			}
		}
	}

}
