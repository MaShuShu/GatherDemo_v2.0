package com.modules.base.orm;

import java.util.Date;

public class User {
	
	/**�û�id**/
	private String id;
	
	/** sessionID**/
	private String session;
	
	/**�û��ʺ�**/
	private String name;
	
	/**�ǳ�**/
	private String nickName;
	
	/**�Ա�0���У�1��Ů��2��δ֪��**/
	private int sex;
	
	/**����**/
	private String email;
	
	/**�ֻ�����**/
	private String phoneNum;
	
	/**ͷ��ͼƬ**/
	private String icon;
	
	/**�Ƿ��ֹfatie**/
	private String ifSpeak;
	
	/**����¼ʱ��**/
	private Date lastLoginTime;
	
	/** ��¼���� **/
	private String loginorg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIfSpeak() {
		return ifSpeak;
	}

	public void setIfSpeak(String ifSpeak) {
		this.ifSpeak = ifSpeak;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLoginorg() {
		return loginorg;
	}

	public void setLoginorg(String loginorg) {
		this.loginorg = loginorg;
	}
	
}
