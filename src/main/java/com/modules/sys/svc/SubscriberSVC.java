package com.modules.sys.svc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.modules.base.orm.Result;
import com.modules.base.orm.User;
import com.modules.sys.orm.Subscriber;

public interface SubscriberSVC {
	
	/**
	 * ��־��¼�û���¼
	 * @param sub
	 * @return
	 */
	public void login(User user);
	
	public void logout();
	
	public Subscriber getUserByName(String userName);
	
	public Set<String> getRoles(String userName);
	
	public Set<String> getPermissions(String userName);
	
	public Result saveUser(Subscriber sub);
	
	public Result editUser(Subscriber sub);
	
	public Subscriber findOne(String id);
	
	public List<Subscriber> queryUser(Subscriber sub);
	
	public List<Subscriber> queryUserByXml(Subscriber sub);
	
	public void deltUser(String id);
	
	public Result editActivity(String id,String ifactivate,String ifspeak);
	
	/**
	 * �����ɫ
	 * @param id
	 * @return
	 */
	public Result editUserRole(String id,String roleid);
	
	/**
	 * ��������
	 * @param userid
	 * @param pass
	 * @return
	 */
	public Result savePass(String userid);
	
	/**
	 * ɾ���û�
	 * @param ids
	 * @return
	 */
	public Result deltUsers(String ids,User user);
	
	/**
	 * �ж��Ƿ���ڸý�ɫ���û�
	 * @param rid
	 * @return
	 */
	public Boolean getUserByRoleId(String rid);
	
}
