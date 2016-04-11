package com.util;

import java.text.NumberFormat;
import java.util.HashMap;

/**
 * <p>
 * ����ת��д
 * </p>
 * <p>
 * create: 2011-7-5 ����05:29:29
 * </p>
 * 
 * @author ����[jimsonhappy@126.com]
 * @version 1.0
 */
public class MoneyUtils {
	
	private static HashMap<String, String> NUMBER_MAP = new HashMap<String, String>();

	private static HashMap<String, String> MONEY_MAP = new HashMap<String, String>();

	private static NumberFormat FORMAT = NumberFormat.getInstance();

	static {
		FORMAT.setMaximumFractionDigits(4);
		FORMAT.setMinimumFractionDigits(2);
		FORMAT.setGroupingUsed(false);
		NUMBER_MAP.put("0", "��");
		NUMBER_MAP.put("1", "Ҽ");
		NUMBER_MAP.put("2", "��");
		NUMBER_MAP.put("3", "��");
		NUMBER_MAP.put("4", "��");
		NUMBER_MAP.put("5", "��");
		NUMBER_MAP.put("6", "½");
		NUMBER_MAP.put("7", "��");
		NUMBER_MAP.put("8", "��");
		NUMBER_MAP.put("9", "��");
		NUMBER_MAP.put(".", ".");
		MONEY_MAP.put("1", "ʰ");
		MONEY_MAP.put("2", "��");
		MONEY_MAP.put("3", "Ǫ");
		MONEY_MAP.put("4", "��");
		MONEY_MAP.put("5", "ʰ");
		MONEY_MAP.put("6", "��");
		MONEY_MAP.put("7", "Ǫ");
		MONEY_MAP.put("8", "��");
	}

	public static String format(String number) {
		checkPrecision(number);
		String result = convertToChineseNumber(number);
		result = addUnitsToChineseMoneyString(result);
		return result;
	}

	public static String format(double number) {
		return format(FORMAT.format(number));
	}

	public static String format(int number) {
		return format(FORMAT.format(number));
	}

	public static String format(long number) {
		return format(FORMAT.format(number));
	}

	public static String format(Number number) {
		return format(FORMAT.format(number));
	}

	private static String convertToChineseNumber(String number) {
		if (number == null) {
			return "";
		}
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer();
		for (int i = 0; i < number.length(); i++) {
			cMoneyStringBuffer
					.append(NUMBER_MAP.get(number.substring(i, i + 1)));
		}
		// ʰ��Ǫ���ڵȶ��Ǻ���������еĵ�λ����������
		int indexOfDot = cMoneyStringBuffer.indexOf(".");
		int moneyPatternCursor = 1;
		for (int i = indexOfDot - 1; i > 0; i--) {
			cMoneyStringBuffer
					.insert(i, MONEY_MAP.get("" + moneyPatternCursor));
			moneyPatternCursor = moneyPatternCursor == 8 ? 1
					: moneyPatternCursor + 1;
		}
		String fractionPart = cMoneyStringBuffer.substring(cMoneyStringBuffer
				.indexOf("."));
		cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("."),
				cMoneyStringBuffer.length());
		while (cMoneyStringBuffer.indexOf("��ʰ") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("��ʰ"),
					cMoneyStringBuffer.indexOf("��ʰ") + 2, "��");
		}
		while (cMoneyStringBuffer.indexOf("���") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("���"),
					cMoneyStringBuffer.indexOf("���") + 2, "��");
		}
		while (cMoneyStringBuffer.indexOf("��Ǫ") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("��Ǫ"),
					cMoneyStringBuffer.indexOf("��Ǫ") + 2, "��");
		}
		while (cMoneyStringBuffer.indexOf("����") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("����"),
					cMoneyStringBuffer.indexOf("����") + 2, "��");
		}
		while (cMoneyStringBuffer.indexOf("����") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("����"),
					cMoneyStringBuffer.indexOf("����") + 2, "��");
		}
		while (cMoneyStringBuffer.indexOf("����") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("����"),
					cMoneyStringBuffer.indexOf("����") + 2, "��");
		}
		if (cMoneyStringBuffer.lastIndexOf("��") == cMoneyStringBuffer.length() - 1)
			cMoneyStringBuffer.delete(cMoneyStringBuffer.length() - 1,
					cMoneyStringBuffer.length());
		cMoneyStringBuffer.append(fractionPart);
		result = cMoneyStringBuffer.toString();
		return result;
	}

	private static String addUnitsToChineseMoneyString(String number) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer(number);
		int indexOfDot = cMoneyStringBuffer.indexOf(".");
		cMoneyStringBuffer.replace(indexOfDot, indexOfDot + 1, "Ԫ");
		cMoneyStringBuffer.insert(cMoneyStringBuffer.length() - 1, "��");
		cMoneyStringBuffer.insert(cMoneyStringBuffer.length(), "��");
		if (cMoneyStringBuffer.indexOf("������") != -1) // û����ͷ������
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("������"),
					cMoneyStringBuffer.length(), "��");
		else if (cMoneyStringBuffer.indexOf("���") != -1) // û����֣�����
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("���"),
					cMoneyStringBuffer.length(), "��");
		else {
			if (cMoneyStringBuffer.indexOf("���") != -1)
				cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("���"),
						cMoneyStringBuffer.indexOf("���") + 2);
			// tmpBuffer.append("��");
		}
		result = cMoneyStringBuffer.toString();
		return result;
	}

	private static void checkPrecision(String moneyStr) {
		int fractionDigits = moneyStr.length() - moneyStr.indexOf(".") - 1;
		if (fractionDigits > 2) {
			throw new RuntimeException("���" + moneyStr + "��С��λ������λ��"); // ���Ȳ��ܱȷֵ�
		}
	}

}
