package com.modules.base.orm;

import java.util.List;
import java.util.Map;

public class Page<T> {
	
	public boolean notPage;

	/** ҳ�� */
	public int pageNumber;

	/** ҳ�� **/
	public int pageSize;

	/** �ܼ�¼�� */
	public long total;

	/** ������ */
	public List<T> rows;

	/** ҳ���� */
	public List<Map<String, Object>> footer;

	/** �����ֶ��� **/
	public String order;

	/** ����ʽ **/
	public String sort;

	public boolean isNotPage() {
		return notPage;
	}

	public void setNotPage(boolean notPage) {
		this.notPage = notPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<Map<String, Object>> getFooter() {
		return footer;
	}

	public void setFooter(List<Map<String, Object>> footer) {
		this.footer = footer;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
