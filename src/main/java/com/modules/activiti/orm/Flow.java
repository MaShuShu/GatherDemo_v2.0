package com.modules.activiti.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class Flow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���̼�
	 */
	public String key;
	
	/**
	 * ��������
	 */
	public String name;
	
	/**
	 * ��������
	 */
	public String flowType;
	
	/**
	 * ����ʱ��
	 */
	public Date startTime;
	
	/**
	 * ����������
	 */
	public String initiator;
	
	/**
	 * �û�ID
	 */
	public String userId;

	/**
	 * �û�����
	 */
	public String userName;

	/**
	 * ���ӱ���
	 */
	public Map<String, Object> vars;

	/**
	 * ��������
	 */
	public String flowName;

	/**
	 * ���ص�����
	 */
	public ProcessInstance pi;

	/**
	 * ���ص�ǰ����������
	 */
	public Task currTask;

	/**
	 * ���ص�ǰ�����������б�
	 */
	public List<Task> currTasks;
	
	public Flow() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Flow(String key, String name, String flowType, Date startTime,
			String initiator, String userId, String userName,
			Map<String, Object> vars, String flowName, ProcessInstance pi,
			Task currTask, List<Task> currTasks) {
		super();
		this.key = key;
		this.name = name;
		this.flowType = flowType;
		this.startTime = startTime;
		this.initiator = initiator;
		this.userId = userId;
		this.userName = userName;
		this.vars = vars;
		this.flowName = flowName;
		this.pi = pi;
		this.currTask = currTask;
		this.currTasks = currTasks;
	}


	@Override
	public String toString() {
		return "Flow [key=" + key + ", name=" + name + ", flowType=" + flowType
				+ ", startTime=" + startTime + ", initiator=" + initiator
				+ ", userId=" + userId + ", userName=" + userName + ", vars="
				+ vars + ", flowName=" + flowName + ", pi=" + pi
				+ ", currTask=" + currTask + ", currTasks=" + currTasks + "]";
	}


}
