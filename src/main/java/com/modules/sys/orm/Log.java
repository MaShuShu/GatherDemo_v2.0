package com.modules.sys.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="s_log")
public class Log implements Serializable {

	private static final long serialVersionUID = -5034052907038321178L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	/*** �û�id */
	private String userid;
	
	/*** �û��˺� */
	private String username;
	
	/*** ����ʱ�� */
	private Date createdate;
	
	/*** ���� */
	private String handle;
	
	/*** url */
	private String url;

	/*** �����ķ��� */
	private String method;
	
	/*** ���� */
	private String parameter;
	
	/*** ip��ַ */
	private String ip;
	
	/*** ��ʼʱ�� */
	@Transient
	private Date beginDate;
	
	/*** ����ʱ�� */
	@Transient
	private Date endDate;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}