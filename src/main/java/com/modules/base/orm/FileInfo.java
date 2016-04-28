package com.modules.base.orm;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="app_files")
public class FileInfo {
	
	/** id **/
	@Id
	@GeneratedValue(generator = "UUID",strategy = GenerationType.IDENTITY)
	private String id;
	
	/** �ļ����� **/
	private String name;
	
	/** �ļ���С **/
	private long length;

	/** �ļ����� **/
	private String contentType;
	
	/** �ļ���չ�� **/
	private String extName;
	
	/** �ļ�·�� **/
	private String path;
	
	/** ״̬ **/
	private String status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
