package com.modules.web.orm;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="w_context")
public class Context extends BasePojo {

	//����
	private String title;
	
	//����
	private String context;
	
	//��ǩ
	private String tag;
	
	//��id
	private String groupid;
	
	//�Ƿ��������
	private String ifspeak;
	
	//����
	@Transient
	private String groupname;
	
	@Transient
	private String username;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getIfspeak() {
		return ifspeak;
	}

	public void setIfspeak(String ifspeak) {
		this.ifspeak = ifspeak;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
