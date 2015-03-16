package com.stone.core.game;

import com.stone.core.entity.IEntity;

/**
 * 游戏业务对象接口;
 * 
 * @author crazyjohn
 *
 */
public interface IGameObject<Entity extends IEntity, GameObject> {
	
	public Entity toEntity();
	
	public GameObject fromEntity(Entity entity);
}
