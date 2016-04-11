package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * ʱ��ƫ����
	 */
	private static final int TIME_ZONE_OFFSET = Calendar.getInstance().getTimeZone().getRawOffset();

	/**
	 * ʱ���ʽ����������ʱ����
	 */
	public static final String SDF_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ʱ���ʽȫ��
	 */
	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.sss";

	/**
	 * <p>
	 * ����ʱ�䴮
	 * </p>
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public static Date parse(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		String format = DATE_FORMAT_FULL.substring(0, dateStr.length());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * ����ʱ�䴮
	 * </p>
	 * 
	 * @param dateStr
	 * @param format
	 * @return Date
	 */
	public static Date parse(String dateStr, String format) {
		if (dateStr == null) {
			return null;
		}
		if (format == null) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			} catch (ParseException e) {
			}
		}
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * ��ʽ������
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date toDate(Date date) {
		if (date == null) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * ��ʽ��ʱ��Ϊ�ַ���
	 * </p>
	 * 
	 * @param date
	 * @return String
	 */
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		String dateStr = new SimpleDateFormat(SDF_YMDHMS).format(date);
		return dateStr.replaceAll("((\\s|:)00)+$", "");
	}

	/**
	 * <p>
	 * ��ʽ��ʱ��Ϊ�ַ���
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (format == null) {
			return format(date);
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * <p>
	 * �����Ƚ�
	 * </p>
	 * 
	 * @param date1
	 * @param date2
	 * @return int ���ش���
	 *         <ul>
	 *         <li>1 : date1��date2֮ǰ</li>
	 *         <li>-1 :��date1��date2֮��</li>
	 *         <li>0 : date1��date2ͬһ��</li>
	 *         <li>-2 : date1��date2��һ��Ϊnull</li>
	 *         </ul>
	 */
	public static int compareForDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return -2;
		}
		int offset = getDayOffset(date1, date2);
		if (offset > 0) {
			return 1;
		}
		if (offset < 0) {
			return -1;
		}
		return 0;
	}

	/**
	 * <p>
	 * ��ȡ�������
	 * </p>
	 * 
	 * @param begin
	 *            ��ʼ����
	 * @param end
	 *            ��ֹ����
	 * @return int ����
	 */
	public static int getDayOffset(Date begin, Date end) {
		if (begin == null || end == null) {
			return 0;
		}
		long day1 = (begin.getTime() + TIME_ZONE_OFFSET) / 86400000;
		long day2 = (end.getTime() + TIME_ZONE_OFFSET) / 86400000;
		return (int) (day2 - day1);
	}

	/**
	 * <p>
	 * ��ȡ��������
	 * </p>
	 * 
	 * @return date
	 */
	public static Date getCurrentDay() {
		try {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			long tm = System.currentTimeMillis();
			tm = tm - (tm + TIME_ZONE_OFFSET) % 86400000;
			return new Date(tm);
		}
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return Date
	 */
	public static Date getFirstDateOfYear() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(Calendar.getInstance().get(Calendar.YEAR) + "-01-01");
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return Date
	 */
	public static Date getFirstDateOfYear(Date date) {
		try {
			Calendar cal = Calendar.getInstance();
			if (date != null) {
				cal.setTime(date);
			}
			return new SimpleDateFormat("yyyy-MM-dd").parse(Calendar.getInstance().get(Calendar.YEAR) + "-01-01");
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡָ��������һ���µĵ�һ��
	 * </p>
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getFirstDayOfNextMonth(Date date) {
		if (date == null) {
			return null;
		}
		String monthStr = new SimpleDateFormat("yyyy-MM").format(date);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(monthStr + "-01"));
			cal.add(Calendar.MONTH, 1);
			return cal.getTime();
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡָ�����ڵĵ�һ��
	 * </p>
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		String monthStr = new SimpleDateFormat("yyyy-MM").format(date);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(monthStr + "-01"));
			return cal.getTime();
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡָ�����ڵ����һ��
	 * </p>
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getLastDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		String monthStr = new SimpleDateFormat("yyyy-MM").format(date);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(monthStr + "-01"));
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			return cal.getTime();
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * <p>
	 * ��ȡָ�����ڵ��¸��µ�һ��
	 * </p>
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getLastDayOfNextMonth(Date date) {
		if (date == null) {
			return null;
		}
		String monthStr = new SimpleDateFormat("yyyy-MM").format(date);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(monthStr + "-01"));
			cal.add(Calendar.MONTH, 1);
			return cal.getTime();
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * <p>
	 * �޸�ʱ��
	 * </p>
	 * 
	 * @param date
	 *            ��Ҫ�޸ĵ�ʱ��
	 * @param type
	 *            ʱ������
	 * @param offset
	 *            ƫ����
	 * @return Date
	 */
	public static Date add(Date date, int type, int offset) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(type, offset);
		return cal.getTime();
	}

	/**
	 * <p>
	 * �޸�ʱ��
	 * </p>
	 * 
	 * @param time
	 *            ʱ���
	 * @param type
	 *            ʱ�䳣������
	 * @param offset
	 *            ƫ����
	 * @return Date
	 */
	public static Date add(long time, int type, int offset) {
		Date date = new Date(time);
		return add(date, type, offset);
	}

	/**
	 * <p>
	 * ��ȡ���Сʱ��
	 * </p>
	 * 
	 * @param begin
	 *            ��ʼ����
	 * @param end
	 *            ��ֹ����
	 * @return int Сʱ
	 */
	public static int getHourOffset(Date begin, Date end) {
		if (begin == null || end == null) {
			return 0;
		}
		long hour1 = (begin.getTime() + TIME_ZONE_OFFSET) / 3600000;
		long hour2 = (end.getTime() + TIME_ZONE_OFFSET) / 3600000;
		return (int) (hour2 - hour1);
	}

	/**
	 * <p>
	 * ��ȡ��������
	 * </p>
	 * 
	 * @param begin
	 *            ��ʼ����
	 * @param end
	 *            ��ֹ����
	 * @return int ����
	 */
	public static int getMinuteOffset(Date begin, Date end) {
		if (begin == null || end == null) {
			return 0;
		}
		long minute1 = (begin.getTime() + TIME_ZONE_OFFSET) / 60000;
		long minute2 = (end.getTime() + TIME_ZONE_OFFSET) / 60000;
		return (int) (minute2 - minute1);
	}

	/**
	 * <p>
	 * ��ȡʱ����ʾ
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @date 2015��5��2��
	 */
	public static String getDateText(Date date, String format) {
		if (date == null)
			return "";
		String hm = new SimpleDateFormat(" HH:mm").format(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		if (sdf.format(date).equals(sdf.format(cal.getTime()))) {
			return "����" + hm;
		}
		cal.add(Calendar.DAY_OF_MONTH, -1);
		if (sdf.format(date).equals(sdf.format(cal.getTime()))) {
			return "����" + hm;
		}
		cal.add(Calendar.DAY_OF_MONTH, -1);
		if (sdf.format(date).equals(sdf.format(cal.getTime()))) {
			return "ǰ��" + hm;
		}
		if (format == null)
			format = "MM-dd";
		return new SimpleDateFormat(format).format(date) + hm;
	}

}
