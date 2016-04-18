package com.modules.sys.orm;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * �˵�Ŀ¼����
 * @author Acer
 *
 */
@Table(name="s_module")
public class Module {
	
	/** ID **/
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	/** �ϼ�ID **/
	private String pid;
	
	/** ���� **/
	private String name;
	
	/** url **/
	private String url;
	
	/** �Ƿ�չ�� **/
	private String ifopen;
	
	/** һ���ͼ�� **/
	private String icon;
	
	/** �Ƿ�Ŀ¼('0':��ʾ��,'1':��ʾ��) **/
	private String dir;
	
	/** �Ƿ�˵�('0':��ʾ��,'1':��ʾ��) **/
	private String menu;
	
	/** �Ƿ񹫿� **/
	private Integer valid;
	
	/** ���� **/
	private Integer rank;
	
	/** Ȩ��no **/
	private String permitno;
	
	/** Ȩ��ע�� **/
	private String permitmark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getPermitno() {
		return permitno;
	}

	public void setPermitno(String permitno) {
		this.permitno = permitno;
	}

	public String getPermitmark() {
		return permitmark;
	}

	public void setPermitmark(String permitmark) {
		this.permitmark = permitmark;
	}

	public Integer getValid() {
		return valid;
	}
	
}
