package com.modules.sys.realm.sessionDao;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modules.sys.util.RedisUtil;
import com.modules.sys.util.SerializationUtils;

public class RedisSessionDao extends CachingSessionDAO {
	
	private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
	
	// ��¼�ɹ�����Ϣ�洢�� session ����� attribute ��.
    private static final String AUTHENTICATED_SESSION_KEY = "org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY";

    private String keyPrefix = "shiro_redis_session:";
    
    private String deleteChannel = "shiro_redis_session:delete";
    
    //20����,��λ����
    private long timeToLiveSeconds = 1800; 
    
	private RedisUtil redis;
    
	@Override
	protected Serializable doCreate(Session session) {
		logger.debug("=> Create session with ID [{}]",session.getId());
		
		//����һ��Id�����ø�session
		Serializable sessionId = this.generateSessionId(session);
		assignSessionId(session, sessionId);
		
		//session �� Redis ����ʧЧ����
		String key = SerializationUtils.sessionKey(keyPrefix, session);
		String value = SerializationUtils.sessionFromString(session);
		
		redis.set(key, value, timeToLiveSeconds);
		return sessionId;
	}
	
	/**
     * �����ӱ��� Cache ���Ǵ� Redis ��ȡ Session.
     * @param sessionId
     * @return
     * @throws UnknownSessionException
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session s = getCachedSession(sessionId);

        // 1. ������ػ���û�У���� Redis ��ȡ��
        // 2. ServerA ��¼�ˣ�ServerB û�е�¼���������д� session�����Դ� Redis ��ȡ������ֱ���û������
        if (s == null || (
                s.getAttribute(AUTHENTICATED_SESSION_KEY) != null
                && !(Boolean) s.getAttribute(AUTHENTICATED_SESSION_KEY)
        )) {
            s = doReadSession(sessionId);
            if (s == null) {
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            }
            return s;
        }

        return s;
    }
    
    @Override
	protected Session doReadSession(Serializable sessionId) {
		logger.debug("=> Read session with ID [{}]",sessionId);
    	
		String value = (String) redis.get(SerializationUtils.sessionKey(keyPrefix, sessionId));
		
		//���� Redis ���� flushdb ��������е����ݣ�������session���ǿյ�
    	if(value != null){
    		Session session = (Session) SerializationUtils.sessionIdFromString(value);
    		super.cache(session, session.getId());

            return session;
    	}
    	
		return null;
	}
    
	@Override
	protected void doUpdate(Session session) {
		// ����Ự����/ֹͣ��û��Ҫ�ٸ�����
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            logger.debug("=> Invalid session.");
            return;
        }

        logger.debug("=> Update session with ID [{}]", session.getId());

        String key = SerializationUtils.sessionKey(keyPrefix, session);
        String value = SerializationUtils.sessionFromString(session);
        redis.set(key, value, timeToLiveSeconds);
	}

	@Override
	protected void doDelete(Session session) {
		logger.debug("=> Delete session with ID [{}]", session.getId());

		redis.remove(SerializationUtils.sessionKey(keyPrefix, session));
        // ������Ϣ֪ͨ���� Server �ϵ� cache ɾ�� session.
		redis.publish(deleteChannel, SerializationUtils.sessionIdToString(session));
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public String getDeleteChannel() {
		return deleteChannel;
	}

	public void setDeleteChannel(String deleteChannel) {
		this.deleteChannel = deleteChannel;
	}

	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public RedisUtil getRedis() {
		return redis;
	}

	public void setRedis(RedisUtil redis) {
		this.redis = redis;
	}

}
