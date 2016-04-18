package com.modules.sys.orm;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "s_permission")
public class Permission {
	
	/** ID **/
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	/**��Ӧ�Ľ�ɫid**/
	private String roleid;
	
	/** ��Ӧ�Ĳ˵�id **/
	private String moduleid;
	
	
	//------------һ���ֶ������ݿ��޹�-------------//
	/**Ȩ�޴���**/
	@Transient
	private String permitno;
	
	/**Ȩ������**/
	@Transient
	private String permitname;
	
	/**����id**/
	@Transient
	private String pid;
	
	/**����**/
	@Transient
	private Integer rank;
	
	/** url��ַ **/
	@Transient
	private String url;
	
	/** �Ƿ�չ�� **/
	@Transient
	private String ifopen;
	
	/** һ���ͼ�� **/
	@Transient
	private String icon;
	
	/** �Ƿ�Ŀ¼('0':��ʾ��,'1':��ʾ��) **/
	@Transient
	private String dir;
	
	/** �Ƿ�˵�('0':��ʾ��,'1':��ʾ��) **/
	@Transient
	private String menu;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermitno() {
		return permitno;
	}

	public void setPermitno(String permitno) {
		this.permitno = permitno;
	}

	public String getPermitname() {
		return permitname;
	}

	public void setPermitname(String permitname) {
		this.permitname = permitname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIfopen() {
		return ifopen;
	}

	public void setIfopen(String ifopen) {
		this.ifopen = ifopen;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}
