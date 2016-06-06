package com.modules.sys.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.modules.activiti.svc.RegisterFlowSVC;
import com.modules.base.orm.Page;
import com.modules.base.orm.Result;
import com.modules.base.orm.TreeNode;
import com.modules.base.orm.User;
import com.modules.base.svc.BaseSVC;
import com.modules.sys.helper.ActivityUserHelper;
import com.modules.sys.orm.Log;
import com.modules.sys.orm.Module;
import com.modules.sys.orm.Role;
import com.modules.sys.orm.Subscriber;
import com.modules.sys.svc.LogSVC;
import com.modules.sys.svc.ModuleSVC;
import com.modules.sys.svc.PermissionSVC;
import com.modules.sys.svc.RoleSVC;
import com.modules.sys.svc.SubscriberSVC;
import com.modules.sys.util.RedisUtil;

@Controller
@RequestMapping("/sys/")
public class SysCTRL {
	
	@Autowired
	private SubscriberSVC userSVC;
	
	@Autowired
	private RoleSVC roleSVC;
	
	@Autowired
	private ModuleSVC moduleSVC;
	
	@Autowired
	private PermissionSVC permitSVC;
	
	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private ActivityUserHelper activity;
	
	@Autowired
	private LogSVC logSVC;
	
	@Autowired
	private BaseSVC<Role> baseSVC;
	
	//����
	@Autowired
	private RegisterFlowSVC flowSVC;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "userIndex", method = RequestMethod.GET)
	public String userIndex(){
		//redis����
		//redis.set("abc", "�й��ƶ�10086");
		return "/sys/user/UserIndex";
	}
	
	/**
	 * ��ת������ҳ��
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("Q8")
	@RequestMapping(value="addUser",method = RequestMethod.GET)
	public String addUser(String id,Model model){
		if(id != null){
			Subscriber sub = userSVC.findOne(id);
			model.addAttribute("item", sub);
		}
		return "/sys/user/AddUser";
	}
	
	/**
	 * ��ת���޸�ҳ��
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="editUser",method = RequestMethod.GET)
	public String editUser(String id,Module model){
		return "/sys/user/EditUser";
	}
	
	/**
	 * �鿴�û���Ϣ
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="viewUser",method = RequestMethod.GET)
	public String viewUser(String id,Model model,User user){
		if(id==null) return null;
		Subscriber sub = userSVC.findOne(id);
		
		//String abc = (String) redis.get("abc");
		//flowSVC.findMyPersonalTask(user.getId());
		
		model.addAttribute("user", sub);
		return "/sys/user/ViewUser";
	}
	
	/**
	 * �����û���Ϣ
	 * @param sub
	 * @return
	 */
	@RequestMapping(value="saveUser",method = RequestMethod.POST)
	public @ResponseBody Result saveUser(Subscriber sub){
		Result r = null;
		if(sub.getId()==null || "".equals(sub.getId())){
			r = userSVC.saveUser(sub);
			//flowSVC.startProcessInstance(sub.getId(), "Examine");
		}else{
			r = userSVC.editUser(sub);
		}
		return r;
	}
	
	/**
	 * ��ʼ������
	 * @param userid
	 * @return
	 */
	@RequestMapping(value="user/resetPass",method = RequestMethod.POST)
	public @ResponseBody Result resetPass(String userids){
		Result r = userSVC.savePass(userids);
		return r;
	}
	
	/**
	 * ��ת�������ɫҳ��
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="user/allotRole",method = RequestMethod.GET)
	public String allotRole(String id,Model model){
		Subscriber sub = userSVC.findOne(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("allotUserid", sub.getId());
		map.put("roleid", sub.getRoleid());
		model.addAttribute("AllotMap", map);
		return "/sys/user/AllotRole";
	}
	
	/**
	 * �����ɫ
	 * @param id
	 * @return
	 */
	@RequestMapping(value="user/allotRole",method = RequestMethod.POST)
	public @ResponseBody Result allotRole(String id, String roleid){
		Result r = userSVC.editUserRole(id, roleid);
		return r;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="userList",method = RequestMethod.POST)
	public @ResponseBody Page<Subscriber> userList(Subscriber sub,int page,int rows){
		
		com.github.pagehelper.Page<Object> pg = PageHelper.startPage(page,rows);
		List<Subscriber> list = userSVC.queryUserByXml(sub);
		//�Ƿ�����
		for (Subscriber subscriber : list) {
			subscriber.setActivity(activity.getActivityUser(subscriber.getUsername()));
		}
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
	
	/**
	 * �߳���¼�û�
	 * @param id
	 * @return
	 */
	@RequestMapping(value="UserloginOut",method = RequestMethod.POST)
	public @ResponseBody Result loginout(String id){
		Subscriber sub = userSVC.findOne(id);
		Result r = activity.forceQuit(sub.getUsername());
		return r;
	}
	
	/**
	 * ɾ���û�
	 * @param ids
	 * @param user
	 * @return
	 */
	@RequestMapping(value="user/delt",method = RequestMethod.POST)
	public @ResponseBody Result deltUsers(String ids,User user){
		return userSVC.deltUsers(ids, user);
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
	 * ��ת���������޸�ҳ��
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="role/addRole",method = RequestMethod.GET)
	public String addRole(String id,Model model){
		if(id!=null || !"".equals(id)){
			Role role = roleSVC.findOne(id);
			model.addAttribute("item", role);
		}
		return "/sys/role/AddRole";
	}
	
	/**
	 * �����ɫ
	 * @param role
	 * @return
	 */
	@RequestMapping(value="role/saveRole",method = RequestMethod.POST)
	public @ResponseBody Result saveRole(Role role){
		if(role != null){
			Result r = null;
			if(role.getId()==null || "".equals(role.getId())){
				//r = roleSVC.saveRole(role);
				role.setId(UUID.randomUUID().toString());
				r = baseSVC.saveBase(role);
			}else{
				r = roleSVC.editRole(role);
			}
			return r;
		}
		return null;
	}

	/**
	 * ɾ����ɫ
	 * @param id
	 * @return
	 */
	@RequestMapping(value="role/deltRole",method = RequestMethod.POST)
	public @ResponseBody Result deltRole(String id){
		if(userSVC.getUserByRoleId(id)){
			Result r = roleSVC.deltRole(id);
			return r;
		}
		return Result.error("���иý�ɫ���û�������ɾ����");
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
		List<Role> list = roleSVC.queryRole(role);

		Page<Role> pages = new Page<Role>();
		pages.setRows(list);
		
		pages.setPageNumber(page);
		pages.setPageSize(pg.getPageSize());
		pages.setTotal(pg.getTotal());
		return pages;
	}
	
	/**
	 * ��ȡ��ɫ
	 * @return
	 */
	@RequestMapping(value="role/list",method = RequestMethod.POST)
	public @ResponseBody List<Role> getRoleList(){
		return roleSVC.queryRoles();
	}
	
	/**
	 * ��ת��ѡ��Ȩ��ҳ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="role/allot",method = RequestMethod.GET)
	public String allotFunction(String id,Model model){
		JSONArray jsonArray = permitSVC.queryByRoleId(id);
		model.addAttribute("roleid", id);
		model.addAttribute("zTreeCheked", jsonArray);
		return "/sys/role/AllotPermit";
	}
	
	/**
	 * ����Ȩ�ޣ���ɾ���ɵ�
	 * @param roleid
	 * @param permitIds
	 * @return
	 */
	@RequestMapping(value="role/savePermit",method = RequestMethod.POST)
	public @ResponseBody Result savePermit(String rid,String pids){
		Result r = permitSVC.savePermit(rid, pids);
		return r;
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
		Result r = moduleSVC.deltModule(id);
		return r;
	}
	
	/**==============================��־����================================**/
	
	@RequestMapping(value="log/logIndex",method = RequestMethod.GET)
	public String logIndex(){
		return "/sys/log/LogIndex";
	}
	
	/**
	 * �鿴�б�
	 * @param log
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="log/list",method = RequestMethod.POST)
	public @ResponseBody Page<Log> queryLog(Log log,int page,int rows) throws Exception{
		com.github.pagehelper.Page<Object> pg = PageHelper.startPage(page,rows);
		List<Log> list = logSVC.queryLog(log);
		
		Page<Log> pages = new Page<Log>();
		pages.setRows(list);
		
		pages.setPageNumber(page);
		pages.setPageSize(pg.getPageSize());
		pages.setTotal(pg.getTotal());  
		return pages;
	}
	
	/**
	 * ɾ��
	 * @param ids
	 * @return
	 */
	@RequiresRoles("admin")
	@RequestMapping(value="log/delt",method = RequestMethod.POST)
	public @ResponseBody Result deltLog(String ids){
		return logSVC.deltLog(ids);
	}
	
	/**
	 * �鿴
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/log/view",method = RequestMethod.GET)
	public String viewLog(String id,Model model){
		Log log = logSVC.findOne(id);
		model.addAttribute("log", log);
		return "/sys/log/ViewLog";
	}
	
}
