package com.modules.sys.constant;

/**
 * �˵���Ŀ¼����
 * @author Acer
 *
 */
public enum ModuleType {
	
	dir("0","Ŀ¼"),menu("1","�˵�");

	public String type;
	public String name;
	
	ModuleType(String type,String name){
		this.type = type;
		this.name = name;
	}
}
