package com.modules.sys.svc;

import java.util.List;
import java.util.Map;

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
	 */
	public List<Log> queryLog(Log log);
	
}
