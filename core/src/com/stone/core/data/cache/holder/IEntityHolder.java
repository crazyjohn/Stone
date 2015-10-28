package com.stone.core.data.cache.holder;

import java.io.Serializable;
import java.util.Collection;

/**
 * 实体持有器接口;<br>
 * 大概分为两类;<br>
 * 
 * <pre>
 * 1. 是持有具有唯一性的实体;此类实体一旦更新,就会以新换旧;
 * 2. 持有集合类型的实体,比如角色的物品等;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface IEntityHolder<E> {

	/**
	 * 添加一个实体到实体持有器;
	 * 
	 * @param entity
	 *            需要添加的实体;
	 * @return true表示添加成功, false表示添加失败;
	 */
	public boolean add(E entity);

	/**
	 * 更新一个实体到持有器中;
	 * 
	 * @param entity
	 *            需要更新的实体;
	 * @return true表示更新成功, false表示更新失败;
	 */
	public boolean update(E entity);

	/**
	 * 移除指定ID的实体
	 * 
	 * @param id
	 */
	public void remove(Serializable id);

	/**
	 * 实体持有器中的数据是否被更改过;
	 * 
	 * @return true表示更改过;false表示没有被更改过;
	 */
	public boolean isModified();

	/**
	 * 重新设置更改标记为;
	 */
	public void resetModified();

	/**
	 * 移除持有器中的所有数据;
	 */
	public void removeAll();

	/**
	 * 获取持有器中的所有实体;
	 * 
	 * @return 返回持有器中的所有实体,不会返回NULL;
	 */
	public Collection<E> getEntities();
}
