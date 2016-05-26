package com.modules.sys.svc;

import com.modules.base.orm.Result;

import net.sf.json.JSONArray;

public interface PermissionSVC {

	public JSONArray queryPermit(String username,String menu);
	
	/**
	 * ���ݽ�ɫid��ȡȨ��
	 * @param id
	 * @return
	 */
	public JSONArray queryByRoleId(String id);
	
	/**
	 * ����Ȩ��
	 * @param roleid
	 * @param permitids
	 * @return
	 */
	public Result savePermit(String roleid,String permitids);
	
}
