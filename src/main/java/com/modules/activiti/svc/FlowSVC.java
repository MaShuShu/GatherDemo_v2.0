package com.modules.activiti.svc;

public interface FlowSVC {
	
	/**
	 * �������̣�����
	 * @param id
	 */
	public void startProcessInstance(String id);
	
	/**
	 * ����Լ�������
	 */
	public void completeMyPersonalTask(String id);
	
	/**
	 * �鿴����
	 */
	public void findMyPersonalTask(String id);
	

}
