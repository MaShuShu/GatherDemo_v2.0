package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * �๦�ܲ���������
 * </p>
 * <p>
 * create: 2011-2-22 ����03:52:09
 * </p>
 * @author Acer
 *
 */
public class ReflectUtils {
	
	public static final Logger LOG = Logger.getLogger(ReflectUtils.class.getName());

	public static byte[] serialize(Object obj) {
		if (obj == null) {
			return null;
		}
		byte[] bs = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream bos = null;
		try {
			bos = new ObjectOutputStream(baos);
			bos.writeObject(obj);
			bs = baos.toByteArray();
		}
		catch (Exception e) {
		}
		finally {
			if (baos != null) {
				try {
					baos.close();
				}
				catch (IOException e) {
				}
			}
			if (bos != null) {
				try {
					bos.close();
				}
				catch (IOException e) {
				}
			}
		}
		return bs;
	}

	public static Object deserialize(byte[] bs) {
		if (bs == null) {
			return null;
		}
		Object obj = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		ObjectInputStream bis = null;
		try {
			bis = new ObjectInputStream(bais);
			obj = bis.readObject();
		}
		catch (Exception e) {
		}
		finally {
			if (bis != null) {
				try {
					bis.close();
				}
				catch (IOException e) {
				}
			}
			if (bais != null) {
				try {
					bais.close();
				}
				catch (IOException e) {
				}
			}
		}
		return obj;
	}

	/**
	 * <p>
	 * ��ȡ������
	 * </p>
	 * 
	 * @param clazz
	 *            ��
	 * @return ClassType ����ָ��������
	 */
	public static ClassType classType(Class<?> clazz) {
		if (clazz == null) {
			return ClassType.defalt;
		}
		if (String.class.isAssignableFrom(clazz)) {
			return ClassType.string;
		}
		if (StringBuffer.class.isAssignableFrom(clazz)
				|| Boolean.class.isAssignableFrom(clazz)
				|| boolean.class.isAssignableFrom(clazz)
				|| Character.class.isAssignableFrom(clazz)
				|| char.class.isAssignableFrom(clazz)) {
			return ClassType.tostring;
		}
		if (Byte.class.isAssignableFrom(clazz)
				|| byte.class.isAssignableFrom(clazz)
				|| Short.class.isAssignableFrom(clazz)
				|| short.class.isAssignableFrom(clazz)
				|| Integer.class.isAssignableFrom(clazz)
				|| int.class.isAssignableFrom(clazz)
				|| Long.class.isAssignableFrom(clazz)
				|| long.class.isAssignableFrom(clazz)
				|| Float.class.isAssignableFrom(clazz)
				|| float.class.isAssignableFrom(clazz)
				|| Double.class.isAssignableFrom(clazz)
				|| double.class.isAssignableFrom(clazz)
				|| Number.class.isAssignableFrom(clazz)
				|| BigInteger.class.isAssignableFrom(clazz)
				|| BigDecimal.class.isAssignableFrom(clazz)) {
			return ClassType.number;
		}
		if (Date.class.isAssignableFrom(clazz)) {
			return ClassType.date;
		}
		if (clazz.isArray()) {
			return ClassType.array;
		}
		if (List.class.isAssignableFrom(clazz)) {
			return ClassType.list;
		}
		if (Set.class.isAssignableFrom(clazz)) {
			return ClassType.set;
		}
		if (Map.class.isAssignableFrom(clazz)) {
			return ClassType.map;
		}
		return ClassType.object;
	}

	/**
	 * <p>
	 * ��ȡ���������ֶ�,��������
	 * </p>
	 * 
	 * @param clazz
	 *            ����
	 * @param deep
	 *            ȡ������ȣ�����ȡ�����ֶ�
	 * @return List [Field]
	 */
	public static List<Field> fields(Class<?> clazz, int deep) {
		if (clazz == null) {
			return null;
		}
		java.util.List<Field> fields = new java.util.ArrayList<Field>();
		Class<?> tClazz = clazz;
		int n = 0;
		while (!tClazz.equals(Object.class) && (deep <= 0 || (n++) < deep)) {
			try {
				Field[] fieldArr = tClazz.getDeclaredFields();
				java.util.Collections.addAll(fields, fieldArr);
			}
			catch (SecurityException e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return fields;
	}

	/**
	 * <p>
	 * ��ȡ���������ֶ�
	 * </p>
	 * 
	 * @param clazz
	 * @return List [Field]
	 */
	public static List<Field> fields(Class<?> clazz) {
		return fields(clazz, -1);
	}

	/**
	 * <p>
	 * ��ȡ���е�����
	 * </p>
	 * 
	 * @param clazz
	 *            ����
	 * @param fieldName
	 *            �ֶ�����
	 * @return Field �ֶ�����
	 */
	public static Field field(Class<?> clazz, String fieldName) {
		if (clazz == null || fieldName == null
				|| (fieldName = fieldName.trim()).length() == 0) {
			return null;
		}
		Class<?> tClazz = clazz;
		Field field = null;
		while (!tClazz.equals(Object.class)) {
			try {
				field = tClazz.getDeclaredField(fieldName);
				if (field != null) {
					break;
				}
			}
			catch (Exception e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return field;
	}

	/**
	 * <p>
	 * ��ȡ�������з���,��������
	 * </p>
	 * 
	 * @param clazz
	 * @param deep
	 * @return List[Method]
	 */
	public static List<Method> methods(Class<?> clazz, int deep) {
		if (clazz == null) {
			return null;
		}
		java.util.List<Method> methods = new java.util.ArrayList<Method>();
		Class<?> tClazz = clazz;
		int n = 0;
		while (!tClazz.equals(Object.class) && (deep <= 0 || (n++) < deep)) {
			try {
				Method[] fieldArr = tClazz.getDeclaredMethods();
				java.util.Collections.addAll(methods, fieldArr);
			}
			catch (SecurityException e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return methods;
	}

	/**
	 * <p>
	 * ��ȡ�������з���
	 * </p>
	 * 
	 * @param clazz
	 * @return List [Method]
	 */
	public static List<Method> methods(Class<?> clazz) {
		return methods(clazz, -1);
	}

	/**
	 * <p>
	 * ��ȡ����û�в����ķ���
	 * </p>
	 * 
	 * @param clazz
	 *            ����
	 * @param methodName
	 *            ��������
	 * @return Method ����
	 */
	public static Method method(Class<?> clazz, String methodName) {
		if (clazz == null || methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		Class<?> tClazz = clazz;
		Method method = null;
		while (!tClazz.equals(Object.class)) {
			try {
				method = tClazz.getDeclaredMethod(methodName);
				if (method != null) {
					break;
				}
			}
			catch (Exception e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return method;
	}

	/**
	 * <p>
	 * ��ȡ����ָ���������͵ķ���
	 * </p>
	 * 
	 * @param clazz
	 *            ����
	 * @param methodName
	 *            ������
	 * @param parameterTypes
	 *            ��������
	 * @return Method
	 */
	public static Method method(Class<?> clazz, String methodName,
			Class<?>... parameterTypes) {
		if (parameterTypes == null) {
			return method(clazz, methodName);
		}
		if (clazz == null || methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		Class<?> tClazz = clazz;
		Method method = null;
		while (!tClazz.equals(Object.class)) {
			try {
				method = tClazz.getDeclaredMethod(methodName, parameterTypes);
				if (method != null) {
					break;
				}
			}
			catch (Exception e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return method;
	}

	/**
	 * <p>
	 * ��ȡָ���������Ͳ����������෽���б�
	 * </p>
	 * 
	 * @param clazz
	 *            ��
	 * @param methodName
	 *            ������
	 * @param types
	 *            ��������
	 * @return List<Method>
	 */
	public static List<Method> methods(Class<?> clazz, String methodName,
			int types) {
		if (clazz == null || methodName == null) {
			return null;
		}
		if (types < 0) {
			types = 0;
		}
		java.util.List<Method> methods = new java.util.ArrayList<Method>();
		Class<?> tClazz = clazz;
		while (!tClazz.equals(Object.class)) {
			try {
				Method[] methodArr = tClazz.getDeclaredMethods();
				for (Method method : methodArr) {
					if (method.getName().equals(methodName)
							&& method.getParameterTypes().length == types) {
						methods.add(method);
					}
				}
			}
			catch (SecurityException e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return methods;
	}

	/**
	 * <p>
	 * ��ȡָ���������Ͳ����������෽��(���ж���򷵻ص�һ��)
	 * </p>
	 * 
	 * @param clazz
	 *            ��
	 * @param methodName
	 *            ������
	 * @param types
	 *            ��������
	 * @return Method
	 */
	public static Method method(Class<?> clazz, String methodName, int types) {
		if (clazz == null || methodName == null) {
			return null;
		}
		if (types < 0) {
			types = 0;
		}
		Class<?> tClazz = clazz;
		while (!tClazz.equals(Object.class)) {
			try {
				Method[] methodArr = tClazz.getDeclaredMethods();
				for (Method method : methodArr) {
					if (method.getName().equals(methodName)
							&& method.getParameterTypes().length == types) {
						return method;
					}
				}
			}
			catch (SecurityException e) {
			}
			tClazz = tClazz.getSuperclass();
		}
		return null;
	}

	/**
	 * <p>
	 * ��ʽ���ֶ�����
	 * </p>
	 * 
	 * @param fieldName
	 *            �ֶ�����
	 * @return String ��ʽ������ֶ�����
	 */
	public static String formatFieldName(String fieldName) {
		if (fieldName == null) {
			return null;
		}
		StringBuffer nameBuf = new StringBuffer();
		fieldName = fieldName.trim().toLowerCase().replaceAll("(\\s+|_$)", "");
		char ch;
		boolean lastCharIsSpace = false;
		for (int chAt = 0; chAt < fieldName.length(); chAt++) {
			ch = fieldName.charAt(chAt);
			if (ch == '_') {
				lastCharIsSpace = true;
				continue;
			}
			nameBuf.append(lastCharIsSpace ? (char) (ch & 0xDF) : ch);
			lastCharIsSpace = false;
		}
		return nameBuf.toString();
	}

	/**
	 * <p>
	 * ��ʽ�������ݿ��ֶ�
	 * </p>
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String formatColumnName(String fieldName) {
		if (fieldName == null || fieldName.isEmpty()) {
			return null;
		}
		char[] chs = fieldName.toCharArray();
		StringBuilder columnName = new StringBuilder();
		for (char ch : chs) {
			if (ch >= 'A' && ch <= 'Z') {
				columnName.append("_").append(ch);
			}
			else if (ch >= 'a' && ch <= 'z') {
				columnName.append((char) (ch - 32));
			}
			else {
				columnName.append(ch);
			}
		}
		return columnName.toString() + "_";
	}

	/**
	 * <p>
	 * �����ֶθ�ʽ���ɻ�ȡ��get������
	 * </p>
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String formatGetMethodName(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			return "get";
		}
		return "get" + (char) (fieldName.charAt(0) & 0xDF)
				+ fieldName.substring(1);
	}

	/**
	 * <p>
	 * �����ֶθ�ʽ�������ã�set������
	 * </p>
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String formatSetMethodName(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			return "set";
		}
		return "set" + (char) (fieldName.charAt(0) & 0xDF)
				+ fieldName.substring(1);
	}

	/**
	 * <p>
	 * ��������
	 * </p>
	 * 
	 * @param desc
	 *            Ŀ�����
	 * @param orig
	 *            Դ����
	 * @param excludeNull
	 *            �Ƿ����NULLֵ���ֶ�
	 */
	public static void copy(Object desc, Object orig, boolean excludeNull) {
		if (desc == null || orig == null) {
			return;
		}
		List<Field> descFields = fields(desc.getClass());
		List<Field> origFields = fields(orig.getClass());
		Map<String, Field> descFieldMap = new java.util.HashMap<String, Field>();
		for (Field f : descFields) {
			descFieldMap.put(f.getName(), f);
		}
		Field descField;
		Object oValue;
		for (Field f : origFields) {
			descField = descFieldMap.get(f.getName());
			if (descField != null
					&& (descField.getType().equals(f.getType()) || descField.getType().isAssignableFrom(f.getType()))) {
				try {
					oValue = fieldValue(orig, f);
					if (oValue == null) {
						if (!excludeNull) {
							putFieldValue(desc, descField, null);
						}
					}
					else {
						putFieldValue(desc, descField, oValue);
					}
				}
				catch (Exception e) {
				}
			}
		}
	}

	/**
	 * <p>
	 * ��������
	 * </p>
	 * 
	 * @param desc
	 *            Ŀ�����
	 * @param orig
	 *            Դ����
	 * @param includeFields
	 *            ָ���ֶ�
	 * @param excludeFields
	 *            �����ֶ�
	 * @param excludeNull
	 *            �Ƿ����NULLֵ���ֶ�
	 */
	public static void copy(Object desc, Object orig,
			Set<String> includeFields, Set<String> excludeFields,
			boolean excludeNull) {
		if (desc == null || orig == null) {
			return;
		}
		List<Field> descFields = fields(desc.getClass());
		List<Field> origFields = fields(orig.getClass());
		Map<String, Field> descFieldMap = new java.util.HashMap<String, Field>();
		for (Field f : descFields) {
			if (includeFields != null && !includeFields.contains(f.getName())
					|| excludeFields != null
					&& excludeFields.contains(f.getName())) {
				continue;
			}
			descFieldMap.put(f.getName(), f);
		}
		Field descField;
		Object oValue;
		for (Field f : origFields) {
			descField = descFieldMap.get(f.getName());
			if (descField != null
					&& (descField.getType().equals(f.getType()) || descField.getType().isAssignableFrom(f.getType()))) {
				try {
					oValue = fieldValue(orig, f);
					if (oValue == null) {
						if (!excludeNull) {
							putFieldValue(desc, descField, null);
						}
					}
					else {
						putFieldValue(desc, descField, oValue);
					}
				}
				catch (Exception e) {
				}
			}
		}
	}

	/**
	 * <p>
	 * ��ȡ�ֶ�ֵ
	 * </p>
	 * 
	 * @param obj
	 *            Object
	 * @param field
	 *            Field
	 * @return Object
	 */
	public static Object fieldValue(Object obj, Field field) {
		if (obj == null || field == null) {
			return null;
		}
		String fieldName = field.getName();
		Method method = null;
		String methodName = (char) (fieldName.charAt(0) & 0xDF)
				+ fieldName.substring(1);
		Class<?> clazz = obj.getClass();
		if (field.getType() == Boolean.class
				|| field.getType() == boolean.class) {
			try {
				method = clazz.getMethod("is" + methodName);
				if (Modifier.isPublic(method.getModifiers())) {
					return method.invoke(obj);
				}
			}
			catch (Exception e) {
			}
		}
		try {
			method = clazz.getMethod("get" + methodName);
			if (Modifier.isPublic(method.getModifiers())) {
				return method.invoke(obj);
			}
		}
		catch (Exception e) {
		}
		if (Modifier.isPublic(field.getModifiers())) {
			try {
				return field.get(obj);
			}
			catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡ�ֶ�ֵ
	 * </p>
	 * 
	 * @param obj
	 *            Object
	 * @param fieldName
	 *            String
	 * @return Object
	 */
	public static Object fieldValue(Object obj, String fieldName) {
		if (obj == null || fieldName == null
				|| (fieldName = fieldName.trim()).isEmpty()) {
			return null;
		}
		Method method = null;
		String methodName = (char) (fieldName.charAt(0) & 0xDF)
				+ fieldName.substring(1);
		Class<?> clazz = obj.getClass();
		try {
			method = clazz.getMethod("is" + methodName);
			if (Modifier.isPublic(method.getModifiers())) {
				Object val = method.invoke(obj);
				if (val instanceof Boolean) {
					return val;
				}
			}
		}
		catch (Exception e) {
		}
		try {
			method = clazz.getMethod("get" + methodName);
			if (Modifier.isPublic(method.getModifiers())) {
				return method.invoke(obj);
			}
		}
		catch (Exception e) {
		}
		Field field = null;
		while (!clazz.equals(Object.class)) {
			try {
				field = clazz.getDeclaredField(fieldName);
			}
			catch (Exception e) {
			}
			if (field != null) {
				try {
					if (field.isAccessible()) {
						return field.get(obj);
					}
					else {
						field.setAccessible(true);
						Object val = field.get(obj);
						field.setAccessible(false);
						return val;
					}
				}
				catch (Exception e) {
					return null;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	/**
	 * <p>
	 * ִ�з���ֵ
	 * </p>
	 * 
	 * @param bean
	 *            Object
	 * @param methodName
	 *            String
	 * @return Object
	 */
	public static Object methodValue(Object bean, String methodName) {
		if (bean == null) {
			return null;
		}
		Method method = method(bean.getClass(), methodName);
		if (method == null) {
			return null;
		}
		try {
			return method.invoke(bean);
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * ִ�з���ֵ
	 * </p>
	 * 
	 * @param bean
	 * @param methodName
	 * @param params
	 * @return Object
	 */
	public static Object methodValue(Object bean, String methodName,
			Object... params) {
		if (bean == null || methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		Method method = null;
		if (params == null) {
			return methodValue(bean, methodName);
		}
		Class<?>[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i] != null ? params[i].getClass()
					: String.class;
		}
		try {
			method = bean.getClass().getDeclaredMethod(methodName.trim(), classes);
		}
		catch (Exception ex) {
			Method[] methods = bean.getClass().getDeclaredMethods();
			Class<?>[] types = null;
			boolean isType = false;
			for (int i = 0; i < methods.length; i++) {
				if (!methods[i].getName().equals(methodName)) {
					continue;
				}
				types = methods[i].getParameterTypes();
				if (types.length != classes.length) {
					continue;
				}
				isType = false;
				for (int j = 0; j < types.length; j++) {
					if (types[j].isAssignableFrom(classes[j])) {
						isType = true;
						continue;
					}
					isType = false;
					break;
				}
				if (isType) {
					method = methods[i];
					break;
				}
			}

		}
		if (method == null) {
			return null;
		}
		try {
			return method.invoke(bean, params);
		}
		catch (Exception ex1) {
			return null;
		}
	}

	/**
	 * <p>
	 * ִ���ֶα�ʽ
	 * </p>
	 * 
	 * @param bean
	 *            ����
	 * @param expression
	 *            �ֶα��ʽ
	 * @return Object
	 */
	@SuppressWarnings("rawtypes")
	public static Object eval(Object bean, String expression) {
		if (bean == null) {
			return null;
		}
		if (expression == null || (expression = expression.trim()).isEmpty()) {
			return bean;
		}
		int dot = expression.indexOf('.');
		String fieldName = dot != -1 ? fieldName = expression.substring(0, dot)
				: expression;
		Object v = null;
		Pattern pattern = Pattern.compile("\\[.*\\]");
		Matcher matcher = pattern.matcher(fieldName);
		String mo = null, mf = null;
		int mi = -1;
		if (matcher.find()) {
			mf = matcher.group().replaceAll("\\[|\\]", "");
			if (mf.indexOf('"') == -1) {
				try {
					mi = Integer.parseInt(mf);
					mf = null;
				}
				catch (NumberFormatException e) {
				}
			}
			else {
				mf = mf.replaceAll("\"|\'", "");
			}
			mo = matcher.replaceAll("");
		}
		if (mo != null) {
			fieldName = mo;
		}
		if (bean instanceof Map) {
			v = ((Map) bean).get(fieldName);
		}
		else {
			v = fieldValue(bean, fieldName);
		}
		if (mo != null && v != null) {
			if ((v instanceof Map) && mf != null) {
				v = ((Map) v).get(mf);
			}
			else if ((v instanceof Collection) && mi > 0) {
				Collection<?> coll = (Collection<?>) v;
				v = coll.size() > mi ? coll.toArray()[mi] : null;
			}
			else if (v.getClass().isArray() && mi > 0) {
				Object[] arr = ((Object[]) v);
				v = arr.length > mi ? arr[mi] : null;
			}
		}
		if (v != null && dot > 1) {
			v = eval(v, expression.substring(dot + 1));
		}
		return v;
	}

	/**
	 * <p>
	 * ʵ������
	 * </p>
	 * 
	 * @param className
	 * @param singletonMethod
	 * @param param
	 * @return Object
	 */
	public static Object newInstance(String className, String singletonMethod,
			Object param) {
		if (className == null || (className = className.trim()).length() == 0) {
			return null;
		}
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException ex) {
			return null;
		}
		if (singletonMethod != null
				&& (singletonMethod = singletonMethod.trim()).length() > 0) {
			if (singletonMethod.length() == 0) {
				singletonMethod = "getInstance";
			}
			return callStaticMethod(clazz, singletonMethod, param);
		}
		try {
			return clazz.newInstance();
		}
		catch (Exception ex) {
		}
		return null;
	}

	/**
	 * <p>
	 * ʵ�������
	 * </p>
	 * 
	 * @param clazz
	 * @param param
	 * @return Object
	 */
	public static <T> T newInstance(Class<T> clazz, Object param) {
		if (clazz == null) {
			return null;
		}
		try {
			if (param == null) {
				return clazz.newInstance();
			}
			Constructor<T> c = clazz.getConstructor(param.getClass());
			return c.newInstance(param);
		}
		catch (Exception ex) {
		}
		return null;
	}

	/**
	 * <p>
	 * ���þ�̬����
	 * </p>
	 * 
	 * @param className
	 *            String
	 * @param methodName
	 *            String
	 * @return Object
	 */
	public static Object callStaticMethod(String className, String methodName) {
		if (className == null || (className = className.trim()).length() == 0
				|| methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		try {
			return callStaticMethod(Class.forName(className), methodName);
		}
		catch (ClassNotFoundException ex) {
			return null;
		}
	}

	/**
	 * <p>
	 * ���þ�̬����
	 * </p>
	 * 
	 * @param className
	 *            String
	 * @param methodName
	 *            String
	 * @param param
	 *            Object
	 * @return Object
	 */
	public static Object callStaticMethod(String className, String methodName,
			Object param) {
		if (className == null || (className = className.trim()).length() == 0
				|| methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		try {
			return callStaticMethod(Class.forName(className), methodName, param);
		}
		catch (ClassNotFoundException ex) {
			return null;
		}
	}

	/**
	 * <p>
	 * ���þ�̬����
	 * </p>
	 * 
	 * @param clazz
	 *            Class
	 * @param methodName
	 *            String
	 * @return Object
	 */
	public static Object callStaticMethod(Class<?> clazz, String methodName) {
		if (clazz == null || methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName);
			int iModifier = Modifier.PUBLIC | Modifier.STATIC;
			if (method != null
					&& (method.getModifiers() & iModifier) == iModifier) {
				return method.invoke(null);
			}
		}
		catch (Exception ex) {
		}
		return null;
	}

	/**
	 * <p>
	 * ���þ�̬����
	 * </p>
	 * 
	 * @param clazz
	 * @param methodName
	 * @param params
	 * @return Object
	 */
	public static Object callStaticMethod(Class<?> clazz, String methodName,
			Object... params) {
		if (clazz == null || methodName == null
				|| (methodName = methodName.trim()).length() == 0) {
			return null;
		}
		if (params == null) {
			return callStaticMethod(clazz, methodName);
		}
		Method method = null;
		Class<?>[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i] != null ? params[i].getClass()
					: String.class;
		}
		try {
			method = clazz.getDeclaredMethod(methodName, classes);
		}
		catch (Exception ex) {
			Method[] methods = clazz.getDeclaredMethods();
			Class<?>[] types = null;
			boolean isType = false;
			for (int i = 0; i < methods.length; i++) {
				if (!methods[i].getName().equals(methodName)) {
					continue;
				}
				types = methods[i].getParameterTypes();
				if (types.length != classes.length) {
					continue;
				}
				isType = false;
				for (int j = 0; j < types.length; j++) {
					if (types[j].isAssignableFrom(classes[j])) {
						isType = true;
						continue;
					}
					isType = false;
					break;
				}
				if (isType) {
					method = methods[i];
					break;
				}
			}
		}
		int iModifier = Modifier.PUBLIC | Modifier.STATIC;
		if (method != null && (method.getModifiers() & iModifier) == iModifier) {
			try {
				return method.invoke(null, params);
			}
			catch (Exception ex1) {
				LOG.log(java.util.logging.Level.WARNING, "conn't invoke method!", ex1);
				return null;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * ���ö���ָ���ֶ�ֵ
	 * </p>
	 * 
	 * @param object
	 * @param fieldName
	 * @param oValue
	 * @return boolean
	 */
	public static boolean putFieldValue(Object object, String fieldName,
			Object oValue) {
		if (object == null) {
			return false;
		}
		Field field = field(object.getClass(), fieldName);
		return putFieldValue(object, field, oValue);
	}

	/**
	 * <p>
	 * ���ö���ָ���ֶ�ֵ
	 * </p>
	 * 
	 * @param object
	 *            Object
	 * @param field
	 *            Field
	 * @param oValue
	 *            Object
	 * @return boolean
	 */
	public static boolean putFieldValue(Object object, Field field,
			Object oValue) {
		if (object == null || field == null) {
			return false;
		}
		boolean flag = false;
		Class<?> clazz = object.getClass();
		try {
			if (Modifier.isPublic(field.getModifiers())) {
				field.set(object, oValue);
			}
			else {
				String methodName = "set"
						+ ((char) (field.getName().charAt(0) & 0xDF))
						+ field.getName().substring(1);
				Method method = clazz.getMethod(methodName, new Class[] { field.getType() });
				if (Modifier.isPublic(method.getModifiers())) {
					method.invoke(object, new Object[] { oValue });
				}
			}
		}
		catch (Exception e) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
				try {
					field.set(object, oValue);
					flag = true;
				}
				catch (Exception e1) {
				}
				field.setAccessible(false);
			}
		}
		return flag;
	}

	/**
	 * <p>
	 * ��������ֵ
	 * </p>
	 * 
	 * @param object
	 * @param fieldName
	 * @param value
	 * @return boolean
	 */
	public static boolean putPropertyValue(Object object, String fieldName,
			String value) {
		if (object == null || fieldName == null) {
			return false;
		}
		boolean flag = false;
		Class<?> clazz = object.getClass();
		try {
			Field field = field(clazz, fieldName);
			if (field == null) {
				String methodName = "set"
						+ ((char) (fieldName.charAt(0) & 0xDF))
						+ fieldName.substring(1);
				Method[] methods = clazz.getMethods();
				Method method;
				for (int i = 0; i < methods.length; i++) {
					method = methods[i];
					if (method.getName().equals(methodName)) {
						Class<?>[] clazzes = method.getParameterTypes();
						if (clazzes != null && clazzes.length == 1) {
							if (Modifier.isPublic(method.getModifiers())) {
								Object oValue = StringUtils.valueOf(value, clazzes[0]);
								method.invoke(object, new Object[] { oValue });
							}
						}
					}
				}
				return false;
			}
			Object oValue = StringUtils.valueOf(value, field.getType());
			if (oValue == null) {
				return false;
			}
			if (Modifier.isPublic(field.getModifiers())) {
				field.set(object, oValue);
			}
			else {
				String methodName = "set"
						+ ((char) (field.getName().charAt(0) & 0xDF))
						+ field.getName().substring(1);
				Method method = clazz.getMethod(methodName, new Class[] { field.getType() });
				if (Modifier.isPublic(method.getModifiers())) {
					method.invoke(object, new Object[] { oValue });
				}
			}
		}
		catch (Exception ex) {
		}
		return flag;
	}

	/**
	 * <p>
	 * ��ȡ�������Ե�ʵ������
	 * </p>
	 * 
	 * @param field
	 * @return Class<?>
	 */
	public static Class<?> fetchActualType(Field field) {
		if (field == null) {
			return null;
		}
		try {
			Type t = field.getGenericType();
			if (t != null && t instanceof ParameterizedType) {
				ParameterizedType ptype = (ParameterizedType) t;
				Type[] types = ptype.getActualTypeArguments();
				if (types != null && types.length > 0) {
					return (Class<?>) types[0];
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡ�������Ե�ʵ������
	 * </p>
	 * 
	 * @param field
	 * @return Class<?>[]
	 */
	public static Class<?>[] fetchActualTypes(Field field) {
		if (field == null) {
			return null;
		}
		Class<?>[] classes = null;
		try {
			Type t = field.getGenericType();
			if (t != null && t instanceof ParameterizedType) {
				ParameterizedType ptype = (ParameterizedType) t;
				Type[] types = ptype.getActualTypeArguments();
				if (types != null && types.length > 0) {
					classes = new Class<?>[types.length];
					for (int i = 0; i < classes.length; i++) {
						classes[i] = (Class<?>) types[i];
					}
				}
			}
		}
		catch (Exception e) {
		}
		return classes;
	}

	/**
	 * <p>
	 * ��ȡ�������ʵ������
	 * </p>
	 * 
	 * @param clazz
	 * @return Class<?>
	 */
	public static Class<?> fetchActualType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		try {
			Type t = clazz.getGenericSuperclass();
			if (t != null && t instanceof ParameterizedType) {
				ParameterizedType ptype = (ParameterizedType) t;
				Type[] types = ptype.getActualTypeArguments();
				if (types != null && types.length > 0) {
					if (types[0] instanceof Class) {
						return (Class<?>) types[0];
					}
					return (new Object[0]).getClass();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡ�������ʵ������
	 * </p>
	 * 
	 * @param clazz
	 * @return Class<?>[]
	 */
	public static Class<?>[] fetchActualTypes(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		Class<?>[] classes = null;
		try {
			Type t = clazz.getGenericSuperclass();
			if (t != null && t instanceof ParameterizedType) {
				ParameterizedType ptype = (ParameterizedType) t;
				Type[] types = ptype.getActualTypeArguments();
				if (types != null && types.length > 0) {
					classes = new Class<?>[types.length];
					for (int i = 0; i < classes.length; i++) {
						classes[i] = (Class<?>) types[i];
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * <p>
	 * ����
	 * </p>
	 * <p>
	 * create: 2010-12-21 ����10:50:35
	 * </p>
	 * 
	 * @author ��½�[xudejian@126.com]
	 * @version 1.0
	 */
	public static enum ClassType {
		/**
		 * ȱʡ����
		 */
		defalt,

		/**
		 * �ַ�������
		 */
		string,

		/**
		 * ��չ�ַ�������
		 */
		tostring,

		/**
		 * ��ֵ����
		 */
		number,

		/**
		 * ʱ������
		 */
		date,

		/**
		 * ��������
		 */
		array,

		/**
		 * set��������
		 */
		set,

		/**
		 * list��������
		 */
		list,

		/**
		 * map����ӳ������
		 */
		map,

		/**
		 * ������������
		 */
		object

	}

}
