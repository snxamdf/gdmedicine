/* 
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * 日期工具类, 继承org.apache.commons.lang3.time.DateUtils类.
 * 
 * @author YHY
 * @version 2013-05-15
 */
public abstract class Dates extends DateUtils {

	/**
	 * Date时间转字符，格式默认为yyyy-MM-dd，也可以改为yyyy-MM-dd HH:mm:ss等.
	 * 例1：format(new Date(), null);
	 * 例2：format(new Date(), "yyyy-MM-dd HH:mm:ss");
	 * 
	 * @return 转化后的日期字符.
	 */
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (format == null || "".equals(format.trim())) {
			format = "yyyy-MM-dd";
		}
		return DateFormatUtils.format(date, format);
	}

	/**
	 * Date时间转字符，格式默认为yyyy-MM-dd，也可以改为yyyy-MM-dd HH:mm:ss等.
	 * 例1：format(new Date(), null, null);
	 * 例2：format(new Date(), "yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	 * 
	 * @return 转化后的日期字符.
	 */
	public static String format(Date date, String format, Locale locale) {
		if (date == null) {
			return "";
		}
		if (format == null || "".equals(format.trim())) {
			format = "yyyy-MM-dd";
		}
		if (locale == null) {
			return DateFormatUtils.format(date, format, Locale.CHINA);
		} else {
			return DateFormatUtils.format(date, format, locale);
		}
	}

	/**
	 * 日期型字符串转化为日期 格式（"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmss" ）.
	 * 例1：parse("2008-08-08");
	 * 例2：parse("2008-08-08 08:08:08");
	 * 
	 * @return 当前时间.
	 */
	public static Date parse(String date) {
		try {
			String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmss", "yyyyMM" };
			return parseDate(date, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断今天是否是周六.
	 * 
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isSaturday() {
		return new DateTime().dayOfWeek().get() == 6;
	}

	/**
	 * 判断日期是否是周六.
	 * 
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isSaturday(Date date) {
		return new DateTime(date.getTime()).dayOfWeek().get() == 6;
	}

	/**
	 * 判断今天是否是周日.
	 * 
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isSunday() {
		return new DateTime().dayOfWeek().get() == 7;
	}

	/**
	 * 判断日期是否是周日.
	 * 
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isSunday(Date date) {
		return new DateTime(date.getTime()).dayOfWeek().get() == 7;
	}

	/**
	 * 判断时间是否相等.
	 * 
	 * @param source 比较的时间.
	 * @param target 比较的时间.
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isEqual(Date source, Date target) {
		if (source.getTime() - target.getTime() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断source时间是否在target时间之后.
	 * 返回true时表达式：source >= target.
	 * 
	 * @param source 比较的时间.
	 * @param target 比较的时间.
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isAfter(Date source, Date target) {
		if (isEqual(source, target)) {
			return true;
		}
		return source.after(target);
	}

	/**
	 * 判断source时间是否在target时间之前.
	 * 返回true时表达式：source <= target.
	 * 
	 * @param source 比较的时间.
	 * @param target 比较的时间.
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isBefore(Date source, Date target) {
		if (isEqual(source, target)) {
			return true;
		}
		return source.before(target);
	}

	/**
	 * 判断某日期是否在日期范围内.
	 * 返回true时表达式：begin < date < end.
	 * 
	 * @param date 判断的日期.
	 * @param begin 开始时间.
	 * @param end 截止时间.
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isBetween(Date date, Date begin, Date end) {
		if (date.after(begin) && date.before(end)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断某日期是否在日期范围内.
	 * 返回true时表达式：begin <= date <= end.
	 * 
	 * @param date 判断的日期.
	 * @param begin 开始时间.
	 * @param end 截止时间.
	 * @return 是返回true，否返回false.
	 */
	public static Boolean isBetween2(Date date, Date begin, Date end) {
		if (isAfter(date, begin) && isBefore(date, end)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取今天的几年后的日期.
	 * 
	 * @return 几年后的日期.
	 */
	public static Date plusYears(int years) {
		return new Date(new DateTime(new Date().getTime()).plusYears(years).toDate().getTime());
	}

	/**
	 * 获取今天的几年后的日期.
	 * 
	 * @return 几年后的日期.
	 */
	public static String plusYears2(int years) {
		return new LocalDate(new Date().getTime()).plusYears(years).toString();
	}

	/**
	 * 获取某天的几年后的日期.
	 * 
	 * @return 几年后的日期.
	 */
	public static Date plusYears(Date date, int years) {
		return new Date(new DateTime(date.getTime()).plusYears(years).toDate().getTime());
	}

	/**
	 * 获取某天的几年后的日期.
	 * 
	 * @return 几年后的日期.
	 */
	public static String plusYears2(Date date, int years) {
		return new LocalDate(date.getTime()).plusYears(years).toString();
	}

	/**
	 * 获取今天的几月后的日期.
	 * 
	 * @return 几月后的日期.
	 */
	public static Date plusMonths(int months) {
		return new Date(new DateTime(new Date().getTime()).plusMonths(months).toDate().getTime());
	}

	/**
	 * 获取今天的几月后的日期.
	 * 
	 * @return 几月后的日期.
	 */
	public static String plusMonths2(int months) {
		return new LocalDate(new Date().getTime()).plusMonths(months).toString();
	}

	/**
	 * 获取某天的几月后的日期.
	 * 
	 * @return 几月后的日期.
	 */
	public static Date plusMonths(Date date, int months) {
		return new Date(new DateTime(date.getTime()).plusMonths(months).toDate().getTime());
	}

	/**
	 * 获取某天的几月后的日期.
	 * 
	 * @return 几月后的日期.
	 */
	public static String plusMonths2(Date date, int months) {
		return new LocalDate(date.getTime()).plusMonths(months).toString();
	}

	/**
	 * 获取某月的几月后的日期.
	 * 例如201512L mounths=1 返回 201601L
	 * 
	 * @param date Long类型
	 * @param months
	 * @return
	 * @author sdh
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-01-14
	 */
	public static Long plusMonths3(Long dateValue, int months) {
		String dateStr = String.valueOf(dateValue);
		Date date = Dates.parse(dateStr);
		dateStr = Dates.format(Dates.plusMonths(date, months), "yyyyMM");
		return Long.valueOf(dateStr);
	}

	/**
	 * 获取今天的几天后的日期.
	 * 
	 * @return 几天后的日期.
	 */
	public static Date plusDays(int days) {
		return new Date(new DateTime(new Date().getTime()).plusDays(days).toDate().getTime());
	}

	/**
	 * 获取今天的几天后的日期.
	 * 
	 * @return 几天后的日期.
	 */
	public static String plusDays2(int days) {
		return new LocalDate(new Date().getTime()).plusDays(days).toString();
	}

	/**
	 * 获取某天的几天后的日期.
	 * 
	 * @return 几天后的日期.
	 */
	public static Date plusDays(Date date, int days) {
		return new Date(new DateTime(date.getTime()).plusDays(days).toDate().getTime());
	}

	/**
	 * 获取某天的几天后的日期.
	 * 
	 * @return 几天后的日期.
	 */
	public static String plusDays2(Date date, int days) {
		return new LocalDate(date.getTime()).plusDays(days).toString();
	}

	/**
	 * 获取当前时间的几小时后的时间.
	 * 
	 * @return 几小时后的时间.
	 */
	public static Date plusHours(int hours) {
		return new Date(new DateTime(new Date().getTime()).plusHours(hours).toDate().getTime());
	}

	/**
	 * 获取当前时间的几小时后的时间.
	 * 
	 * @return 几小时后的时间.
	 */
	public static String plusHours2(int hours) {
		return format(plusHours(hours), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取某时间的几小时后的时间.
	 * 
	 * @return 几小时后的时间.
	 */
	public static Date plusHours(Date date, int hours) {
		return new Date(new DateTime(date.getTime()).plusHours(hours).toDate().getTime());
	}

	/**
	 * 获取某时间的几小时后的时间.
	 * 
	 * @return 几小时后的时间.
	 */
	public static String plusHours2(Date date, int hours) {
		return format(plusHours(date, hours), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当前时间的几分钟后的时间.
	 * 
	 * @return 几分钟后的时间.
	 */
	public static Date plusMinutes(int minutes) {
		return new Date(new DateTime(new Date().getTime()).plusMinutes(minutes).toDate().getTime());
	}

	/**
	 * 获取当前时间的几分钟后的时间.
	 * 
	 * @return 几分钟后的时间.
	 */
	public static String plusMinutes2(int minutes) {
		return format(plusMinutes(minutes), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取某时间的几分钟后的时间.
	 * 
	 * @return 几分钟后的时间.
	 */
	public static Date plusMinutes(Date date, int minutes) {
		return new Date(new DateTime(date.getTime()).plusMinutes(minutes).toDate().getTime());
	}

	/**
	 * 获取某时间的几分钟后的时间.
	 * 
	 * @return 几分钟后的时间.
	 */
	public static String plusMinutes2(Date date, int minutes) {
		return format(plusMinutes(date, minutes), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取今天的几年前的日期.
	 * 
	 * @return 几年前的日期.
	 */
	public static Date minusYears(int years) {
		return new Date(new DateTime(new Date().getTime()).minusYears(years).toDate().getTime());
	}

	/**
	 * 获取今天的几年前的日期.
	 * 
	 * @return 几年前的日期.
	 */
	public static String minusYears2(int years) {
		return new LocalDate(new Date().getTime()).minusYears(years).toString();
	}

	/**
	 * 获取某天的几年前的日期.
	 * 
	 * @return 几年前的日期.
	 */
	public static Date minusYears(Date date, int years) {
		return new Date(new DateTime(date.getTime()).minusYears(years).toDate().getTime());
	}

	/**
	 * 获取某天的几年前的日期.
	 * 
	 * @return 几年前的日期.
	 */
	public static String minusYears2(Date date, int years) {
		return new LocalDate(date.getTime()).minusYears(years).toString();
	}

	/**
	 * 获取今天的几月前的日期.
	 * 
	 * @return 几月前的日期.
	 */
	public static Date minusMonths(int months) {
		return new Date(new DateTime(new Date().getTime()).minusMonths(months).toDate().getTime());
	}

	/**
	 * 获取今天的几月前的日期.
	 * 
	 * @return 几月前的日期.
	 */
	public static String minusMonths2(int months) {
		return new LocalDate(new Date().getTime()).minusMonths(months).toString();
	}

	/**
	 * 获取某天的几月前的日期.
	 * 
	 * @return 几月前的日期.
	 */
	public static Date minusMonths(Date date, int months) {
		return new Date(new DateTime(date.getTime()).minusMonths(months).toDate().getTime());
	}

	/**
	 * 获取某天的几月前的日期.
	 * 
	 * @return 几月前的日期.
	 */
	public static String minusMonths2(Date date, int months) {
		return new LocalDate(date.getTime()).minusMonths(months).toString();
	}

	/**
	 * 获取今天的几天前的日期.
	 * 
	 * @return 几天前的日期.
	 */
	public static Date minusDays(int days) {
		return new Date(new DateTime(new Date().getTime()).minusDays(days).toDate().getTime());
	}

	/**
	 * 获取今天的几天前的日期.
	 * 
	 * @return 几天前的日期.
	 */
	public static String minusDays2(int days) {
		return new LocalDate(new Date().getTime()).minusDays(days).toString();
	}

	/**
	 * 获取某天的几天前的日期.
	 * 
	 * @return 几天前的日期.
	 */
	public static Date minusDays(Date date, int days) {
		return new Date(new DateTime(date.getTime()).minusDays(days).toDate().getTime());
	}

	/**
	 * 获取某天的几天前的日期.
	 * 
	 * @return 几天前的日期.
	 */
	public static String minusDays2(Date date, int days) {
		return new LocalDate(date.getTime()).minusDays(days).toString();
	}

	/**
	 * 获取当前时间的几小时前的时间.
	 * 
	 * @return 几小时前的时间.
	 */
	public static Date minusHours(int hours) {
		return new Date(new DateTime(new Date().getTime()).minusHours(hours).toDate().getTime());
	}

	/**
	 * 获取当前时间的几小时前的时间.
	 * 
	 * @return 几小时前的时间.
	 */
	public static String minusHours2(int hours) {
		return format(minusHours(hours), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取某时间的几小时前的时间.
	 * 
	 * @return 几小时前的时间.
	 */
	public static Date minusHours(Date date, int hours) {
		return new Date(new DateTime(date.getTime()).minusHours(hours).toDate().getTime());
	}

	/**
	 * 获取某时间的几小时前的时间.
	 * 
	 * @return 几小时前的时间.
	 */
	public static String minusHours2(Date date, int hours) {
		return format(minusHours(date, hours), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当前时间的几分钟前的时间.
	 * 
	 * @return 几分钟前的时间.
	 */
	public static Date minusMinutes(int minutes) {
		return new Date(new DateTime(new Date().getTime()).minusMinutes(minutes).toDate().getTime());
	}

	/**
	 * 获取当前时间的几分钟前的时间.
	 * 
	 * @return 几分钟前的时间.
	 */
	public static String minusMinutes2(int minutes) {
		return format(minusMinutes(minutes), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取某时间的几分钟前的时间.
	 * 
	 * @return 几分钟前的时间.
	 */
	public static Date minusMinutes(Date date, int minutes) {
		return new Date(new DateTime(date.getTime()).minusMinutes(minutes).toDate().getTime());
	}

	/**
	 * 获取某时间的几分钟前的时间.
	 * 
	 * @return 几分钟前的时间.
	 */
	public static String minusMinutes2(Date date, int minutes) {
		return format(minusMinutes(date, minutes), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取某时间段的天数.
	 * 
	 * @param begin 开始时间.
	 * @param end 截止时间.
	 * @return 间隔天数.
	 */
	public static long daysBetween(Date begin, Date end) {
		return Days.daysBetween(new DateTime(begin.getTime()), new DateTime(end.getTime())).getDays();
	}

	/**
	 * 获取某时间段的秒数.
	 * 
	 * @param begin
	 * @param end
	 * @return
	 * @author heli
	 * @version 2013-7-23
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by heli
	 * @updated at 2013-7-23
	 */
	public static long secondsBetween(Date begin, Date end) {
		long diff = end.getTime() - begin.getTime();
		long seconds = diff / 1000;
		return seconds;
	}

	/**
	 * 传入日期剩余的秒数.
	 * 
	 * @param begin
	 * @return
	 * @author sdh
	 * @version 2014-11-6
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2014-11-6
	 */
	public static Long secondsBetween2(Date begin) {
		String endStr = plusDays2(begin, 1);
		return secondsBetween(begin, parse(endStr));
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date begin, Date end) {
		long t = end.getTime() - begin.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取当前时间.
	 * 
	 * @return 当前时间.
	 */
	public static Date getDate() {
		Date date = new Date();
		return date;
	}

	/**
	 * 获取当前时间.
	 * 
	 * @return 当前时间.
	 */
	public static Date getDate(String format) {
		Date date = Dates.parse(Dates.format(new Date(), format));
		return date;
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate2() {
		return getDate2("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"等
	 */
	public static String getDate2(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return format(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取今天(时分秒为0，如：2011-11-08 00:00:00.0).
	 * 
	 * @return 今天.
	 */
	public static Date getToday() {
		return new Date(new LocalDate().toDate().getTime());
	}

	/**
	 * 获取今天(时分秒为0，如：2011-11-08).
	 * 
	 * @return 今天.
	 */
	public static String getToday2() {
		return new LocalDate().toString();
	}

	/**
	 * 获取昨天(时分秒为0，如：2011-11-08 00:00:00.0).
	 * 
	 * @return 昨天.
	 */
	public static Date getYesterday() {
		return new Date(new LocalDate().minusDays(1).toDate().getTime());
	}

	/**
	 * 获取昨天(时分秒为0，如：2011-11-08).
	 * 
	 * @return 昨天.
	 */
	public static String getYesterday2() {
		return new LocalDate().minusDays(1).toString();
	}

	/**
	 * 获取前天(时分秒为0，如：2011-11-08 00:00:00.0).
	 * 
	 * @return 前天.
	 */
	public static Date getAnteayer() {
		return new Date(new LocalDate().minusDays(2).toDate().getTime());
	}

	/**
	 * 获取前天(时分秒为0，如：2011-11-08).
	 * 
	 * @return 前天.
	 */
	public static String getAnteayer2() {
		return new LocalDate().minusDays(2).toString();
	}

	/**
	 * 获取明天(时分秒为0，如：2011-11-08 00:00:00.0).
	 * 
	 * @return 明天.
	 */
	public static Date getTomorrow() {
		return new Date(new LocalDate().plusDays(1).toDate().getTime());
	}

	/**
	 * 获取明天(时分秒为0，如：2011-11-08).
	 * 
	 * @return 明天.
	 */
	public static String getTomorrow2() {
		return new LocalDate().plusDays(1).toString();
	}

	/**
	 * 获取当前日期的年份.
	 * 
	 * @return 当前日期的年份.
	 */
	public static int getYear() {
		return new LocalDate().getYear();
	}

	/**
	 * 获取当前日期的年份(字符型).
	 * 
	 * @return 当前日期的年份(字符型).
	 */
	public static String getYear2() {
		return new LocalDate().year().getAsString();
	}

	/**
	 * 获取当前日期的月份.
	 * 
	 * @return 当前日期的月份.
	 */
	public static int getMonth() {
		return new LocalDate().getMonthOfYear();
	}

	/**
	 * 获取当前日期的月份(字符型).
	 * 
	 * @return 当前日期的月份(字符型).
	 */
	public static String getMonth2() {
		return new LocalDate().toString("MM");
	}

	/**
	 * 获取当前日期的月份(英文:January、February、March等).
	 * 
	 * @return 当前日期的月份(英文).
	 */
	public static String getMonthEn() {
		return format(new Date(), "MMMMM", Locale.US);
	}

	/**
	 * 获取指定日期的月份(英文:January、February、March等).
	 * 
	 * @return 指定日期的月份(英文).
	 */
	public static String getMonthEn(Date date) {
		return format(date, "MMMMM", Locale.US);
	}

	/**
	 * 获取当前日期对应的星期几
	 */
	public static String getWeek() {
		return format(new Date(), "E");
	}

	/**
	 * 获取某日期对应的星期几
	 */
	public static String getWeek(Date date) {
		return format(date, "E");
	}

	/**
	 * 某天所在周的周一.
	 * 
	 * @return 周一.
	 */
	public static Date getMondayOfWeek(Date date) {
		return new Date(new LocalDate(date.getTime()).dayOfWeek().withMinimumValue().toDate().getTime());
	}

	/**
	 * 某天所在周的周一.
	 * 
	 * @return 周一.
	 */
	public static String getMondayOfWeek2(Date date) {
		return new LocalDate(date.getTime()).dayOfWeek().withMinimumValue().toString();
	}

	/**
	 * 本周一.
	 * 
	 * @return 本周一.
	 */
	public static Date getMondayOfWeek() {
		return new Date(new LocalDate().dayOfWeek().withMinimumValue().toDate().getTime());
	}

	/**
	 * 本周一.
	 * 
	 * @return 本周一.
	 */
	public static String getMondayOfWeek2() {
		return new LocalDate().dayOfWeek().withMinimumValue().toString();
	}

	/**
	 * 上周一.
	 * 
	 * @return 上周一.
	 */
	public static Date getMondayOfPreviousWeek() {
		return new Date(new LocalDate().minusWeeks(1).dayOfWeek().withMinimumValue().toDate().getTime());
	}

	/**
	 * 上周一.
	 * 
	 * @return 上周一.
	 */
	public static String getMondayOfPreviousWeek2() {
		return new LocalDate().minusWeeks(1).dayOfWeek().withMinimumValue().toString();
	}

	/**
	 * 下周一.
	 * 
	 * @return 下周一.
	 */
	public static Date getMondayOfNextWeek() {
		return new Date(new LocalDate().plusWeeks(1).dayOfWeek().withMinimumValue().toDate().getTime());
	}

	/**
	 * 下周一.
	 * 
	 * @return 下周一.
	 */
	public static String getMondayOfNextWeek2() {
		return new LocalDate().plusWeeks(1).dayOfWeek().withMinimumValue().toString();
	}

	/**
	 * 本周五.
	 * 
	 * @return 本周五.
	 */
	public static Date getFridayOfWeek() {
		return new Date(new LocalDate().dayOfWeek().withMaximumValue().minusDays(2).toDate().getTime());
	}

	/**
	 * 本周五.
	 * 
	 * @return 本周五.
	 */
	public static String getFridayOfWeek2() {
		return new LocalDate().dayOfWeek().withMaximumValue().minusDays(2).toString();
	}

	/**
	 * 上周五.
	 * 
	 * @return 上周五.
	 */
	public static Date getFridayOfPreviousWeek() {
		return new Date(new LocalDate().minusWeeks(1).dayOfWeek().withMaximumValue().minusDays(2).toDate().getTime());
	}

	/**
	 * 上周五.
	 * 
	 * @return 上周五.
	 */
	public static String getFridayOfPreviousWeek2() {
		return new LocalDate().minusWeeks(1).dayOfWeek().withMaximumValue().minusDays(2).toString();
	}

	/**
	 * 下周五.
	 * 
	 * @return 下周五.
	 */
	public static Date getFridayOfNextWeek() {
		return new Date(new LocalDate().plusWeeks(1).dayOfWeek().withMaximumValue().minusDays(2).toDate().getTime());
	}

	/**
	 * 下周五.
	 * 
	 * @return 下周五.
	 */
	public static String getFridayOfNextWeek2() {
		return new LocalDate().plusWeeks(1).dayOfWeek().withMaximumValue().minusDays(2).toString();
	}

	/**
	 * 本周日.
	 * 
	 * @return 本周日.
	 */
	public static Date getSundayOfWeek() {
		return new Date(new LocalDate().dayOfWeek().withMaximumValue().toDate().getTime());
	}

	/**
	 * 本周日.
	 * 
	 * @return 本周日.
	 */
	public static String getSundayOfWeek2() {
		return new LocalDate().dayOfWeek().withMaximumValue().toString();
	}

	/**
	 * 上周日.
	 * 
	 * @return 上周日.
	 */
	public static Date getSundayOfPreviousWeek() {
		return new Date(new LocalDate().minusWeeks(1).dayOfWeek().withMaximumValue().toDate().getTime());
	}

	/**
	 * 上周日.
	 * 
	 * @return 上周日.
	 */
	public static String getSundayOfPreviousWeek2() {
		return new LocalDate().minusWeeks(1).dayOfWeek().withMaximumValue().toString();
	}

	/**
	 * 下周日.
	 * 
	 * @return 下周日.
	 */
	public static Date getSundayOfNextWeek() {
		return new Date(new LocalDate().plusWeeks(1).dayOfWeek().withMaximumValue().toDate().getTime());
	}

	/**
	 * 下周日.
	 * 
	 * @return 下周日.
	 */
	public static String getSundayOfNextWeek2() {
		return new LocalDate().plusWeeks(1).dayOfWeek().withMaximumValue().toString();
	}

	/**
	 * 本月第一天.
	 * 
	 * @return 本月第一天.
	 */
	public static Date getFirstDayOfMonth() {
		return new Date(new LocalDate().dayOfMonth().withMinimumValue().toDate().getTime());
	}

	/**
	 * 本月第一天.
	 * 
	 * @return 本月第一天.
	 */
	public static String getFirstDayOfMonth2() {
		return new LocalDate().dayOfMonth().withMinimumValue().toString();
	}

	/**
	 * 上月第一天.
	 * 
	 * @return 上月第一天.
	 */
	public static Date getFirstDayOfPreviousMonth() {
		return new Date(new LocalDate().minusMonths(1).dayOfMonth().withMinimumValue().toDate().getTime());
	}

	/**
	 * 上月第一天.
	 * 
	 * @return 上月第一天.
	 */
	public static String getFirstDayOfPreviousMonth2() {
		return new LocalDate().minusMonths(1).dayOfMonth().withMinimumValue().toString();
	}

	/**
	 * 下月第一天.
	 * 
	 * @return 下月第一天.
	 */
	public static Date getFirstDayOfNextMonth() {
		return new Date(new LocalDate().plusMonths(1).dayOfMonth().withMinimumValue().toDate().getTime());
	}

	/**
	 * 下月第一天.
	 * 
	 * @return 下月第一天.
	 */
	public static String getFirstDayOfNextMonth2() {
		return new LocalDate().plusMonths(1).dayOfMonth().withMinimumValue().toString();
	}

	/**
	 * 本月第几天.
	 * 
	 * @return 本月第几天.
	 */
	public static String getDayOfMonth2(int days) {
		return new LocalDate().dayOfMonth().withMinimumValue().plusDays(days - 1).toString();
	}

	/**
	 * 上月第几天.
	 * 
	 * @return 上月第几天.
	 */
	public static Date getDayOfPreviousMonth(int days) {
		return new Date(new LocalDate().minusMonths(1).dayOfMonth().withMinimumValue().plusDays(days - 1).toDate().getTime());
	}

	/**
	 * 上月第几天.
	 * 
	 * @return 上月第几天.
	 */
	public static String getDayOfPreviousMonth2(int days) {
		return new LocalDate().minusMonths(1).dayOfMonth().withMinimumValue().plusDays(days - 1).toString();
	}

	/**
	 * 下月第几天.
	 * 
	 * @return 下月第几天.
	 */
	public static Date getDayOfNextMonth(int days) {
		return new Date(new LocalDate().plusMonths(1).dayOfMonth().withMinimumValue().plusDays(days - 1).toDate().getTime());
	}

	/**
	 * 下月第几天.
	 * 
	 * @return 下月第几天.
	 */
	public static String getDayOfNextMonth2(int days) {
		return new LocalDate().plusMonths(1).dayOfMonth().withMinimumValue().plusDays(days - 1).toString();
	}

	/**
	 * 某天所在月的最后一天.
	 * 
	 * @return 某月最后一天.
	 */
	public static Date getLastDayOfMonth(Date date) {
		return new Date(new LocalDate(date.getTime()).dayOfMonth().withMaximumValue().toDate().getTime());
	}

	/**
	 * 某月的最后一天.
	 * 
	 * @param dateValue 201501L
	 * @return 某月最后一天
	 * @author sdh
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-01-14
	 */
	public static Date getLastDayOfMonth(Long dateValue) {
		String dateStr = String.valueOf(dateValue);
		Date date = Dates.parse(dateStr);
		date = getLastDayOfMonth(date);
		return Dates.parse(Dates.format(date, "yyyy-MM-dd") + " 23:23:59");
	}

	/**
	 * 本月最后一天.
	 * 
	 * @return 本月最后一天.
	 */
	public static Date getLastDayOfMonth() {
		return new Date(new LocalDate().dayOfMonth().withMaximumValue().toDate().getTime());
	}

	/**
	 * 本月最后一天.
	 * 
	 * @return 本月最后一天.
	 */
	public static String getLastDayOfMonth2() {
		return new LocalDate().dayOfMonth().withMaximumValue().toString();
	}

	/**
	 * 上月最后一天.
	 * 
	 * @return 上月最后一天.
	 */
	public static Date getLastDayOfPreviousMonth() {
		return new Date(new LocalDate().minusMonths(1).dayOfMonth().withMaximumValue().toDate().getTime());
	}

	/**
	 * 上月最后一天.
	 * 
	 * @return 上月最后一天.
	 */
	public static String getLastDayOfPreviousMonth2() {
		return new LocalDate().minusMonths(1).dayOfMonth().withMaximumValue().toString();
	}

	/**
	 * 下月最后一天.
	 * 
	 * @return 下月最后一天.
	 */
	public static Date getLastDayOfNextMonth() {
		return new Date(new LocalDate().plusMonths(1).dayOfMonth().withMaximumValue().toDate().getTime());
	}

	/**
	 * 下月最后一天.
	 * 
	 * @return 下月最后一天.
	 */
	public static String getLastDayOfNextMonth2() {
		return new LocalDate().plusMonths(1).dayOfMonth().withMaximumValue().toString();
	}

	/**
	 * 某天所在月的第一天.
	 * 
	 * @return 某月第一天.
	 */
	public static Date getFirstDayOfMonth(Date date) {
		return new Date(new LocalDate(date.getTime()).dayOfMonth().withMinimumValue().toDate().getTime());
	}

	/**
	 * 某月的第一天.
	 * 
	 * @param dateValue 201501L
	 * @return 某月第一天
	 * @author sdh
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-01-14
	 */
	public static Date getFirstDayOfMonth(Long dateValue) {
		String dateStr = String.valueOf(dateValue);
		Date date = Dates.parse(dateStr);
		return getFirstDayOfMonth(date);
	}

	/**
	 * 本年第一天.
	 * 
	 * @return 本年第一天.
	 */
	public static Date getFirstDayOfYear() {
		return new Date(new LocalDate().dayOfYear().withMinimumValue().toDate().getTime());
	}

	/**
	 * 本年第一天.
	 * 
	 * @return 本年第一天.
	 */
	public static String getFirstDayOfThisYear() {
		return new LocalDate().dayOfYear().withMinimumValue().toString();
	}

	/**
	 * 上年第一天.
	 * 
	 * @return 上年第一天.
	 */
	public static Date getFirstDayOfPreviousYear() {
		return new Date(new LocalDate().minusYears(1).dayOfYear().withMinimumValue().toDate().getTime());
	}

	/**
	 * 上年第一天.
	 * 
	 * @return 上年第一天.
	 */
	public static String getFirstDayOfPreviousYear2() {
		return new LocalDate().minusYears(1).dayOfYear().withMinimumValue().toString();
	}

	/**
	 * 下年第一天.
	 * 
	 * @return 下年第一天.
	 */
	public static Date getFirstDayOfNextYear() {
		return new Date(new LocalDate().plusYears(1).dayOfYear().withMinimumValue().toDate().getTime());
	}

	/**
	 * 下年第一天.
	 * 
	 * @return 下年第一天.
	 */
	public static String getFirstDayOfNextYear2() {
		return new LocalDate().plusYears(1).dayOfYear().withMinimumValue().toString();
	}

	/**
	 * 本年最后一天.
	 * 
	 * @return 本年最后一天.
	 */
	public static Date getLastDayOfYear() {
		return new Date(new LocalDate().dayOfYear().withMaximumValue().toDate().getTime());
	}

	/**
	 * 本年最后一天.
	 * 
	 * @return 本年最后一天.
	 */
	public static String getLastDayOfYear2() {
		return new LocalDate().dayOfYear().withMaximumValue().toString();
	}

	/**
	 * 上年最后一天.
	 * 
	 * @return 上年最后一天.
	 */
	public static Date getLastDayOfPreviousYear() {
		return new Date(new LocalDate().minusYears(1).dayOfYear().withMaximumValue().toDate().getTime());
	}

	/**
	 * 上年最后一天.
	 * 
	 * @return 上年最后一天.
	 */
	public static String getLastDayOfPreviousYear2() {
		return new LocalDate().minusYears(1).dayOfYear().withMaximumValue().toString();
	}

	/**
	 * 下年最后一天.
	 * 
	 * @return 下年最后一天.
	 */
	public static Date getLastDayOfNextYear() {
		return new Date(new LocalDate().plusYears(1).dayOfYear().withMaximumValue().toDate().getTime());
	}

	/**
	 * 下年最后一天.
	 * 
	 * @return 下年最后一天.
	 */
	public static String getLastDayOfNextYear2() {
		return new LocalDate().plusYears(1).dayOfYear().withMaximumValue().toString();
	}

	/**
	 * 将Date类转换为XMLGregorianCalendar
	 * 
	 * @return
	 */
	public static XMLGregorianCalendar dateToXmlGDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		XMLGregorianCalendar dateType = null;

		try {
			dtf = DatatypeFactory.newInstance();
			dateType = dtf.newXMLGregorianCalendar();
			dateType.setYear(cal.get(Calendar.YEAR));
			//由于Calendar.MONTH取值范围为0~11,需要加1
			dateType.setMonth(cal.get(Calendar.MONTH) + 1);
			dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
			dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
			dateType.setMinute(cal.get(Calendar.MINUTE));
			dateType.setSecond(cal.get(Calendar.SECOND));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return dateType;
	}

	/**
	 * 将XMLGregorianCalendar转换为Date
	 * 
	 * @param xcal
	 * @return
	 */
	public static Date xmlGDate2Date(XMLGregorianCalendar xcal) {
		return xcal == null ? null : xcal.toGregorianCalendar().getTime();
	}

	/**
	 * 取得当前时间几点
	 */
	public static int getHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 判断传入时间是不是整点,例如08:00:00-08:00:59
	 */
	public static boolean isWholeHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE) == 0;
	}

}
