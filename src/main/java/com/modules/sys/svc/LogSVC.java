package com.modules.sys.svc;

import java.util.List;

import com.modules.base.orm.Result;
import com.modules.sys.orm.Log;

public interface LogSVC {

	/**
	 * ������־
	 * @param log
	 */
	public void saveLog(Log log);
	
	/**
	 * ɾ����־
	 * @param ids
	 */
	public Result deltLog(String ids);
	
	/**
	 * ��ѯ�б�
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<Log> queryLog(Log log);
	
	/**
	 * �鿴
	 * @param id
	 * @return
	 */
	public Log findOne(String id);
	
}
