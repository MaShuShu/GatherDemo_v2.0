package com.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.modules.sys.orm.Permission;
import com.modules.sys.util.NetStarDao;

public interface PermissionDao extends NetStarDao<Permission>{
	
	/**
	 * ����Ȩ����Ϣ
	 */
	public void save(Permission pers);

	/**
	 * ��ȡĿ¼
	 * @return
	 */
	public List<Permission> getMenu(@Param(value="username")String username,@Param(value="menu") String menu);
	
	/**
	 * ����moduleidɾ����Ϣ
	 * @param moduleid
	 */
	public void delByModuleid(@Param(value="moduleid")String moduleid);
}
