package com.stone.core.template;

import java.util.Map;

/**
 * The template service;
 * 
 * @author crazyjohn
 *
 */
public interface ITemplateService {

	/**
	 * Load all templates;
	 * 
	 * @param templateFilePath
	 */
	public void loadAllTemplates(String templateFilePath);

	/**
	 * Get typed templates;
	 * 
	 * @param templateClass
	 * @return
	 */
	public <T extends ITemplateClass> Map<Integer, T> getTemplates(
			Class<T> templateClass);

	/**
	 * Get template object by class and id;
	 * 
	 * @param templateClass
	 * @param id
	 * @return
	 */
	public <T extends ITemplateClass> T getTemplateById(Class<T> templateClass,
			int id);
}
