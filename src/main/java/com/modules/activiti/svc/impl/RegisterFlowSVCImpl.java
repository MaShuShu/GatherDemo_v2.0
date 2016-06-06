package com.modules.activiti.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.activiti.orm.Flow;
import com.modules.activiti.svc.RegisterFlowSVC;
import com.modules.base.orm.Page;
import com.modules.sys.svc.SubscriberSVC;

@Service("registerFlowSVC")
public class RegisterFlowSVCImpl implements RegisterFlowSVC {

	@Autowired
	private SubscriberSVC subSVC;
	
	@Autowired
	private ProcessEngine processEngine;
	
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Override
	public void startProcessInstance(String id,String key) {
		if(id != null && key != null){
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("userId", id);
			processEngine.getRuntimeService()
								.startProcessInstanceByKey(key,variables);
			//�Լ��������Զ��ύ
			Task task = processEngine.getTaskService().createTaskQuery().taskAssignee(id).singleResult();
			String taskId = task.getId();
			processEngine.getTaskService().complete(taskId);//������ִ�е����������ص�Service
		}
	}

	@Override
	public void completeMyPersonalTask(String taskId) {
		if(taskId != null || !"".equals(taskId)){
			processEngine.getTaskService()
			.complete(taskId);
		}
	}

	@Override
	public Page<Flow> findMyPersonalTask(String id,int page,int rows) {
		if(id != null || !"".equals(id)){
			List<Task> list = processEngine.getTaskService()
					.createTaskQuery()
					.taskCandidateUser(id)
					.orderByTaskCreateTime()
					.asc()
					.listPage((page-1)*rows, rows);
			//������
			Long total = processEngine.getTaskService().createTaskQuery().taskCandidateUser(id).count();
			if(list.size()>0){
				List<Flow> fList = new ArrayList<Flow>();
				for (Task task : list) {
					Flow f = new Flow();
					f.key = task.getId();
					f.name = task.getName();
					f.startTime = task.getCreateTime();
					f.userId = task.getCategory(); //����
					task.getDelegationState();  //��Ȩ
					task.getDescription(); //˵��
					task.getDueDate();  //����ʱ��
					task.getExecutionId();  //ִ��id
					task.getOwner(); // ����Ȩ
					task.getPriority();  //����
					fList.add(f);
				}
				Page<Flow> pages = new Page<Flow>();
				pages.setRows(fList);
				pages.setPageNumber(page);
				pages.setPageSize(rows);
				pages.setTotal(total);
				return pages;
			}
		}
		return null;
	}

}
