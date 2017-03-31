package com.catsic.core.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.kobjects.base64.Base64;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.activity.LoginActivity;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.FileService;
import com.catsic.core.tools.DateUtil;
import com.catsic.core.tools.IPUtils;
import com.catsic.core.tools.ObjectUtils;

/**
 * @ClassName: AppExceptionHandler
 * @Description: UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * @author wuxianling
 * @date 2015年6月29日 下午2:09:12
 */
public class AppExceptionHandler implements UncaughtExceptionHandler {

	public static final String TAG = "AppExceptionHandler";

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler defaultHandler;
	// CrashHandler实例
	private static AppExceptionHandler INSTANCE = new AppExceptionHandler();
	// 程序的Context对象
	private Context context;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	private AppExceptionHandler() {
	}

	/** 获取AppExceptionHandler实例 ,单例模式 */
	public static AppExceptionHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		this.context = context;
		// 获取系统默认的UncaughtException处理器
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该AppExceptionHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && defaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			defaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
//				Toast.makeText(context, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
				Toast.makeText(context, "很抱歉,网络出现异常,即将退出.", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(context);
		// 保存日志文件
		sendException(ex);
		
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null": pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String sendException(Throwable ex) {
		//用户信息
		try {
			infos.put("userid", AppContext.LOGINUSER.getString("userid"));
			infos.put("username", AppContext.LOGINUSER.getString("username"));
			infos.put("useralias", AppContext.LOGINUSER.getString("useralias"));
			infos.put("orgid", AppContext.LOGINUSER.getString("orgid"));
			infos.put("ip", IPUtils.getIp());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "zhywxt-android-app-" + time + "-" + timestamp + ".log";
//			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				String sdcardPath = "/sdcard/zhywxt-android-app/";
//				File f = new File(sdcardPath + fileName);
//				if (!f.exists()) {
//					f.mkdirs();
//				}
//				FileOutputStream fos = new FileOutputStream(sdcardPath + fileName);
//				fos.write(sb.toString().getBytes());
//				fos.close();
//			}
			
			//send 
			String content = new String(Base64.encode(sb.toString().getBytes()));  //进行Base64编码
			
			Tfile file = new Tfile();
			String dir = DateUtil.format(new Date(),"yyyy-MM");
			file.setFilePath(AppConstants.UPLOAD_APK_EXCEPTION_LOG+dir+"/");
			
			file.setFileSize(content.length()+"");
			String fileType = "";
			if (ObjectUtils.isNotEmpty(fileName)&&(fileName.indexOf(".")>0)) {
				fileType = fileName.substring(fileName.lastIndexOf(".")+1);
			}
			file.setFileType(fileType);
			file.setFileTime(time);
			file.setGroupId("APKERROR");
			file.setFileName(fileName);
			file.setContent(content);
			new FileService(context).upload(file);
//			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		
		return null;
	}
}
