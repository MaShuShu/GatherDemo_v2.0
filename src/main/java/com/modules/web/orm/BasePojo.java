package com.modules.web.orm;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public abstract class BasePojo {
	
	/** ID **/
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	/** ������id**/
	private String userid;
	
	/** ����ʱ�� **/
	private Date createtime;
	
	/** ����ʱ�� **/
	private Date edittimel;

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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getEdittimel() {
		return edittimel;
	}

	public void setEdittimel(Date edittimel) {
		this.edittimel = edittimel;
	}

}
