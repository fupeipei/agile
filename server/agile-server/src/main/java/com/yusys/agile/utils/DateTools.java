package com.yusys.agile.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description 时间处理类
 *    zt
 */
public class DateTools {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateTools.class);

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String DATETIME_BEGIN = " 00:00:00";

	public static final String DATETIME_END = " 23:59:59";

	private static final long HOUR_OF_MILLIS = 1000 * 60 * 60;

	/**
	 * 获取一个月之前的日期
	 * @return
	 */
	public static Date getLastMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}

	/**
	 * 根据月份获取时间
	 * @param month
	 * @return
	 */
	public static Date getDateAddMonth(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * 根据字符串，转换成日期，字符串格式："yyyy-MM-dd HH:mm:ss"
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateStrictly(String time) {

		if (time == null || time.trim().length() == 0) {
			return null;
		}

		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = null;
		SimpleDateFormat parser = new SimpleDateFormat();

		// 设置为严格按照日期格式,解析字符串
		parser.setLenient(false);

		ParsePosition pos = new ParsePosition(0);
		parser.applyPattern(pattern);
		pos.setIndex(0);

		try {
			date = parser.parse(time, pos);
		} catch (Exception e) {
            LOGGER.debug("parseDateStrictly error", e);
			return null;
		}
		return date;
	}

	/**
	 * @param time
	 * @return
	 */
	public static Date parseDateByFormat(String time) {

		if (time == null || time.trim().length() == 0) {
			return null;
		}

		String timeStr = time;
		if (time.trim().length() == 10) {
			timeStr = timeStr + " 00:00:00";
		}
		Date date = null;
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			date = parser.parse(timeStr);
		} catch (Exception e) {
		    LOGGER.debug("parseDateByFormat error", e);
		}
		return date;
	}

	/**
	 * 获取两个日期之间的天数
	 * @param start
	 * @param end
	 * @return
	 */
	public static Integer getDayCountBetweenTowDate(String start, String end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer dayCount = 0;
		// 得到毫秒数
		try {
			long timeStart = sdf.parse(start).getTime();
			long timeEnd = sdf.parse(end).getTime();
			// 两个日期相减得到天数
			dayCount = (int) ((timeEnd - timeStart) / (24 * 3600 * 1000));
		} catch (ParseException e) {
		    LOGGER.debug("getDayCountBetweenTowDate error", e);
		}
		return dayCount;
	}

	public static Integer daysBetween(String smdate, String bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		long between_days = 0;
		try {
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {
		    LOGGER.debug("daysBetween error", e);
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 根据天数获取之前之后的日期
	 * @return
	 */
	public static String getDateByDays(int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = SIMPLE_FORMAT.format(c.getTime());
		return strDate;
	}

	/**
	 * 根据天数获取之前之后的天
	 * @return
	 */
	public static String getDateByDay(int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpleDateFormat.format(c.getTime());
		return strDate;
	}

	/**
	 * 传入的时间，在该时间的基础上计算 几天之前之后的日期
	 * @param dateStr
	 * @param days
	 * @return
	 */
	public static String getDateByDays(String dateStr, int days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.setTime(simpleDateFormat.parse(dateStr));// 把传入的时间转换成Calendar
		} catch (ParseException e) {
		    LOGGER.debug("getDateByDays error", e);
		}
		c.add(Calendar.DATE, days);
		String strDate = simpleDateFormat.format(c.getTime());
		return strDate;
	}

	/**
	 * 根据年月日信息，返回日期对象
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal.getTime();
	}


	/**
	 * 时间转化为时间戳
	 * @param date
	 * @return
	 */
	public static Long getExpireDateUnixTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * 根据格式模板格式化日期
	 * @param date
	 * @param simpleFormat
	 * @return
	 */
	public static String covertDateToString(Date date, SimpleDateFormat simpleFormat) {
		if (date == null) {
			return null;
		}
		return simpleFormat.format(date);
	}

	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return SIMPLE_FORMAT.format(System.currentTimeMillis());
	}

	public static String getCurrentDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 根据传过来的天数获取当前天数之后的日期
	 * @param DateNum 天数
	 * @return
	 */
	public static List<String> queryDateStrByDateNum(Integer DateNum) {
		List<String> dates = new ArrayList<String>();
		for (int i = 1; i <= DateNum; i++) {
			String date = getDateByDay(i);

			dates.add(date);
		}
		return dates;
	}

	/**
	 * 解析字符串到日期
	 * @param dateStr 日期格式的字符串
	 * @return 解析后的日期
	 */
	public static Date parseToDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		} catch (ParseException e) {
		    LOGGER.debug("parseToDate error", e);
			return new Date();
		}
	}

	/**
	 * 解析字符串到日期
	 * @param dateStr 日期格式的字符串
	 * @return 解析后的日期
	 */
	public static Date parseToDate(String dateStr, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
		    LOGGER.debug("parseToDate error", e);
			return new Date();
		}
	}

	/**
	 * 日期转字符串
	 * @param date 日期
	 * @return
	 */
	public static String parseToString(Date date) {
		String dateStr = null;
		if (null != date) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
				dateStr = sdf.format(date);
			} catch (Exception e) {
				LOGGER.debug("DateTools.parseToString occur error", e);
			}
		}
		return dateStr;
	}

	/**
	 * 日期转字符串
	 * @param date 日期
	 * @return
	 */
	public static String parseToString2(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 日期转字符串
	 * @param date 日期
	 * @return
	 */
	public static String parseToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将String型格式化,比如想要将2015-01-01 10:10:10 格式化为2015-01-01).
	 * @param date
	 *            String 想要格式化的日期
	 * @param oldPattern
	 *            String 想要格式化的日期的现有格式
	 * @param newPattern
	 *            String 想要格式化成什么格式
	 * @return String
	 */
	public static final String stringPattern(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象
		Date d = null;
		try {
			d = sdf1.parse(date); // 将给定的字符串中的日期提取出来
		} catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
		    LOGGER.debug("stringPattern error", e);
		}
		return sdf2.format(d);
	}

	/**
	 * 取活动传过来时间前1秒的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateByAddSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, -1);
		Date afterDate = cal.getTime();
		return afterDate;

	}

	public static Date dateByAddSecond(Date date, int secondsCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, secondsCount);
		Date afterDate = cal.getTime();
		return afterDate;

	}

	/**
	 * 根据日期转换成周
	 * @param day
	 * @return
	 */
	public static String getWeek(String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(day);
		} catch (ParseException e) {
		    LOGGER.debug("getWeek error", e);
			return null;
		}
		String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	public static String getCurrentDateYMD() {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		result = simpleDateFormat.format(new Date());
		return result;
	}

	/**
	 * 传入的时间，在该时间的基础上增加天数和小时数
	 * @param dateStr
	 * @param day
	 *            天数
	 * @param hour
	 *            小时数
	 * @return
	 */
	public static Date getDateAddDayAndHour(String dateStr, int day, int hour) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			c.setTime(SIMPLE_FORMAT.parse(dateStr));// 把传入的时间转换成Calendar
		} catch (ParseException e) {
		    LOGGER.debug("getDateAddDayAndHour error", e);
		}
		c.add(Calendar.DATE, day);
		if (hour > 0) {
			c.add(Calendar.HOUR, hour);
		}

		return c.getTime();
	}

	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String getCurrentTimeTOMinute() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formate.format(new Date());
	}

	/**
	 * 传入的时间，在该时间的基础上增加或者减少小时数
	 * @param date 日期
	 * @param hour 小时数
	 *
	 * @return
	 */
	public static String getDateAddHour(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);// 把传入的时间转换成Calendar
		c.add(Calendar.HOUR, hour);
		Date time = c.getTime();
		return parseToString(time, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 传入的时间，在该时间的基础上增加或者减少小时数
	 * @param hour 小时数
	 * @return
	 */
	public static String getCurrDateAddHour(int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR, hour);
		Date time = c.getTime();
		return parseToString(time, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 
	 * 取时间的年月日拼接0时0分0秒的时间戳
	 * @param date
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Long getDateDayTimeInMillis(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 设置当前时刻的时钟为0
		cal.set(Calendar.HOUR_OF_DAY, 0);
		// 设置当前时刻的分钟为0
		cal.set(Calendar.MINUTE, 0);
		// 设置当前时刻的秒钟为0
		cal.set(Calendar.SECOND, 0);
		// 设置当前的毫秒钟为0
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 传入的时间，在该时间的基础上增加或者减少小时数
	 * 
	 * @param date 日期
	 * @param hour 小时数
	 * @return
	 */
	public static Date getDateAddHour2(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);// 把传入的时间转换成Calendar
		c.add(Calendar.HOUR, hour);
		return c.getTime();
	}

	/**
	 * 计算入参时间与当天时间相差的天数
	 */
	public static Integer getDaysBetweenTwoDate(String gbBeginTimeStr) {
		if (StringUtils.isNotEmpty(gbBeginTimeStr)) {
			try {
				// 将入参转换为 yy-mm-dd格式的日志
				Date gbBeginDate = parseToDate(gbBeginTimeStr);
				// 获取当前时间的 yy-mm-dd格式日期
				String currentDateStr = getCurrentDateYMD();
				Date currentDate = parseToDate(currentDateStr);
				long betweenDays = (gbBeginDate.getTime() - currentDate.getTime()) / (3600 * 24 * 1000);
				// 计算当前时间和入参时间相差天数
				return Integer.valueOf(String.valueOf(betweenDays));
			} catch (Exception e) {
			    LOGGER.debug("getDaysBetweenTwoDate error", e);
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<String> getDatesBetweenTwoDate(Date beginDate, Date endDate, String formatter) {
		List<String> lDate = new ArrayList<String>();
		// 格式化yyyy-MM-dd
		Date newBeginDate = convertDateToYMDDate(beginDate);
		// 格式化yyyy-MM-dd
		Date newEndDate = convertDateToYMDDate(endDate);
		if (DateTools.parseToString(newBeginDate,formatter).equals(DateTools.parseToString(newEndDate,formatter))) {
			lDate.add(DateTools.parseToString(newBeginDate,formatter));
			return lDate;
		}
		lDate.add(DateTools.parseToString(newBeginDate,formatter));// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 CalendarEvent 的时间
		cal.setTime(newBeginDate);
		while (true) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (newEndDate.after(cal.getTime())) {
				lDate.add(DateTools.parseToString(cal.getTime(),formatter));
			} else {
				break;
			}
		}
		lDate.add(DateTools.parseToString(newEndDate,formatter));// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 在当前日期是增加或减少天数
	 * @param date
	 *            yyyy-MM-dd HH:mm:ss
	 * @param day
	 * @return
	 */
	public static Date addDateByDay(String date, int day) {
		Date dateObj = DateTools.parseDateStrictly(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dateObj);// 把传入的时间转换成Calendar
		c.add(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	/**
	 * 转化为时间格式
	 * @param time
	 * @return
	 */
	public static Date convertToTime(String time) {
		Date timeObj = null;
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			timeObj = timeFormat.parse(time);
		} catch (ParseException e) {
		    LOGGER.debug("convertToTime error", e);
		}
		return timeObj;
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date convertDateToYMDDate(Date date) {
		Date newDate = date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		try {
			newDate = sdf.parse(dateStr);
		} catch (ParseException e) {
		    LOGGER.debug("convertDateToYMDDate error", e);
		}
		return newDate;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static Date convertDateToYM(Date date) {
		Date newDate = date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateStr = sdf.format(date);
		try {
			newDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			LOGGER.debug("convertDateToYMDate error", e);
		}
		return newDate;
	}

	/**
	 * 转换日期至字符串
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date) {
		String str = null;
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = sdf.format(date);
		}
		return str;
	}

	public static Date getTodayDate(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String result = simpleDateFormat.format(new Date());
		Date d = convertStringToDate(format, result);
		return d;
	}

	public static Date convertStringToDate(String format, String time) {
		Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			date = simpleDateFormat.parse(time);
		} catch (ParseException e) {
		    LOGGER.debug("convertStringToDate error", e);
		}
		return date;
	}

	/**
	 * 转换日期时间字符串为date
	 *
	 * @param dateTime
	 *            2017-07-12 00:00:00
	 * @return
	 */
	public static Date parseStringToDateTime(String dateTime) {
		Date d = null;
		if (StringUtils.isNotBlank(dateTime)) {
			d = convertStringToDate(YYYY_MM_DD_HH_MM_SS, dateTime);
		}
		return d;
	}

	/**
	 * date2比date1多的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			return day2 - day1;
		}
	}

	public static String parstDateString(String dateStr) {
		String dateTmp = dateStr;
		if (dateStr.length() > 19) {
			dateTmp = dateStr.substring(0, 19);
		}
		return dateTmp;
	}

	/**
	 * 获取redis失效时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
    public static int getRedisExpireSeconds(String endTime) {
        long seconds = 0;
        try {
            Date gbEndDate = parseToDate(endTime, "yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            seconds = gbEndDate.getTime() - now.getTime();
            if (seconds > 0) {
                seconds = seconds / 1000;
            }
        } catch (Exception e) {
            LOGGER.debug("DateTools.getRedisExpireSeconds occur error", e);
        }
        return (int) seconds;
    }

	/**
	 * 在传入时间基础上增加或减少N天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAddDay(Date date, int day) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, day);
			return c.getTime();
		} catch (Exception e) {
			LOGGER.debug("DateTools.getDateAddDay occur error", e);
			return null;
		}
	}

	/**
	 * @description 判断日期是否发生改变
	 * @param newVal
	 * @param oldVal
	 * @return boolean
	 *    HaoXU
	 * @date 2020/9/30
	 **/
	public static boolean isChanged(Date newVal, Date oldVal) {
		if (newVal != null ) {
			if (oldVal == null) {
				return true;
			}
			if (!DateUtils.isSameDay(newVal, oldVal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 计算两个日期之间相差多少个小时
	 * @param target1
	 * @param target2
	 * @return int
	 *    HaoXU
	 * @date 2020/9/30
	 **/
	public static int betweenHour(Date target1, Date target2) {
		if (target1 == null || target2 == null) {
			throw  new RuntimeException("日期参数 target1 和 target2 均不能为空");
		}
		return Math.abs((int) ((target2.getTime() - target1.getTime()) / HOUR_OF_MILLIS));
	}

	/**
	 * 获取两个日期相差的秒数
	 *
	 * @param endDate
	 * @param startDate
	 * @return
	 */
	public  static long calLastedTime(Date endDate,Date startDate) {
		long a = endDate.getTime();
		long b = startDate.getTime();
		long c = (a - b) / 1000;
		return c;
	}

	/**
	 * @description 将日期毫秒转成日期字符串
	 *  
	 * @param milliseconds
	 * @param format
	 * @return
	 */
	public static String convertMillisecondsToDateStr(long milliseconds, String format) {
		String str = null;
		try {
			Date date = new Date(milliseconds);
			str = new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			LOGGER.error("convertMillisecondsToDateStr occur exception, message:{}", e.getMessage());
		}
		return str;
	}
}