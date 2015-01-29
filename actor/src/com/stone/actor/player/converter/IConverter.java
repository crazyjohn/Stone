package com.stone.actor.player.converter;

public interface IConverter<From, To> {

	public To convertFrom(From entity);

	public From convertTo(To toObject);

}
