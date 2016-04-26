package com.modules.base.dao;

import com.modules.base.orm.FileInfo;
import com.modules.sys.util.NetStarDao;

public interface FileInfoDao extends NetStarDao<FileInfo>{
	
	/**
	 * ����
	 */
	public void insertFile(FileInfo file);
	
}
