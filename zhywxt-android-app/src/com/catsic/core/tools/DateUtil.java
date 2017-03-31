package com.catsic.core.tools;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**  
  * @Description: 日期工具类 
  * @author wuxianling  
  * @date 2014年7月31日 下午2:12:58    
  */ 
public class DateUtil {
	
	/**
	 * 常用格式
	 */
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATETIME_WITHOUTSS = "yyyy-MM-dd HH:mm";

	
	@SuppressLint("SimpleDateFormat")
	public static String format(Date date,String pattern){
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Date parse(String dateStr,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
