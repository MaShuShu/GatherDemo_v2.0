package com.modules.sys.util;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

public class SerializationUtils {

	/**
     * ʹ�� sessionId �����ַ����� key�������� Redis ����Ϊ�洢 Session �� key.
     * @param prefix
     * @param sessionId
     * @return
     */
    public static String sessionKey(String prefix, Serializable sessionId) {
        return prefix + sessionId;
    }

    /**
     * ʹ�� session �����ַ����� key�������� Redis ����Ϊ�洢 Session �� key.
     * @param prefix
     * @param session
     * @return
     */
    public static String sessionKey(String prefix, Session session) {
        return prefix + session.getId();
    }

    /**
     * �� sessionId ���л�Ϊ string����Ϊ Redis �� key �� value ����ͬʱΪ string ���� byte[].
     * @param session
     * @return
     */
    public static String sessionIdToString(Session session) {
        byte[] content = org.apache.commons.lang3.SerializationUtils.serialize(session.getId());
        return org.apache.shiro.codec.Base64.encodeToString(content);
    }

    /**
     * �����л��õ� sessionId.
     * @param value
     * @return
     */
    public static Serializable sessionIdFromString(String value) {
        byte[] content = org.apache.shiro.codec.Base64.decode(value);
        return org.apache.commons.lang3.SerializationUtils.deserialize(content);
    }

    /**
     * �� session ���л�Ϊ string����Ϊ Redis �� key �� value ����ͬʱΪ string ���� byte[].
     * @param value
     * @return
     */
    public static Session sessionToString(String value) {
        byte[] content = org.apache.shiro.codec.Base64.decode(value);
        return org.apache.commons.lang3.SerializationUtils.deserialize(content);
    }

    /**
     * �����л��õ� session.
     * @param session
     * @return
     */
    public static String sessionFromString(Session session) {
        byte[] content = org.apache.commons.lang3.SerializationUtils.serialize((SimpleSession) session);
        return org.apache.shiro.codec.Base64.encodeToString(content);
    }
    
}
