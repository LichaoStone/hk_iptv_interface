package com.hicon.utils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @作者 栗超
 * @时间 2018年5月14日 下午1:36:06
 * @说明
 */
public class DateUtil {
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	
	/**
	 * 时间转换为毫秒数
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return 1526275490000
	 */
    public static String date2msec(String date){
    	long time=0L;
    	try {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
	        time = simpleDateFormat.parse(date).getTime();
		} catch (ParseException e) {
			logger.error("时间转换为毫秒数出错:",e);
		}  
		return String.valueOf(time);
    }
    
    /**
     * 毫秒转换为时间
     * @param msec 1526275490000
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String msec2date(String msec){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
        Date date2 = new Date();  
        try {
            date2.setTime(Long.parseLong(msec));  
		} catch (Exception e) {
			logger.error("毫秒转换为时间出错:",e);
		}
    	
    	return simpleDateFormat.format(date2);  
    }
    
    /**
     * 时间转换为N时N分N秒   
     * 	   86（单位秒）-->1分26秒
     * @time 
     * @return 例:1分26秒
     */
    public static String second2HourMinSecond(Integer seconds){
    	String retStr="";
    	try {
			if (seconds>60) {
				Integer second = seconds % 60;  
				Integer min = seconds / 60; 
				retStr=min+"分"+second+"秒"; 
				
				if (min>60){ 
					 min = (seconds / 60) % 60;  
					 Integer hour = (seconds / 60) / 60; 
					 retStr = hour + "小时" + min + "分" + second + "秒";  
					 
					 if (hour>24){  
		                    hour =((seconds/60)/60)%24;  
		                    Integer day =(((seconds/60)/60)/24);  
		                    retStr=day+"天"+hour+"小时"+min+"分"+second+"秒";  
		              }  
				}
			}else{
				retStr=seconds+"秒";
			}
		} catch (Exception e) {
		}
    	
		return retStr;
    }
    
    /**
     * 获取日期对应的星期几
     * @param time 2018-05-15
     * @return     星期二
     */
    public static String getWeekOfDate(String time) {
    	String[] weekDays = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
    	int dayForWeek = 0;

    	try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	Calendar c = Calendar.getInstance();
        	c.setTime(format.parse(time));
        	if(c.get(Calendar.DAY_OF_WEEK) == 1){
        		dayForWeek = 0;
        	}else{
        		dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        	}
		} catch (Exception e) {
			logger.error("获取日期对应星期几出错:",e);
		}
    	
    	return weekDays[dayForWeek];
    }
    
    /**
     * 转换时间为3月12日 12:23格式
     * @return
     */
    public static String getMMDDHHmm(String time) {
    	
    	String returnStr="";
    	if (!StringUtils.isEmpty(time)) {
        	try {
            	String[] timeArr=time.split(" ");
            	String[] YYYYMMDDArr=timeArr[0].split("-");
            	
            	returnStr=YYYYMMDDArr[1]+"月"+YYYYMMDDArr[2]+"日 "+timeArr[1];
    		} catch (Exception e) {
    			logger.error("【MM月dd日 HH:mm】时间转换错误",e);
    		}
		}
    	
		return returnStr;
	}
    
    /**
     * 获取当前时间
     * @return
     */
    public static String getTimeToSec() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
    
    
    
    /**
     * 获取当日
     * @return
     */
    public static String getTody(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
    
    /**
     * 获取当前日期前N天或者后N天
     * @param n 天数
     * @return 2018-06-30
     */
    public static String getBeforeOrNextDay(int n) {
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    		String day=dateFormat.format(new Date());
        	
            String sYear = day.substring(0,4);
            int year = Integer.parseInt(sYear);
            String sMonth = day.substring(4,6);
            int month = Integer.parseInt(sMonth);
            String sDay = day.substring(6,8);
            int dday = Integer.parseInt(sDay);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, dday);
            cal.add(Calendar.DATE,n);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(cal.getTime());
        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        }
    }
    
    /**
     * 获取日期对应月份的第一天
     * @return 2018-06-01
     */
    public static String getFirstDayOfMonth() {
    	try {
    		Calendar cale = null;
    		cale = Calendar.getInstance();
    		
        	// 获取当月第一天和最后一天
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		// 获取前月的第一天
    		cale = Calendar.getInstance();
    		cale.add(Calendar.MONTH,0);
    		cale.set(Calendar.DAY_OF_MONTH, 1);
    		return format.format(cale.getTime());
		} catch (Exception e) {
			logger.error("获取日期对应月份的第一天出错",e);
		}
    	
    	return null;
	}
    

    /**
	 * 计算两个日期之间的间隔
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */

	public static  int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  

       return Integer.parseInt(String.valueOf(between_days+new Long(1)));     
    }  
	
	/**
	 * 比较两个时间的先后
	 * @param time1 格式12:03
	 * @param time2 格式12:03
	 * @return 0：两个时间相等，1：time1比time2晚，-1：time1比time2早
	 */
	public static int timeLate(String time1, String time2){
		if(time1.equals(time2)){
			return 0;
		}
		String t1[] = time1.split(":");
		String t2[] = time2.split(":");
		//比较小时
		if(Integer.parseInt(t1[0]) > Integer.parseInt(t2[0])){
			return 1;
		}
		if(Integer.parseInt(t1[0]) < Integer.parseInt(t2[0])){
			return 2;
		}
		//比较分钟
		if(Integer.parseInt(t1[1]) > Integer.parseInt(t2[1])){
			return 1;
		}
		if(Integer.parseInt(t1[1]) < Integer.parseInt(t2[1])){
			return 2;
		}
		return 0;
	}
    
	/**
	 * 获取当前年份
	 * @return
	 */
	public static String getCurrYear() {
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		return year;
	}
	
	/**
	 * 获取当前月份
	 * @return
	 */
	public static String getCurrMonth() {
		Calendar cal = Calendar.getInstance();
	    int month = cal.get(Calendar.MONTH) + 1;
	    
	    String str="";
	    if(month<10){
	    	str="0"+String.valueOf(month);
	    }else{
	    	str=String.valueOf(month);
	    }
	    
		return str;
	}
	
	
	
	/**
	 * 获取时间  
	 *     例如:08:00
	 *     24小时制
	 * @return
	 */
	public static String getCurrTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date()).split(" ")[1].split(":")[0]+":"+df.format(new Date()).split(" ")[1].split(":")[1];
	}
	
	
    public static void main(String[] args) {
    	//System.out.println(getCurrYear());
    	//System.out.println(getCurrMonth());
    	System.out.println(getCurrTime());
	}
}
