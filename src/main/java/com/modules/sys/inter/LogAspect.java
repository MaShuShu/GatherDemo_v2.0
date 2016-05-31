package com.modules.sys.inter;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.base.orm.User;
import com.modules.sys.dao.LogDao;
import com.modules.sys.orm.Log;
import com.modules.sys.orm.Subscriber;

@Service
@Aspect
public class LogAspect {

	@Autowired
	private LogDao dao;
	
	@Autowired
	private HttpServletRequest request;
	
	public final static String SESSION_KEY = "SESSION_USER";
	
	/**
	 * �û���¼�����
	 */
	@Pointcut("execution(* com.modules.sys.svc.impl.SubscriberSVCImpl.login(..))")
	public void loginCell(){}
	
	/**
	 * �û��ǳ������
	 */
	@Pointcut("execution(* com.modules.sys.svc.impl.SubscriberSVCImpl.logout(..))")
	public void logoutCell(){}
	
	/**
	 * ���������
	 */
	@Pointcut("execution(* com.modules..svc.impl.*.save*(..))")
	public void saveCell(){}
	
	/**
	 * ���������
	 */
	@Pointcut("execution(* com.modules..svc.impl.*.edit*(..))")
	public void editCell(){}
	
	/**
	 * ɾ�������
	 */
	@Pointcut("execution(* com.modules..svc.impl.*.delt*(..))")
	public void deltCell(){}
	
	/**
	 * �û���¼�����
	 * @param joinPoint
	 * @param rtv
	 */
	@AfterReturning(value="loginCell()",argNames="rtv", returning="rtv")
	public void loginLog(JoinPoint joinPoint,Object rtv){
		autoFinish(joinPoint,"��½ϵͳ");
	}
	
	/**
	 * �˳���¼�����
	 * @param joinPoint
	 * @param rtv
	 */
	@Before(value="logoutCell()")
	public void logoutLog(){
		autoFinish(null,"�˳�ϵͳ");
	}
	
	/**
	 * ��Ӳ�����־(����֪ͨ)
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value="saveCell()",argNames="rtv", returning="rtv")
	public void insertLog(JoinPoint joinPoint,Object rtv) throws Throwable{
		autoFinish(joinPoint,"��������");
	}
	
	/**
	 * �޸Ĳ�����־(����֪ͨ) 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
    @AfterReturning(value="editCell()", argNames="rtv", returning="rtv")    
    public void updateLog(JoinPoint joinPoint, Object rtv) throws Throwable{    
    	autoFinish(joinPoint,"���²���");
	}
	
    /**
     * ɾ��������־(����֪ͨ) 
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value="deltCell()",argNames="rtv", returning="rtv")  
    public void deleteLog(JoinPoint joinPoint, Object rtv) throws Throwable{  
    	autoFinish(joinPoint,"ɾ������");
	}
	
    /** 
     * ʹ��Java��������ȡ�����ط���(insert��update)�Ĳ���ֵ��  
     * ������ֵƴ��Ϊ�������� 
     * @param args 
     * @param mName 
     * @return 
     */  
    private String optionContent(Object[] args, String mName){  
        if(args == null){  
            return null;  
        }  
        StringBuffer rs = new StringBuffer();  
        rs.append(mName);  
        String className = null;  
        int index = 1;  
        //������������   
        for(Object info : args){  
            //��ȡ��������  
            className = info.getClass().getName();  
            className = className.substring(className.lastIndexOf(".") + 1);  
            rs.append("[����"+index+"������:" + className + "��ֵ:");  
            //��ȡ��������з���  
            Method[] methods = info.getClass().getDeclaredMethods();  
            // �����������ж�get����   
            for(Method method : methods){  
                String methodName = method.getName();  
                // �ж��ǲ���get����  
                if(methodName.indexOf("get") == -1){//����get����   
                    continue;//������  
                }  
                Object rsValue = null;  
                try{  
                    // ����get��������ȡ����ֵ  
                    rsValue = method.invoke(info);  
                }catch (Exception e) {  
                    continue;  
                }  
                //��ֵ����������  
                rs.append("(" + methodName+ ":" + rsValue + ")");  
            }  
            rs.append("]");  
            index ++;  
        }  
        return rs.toString();  
    }
    
    /**
     * ���
     * @param log
     * @param sub
     * @return
     */
    private void autoFinish(JoinPoint joinPoint,String handle){
    	
    	String username =  (String) SecurityUtils.getSubject().getPrincipal();
    	
    	/*Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Subscriber user = (Subscriber) session.getAttribute(SESSION_KEY);*/
    	
    	Log log = new Log();
    	log.setUsername(username==null?"ʧȥ�ʺ���Ϣ":username);
    	log.setCreatedate(new Date()); 
    	log.setIp(getIpAddr(request));
    	if(joinPoint!=null){
    		log.setUrl(joinPoint.getSignature().toString());
    		//��ȡ������   
            String methodName = joinPoint.getSignature().getName();  
            //��ȡ��������  
            String opContent = optionContent(joinPoint.getArgs(),methodName);
            log.setMethod(methodName);
    		log.setParameter(opContent);
    	}
		log.setHandle(handle);
		dao.insert(log);
    }
    
    /**
	 * ��ȡip��ַ
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
}
