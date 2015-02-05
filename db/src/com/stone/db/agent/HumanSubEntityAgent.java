package com.stone.db.agent;

import java.io.Serializable;

import com.stone.core.entity.IEntity;

public class HumanSubEntityAgent extends BaseEntityAgent {
	protected HumanAgent humanAgent;

	public HumanSubEntityAgent(HumanAgent humanAgent) {
		this.humanAgent = humanAgent;
	}

	@Override
	public void update(IEntity<?> entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}

	@Override
	public Serializable insert(IEntity<?> entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	public void delete(IEntity<?> entity) {
		// TODO Auto-generated method stub
		super.delete(entity);
	}

}
