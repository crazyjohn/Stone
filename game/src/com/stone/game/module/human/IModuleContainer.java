package com.stone.game.module.human;

public interface IModuleContainer {

	public void registerModule(IHumanModule module);

	public <M> M getModule(Class<M> moduleClass);

}
