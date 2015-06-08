package com.stone.tools.template;

public class TemplateObject implements ITemplateObject {
	protected String group;

	public TemplateObject(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return super.toString() + " group: " + group;
	}

}
