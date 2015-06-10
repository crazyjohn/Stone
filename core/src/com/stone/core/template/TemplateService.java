package com.stone.core.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TemplateService implements ITemplateService {
	protected Map<Class<?>, Map<Integer, ITemplateClass>> allTemplates = new ConcurrentHashMap<Class<?>, Map<Integer, ITemplateClass>>();

	@Override
	public void loadAllTemplates(String templateFilePath) {
		// TODO 扫描指定的包，加载配置类
		// 2。根据对应的文件加载文件
		// 3. 每行记录对应一个object，使用反射映射字段 

	}

	@Override
	public <T extends ITemplateClass> Map<Integer, T> getTemplates(
			Class<T> templateClass) {
		@SuppressWarnings("unchecked")
		Map<Integer, T> templates = (Map<Integer, T>) allTemplates
				.get(templateClass);
		return templates;
	}

	@Override
	public <T extends ITemplateClass> T getTemplateById(Class<T> templateClass,
			int id) {
		Map<Integer, T> templates = this.getTemplates(templateClass);
		return templates.get(id);
	}

}
