package com.modules.sys.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.modules.sys.dao.RoleDao;
import com.modules.sys.orm.Role;
import com.util.ReflectUtils;

@Service("roleSVC")
public class RoleSVC {
	
	@Autowired
	private RoleDao dao;
	
	/**
	 * ����������ѯ
	 * @param role
	 * @return
	 */
	public Role select(Role role){
		
		//��ѯ����
		Example example = new Example(Role.class);
		example.createCriteria()
			.andEqualTo("id",role.getId())  //id
			.andEqualTo("rolename",role.getRolename());  //��ɫ����
		dao.selectByExample(example);
		return null;
	}
	
	/**
	 * ��ѯȫ��
	 * @param role
	 * @return
	 */
	public List<Role> queryList(Role role){
		Example example = new Example(Role.class);
		Criteria criteria = example.createCriteria();
		if(role.getRoleno()!=null){
			criteria.andEqualTo(role.getRoleno());
		}
		if(role.getRolename()!=null){
			criteria.andLike("rolename", role.getRolename());
		}
		
		List<Role> list = dao.selectByExample(example);
		
		return list;
	}
	
	
	/**
	 * �����ɫ
	 * @param role
	 */
	public void save(Role role){
		if(role != null){
			//dao.insert(role);
			dao.save(role);
		}
	}
	
	/**
	 * ���½�ɫ
	 * @param role
	 */
	public void update(Role role){
		if(role != null){
			Role o_role = dao.selectByPrimaryKey(role.getId());
			ReflectUtils.copy(o_role, role, true);
			Example example = new Example(Role.class);
			example.createCriteria().andEqualTo("id",role.getId());
			
			dao.updateByExample(o_role, example);
		}
	}
	
}
