package com.stone.game.player.state;

import com.stone.core.state.IState;

/**
 * 玩家状态类型;
 * @author crazyjohn
 *
 */
public enum PlayerState implements IState {
	NONE() {
		@Override
		public boolean canProcessMessage(short type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canTransferTo(IState state) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	/** 已连接 */
	CONNECTED() {
		@Override
		public boolean canProcessMessage(short type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canTransferTo(IState state) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	/** 已认证 */
	AUTHORIZED() {
		@Override
		public boolean canProcessMessage(short type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canTransferTo(IState state) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	/** 进入游戏中 */
	ENTERING() {
		@Override
		public boolean canProcessMessage(short type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canTransferTo(IState state) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	/** 游戏中 */
	GAMEING() {
		@Override
		public boolean canProcessMessage(short type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canTransferTo(IState state) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	/** 战斗中  */
	BATTLING() {
		@Override
		public boolean canProcessMessage(short type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canTransferTo(IState state) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	;

}
