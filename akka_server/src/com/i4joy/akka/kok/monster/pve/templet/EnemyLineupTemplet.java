/**
 * 
 */
package com.i4joy.akka.kok.monster.pve.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 敌人阵型模板
 * @author Administrator
 *
 */
public class EnemyLineupTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5116729859682034006L;
	
	private int id;
	
	List<Integer> enemies=new ArrayList<Integer>();

	/**
	 * 
	 */
	public EnemyLineupTemplet() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Integer> enemies) {
		this.enemies = enemies;
	}
	
	public void addEnemy(int enemyId){
		this.enemies.add(enemyId);
	}

}
