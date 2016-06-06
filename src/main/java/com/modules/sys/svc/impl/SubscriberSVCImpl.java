package com.modules.sys.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.modules.base.orm.Result;
import com.modules.base.orm.User;
import com.modules.sys.dao.SubscriberDao;
import com.modules.sys.orm.Subscriber;
import com.modules.sys.svc.SubscriberSVC;
import com.modules.sys.util.PasswordHelper;
import com.util.ReflectUtils;

@Service("subscriberSVC")
public class SubscriberSVCImpl implements SubscriberSVC{
	
	@Autowired
	private SubscriberDao subDao;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * �����û����Ʋ�ѯ
	 * @param userName
	 * @return
	 */
	@Override
	public Subscriber getUserByName(String userName){
		if(userName != null){
			Subscriber sub =  subDao.getByUserName(userName);
			if(sub!=null){
				return sub;
			}
		}
		return null;
	}
	
	/**
	 * �����û�����ѯ��ɫ
	 * @param userName
	 * @return
	 */
	@Override
	public Set<String> getRoles(String userName){
		if(userName != null){
			Set<String> roles =  subDao.getRoles(userName);
			if(roles.size()>0){
				return roles;
			}
		}
		return null;
	}
	
	/**
	 * �����û��ʺŲ�ѯȨ��
	 * @param userName
	 * @return
	 */
	@Override
	public Set<String> getPermissions(String userName){
		if(userName != null){
			Set<String> permissions = subDao.getPermissions(userName);
			if(permissions.size()>0){
				return permissions;
			}
		}
		return null;
	}
	
	/**
	 * �����û���Ϣ
	 * @param sub
	 * @return 
	 */
	@Override
	public Result saveUser(Subscriber sub){
		PasswordHelper passwordHelper = new PasswordHelper();
		try{
			sub.setAge(sub.getAge()==""?"0":sub.getAge());
			sub.setId(UUID.randomUUID().toString());
			sub.setPassword("12345");  //һ��ʼʱ����
			passwordHelper.encryptPassword(sub);
			sub.setIfactivate("0");  //Ĭ�ϼ���
			sub.setIfspeak("0");  //Ĭ�Ͽ��Կ���
			subDao.insertSelective(sub);
			return Result.ok();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Result.error("ϵͳ����ʱ����");
	}
	
	/**
	 * 
	 * @param sub
	 * @return
	 */
	@Override
	public Result editUser(Subscriber sub){
		if(sub != null){
			Subscriber o_sub = subDao.selectByPrimaryKey(sub.getId());
			ReflectUtils.copy(o_sub, sub, true);
			Example example = new Example(Subscriber.class);
			example.createCriteria().andEqualTo("id",o_sub.getId());
			subDao.updateByExample(o_sub, example);
			return Result.ok();
		}
		return Result.error("����ʧ�ܣ�");
	}
	
	/**
	 * ����id��ѯ
	 * @param id
	 * @return
	 */
	@Override
	public Subscriber findOne(String id){
		if(id != null || !"".equals(id)){
			Subscriber sub = subDao.findOne(id);
			return sub;
		}
		return null;
	}
	
	/**
	 * ����id��ѯ
	 * @param id
	 * @return
	 */
	@Override
	public List<Subscriber> queryUser(Subscriber sub){
		if(sub != null){
			Example example = new Example(Subscriber.class);
			/*if(sub == null){
				example.createCriteria()
				.andEqualTo("roleid",sub.getRoleid())  //id
				.andEqualTo("sex",sub.getSex())  //�Ա�
				.andEqualTo("ifactivate",sub.getIfactivate())  //�Ƿ񼤻�
				.andEqualTo("lastlogintime",sub.getLastlogintime());
			}*/
			List<Subscriber> subList = subDao.selectByExample(example);
			return subList;
		}
		return null;
	}
	
	/**
	 * ����id��ѯ
	 * @param id
	 * @return
	 */
	@Override
	public List<Subscriber> queryUserByXml(Subscriber sub){
		Map map = new HashMap();
		if(sub!=null){
			if(!"".equals(sub.getUsername())){
				map.put("username",sub.getUsername());
			}
			if(!"".equals(sub.getRoleid())){
				map.put("roleid",sub.getRoleid());
			}
		}
		List<Subscriber> subList = subDao.queryList(map);
		return subList;
	}
	
	/**
	 * ����idɾ��
	 * @param id
	 */
	@Override
	public void deltUser(String id){
		if(id != null || !"".equals(id)){
			subDao.delete(id);
		}
	}
	
	@Override
	public Result editActivity(String id,String ifactivate,String ifspeak){
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("ifactivate", ifactivate);
		map.put("ifspeak", ifspeak);
		try{ 
			subDao.editActivity(map);
			return Result.ok();
		}catch(Exception e){
			e.printStackTrace();
			return Result.error("��������");
		}
	}

	@Override
	public Result editUserRole(String id,String roleid) {
		if(id!=null){
			Subscriber sub = subDao.selectByPrimaryKey(id);
			if(sub!=null){
				sub.setRoleid(roleid);
				Example example = new Example(Subscriber.class);
				example.createCriteria().andEqualTo("id", id);
				subDao.updateByExample(sub, example);
				return Result.ok();
			}
		}
		return Result.error("�����ɫʧ�ܣ�");
	}

	@Override
	public Result savePass(String userids) {
		PasswordHelper passwordHelper = new PasswordHelper();
		if(userids != null){
			JSONArray ids = JSONArray.fromObject(userids);
			for (int i=0;i<ids.size();i++) {
				Subscriber sub = subDao.selectByPrimaryKey(ids.get(i));
				if(sub != null){
					sub.setPassword("12345");  //��ʼ������
					passwordHelper.encryptPassword(sub);
					Example example = new Example(Subscriber.class);
					example.createCriteria().andEqualTo("id",ids.get(i));
					subDao.updateByExample(sub, example);
				}
			}
			return Result.ok();
		}
		return Result.error("��ʼ������ʧ�ܣ�");
	}

	
	@Override
	public Result deltUsers(String ids,User user) {
		List<String> ListIds = new ArrayList<String>();
		try{
			if(ids != null){
				JSONArray userids = JSONArray.fromObject(ids);
				for(int i=0; i<userids.size(); i++){
					//����ɾ���Լ���
					if(!user.getId().equals(userids.get(i).toString())){
						ListIds.add(userids.get(i).toString());
					}
				}
			}
			//��ֻѡ�Լ�ɾ��ʱ������ɾ��
			if(ListIds.size()>0){
				subDao.deleteUser(ListIds);
			}else{
				return Result.error("����������߲���ɾ���Լ���");
			}
			return Result.ok();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Result.error("ɾ��ʧ�ܣ�");
	}

	@Override
	public void login(User user) {
		//�û�û�е�¼
		if(user==null){
			return;
		}
	}

	@Override
	public void logout() {
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
	}

	@Override
	public Boolean getUserByRoleId(String rid) {
		if(rid != null || !"".equals(rid)){
			Example example = new Example(Subscriber.class);
			example.createCriteria().andEqualTo("roleid", rid);
			int i = subDao.selectCountByExample(example);
			if(i<=0){
				return true;
			}
		}
		return false;
	}

}
