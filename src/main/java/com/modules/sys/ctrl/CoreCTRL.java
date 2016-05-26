package com.modules.sys.ctrl;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
import com.modules.sys.orm.Subscriber;
import com.modules.sys.svc.PermissionSVC;
import com.modules.sys.svc.RoleSVC;

@Controller
public class CoreCTRL {

	@Autowired
	private RoleSVC roleSVC;
	
	@Autowired
	private PermissionSVC permissionSVC;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	public final static String SESSION_KEY = "SESSION_USER";
	
	public final static String SESSION_MODULE = "SESSION_MODULE";
	
	/**
	 * ��ҳ
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String Index(Model model){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()==true){
			//�˵�
			JSONArray module = new JSONArray();
			if("admin".equals(subject.getPrincipal().toString())){
				module = permissionSVC.queryPermit(null, ModuleType.menu.type);
			}else{
				module = permissionSVC.queryPermit(subject.getPrincipal().toString(), ModuleType.menu.type);
			}
			//model.addAttribute("user", sub);
			model.addAttribute("module", module.toString());
			return "/index";
		}
		return "/login";
	}
	
	/**
	 * ��½ҳ
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String gotoLogin(){
		return "/login";
	}
	
	/**
	 * ��½
	 * @param user
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Result login(Subscriber sub){
		Subject subject=SecurityUtils.getSubject();
		String error = null;
		
		//shiro��session
		/*Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session:sessions){
			if(null != session && StringUtils.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)), sub.getUsername().trim().toString())){  
				session.setTimeout(0);//����session����ʧЧ���������߳�ϵͳ
            }  
		}*/
		
		UsernamePasswordToken token=new UsernamePasswordToken(sub.getUsername().trim().toString(),sub.getPassword().trim().toString());
		token.setRememberMe(true);
		try{
			subject.login(token);
			return Result.ok("��֤�ɹ���");
		}catch (IncorrectCredentialsException e) {  
            error = "�û���/�������";
            return Result.error(error);
        }catch (AuthenticationException e) {  
        	error = "�û���/�������";
            return Result.error(error);
        }
	}
	
	/**
	 * �˳���¼
	 * @return
	 */
	@RequestMapping(value="/loginout",method=RequestMethod.GET)
	public String loginOut(){
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login.do";
	}
	
	
	/**
	 * δ֪ҳ
	 * @return
	 */
	@RequestMapping(value="/unauthor")
	public String unauthor(){
		return "/unauthor";
	}
	
	
	@RequestMapping(value="/user/index",method=RequestMethod.GET)
	public String userPage(User user,Model model){
		
		//��ҳ
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
	
	
}
