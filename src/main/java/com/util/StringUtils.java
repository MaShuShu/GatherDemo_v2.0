package com.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;

public class StringUtils {
	
	/**
	 * <p>
	 * ����ת�����ַ���
	 * </p>
	 * 
	 * @param value
	 * @return String
	 */
	public static String valueOf(Object value) {
		if (value == null) {
			return "";
		}
		String text = null;
		if (value instanceof java.util.Date) {
			text = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(value);
			return text.replaceAll("((\\s|:)00)+$", "");
		}
		if (value instanceof Number) {
			return NumberUtils.formatNumber((Number) value, null);
		}
		if (value instanceof Boolean) {
			return (Boolean) value ? "Y" : "N";
		}
		if (value instanceof Byte) {
			return (Byte) value == 1 ? "Y" : "N";
		}
		if (value instanceof InputStream) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			String val = null;
			InputStream is = (InputStream) value;
			byte[] bs = new byte[1024];
			int len;
			try {
				while ((len = is.read(bs)) > 0) {
					bos.write(bs, 0, len);
				}
				val = bos.toString("UTF-8");
			} catch (Exception e) {
			} finally {
				try {
					bos.close();
				} catch (Exception e) {
				}
			}
			return val;
		}
		return value.toString();
	}

	/**
	 * <p>
	 * �ַ���ת���ɶ���
	 * </p>
	 * 
	 * @param <T>
	 * @param value
	 * @param clazz
	 * @return <T> T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T valueOf(String value, Class<?> clazz) {
		if (value == null || clazz == null) {
			return null;
		}
		if (String.class.equals(clazz)) {
			return (T) value;
		}
		value = value.trim();
		if (value.length() == 0) {
			return null;
		}
		if (Boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
			return (T) Boolean.valueOf(value);
		}
		if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) Byte.valueOf(value);
		}
		if (Character.class.equals(clazz) || char.class.equals(clazz)) {
			return (T) new Character(value.length() > 0 ? value.charAt(0)
					: '\0');
		}
		if (Short.class.equals(clazz) || short.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) Short.valueOf(value);
		}
		if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) Integer.valueOf(value);
		}
		if (Long.class.equals(clazz) || long.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) Long.valueOf(value);
		}
		if (Float.class.equals(clazz) || float.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) Float.valueOf(value);
		}
		if (Double.class.equals(clazz) || double.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) Double.valueOf(value);
		}
		if (BigDecimal.class.equals(clazz)) {
			value = value.replaceAll(",", "");
			return (T) new BigDecimal(value);
		}
		if (Date.class.isAssignableFrom(clazz)) {
			return (T) DateUtils.parse(value);
		}
		if (clazz == byte[].class) {
			return (T) value.getBytes();
		}
		if (Collection.class.isAssignableFrom(clazz)
				|| Map.class.isAssignableFrom(clazz) || clazz.isArray()) {
			return null;
		}
		try {
			Constructor<?> c = clazz
					.getConstructor(new Class[] { String.class });
			if (c != null) {
				return (T) c.newInstance(value);
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * <p>
	 * �ض��ֽ���,һ�������ְ��������ֽ���
	 * </p>
	 * 
	 * @param str
	 * @param size
	 * @return String
	 */
	public static String toByteSizeString(String str, int size) {
		return toByteSizeString(str, size, 0);
	}

	/**
	 * <p>
	 * �ض��ֽ���,һ�������ְ��������ֽ���
	 * </p>
	 * 
	 * @param str
	 * @param size
	 * @param index
	 * @return String
	 */
	public static String toByteSizeString(String str, int size, int index) {
		if (str == null || size <= 0) {
			return "";
		}
		byte[] bs = str.getBytes();
		if (bs.length <= size) {
			return str;
		}
		if (index < 0) {
			index = 0;
		}
		char[] cs = str.toCharArray();
		if (cs.length <= index) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = index; i < cs.length; i++) {
			if (buf.toString().getBytes().length > size) {
				break;
			}
			buf.append(cs[i]);
		}
		if (buf.toString().getBytes().length > size) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * �����ַ����з���������Ӵ�
	 * </p>
	 * 
	 * @param input
	 * @param regex
	 * @return List<String>
	 */
	public static List<String> find(String input, String regex) {
		if (input == null || input.length() == 0 || regex == null
				|| regex.length() == 0) {
			return new ArrayList<String>();
		}
		Matcher matcher = Pattern.compile(regex,
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(input);
		List<String> groups = new ArrayList<String>();
		while (matcher.find()) {
			groups.add(matcher.group());
		}
		return groups;
	}

	/**
	 * <p>
	 * �Ƿ�ƥ��id 123,456,789,...
	 * </p>
	 * 
	 * @param input
	 * @param id
	 * @return Boolean
	 */
	public static Boolean matcheId(String input, String id) {
		if (input == null || id == null)
			return false;
		return Pattern.matches("^(|.+,)" + id + "(,.*+|)$", input);
	}

	/**
	 * <p>
	 * �ַ�����ֳ�Long����
	 * </p>
	 * 
	 * @param str
	 * @param regex
	 * @return List<Long>
	 */
	public static List<Long> split(String str, String regex) {
		List<Long> list = new ArrayList<Long>();
		if (str != null && (str = str.trim()).length() > 0) {
			if (regex == null || regex.length() == 0) {
				regex = ",";
			}
			String[] arr = str.split(regex);
			for (String a : arr) {
				try {
					list.add(Long.valueOf(a));
				} catch (NumberFormatException e) {
				}
			}
		}
		return list;
	}

	/**
	 * <p>
	 * ��ʽ������Ϊ�ַ�����Ĭ�϶��ŷָ�
	 * </p>
	 * 
	 * @param <T>
	 * @param coll
	 * @param seperator
	 * @return String
	 */
	public static <T> String join(Collection<T> coll, String seperator) {
		if (coll == null || coll.isEmpty()) {
			return "";
		}
		if (seperator == null || seperator.length() == 0) {
			seperator = ",";
		}
		StringBuilder buf = new StringBuilder();
		Iterator<T> it = coll.iterator();
		buf.append(it.next());
		while (it.hasNext()) {
			buf.append(seperator).append(it.next());
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * ��ʽ����ֵΪ�ַ�����Ĭ�϶��ŷָ�
	 * </p>
	 * 
	 * @param <T>
	 * @param arr
	 * @param seperator
	 * @return String
	 */
	public static <T> String join(T[] arr, String seperator) {
		if (arr == null || arr.length == 0) {
			return "";
		}
		if (seperator == null || seperator.length() == 0) {
			seperator = ",";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			sb.append(seperator).append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * ��ʽ��ָ���������ַ���Ϊ�ϲ��ַ�����Ĭ�϶��ŷָ�
	 * </p>
	 * 
	 * @param str
	 * @param length
	 * @param seperator
	 * @return String
	 */
	public static String join(String str, int length, String seperator) {
		if (str == null || length <= 0) {
			return "";
		}
		if (seperator == null || seperator.length() == 0) {
			seperator = ",";
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i = 1; i < length; i++) {
			sb.append(seperator).append(str);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * ��ȡָ���������������ascii����д�ַ���Ϊ2����
	 * </p>
	 * 
	 * @param str
	 * @param length
	 * @return String
	 */
	public static String charLength(String str, int length) {
		if (str == null || length <= 0) {
			return "";
		}
		char[] chs = str.toCharArray();
		if (chs.length * 2 <= length) {
			return str;
		}
		StringBuilder targetBuffer = new StringBuilder();
		int len = 0;
		int chValue;
		for (char ch : chs) {
			chValue = (int) ch;
			if (chValue > 127 || chValue >= 65 && chValue <= 90) {
				len += 2;
			} else {
				len++;
			}
			if (len > length) {
				break;
			}
			targetBuffer.append(ch);
		}
		return targetBuffer.toString();
	}

	/**
	 * <p>
	 * ǰ�油ָ���ַ�����������ָ�����Ȼ���ָ������С�ڵ����㣬�򷵻�ԭ��
	 * </p>
	 * 
	 * @param str
	 * @param length
	 * @param chr
	 * @return String
	 */
	public static String fillChar(String str, int length, char chr) {
		if (length <= 0) {
			return str;
		}
		if (str == null) {
			str = "";
		}
		int l = length - str.length();
		if (l <= 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < l; i++) {
			buf.append(chr);
		}
		return buf + str;
	}

	/**
	 * <p>
	 * ת���������js�ַ������滻˫���ţ�\"�������У�\n����ת�����\��
	 * </p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String toJsString(String str) {
		if (str == null) {
			return "";
		}
		return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"")
				.replaceAll("\n", "\\\\n");
	}

	/**
	 * �ַ���ƴ��
	 * 
	 * @param objects
	 * @return String
	 */
	public static String concat(Object... objects) {
		if (objects == null || objects.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object object : objects) {
			if (object != null) {
				sb.append(object);
			}
		}
		return sb.toString();
	}

	/**
	 * �Ƿ�Ϊ�����ַ�
	 * 
	 * @param c
	 * @return boolean
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	/**
	 * �Ƿ��������ַ�
	 * 
	 * @param str
	 * @return boolean
	 */
	public static final boolean isChinese(String str) {
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (isChinese(ch[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ������ֵ��ʾת��������
	 * 
	 * @param chinese
	 * @return int
	 */
	public static final int toInt(String chinese) {
		int result = 0;
		int yi = 1;// ��¼�߼���λ
		int wan = 1;// ��¼�߼���λ
		int ge = 1;// ��¼��λ
		char[] chs = chinese.toCharArray();
		for (int i = chs.length - 1; i >= 0; i--) {
			char c = chs[i];
			int temp = 0;// ��¼��ֵ
			switch (c) {
			// ��ֵ
			case '��':
			case '��':
				temp = 0;
				break;
			case 'һ':
				temp = 1 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 2 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 3 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 4 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 5 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 6 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 7 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 8 * ge * wan * yi;
				ge = 1;
				break;
			case '��':
				temp = 9 * ge * wan * yi;
				ge = 1;
				break;
			// ��λ��ǰ׺�ǵ�����
			case 'ʮ':
				ge = 10;
				break;
			case '��':
				ge = 100;
				break;
			case 'ǧ':
				ge = 1000;
				break;
			// �߼���λ��ǰ׺�����Ƕ������
			case '��':
				wan = 10000;
				ge = 1;
				break;
			case '��':
				yi = 100000000;
				wan = 1;
				ge = 1;
				break;
			default:
				return -1;
			}
			result += temp;
		}
		if (ge > 1) {
			result += 1 * ge * wan * yi;
		}
		return result;
	}

	/**
	 * �Ѱ���������תΪ��������
	 * 
	 * @param num
	 * @return String
	 */
	public static String toChinese(int num) {
		StringBuilder sb = new StringBuilder();
		final String CN_NUM = "��һ�����������߰˾�";
		final String CN_W = "ʮ��ǧ";
		int n = num;
		for (int i = 0; n > 0; i++) {
			int g = n % 10;
			if ((i >= 1) && (i <= 3)) {
				if (g != 0) {
					sb.insert(0, CN_W.charAt(i - 1));
				}
			} else if (i == 4) {
				sb.insert(0, '��');
			} else if ((i >= 5) && (i <= 7)) {
				if (g != 0) {
					sb.insert(0, CN_W.charAt(i - 4 - 1));
				}
			} else if (i == 8) {
				sb.insert(0, '��');
			} else if ((i >= 9) && (i <= 10)) {
				if (g != 0) {
					sb.insert(0, CN_W.charAt(i - 8 - 1));
				}
			}
			if ((g != 0) || ((i > 0) && (sb.charAt(0) != '��'))) {
				sb.insert(0, CN_NUM.charAt(g));
			}
			n = n / 10;
		}
		if ((sb.length() > 1) && (sb.charAt(sb.length() - 1) == '��')) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * �����ַ���
	 * 
	 * @param str
	 * @param prefix
	 *            ǰ׺λ��
	 * @param suffix
	 *            ��׺λ��
	 * @return String
	 */
	public static String hide(String str, int prefix, int suffix) {
		if (prefix < 0) {
			prefix = 0;
		}
		if (suffix < 0) {
			suffix = 0;
		}
		int len = 0;
		if (str == null || (len = str.length() - (prefix + suffix)) <= 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		if (prefix > 0) {
			sb.append(str.substring(0, prefix));
		}
		for (int i = 0; i < len; i++) {
			sb.append('*');
		}
		if (suffix > 0) {
			sb.append(str.substring(prefix + len));
		}
		return sb.toString();
	}

	/**
	 * ת���ַ���
	 * 
	 * @param str
	 * @param charset
	 * @param charsetFrom
	 * @return String
	 */
	public static String trans(String str, String charset, String charsetFrom) {
		if (str == null) {
			return null;
		}
		try {
			byte[] bs = charsetFrom != null ? str.getBytes(charsetFrom) : str
					.getBytes();
			str = new String(bs, charset);
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}

	/**
	 * 
	 * <p>
	 * ת����ƴ��
	 * </p>
	 * <p>
	 * author ��½���Email��xudejian_dev@126.com��QQ��66018777��
	 * </p>
	 * 
	 * @param str
	 *            ���ִ�
	 * @return String
	 */
	public static String toPinyin(String str) {
		if (str == null || str.isEmpty()) {
			return "";
		}
		char[] chs = str.toCharArray();
		String[] pinyinArray;
		StringBuilder buf = new StringBuilder();
		for (char ch : chs) {
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch);
			if (pinyinArray != null && pinyinArray.length > 0) {
				for (String py : pinyinArray) {
					buf.append(py);
				}
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 
	 * <p>
	 * ת����������ĸ
	 * </p>
	 * <p>
	 * author ��½���Email��xudejian_dev@126.com��QQ��66018777��
	 * </p>
	 * 
	 * @param str
	 *            ���ִ�
	 * @param onlyFirst
	 *            �Ƿ������ʾһ������ĸ
	 * @return String
	 */
	public static String toInitialPinyin(String str, boolean onlyFirst) {
		if (str == null || str.isEmpty()) {
			return "";
		}
		char[] chs = str.toCharArray();
		String[] pinyinArray;
		StringBuilder buf = new StringBuilder();
		for (char ch : chs) {
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch);
			if (pinyinArray != null && pinyinArray.length > 0) {
				buf.append(pinyinArray[0].charAt(0));
			} else {
				buf.append(ch);
			}
			if (onlyFirst) {
				return buf.toString();
			}
		}
		return buf.toString();
	}

	/***
	 * <p>
	 * �ֽ�����ת16����
	 * </p>
	 * <p>
	 * author ��½���xudejian_dev@126.com��QQ:66018777��
	 * </p>
	 * 
	 * @param src
	 * @return String
	 */
	public static String bytesToHex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * <p>
	 * 16����ת�ֽ�����
	 * </p>
	 * <p>
	 * author ��½���xudejian_dev@126.com��QQ:66018777��
	 * </p>
	 * 
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] hexToBytes(String hex) {
		if (hex == null || hex.isEmpty()) {
			return null;
		}
		hex = hex.toUpperCase();
		int length = hex.length() / 2;
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			d[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * (i + 1)),
					16);
		}
		return d;
	}

}
