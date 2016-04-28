package com.modules.sys.realm;

import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.modules.sys.constant.ModuleType;
import com.modules.sys.orm.Subscriber;
import com.modules.sys.svc.PermissionSVC;
import com.modules.sys.svc.SubscriberSVC;

public class MyRealm extends AuthorizingRealm{

	@Autowired
	private SubscriberSVC userSVC;
	
	//@Autowired
	//private ModuleSVC moduleSVC;
	
	@Autowired
	private PermissionSVC permissionSVC;
	
	public final static String SESSION_KEY = "SESSION_USER";
	
	public final static String SESSION_MODULE = "SESSION_MODULE";
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//��ȡ�ʺ�
		String userName = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//��ȡ�ʺŽ�ɫ
		authorizationInfo.setRoles(userSVC.getRoles(userName));
		//��ȡ�ʺ�Ȩ��
		Set<String> permis = userSVC.getPermissions(userName);
		authorizationInfo.setStringPermissions(permis);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String)token.getPrincipal();
		Subscriber sub = userSVC.getUserByName(userName);
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(sub.getUsername(),sub.getPassword(),getName());
		
		//shiro��session
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(SESSION_KEY, sub);
		//�˵�
		JSONArray module = new JSONArray();
		if("admin".equals(userName)){
			module = permissionSVC.queryList(null, ModuleType.menu.type);
		}else{
			module = permissionSVC.queryList(userName.trim().toString(), ModuleType.menu.type);
		}
		
		session.setAttribute(SESSION_MODULE, module);
		return authcInfo;
	}

}
