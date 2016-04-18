package com.modules.sys.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.modules.base.orm.Page;
import com.modules.base.orm.Result;
import com.modules.base.orm.TreeNode;
import com.modules.base.orm.User;
import com.modules.sys.orm.Module;
import com.modules.sys.orm.Role;
import com.modules.sys.orm.Subscriber;
import com.modules.sys.svc.ModuleSVC;
import com.modules.sys.svc.RoleSVC;
import com.modules.sys.svc.SubscriberSVC;

@Controller
@RequestMapping("sys/")
public class SysCTRL {
	
	@Autowired
	private SubscriberSVC userSVC;
	
	@Autowired
	private RoleSVC roleSVC;
	
	@Autowired
	private ModuleSVC moduleSVC;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "userIndex", method = RequestMethod.GET)
	public String userIndex(){
		return "/sys/user/UserIndex";
	}
	
	/**
	 * ��ת������ҳ��
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="addUser",method = RequestMethod.GET)
	public String addUser(String id,Model model){
		if(id != null){
			Subscriber sub = userSVC.findOne(id);
			model.addAttribute("item", sub);
		}
		return "/sys/user/AddUser";
	}
	
	/**
	 * �鿴�û���Ϣ
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="viewUser",method = RequestMethod.GET)
	public String viewUser(String id,Model model){
		if(id==null) return null;
		Subscriber sub = userSVC.findOne(id);
		model.addAttribute("user", sub);
		return "/sys/user/ViewUser";
	}
	
	/**
	 * �����û���Ϣ
	 * @param sub
	 * @param user
	 * @return
	 */
	@RequestMapping(value="saveUser",method = RequestMethod.POST)
	public @ResponseBody Result saveUser(Subscriber sub,User user){
		sub.setRoleid("1");
		Result r = userSVC.save(sub);
		return r;
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="userList",method = RequestMethod.POST)
	public @ResponseBody Page<Subscriber> userList(Subscriber sub,int page,int rows){
		
		com.github.pagehelper.Page<Object> pg = PageHelper.startPage(page,rows);
		//List<Subscriber> list = userSVC.queryList(new Subscriber());
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", "".equals(sub.getUsername())?null:sub.getUsername());
		map.put("nickname", "".equals(sub.getNickname())?null:sub.getNickname());
		List<Subscriber> list = userSVC.queryListByXml(map);
		
		Page<Subscriber> pages = new Page<Subscriber>();
		pages.setRows(list);
		
		pages.setPageNumber(page);
		pages.setPageSize(pg.getPageSize());
		pages.setTotal(pg.getTotal());  
		return pages;
	}
	
	/**
	 * �޸��û�״̬
	 * @param id
	 * @param ifactivate
	 * @param ifspeak
	 */
	@RequestMapping(value="editActivity",method = RequestMethod.POST)
	public @ResponseBody Result changeActivity(String id,String ifactivate,String ifspeak){
		ifspeak = "1".equals(ifactivate)?"1".toString():ifspeak;
		Result r = userSVC.editActivity(id, ifactivate, ifspeak);
		return r;
	}
	
	
	/**================================��ɫ����==================================**/

	/**
	 * ��ת����ɫ��ҳ
	 * @return
	 */
	@RequestMapping(value="role/index",method = RequestMethod.GET)
	public String roleIndex(){
		return "/sys/role/RoleIndex";
	}
	
	/**
	 * ��ɫ�б�
	 * @param role
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="role/roleList",method=RequestMethod.POST)
	public @ResponseBody Page<Role> queryList(Role role,int page,int rows){		
		com.github.pagehelper.Page<Object> pg = PageHelper.startPage(page,rows);
		List<Role> list = roleSVC.queryList(role);

		Page<Role> pages = new Page<Role>();
		pages.setRows(list);
		
		pages.setPageNumber(page);
		pages.setPageSize(pg.getPageSize());
		pages.setTotal(pg.getTotal());
		return pages;
	}
	
	/**==============================�˵�Ŀ¼================================**/
	
	/**
	 * ��ת��Ȩ��Ŀ¼index
	 * @return
	 */
	@RequestMapping(value="perms/permsIndex",method = RequestMethod.GET)
	public String gotoPermission(){
		return "/sys/pers/PermissionIndex";
	}
	
	/**
	 * ��ȡĿ¼��
	 * @return
	 */
	@RequestMapping(value="perms/menuTree",method = RequestMethod.POST)
	public @ResponseBody List<TreeNode> getMenu(){
		return moduleSVC.getMenuTrees();
	}
	
	/**
	 * ���桢����
	 * @param mod
	 * @return
	 */
	@RequestMapping(value="perms/saveModule",method = RequestMethod.POST)
	public @ResponseBody Result saveModule(Module mod){
		Result r = moduleSVC.saveModule(mod);
		return r;
	}
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="perms/deltModule",method = RequestMethod.POST)
	public @ResponseBody Result deltModule(String id){
		Result r = moduleSVC.deleteModule(id);
		return r;
	}
	
	
}
