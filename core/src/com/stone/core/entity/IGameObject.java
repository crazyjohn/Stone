package com.stone.core.entity;

/**
 * 游戏业务对象接口;
 * 
 * @author crazyjohn
 *
 */
public interface IGameObject<Entity, GameObject> {
	
	public Entity toEntity();
	
	public GameObject fromEntity(Entity entity);
}
