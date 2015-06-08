package com.stone.tools.template;

/**
 * The template object;
 * 
 * @author crazyjohn
 *
 */
public class TemplateObject implements ITemplateObject {
	protected String group;

	public TemplateObject(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return super.toString() + " group: \n" + group;
	}

}
