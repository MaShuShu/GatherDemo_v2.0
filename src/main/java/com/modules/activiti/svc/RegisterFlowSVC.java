package com.modules.activiti.svc;

import java.util.List;

import org.activiti.engine.task.Task;

import com.modules.activiti.orm.Flow;
import com.modules.base.orm.Page;


/**
 * �û�ע���������
 * @author Acer
 *
 */
public interface RegisterFlowSVC {
	
	/**
	 * �������̣�����
	 * @param id
	 */
	public void startProcessInstance(String id,String key);
	
	/**
	 * ����Լ�������
	 */
	public void completeMyPersonalTask(String taskId);
	
	/**
	 * �鿴����
	 */
	public Page<Flow> findMyPersonalTask(String id,int page,int rows);
	
}
