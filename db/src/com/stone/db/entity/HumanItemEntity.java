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
	@Column(name = "templateId")
	private int templateId;
	@Column(name = "humanGuid")
	private long humanGuid;
	@Column(name = "count")
	private int count;

	protected HumanItemEntity(Builder builder) {
		super(builder);
	}

	public HumanItemEntity() {
		this(HumanItemData.newBuilder());
	}

	@Override
	public Long getId() {
		return id;
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setId(long id) {
		this.id = id;
	}

}
