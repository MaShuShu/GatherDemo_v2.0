package com.modules.sys.dao;

import java.util.List;

import com.modules.sys.orm.Module;
import com.modules.sys.util.NetStarDao;

public interface ModuleDao extends NetStarDao<Module> {
	
	/**
	 * ��ȡ���ڵ�
	 * @return
	 */
	public List<Module> getRoot();
	
	/**
	 * ���ݸ���id��ȡ
	 * @param pid
	 * @return
	 */
	public List<Module> getByPid(String pid);
	
	
	
}
