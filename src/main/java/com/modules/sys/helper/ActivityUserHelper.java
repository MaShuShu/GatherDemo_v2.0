package com.modules.sys.helper;

import java.util.Collection;
import java.util.Iterator;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.modules.base.orm.Result;

@Component
public class ActivityUserHelper {
	
	@Autowired
	private SessionDAO sessionDAO;
	
	/**
	 * �ж��û��Ƿ����ߣ�'0'��ʾ�����ߣ�'1'��ʾ����
	 * @param name
	 * @return
	 */
	public String getActivityUser(String name){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session : sessions) {
			if(name.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))){
				return "1";
			}
		}
		return "0";
	}
	
	/**
	 * ǿ���û��˳�
	 * @param name
	 */
	public Result forceQuit(String name){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session : sessions) {
			if(name.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))){
				session.setTimeout(0);
				session.removeAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				return Result.ok();
			}
		}
		return Result.error("ǿ���߳�����");
	}
	

}
