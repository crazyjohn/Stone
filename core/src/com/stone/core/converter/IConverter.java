package com.stone.core.converter;

/**
 * 转换器接口;
 * 
 * @author crazyjohn
 *
 * @param <From>
 * @param <To>
 */
public interface IConverter<From, To> {

	public To convertFrom(From entity);

	public From convertTo(To toObject);

}
