package com.modules.sys.dao;

import com.modules.sys.orm.Role;
import com.modules.sys.util.NetStarDao;

public interface RoleDao extends NetStarDao<Role> {
	
	/**
	 * �����ɫ
	 * @param role
	 */
	public void save(Role role);
	
}
