package com.modules.sys.orm;

public class Permission {
	
	/** ID **/
	private String id;
	
	/**Ȩ�޴���**/
	private String permitno;
	
	/**Ȩ������**/
	private String permitname;
	
	/**Ȩ��˵��**/
	private String permitremark;
	
	/**����id**/
	private String parentid;
	
	/**��Ӧ�Ľ�ɫid**/
	private String roleid;
	
	/**����**/
	private Integer rank;
	
	/**��������**/
	private Integer prank;

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

	public String getPermitremark() {
		return permitremark;
	}

	public void setPermitremark(String permitremark) {
		this.permitremark = permitremark;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
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

	public Integer getPrank() {
		return prank;
	}

	public void setPrank(Integer prank) {
		this.prank = prank;
	}

}
