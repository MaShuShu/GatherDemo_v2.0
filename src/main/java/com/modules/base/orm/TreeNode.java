package com.modules.base.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���νڵ�
 * @author Acer
 *
 */
public class TreeNode {

	/**
	 * <p>
	 * �Ƿ�ڵ�
	 * </p>
	 * 
	 * @return �Ƿ�Ϊ�ڵ�
	 */
	public boolean isNode() {
		return attributes == null || attributes.get("isNode") == null || (Boolean) attributes.get("isNode");
	}
	
	/**
	 * ID
	 */
	public String id;
	
	/**
	 * �ϼ�ID
	 */
	public String pid;
	
	/**
	 * �ڵ�����
	 */
	public String name;
	
	/**
	 * �ڵ�ͼ��
	 */
	public String iconCls;
	
	/**
	 * �ڵ�״̬����open����closed��
	 */
	public String open;
	
	/**
	 * �ڵ��Ƿ�ѡ��
	 */
	public Boolean checked;
	
	/**
	 * �ڵ㸽������
	 */
	public Map<String,Object> attributes;
	
	/**
	 * �ӽڵ�
	 */
	public List<TreeNode> children;
	
	/**
	 * �����ڵ�
	 */
	public List<TreeNode> wings;
	
	/**
	 * ���һ�������ڵ�
	 * 
	 * @param node
	 * @return ���ر��ڵ��ʵ��
	 */
	public TreeNode addWing(TreeNode node) {
		if (wings == null) {
			wings = new ArrayList<TreeNode>();
		}
		wings.add(node);
		return this;
	}

	/**
	 * ����һ����ֵ��
	 * 
	 * @param key
	 * @param value
	 * @return ���ر��ڵ��ʵ��
	 */
	public TreeNode putAttr(String key, Object value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
		return this;
	}
}
