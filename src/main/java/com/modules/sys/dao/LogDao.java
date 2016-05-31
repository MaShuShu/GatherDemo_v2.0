package com.modules.sys.dao;

import java.util.List;

import com.modules.sys.orm.Log;
import com.modules.sys.util.NetStarDao;

public interface LogDao extends NetStarDao<Log> {

	/**
	 * ����
	 * @param log
	 */
	public void saveLog(Log log);
	
	/**
	 * ɾ��
	 * @param ids
	 */
	public void deltLog(List<String> ids);
	
	/**
	 * ����
	 * @param map
	 * @return
	 */
	public List<Log> queryLog(Log log);
}
