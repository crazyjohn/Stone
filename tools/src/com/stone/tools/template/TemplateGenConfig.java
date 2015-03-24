package com.stone.tools.template;

/**
 * 
 * 
 * 
 */
public class TemplateGenConfig {
	/** 文件名字 */
	private String fileName;
	/** 类路径 */
	private String path;
	/** 全称类名 */
	private String className;
	/** 类的注释 */
	private String comment;
	/** 父类 */
	private String father;

	public TemplateGenConfig(String fileName, String path, String className,
			String father, String comment) {
		this.fileName = fileName;
		this.path = path;
		this.className = className;
		this.comment = comment;
		this.father = father;
		if (father == null || father.equals("")) {
			this.father = "TemplateObject";
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getClassName() {
		return className;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
