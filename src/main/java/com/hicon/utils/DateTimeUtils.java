package com.hicon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 时间日期工具类
 * 
 * @author mayi
 * 
 */
public class DateTimeUtils {
	/**
	 * 日期时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTimeString = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTimeString2 = "yyyy/MM/dd HH:mm:ss";
	
	/**
	 * 日期格式 yyyy-MM-dd
	 */
	public static String dateString = "yyyy-MM-dd";
	
	/**
	 * 日期格式 yyyyMMddHHmmss
	 */
	public static String dateTimeLongString = "yyyyMMddHHmmss";
	/**
	 * 日期格式 yyyyMMdd
	 */
	public static String dateLongString = "yyyyMMdd";
	
	/**
	 * 日期时间格式For 文件名 yyyy_MM_dd_HH_mm_ss
	 */
	public static String dateTimeString4FileName = "yyyy_MM_dd_HH_mm_ss_SSS";
	
	public final static String HH = "HH";
	public final static String YYYY = "yyyy";
	public final static String MM_DD = "MM-dd";
	public final static String YYYY_MM = "yyyy-MM";
	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public final static String YYYY_MM_DD_HH_mm = "yyyy-MM-dd HH:mm";
	public final static String YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public final static String YYYY_MM_DD_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
	
	public final static long ZERO_TIME = -62135798400000L;
	/**
	 * 日
	 */
	public final static int INTERVAL_DAY = 1;
	/**
	 * 周
	 */
	public final static int INTERVAL_WEEK = 2;
	/**
	 * 月
	 */
	public final static int INTERVAL_MONTH = 3;
	/**
	 * 年
	 */
	public final static int INTERVAL_YEAR = 4;
	/**
	 * 小时
	 */
	public final static int INTERVAL_HOUR = 5;
	/**
	 * 分钟
	 */
	public final static int INTERVAL_MINUTE = 6;
	/**
	 * 秒
	 */
	public final static int INTERVAL_SECOND = 7;
	
	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
	
	/**
	 * 解决日期字符串,默认日期格式为： yyyy-MM-dd HH:mm:ss，如果出现解析错误，自动尝试其他格式
	 * @param dateStr
	 * @return
	 */
	public static Date parseStr(String dateStr){
		return parseStr(dateStr, dateTimeString);
	}
	
	/**
	 * 解决日期字符串
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseStr(String dateStr , String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date resultDate = null;
		try {
			resultDate = df.parse(dateStr);
		} catch (ParseException e) {
			for(String key : DATE_REGEX_MAP.keySet()){
				String regex = DATE_REGEX_MAP.get(key);
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(dateStr);
				if(m.matches()){
					try {
						df = new SimpleDateFormat(key);
						resultDate = df.parse(dateStr);
					} catch (ParseException e1) {
						logger.error("日期解析错误,dateStr:"+dateStr);
					}
				}
			}
		}
		return resultDate;
	}
	
	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null || pattern == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		String result = df.format(date);
		if(result.equalsIgnoreCase("0001-01-01 00:00:00") || result.equalsIgnoreCase("0001-01-01")){
			result = "";
		}
		return result;
	}
	
	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern,TimeZone timeZone,Locale locale) {
		if (date == null || pattern == null) {
			return "";
		}
		SimpleDateFormat df = null;
		if(locale == null){
			df = new SimpleDateFormat(pattern);
		}else{
			df = new SimpleDateFormat(pattern,locale);
		}
		if(timeZone != null){
			df.setTimeZone(timeZone);
		}
		String result = df.format(date);
		if(result.equalsIgnoreCase("0001-01-01 00:00:00") || result.equalsIgnoreCase("0001-01-01")){
			result = "";
		}
		return result;
	}
	
	 /**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date){
		   return format(date, dateTimeString);
	  }
	
	public static String nowFormat() {
		return format(new Date(), dateTimeString);
	}
	
	/**
	 * 两date比较
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int compareDate(Date beforeDate, Date afterDate) {
		Calendar beforeCalendar = Calendar.getInstance();
		Calendar afterCalendar = Calendar.getInstance();
		beforeCalendar.setTime(beforeDate);
		afterCalendar.setTime(afterDate);
		return beforeCalendar.compareTo(afterCalendar);
	}
	
	/**
	 * 判断目标日期是否在时间段类
	 * @param beforeDate
	 * @param afterDate
	 * @param targetDate
	 * @return
	 */
	public static boolean isBetweenDate(Date beforeDate, Date afterDate,Date targetDate){
		if(targetDate == null){
			throw new RuntimeException("targetDate should not be null!");
		}
		if(beforeDate == null && afterDate == null){
			return false;
		}
		if(afterDate == null){
			return (compareDate(beforeDate,targetDate) <= 0);
		}
		if(beforeDate == null){
			return (compareDate(targetDate,afterDate)<= 0);
		}
		return (compareDate(beforeDate,targetDate) <= 0)&&(compareDate(targetDate,afterDate)<= 0);
	}
	
	/**
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateOperateByMonth(Date date,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date == null ? new Date() : date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date dateOperateByDay(Date date,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date == null ? new Date() : date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	/**
	 * 邮箱激活，时间操作，对验证时间+24H
	 * @param date 需要操作的时间，如果为null,就去当前时间
	 * @param hour 小时，对操作时间增加或减少的量
	 * @author chj
	 */
	public static Date dateOperateByHour(Date date,int hour){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date == null ? new Date() : date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}
	
	/**
	 * 验证手机验证码过期，时间操作，对验证时间+有效时间
	 * @param date 需要操作的时间，如果为null,就去当前时间
	 * @param minute 分钟，对操作时间增加或减少的量
	 * @author chj
	 */
	public static Date dateOperateByMinute(Date date,int minute){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date == null ? new Date() : date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	public static Date dateOperateBySecond(Date date,int second){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date == null ? new Date() : date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}
	
	public static Date getDateWithoutTime(Date date){
		Date result = null;
		String dateStr = format(date,dateString);
		result = parseStr(dateStr, dateString);
		return result;
	}
	
	public static Date getMonthBeginTime(Date date){
		Date result = null;
		String dateStr = format(date, YYYY_MM);
		result = parseStr(dateStr, YYYY_MM);
		return result;
	}
	
	/**
	 * 增加时间
	 * 
	 * @param interval
	 *            [INTERVAL_DAY,INTERVAL_WEEK,INTERVAL_MONTH,INTERVAL_YEAR,
	 *            INTERVAL_HOUR,INTERVAL_MINUTE]
	 * @param date
	 * @param n
	 *            可以为负数
	 * @return
	 */
	public static Date dateAdd(int interval, Date date, int n) {
		//long time = (date.getTime() / 1000); // 单位秒
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (interval) {
		case INTERVAL_DAY:
			calendar.add(Calendar.DAY_OF_MONTH, n);
			//time = time + n * 86400;// 60 * 60 * 24;
			break;
		case INTERVAL_WEEK:
			calendar.add(Calendar.WEEK_OF_MONTH, n);
			//time = time + n * 604800;// 60 * 60 * 24 * 7;
			break;
		case INTERVAL_MONTH:
			calendar.add(Calendar.MONTH, n);
			//time = time + n * 2678400;// 60 * 60 * 24 * 31;
			break;
		case INTERVAL_YEAR:
			calendar.add(Calendar.YEAR, n);
			//time = time + n * 31536000;// 60 * 60 * 24 * 365;
			break;
		case INTERVAL_HOUR:
			calendar.add(Calendar.HOUR_OF_DAY, n);
			//time = time + n * 3600;// 60 * 60 ;
			break;
		case INTERVAL_MINUTE:
			calendar.add(Calendar.MINUTE, n);
			//time = time + n * 60;
			break;
		case INTERVAL_SECOND:
			calendar.add(Calendar.SECOND, n);
			//time = time + n;
			break;
		default:
		}

		//Date result = new Date();
		//result.setTime(time * 1000);
		return calendar.getTime();
	}

	/**
	 * 计算两个时间间隔
	 * 
	 * @param interval
	 *            [INTERVAL_DAY,INTERVAL_WEEK,INTERVAL_MONTH,INTERVAL_YEAR,
	 *            INTERVAL_HOUR,INTERVAL_MINUTE]
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int dateDiff(int interval, Date begin, Date end) {
		long beginTime = (begin.getTime() / 1000); // 单位：秒
		long endTime = (end.getTime() / 1000); // 单位: 秒
		long tmp = 0;
		if (endTime == beginTime) {
			return 0;
		}

		// 确定endTime 大于 beginTime 结束时间秒数 大于 开始时间秒数
		if (endTime < beginTime) {
			tmp = beginTime;
			beginTime = endTime;
			endTime = tmp;
		}

		long intervalTime = endTime - beginTime;
		long result = 0;
		switch (interval) {
		case INTERVAL_DAY:
			result = intervalTime / 86400;// 60 * 60 * 24;
			break;
		case INTERVAL_WEEK:
			result = intervalTime / 604800;// 60 * 60 * 24 * 7;
			break;
		case INTERVAL_MONTH:
			result = intervalTime / 2678400;// 60 * 60 * 24 * 31;
			break;
		case INTERVAL_YEAR:
			result = intervalTime / 31536000;// 60 * 60 * 24 * 365;
			break;
		case INTERVAL_HOUR:
			result = intervalTime / 3600;// 60 * 60 ;
			break;
		case INTERVAL_MINUTE:
			result = intervalTime / 60;
			break;
		case INTERVAL_SECOND:
			result = intervalTime / 1;
			break;
		default:
		}

		// 做过交换
		if (tmp > 0) {
			result = 0 - result;
		}
		return (int) result;
	}
	/**
	 * 日期格式正则map
	 */
	public static final Map<String, String> DATE_REGEX_MAP = new HashMap<String, String>();
	static {
//		DATE_REGEX_MAP.put("yyyy-MM-dd", "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
		DATE_REGEX_MAP.put(dateString, "(\\d{4})-(\\d{2}|\\d{1})-(\\d{2}|\\d{1})");
		DATE_REGEX_MAP.put(dateLongString, "(\\d{4})(\\d{2})(\\d{2})");
		DATE_REGEX_MAP.put(dateTimeLongString, "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
		DATE_REGEX_MAP.put(dateTimeString, "(\\d{4})-(\\d{2}|\\d{1})-(\\d{2}|\\d{1}) (\\d{2}|\\d{1}):(\\d{2}|\\d{1}):(\\d{2}|\\d{1})");
	}
	
	public static void main(String[] args) {
		/*String dateString1 = "1234-5-06 11:22:33";
		Pattern p = Pattern.compile(DATE_REGEX_MAP.get("yyyy-MM-dd HH:mm:ss"));
	    Matcher m = p.matcher(dateString1);
	    boolean b = m.matches();
	    System.out.println(b);
		System.out.println(format(parseStr(dateString1)));*/
		
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(ZERO_TIME);
//		System.out.println(DateTimeUtils.format(calendar.getTime()));
//		System.out.println("初始当前日期：" + DateTimeUtils.format(date));
//		System.out.println("加一秒后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_SECOND, date, 55)));
//		System.out.println("加一分后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_MINUTE, date, 55)));
//		System.out.println("加一时后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_HOUR, date, 55)));
//		System.out.println("加一天后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_DAY, date, 55)));
//		System.out.println("加一周后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_WEEK, date, 55)));
//		System.out.println("加一月后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_MONTH, date, 55)));
//		System.out.println("加一年后日期：" + DateTimeUtils.format(DateTimeUtils.dateAdd(INTERVAL_YEAR, date, 55)));
//		System.out.println(DateTimeUtils.format(new Date(), "EEE, d-MMM-yyyy HH:mm:ss 'GMT'",TimeZone.getTimeZone("GMT"),Locale.UK));
	}
	
	
	/**
	 * 
	 * @param recordTime
	 * @param now
	 * @return
	 */
	public static String countTime(Date recordTime, Date now) {

		if (recordTime == null || now == null) {

			return null;
		}
		StringBuilder sBuilder = new StringBuilder();
		int seconds = dateDiff(INTERVAL_SECOND, recordTime, now);
		if (seconds <= 0) {
			sBuilder.append("刚刚");
		} else if (seconds < 60) {
			sBuilder.append(seconds).append("秒前");
		} else {
			int min = dateDiff(INTERVAL_MINUTE, recordTime, now);

			if (min < 60) {
				sBuilder.append(min).append("分钟前");
			} else if (min >= 60) {
				int hours = dateDiff(INTERVAL_HOUR, recordTime, now);

				if (hours < 24) {
					sBuilder.append(hours).append("小时前");
				} else if (hours >= 24) {
					int day = dateDiff(INTERVAL_DAY, recordTime, now);

					if (day < 31) {
						sBuilder.append(day).append("天前");
					} else {

						int month = dateDiff(INTERVAL_MONTH, recordTime, now);
						if (month < 12) {
							sBuilder.append(month).append("个月前");
						} else {
							int year = dateDiff(INTERVAL_YEAR, recordTime, now);
							sBuilder.append(year).append("年前");
						}
					}
				}

			}
		}

		return sBuilder.toString();
	}
	
	/**
	 * 
	* @Title: timeStampToDate
	* @Description: 
	* @param millis	时间戳 1456367998000
	* @return
	* @author ycg yuan.chenguang@mayi888.com
	 */
	public static Date timeStampToDate(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	/**
	 * 转换0000-00-00 00:00:00的date为null
	 * @param date
	 * @return
	 */
	public static Date transferZeroToNull(Date date) {
		if (date == null) {
			return null;
		}
		long zeroTime = date.getTime();
		if (zeroTime == ZERO_TIME) {
			return null;
		}
		return date;
	}
	
	//获得当天24点时间
	public static Date getDateByHour(int hour,int minute){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	/**
	 * 此方法得到给定日期，N天、月、年前或后的日期
	 * 
	 * @param startDate String 开始日期
	 * @param longNumber int 数值（输入正数，得到N天、月、年后的日期.输入负数，得到N天、月、年前的日期）
	 * @param unitDate String 数值单位（0代表年，1代表月，2代表日）
	 * @return String 返回得到日期
	 */

	public static String getDate2(String startDate, int longNumber, String unitDate) {
		Calendar beginDate = Calendar.getInstance();
		String sStartDate = formatDate2(startDate);
		// 在字符串中取得年
		int year = Integer.parseInt(sStartDate.substring(0, 4));
		// 在字符串中取得月
		int month = Integer.parseInt(sStartDate.substring(5, 7));
		// 在字符串中取得日
		int day = Integer.parseInt(sStartDate.substring(8, 10));
		int end;

		month -= 1;
		// 如果数值单位为0，年加传入数值
		if (unitDate.equals("0")) {
			year += longNumber;
		}
		// 如果数值单位为1，月加传入数值
		if (unitDate.equals("2")) {
			day += longNumber;
		}
		// 如果数值单位为2，日加传入数值
		if (unitDate.equals("1")) {
			month += longNumber;

			beginDate.set(year, month, 1);

			year = beginDate.get(Calendar.YEAR);
			month = beginDate.get(Calendar.MONTH);

			end = getMonthDayNumber(year, month + 1);
			day = day > end ? end : day;
		} else {

			beginDate.set(year, month, day);

			year = beginDate.get(Calendar.YEAR);
			month = beginDate.get(Calendar.MONTH);
			day = beginDate.get(Calendar.DATE);
		}
		month++;
		sStartDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		sStartDate = formatDate2(sStartDate);
		return sStartDate;
	}
	/**
	 * 天数为30天数的月份
	 */
	private static final int monthEnd30[] = { 4, 6, 9, 11 };
	/**
	 * "yyyy/MM/dd"的日期格式
	 */
	private static String dateFormatToDay2 = "yyyy/MM/dd";
	
	private static String dateFormatToDay3 = "yyyy/MM";
	/**
	 * 此方法将将日期转为标准格式"yyyy/MM/dd"
	 * 
	 * @param dt String 时间对象
	 * @return String 传入时间的"yyyy/MM/dd"格式化字符串
	 */
	public static String formatDate2(String dt) {
		String retDt = "";
		try {
			if (null == dt || dt.length() == 0) {
				return "";
			} else {
				String year = "";
				String month = "";
				String date = "";
				int idx = dt.indexOf("/", 5);
				if (idx == -1) {
					return "";
				}
				year = dt.substring(0, 4);
				month = dt.substring(5, idx);
				if (month.length() == 1) {
					month = "0" + month;
				}
				date = dt.substring(idx + 1);
				if (date.length() == 1) {
					date = "0" + date;
				}
				retDt = year + "/" + month + "/" + date;
			}
			return retDt;
		} catch (Exception e) {
			return "";
		}
	}
	/**
	 * 该方法获的某月的天数
	 * 
	 * @param year 年
	 * @param month 月
	 * @return int 某月的天数
	 */
	public static int getMonthDayNumber(int year, int month) {
		int result;
		// 如果月为2月时，判断是28天还是29天
		if (month == 2) {
			result = 28;
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					result = 29;
				}
			} else if (year % 4 == 0) {
				result = 29;
			}
		} else {
			result = 31;
			for (int i = 0; i < monthEnd30.length; i++) {
				if (month == monthEnd30[i]) {
					result = 30;
					break;
				}
			}
		}
		return result;
	}
	/**
	 * 该方法把传入的时间转化为"yyyy/MM/dd"格式的字符串
	 * 
	 * @param date Date 时间对象
	 * @return String 传入时间的"yyyy/MM/dd"格式化字符串
	 */
	public static String getTimeToDay2(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatToDay2);
		return dateFormat.format(date);
	}
	
	public static String getTimeTomMonth(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatToDay3);
		return dateFormat.format(date);
	}
}
