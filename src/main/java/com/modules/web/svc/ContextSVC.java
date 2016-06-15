package com.modules.web.svc;

import java.util.List;
import java.util.Map;

import com.modules.base.orm.Result;
import com.modules.web.orm.Context;

public interface ContextSVC {

	/**
	 * ����
	 * @param cont
	 * @return
	 */
	public Result save(Context cont);
	
	/**
	 * ����
	 * @param cont
	 * @return
	 */
	public Result edit(Context cont);
	
	/**
	 * ����
	 * @param id
	 * @return
	 */
	public Context findOne(String id);
	
	/**
	 * �б�
	 * @param map
	 * @return
	 */
	public List<Context> queryList();
}
