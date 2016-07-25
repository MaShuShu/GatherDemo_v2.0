package com.modules.sys.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.base.orm.Result;
import com.modules.base.orm.TreeNode;
import com.modules.sys.dao.ModuleDao;
import com.modules.sys.dao.PermissionDao;
import com.modules.sys.orm.Module;
import com.modules.sys.svc.ModuleSVC;
import com.util.ReflectUtils;

@Service("moduleSVC")
public class ModuleSVCImpl implements ModuleSVC {

	@Autowired
	private ModuleDao dao;
	
	@Autowired
	private PermissionDao permsDao;
	
	/**
	 * ����������Ϣ
	 * @param mod
	 * @return
	 */
	@Override
	public Result saveModule(Module mod){
		fillInfo(mod);
		try{
			if(mod.getId()==null || "".equals(mod.getId())){
				mod.setId(null);
				dao.insert(mod);
			}else{
				Module o_mod = dao.selectByPrimaryKey(mod.getId());
				//��bean���п�������ֹҳ�洫��null������֮ǰû���޸ĵ�ֵ
				ReflectUtils.copy(o_mod, mod, true);
				dao.updateByPrimaryKey(o_mod);
			}
			return Result.ok();
		}catch(Exception e){
			e.printStackTrace();
			return Result.error("ϵͳ����");
		}
	}
	
	/**
	 * ������Ϣת��
	 * @param mod
	 * @return
	 */
	private Module fillInfo(Module mod){
		mod.setPid("".equals(mod.getPid())?null:mod.getPid());
		mod.setDir("on".equals(mod.getDir())?"1":"0");
		mod.setMenu("on".equals(mod.getMenu())?"1":"0");
		mod.setIfopen("on".equals(mod.getIfopen())?"1":"0");
		mod.setValid("0");  //'0'��ʾ��Ч��
		return mod;
	}
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	@Override
	public Result deltModule(String id){
		try{
			dao.deleteByPrimaryKey(id);
			
			//ͨ��mapper��delete����
			/*Example example = new Example(Permission.class);
            example.createCriteria().andEqualTo("moduleid", id);
            permsDao.deleteByExample(example);*/
			
			permsDao.delByModuleid(id);
			
			return Result.ok();
		}catch(Exception e){
			e.printStackTrace();
			return Result.error("ɾ������");
		}
	}
	
	/**
	 * ��ȡ�˵�
	 * @return
	 */
	@Override
	public List<TreeNode> getMenuTrees(){
		List<TreeNode> roots = new ArrayList<TreeNode>();
		
		List<Module> root = dao.getRoot();
		if(root.size()>0){
			for (int i=0;i<root.size();i++) {
				TreeNode node = new TreeNode();
				roots.add(node = transformNode(root.get(i)));
				node.children = getChildren(node.id);
			}
		}
		return roots;
	}
	
	/**
	 * �ݹ�����ӽڵ�
	 * @param pId
	 * @return
	 */
	private List<TreeNode> getChildren(String pId){
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		List<Module> children = dao.getByPid(pId);
		if(children.size()>0){
			for (Module module : children) {
				nodes.add(node = transformNode(module));
				node.children = getChildren(node.id);
			}
		}
		return nodes;
	}
	
	/**
	 * ת��
	 * @param mod
	 * @return
	 */
	private TreeNode transformNode(Module mod){
		TreeNode node = new TreeNode();
		Map<String,Object> map = new HashMap<String,Object>();
		node.id = mod.getId();
		node.pid = mod.getPid();
		node.name = mod.getName();
		if("1".equals(mod.getIfopen())){
			node.open = "true";
		}
		map.put("dir", mod.getDir());
		map.put("menu", mod.getMenu());
		map.put("rank", mod.getRank());
		map.put("url", mod.getUrl());
		map.put("ifopen", mod.getIfopen());
		map.put("permitno", mod.getPermitno());
		map.put("permitmark", mod.getPermitmark());
		node.attributes = map;
		return node;
	}

}
