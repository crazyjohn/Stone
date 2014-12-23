package com.stone.game.player;

import org.apache.mina.core.session.IoSession;

import com.stone.core.state.IState;
import com.stone.core.state.IStateManager;
import com.stone.game.human.Human;

/**
 * 游戏玩家对象;
 * @author crazyjohn
 *
 */
public class Player implements IStateManager {
	/** 当前绑定的角色 */
	private Human human;
	/** 绑定的回话 */
	private IoSession session;
	/** 当前状态 */
	private IState currentState;

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	@Override
	public IState getCurrentState() {
		return currentState;
	}

	@Override
	public IState setCurrentState(IState state) {
		currentState = state;
		return currentState;
	}

	@Override
	public boolean canTransferStateTo(IState state) {
		return currentState.canTransferTo(state);
	}

	@Override
	public boolean transferStateTo(IState state) {
		if (!canTransferStateTo(state)) {
			return false;
		}
		setCurrentState(state);
		return true;
	}
}
