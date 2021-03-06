package com.modules.sys.ctrl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.modules.base.orm.Result;
import com.modules.base.orm.User;
import com.modules.sys.constant.ModuleType;
import com.modules.sys.orm.Role;
import com.modules.sys.svc.PermissionSVC;
import com.modules.sys.svc.RoleSVC;
import com.modules.sys.svc.SubscriberSVC;
import com.modules.sys.util.RedisUtil;
import com.modules.web.svc.ContextSVC;

@Controller
public class CoreCTRL {

	@Autowired
	private RoleSVC roleSVC;
	
	@Autowired
	private SubscriberSVC subSVC;
	
	@Autowired
	private PermissionSVC permissionSVC;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private ContextSVC contextSVC;
	
	public final static String SESSION_KEY = "SESSION_USER";
	
	public final static String SESSION_MODULE = "SESSION_MODULE";
	
	//登录过期时间
	public final long outTime = 3600;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(Model model){
		return "/index";
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String indexPage(Model model){
		model.addAttribute("blog", contextSVC.queryList());
		return "/index";
	}
	
	/**
	 * 跳转到写博客页面
	 * @return
	 */
	@RequestMapping(value="/contact",method=RequestMethod.GET)
	public String writeBlog(){
		return "/contact";
	}
	
	
	/**
	 * 后台首页
	 * @return
	 */
	@RequestMapping(value="/sys/index",method=RequestMethod.GET)
	public String sysIndex(Model model){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()==true){
			//菜单
			String key = subject.getSession().getId().toString()+"-menu";
			String menu = (String) redis.get(key);
			if(menu==null){
				if("admin".equals(subject.getPrincipal().toString())){
					menu = permissionSVC.queryPermit(null, ModuleType.menu.type).toString();
				}else{
					menu = permissionSVC.queryPermit(subject.getPrincipal().toString(), ModuleType.menu.type).toString();
				}
				redis.set(key, menu, outTime);
			}
			model.addAttribute("module", menu);
			return "/sys/index";
		}
		return "/login";
	}
	
	/**
	 * 登陆页
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String gotoLogin(){
		return "/sys/login";
	}
	
	/**
	 * 获取登录日志
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/setLogin",method=RequestMethod.POST)
	public @ResponseBody Result setLoginInfo(User user){
		subSVC.login(user);
		return Result.ok();
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String loginOut(){
		//移除菜单
		Subject subject = SecurityUtils.getSubject();
		String key = subject.getSession().getId().toString()+"-menu";
		if(key != null) redis.remove(key);
		
		subSVC.logout();
		return "redirect:/login.do";
	}
	
	/**
	 * 未知页
	 * @return
	 */
	@RequestMapping(value="/unauthor")
	public String unauthor(){
		return "/unauthor";
	}
	
	
	@RequestMapping(value="/user/index",method=RequestMethod.GET)
	public String userPage(User user,Model model){
		
		PageHelper.startPage(1, 10);
		List<Role> list = roleSVC.queryRole(new Role());
		for (Role role : list) {
			System.out.println("---------------------"+role.getRolename()+"--------------------");
		}
		model.addAttribute("msg", user.getNickName());
		return "/success";
	}
	
	@RequestMapping(value="upload",method=RequestMethod.GET)
	public String upload1(){
		return "/index";
	}
	
	/**------------------------------------------------------------------------**/
	
	protected void removeJedis(){
		
	}
	
}
