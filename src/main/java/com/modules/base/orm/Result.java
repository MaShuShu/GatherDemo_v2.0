package com.modules.base.orm;


public class Result {

	/**
	 * �������
	 */
	public int code;

	/**
	 * �����Ϣ
	 */
	public String msg;

	/**
	 * �������
	 */
	public Object data;

	/**
	 * ������Ϣ
	 */
	public String error;

	/**
	 * ���ؽ��
	 * 
	 * @param code
	 * @param msg
	 */
	public Result(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	/**
	 * ���ؽ����������
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 */
	public Result(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * ���سɹ�
	 * 
	 * @return �������
	 */
	public static Result ok() {
		return new Result(1, null);
	}

	/**
	 * ���سɹ�
	 * 
	 * @param msg
	 * @return �������
	 */
	public static Result ok(String msg) {
		return new Result(1, msg);
	}

	/**
	 * ��������
	 * 
	 * @param data
	 * @return �������
	 */
	public static Result data(Object data) {
		return new Result(data != null ? 1 : -1, null, data);
	}

	/**
	 * ���ش���
	 * 
	 * @param msg
	 * @return �������
	 */
	public static Result error(String msg) {
		return new Result(-1, msg);
	}

	/**
	 * �����쳣
	 * 
	 * @param t
	 * @return �������
	 */
	public static Result exception(Throwable t) {
		Result r = new Result(-1, t.getMessage());
		if (t instanceof GlobalException) {
			GlobalException e = (GlobalException) t;
			r.code = e.code;
			r.data = e.data;
		}
		r.error = t.getMessage();
		return r;
	}

	/**
	 * �����쳣����������Ϣ
	 * 
	 * @param msg
	 * @param t
	 * @return �������
	 */
	public static Result exception(String msg, Throwable t) {
		Result r = new Result(-1, msg);
		if (t instanceof GlobalException) {
			GlobalException e = (GlobalException) t;
			r.code = e.code;
			r.data = e.data;
		}
		r.error = t.getMessage();
		return r;
	}
	
}
