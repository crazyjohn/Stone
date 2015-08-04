package com.stone.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.stone.core.entity.BaseProtobufEntity;
import com.stone.core.entity.IHumanSubEntity;
import com.stone.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.stone.proto.entity.Entities.HumanItemData;
import com.stone.proto.entity.Entities.HumanItemData.Builder;

/**
 * Item entity;
 * 
 * @author crazyjohn
 *
 */
@Entity
@Table(name = "item")
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanItemEntity extends BaseProtobufEntity<HumanItemData.Builder> implements IHumanSubEntity {
	@Id
	@Column(name = "id")
	private long id;
	private long humanGuid;

	public HumanItemEntity(Builder builder) {
		super(builder);
	}

	public HumanItemEntity() {
		this(HumanItemData.newBuilder());
	}

	@Override
	public long getId() {
		return id;
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public void setId(long id) {
		this.id = id;
	}

}
