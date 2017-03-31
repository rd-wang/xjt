package com.catsic.core.tools.util;

import android.content.Context;
import android.util.Log;

/**
 * Log管理类
 * 
 * @author Administrator
 * 
 */
public class LogUtil {
	private static boolean isDebug = true;// log开关，发布应用前值为false
	/**
	 * 打印i级别的log
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}
	}
	public static void i(Context context,String msg) {
		if (isDebug) {
			Log.i(context.getClass().getSimpleName(), msg);
		}
	}
	public static void e(Context context,String msg) {
		if (isDebug) {
			Log.e(context.getClass().getSimpleName(), msg);
		}
	}

	/**
	 * 打印i级别的log
	 * @param msg
	 */
	public static void i(Object object, String msg) {
		if (isDebug) {
			Log.i(object.getClass().getSimpleName(), msg);
//			if (msg != null) {
//
//				Log.i(object.getClass().getSimpleName(), msg);
//			} else {
//				Log.i(object.getClass().getSimpleName(), "msg==null");
//			}

		}
	}

	/**
	 * 打印e级别的log
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
//			if (msg != null) {
//
//				Log.e(tag, msg);
//			} else {
//				Log.e(tag, "msg==null");
//			}
		}
	}

	/**
	 * 打印e级别的log

	 * @param msg
	 */
	public static void e(Object object, String msg) {
		if (isDebug) {
			Log.i(object.getClass().getSimpleName(), msg);
//			if (msg != null) {
//
//				Log.i(object.getClass().getSimpleName(), msg);
//			} else {
//				Log.i(object.getClass().getSimpleName(), "msg==null");
//			}

		}

	}

}
