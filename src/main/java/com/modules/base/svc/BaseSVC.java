package com.modules.base.svc;

import com.modules.base.orm.Result;

public interface BaseSVC<T> {
	
	/**
	 * ����
	 * @param t
	 * @return
	 */
	public Result saveBase(T t);

}
