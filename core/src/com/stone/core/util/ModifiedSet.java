package com.stone.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.stone.core.entity.IEntity;

/**
 * 
 * 维护更改过的实体的数据结构;
 * 
 * @author crazyjohn
 */
public final class ModifiedSet<E extends IEntity<?>> {
	private final Queue<E> modifiedQueue = new ConcurrentLinkedQueue<E>();
	private final Map<Serializable, E> existMap = new HashMap<Serializable, E>();

	/**
	 * 增加有修改的记录,如果已经有修改的,则不会再增加该记录
	 * 
	 * @param e
	 * @return true,增加成功,false,增加失败
	 */
	public boolean addModified(E e) {
		if (e == null) {
			return false;
		}
		if (existMap.containsKey(e.getId()) && existMap.get(e.getId()) == e) {
			return false;
		}
		modifiedQueue.add(e);
		existMap.put(e.getId(), e);
		return true;
	}

	/**
	 * 取得指定条数的有修改的BigCharacter
	 * 
	 * @param size
	 *            指定记录的条数
	 * @return
	 */
	public List<E> getModified(final int size) {
		if (size <= 0) {
			throw new IllegalArgumentException(
					"The size must be greater than zero.");
		}
		// TODO:对于同一个Entity，Queue中后面的数据永远比前面的要新，在取的时候可以直接用后面的覆盖前面的么？
		List<E> result = new ArrayList<E>(size);
		for (int i = 0; i < size; i++) {
			E entity = this.modifiedQueue.poll();
			// 确保该消息确实是被修改过的
			if (entity != null && this.existMap.containsKey(entity.getId())) {
				// 从已经存在的记录中去掉
				this.existMap.put(entity.getId(), null);
				result.add(entity);
			} else {
				break;
			}
		}
		return result;
	}

	public int size() {
		return this.modifiedQueue.size();
	}

	/**
	 * 删除修改记录
	 * 
	 * @param id
	 *            记录对应的ID
	 * @return
	 */
	public boolean removeModified(Serializable id) {
		if (id == null) {
			return false;
		}
		return existMap.remove(id) != null;
	}
}