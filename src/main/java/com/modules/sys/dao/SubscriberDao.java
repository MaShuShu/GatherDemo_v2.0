package com.modules.sys.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.modules.sys.orm.Subscriber;
import com.modules.sys.util.NetStarDao;

public interface SubscriberDao extends NetStarDao<Subscriber>{
	
	/** 
	 * �����ʺŲ�ѯ 
	 */
	public Subscriber getByUserName(String userName);
	
	/** 
	 * �����ʺŲ�ѯ��ɫ 
	 **/
	public Set<String> getRoles(String userName);
	
	/**
	 * �����ʺŲ�ѯȨ��
	 **/
	public Set<String> getPermissions(String userName);
	
	/**
	 * �����û���Ϣ
	 * @param sub
	 */
	public void save(Subscriber sub);
	
	/**
	 * �����û���Ϣ
	 * @param sub
	 */
	/*public void update(Subscriber sub);*/
	
	/**
	 * ɾ���û���Ϣ
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * ����id�����û���Ϣ
	 * @return
	 */
	public Subscriber findOne(String id);
	
	/**
	 * �г��û���Ϣ
	 * @return
	 */
	public List<Subscriber> queryList(Map<String,String> map);
	
	/**
	 * �û�״̬
	 * @param map
	 */
	public void editActivity(Map<String,String> map);
	
	/**
	 * ����ɾ��
	 * @param ids
	 */
	public void deleteUser(List<String> ids);
}
