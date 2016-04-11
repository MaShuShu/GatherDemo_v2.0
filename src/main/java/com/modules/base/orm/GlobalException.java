package com.modules.base.orm;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(GlobalException.class.getName());

	/** �쳣���� **/
	public int code;

	/** �쳣���� **/
	public Object data;

	public GlobalException(int code) {
		super();
		this.code = code;
	}

	public GlobalException(String message) {
		super(message);
		this.code = -1;
	}

	public GlobalException(Throwable cause) {
		super(cause);
		this.code = -1;
	}

	public GlobalException(int code, String message) {
		super(message);
		this.code = code;
	}

	public GlobalException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public GlobalException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public GlobalException(Throwable cause, Object data) {
		super(cause);
		this.code = -1;
		this.data = data;
	}

	public GlobalException(int code, String message, Object data) {
		super(message);
		this.code = code;
		this.data = data;
	}

	/**
	 * �����쳣����
	 * 
	 * @param t
	 * @return �쳣����
	 */
	public static Data parse(Throwable t) {
		Data data = new Data();
		data.code = -1;
		data.msg = t.getMessage();
		data.type = 0;
		if (t instanceof GlobalException) {
			GlobalException ex = (GlobalException) t;
			data.type = 3;
			data.code = ex.code;
			data.msg = ex.getMessage();
			return data;
		}
		logger.log(Level.INFO, "ϵͳҵ�������", t);
		if (t instanceof NullPointerException) {
			data.msg = "ϵͳ���󣺴��ڿ����õ����ݡ�";
			return data;
		}
		data.msg = "ϵͳ����ԭ��δ֪���������Ա��ϵ��";
		return data;
	}

	/**
	 * <p>
	 * �쳣���ݣ��������ͷ�Ϊ4�ࣺ
	 * </p>
	 * <ul>
	 * <li>0-ϵͳ����</li>
	 * <li>1-Ȩ�޴���</li>
	 * <li>2-���ݴ���</li>
	 * <li>3-��������</li>
	 * </ul>
	 * <div>create time: 2013-3-14 ����11:05:53</div>
	 * 
	 * @author ��½���xudejian@126.com��
	 */
	public static class Data {
		/**
		 * �������
		 */
		public int code;

		/**
		 * ������Ϣ
		 */
		public String msg;

		/**
		 * �������ͣ�0-ϵͳ����1-Ȩ�޴���2-���ݴ���3-��������
		 */
		public int type;
	}

}
