/**
 * 
 */
package com.i4joy.akka.kok.monster.item;

import akka.actor.ActorRef;

import com.i4joy.akka.kok.common.Result;
import com.i4joy.akka.kok.monster.item.templet.ItemTemplet;
import com.i4joy.akka.kok.monster.player.PlayerEntity;

/**
 * @author Administrator
 *
 */
public enum ItemTypeEnum {
	UNUSABLE(0){

		@Override
		public Result<Object> use(ItemTemplet it, PlayerEntity pe, ActorRef sender) {
			return new Result<Object>(false);
		}
		
	},
	
	GIVE_MONEY(1){

		@Override
		public Result<Object> use(ItemTemplet it, PlayerEntity pe,
				ActorRef sender) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	GIVE_STAMINA_PVE(2){

		@Override
		public Result<Object> use(ItemTemplet it, PlayerEntity pe,
				ActorRef sender) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	GIVE_STAMINA_PVP(3){

		@Override
		public Result<Object> use(ItemTemplet it, PlayerEntity pe,
				ActorRef sender) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	CHEST(4){

		@Override
		public Result<Object> use(ItemTemplet it, PlayerEntity pe,
				ActorRef sender) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	CHEST_KEY(5){

		@Override
		public Result<Object> use(ItemTemplet it, PlayerEntity pe,
				ActorRef sender) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	;
	
	private final int type;
	
	private ItemTypeEnum(int type){
		this.type=type;
	}
	
	public abstract Result<Object> use(ItemTemplet it, PlayerEntity pe,ActorRef sender);
	
	public ItemTypeEnum getItemTypeEnum(int type){
		
		return null;
	}

	public int getType() {
		return type;
	}
	

}
