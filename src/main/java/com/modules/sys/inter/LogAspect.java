package com.modules.sys.inter;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.sys.dao.LogDao;
import com.modules.sys.orm.Log;
import com.modules.sys.orm.Subscriber;

@Service
@Aspect
public class LogAspect {

	@Autowired
	private LogDao dao;
	
	public final static String SESSION_KEY = "SESSION_USER";
	
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
    	Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Subscriber sub = (Subscriber) session.getAttribute(SESSION_KEY);
		if(sub.getId()==null || "".equals(sub.getId())){
			return ;
		}
		Log log = new Log();
		log.setUserid(sub.getId());
    	log.setUsername(sub.getUsername());
    	log.setCreatedate(new Date()); 
    	log.setIp(sub.getLoginorg());
    	log.setUrl(joinPoint.getSignature().toString());
		//��ȡ������   
        String methodName = joinPoint.getSignature().getName();  
        //��ȡ��������  
        String opContent = optionContent(joinPoint.getArgs(),methodName);
		log.setMethod(methodName);
		log.setParameter(opContent);
		log.setHandle(handle);
		dao.insert(log);
    }
}
