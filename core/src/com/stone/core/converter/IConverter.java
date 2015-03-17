package com.stone.core.converter;

/**
 * Converter;
 * 
 * @author crazyjohn
 *
 * @param <From>
 * @param <To>
 */
public interface IConverter<From, To> {

	/**
	 * Convert from;
	 * 
	 * @param from
	 * @return
	 */
	public To convertFrom(From from);

	/**
	 * Convert to;
	 * 
	 * @param to
	 * @return
	 */
	public From convertTo(To to);

}
