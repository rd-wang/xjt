package com.catsic.core;

import android.app.Application;
import android.content.Context;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.SharedPreferencesUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: App 初始化
 * @author wuxianling
 * @date 2014年7月25日 上午9:27:12
 */
public class AppContext extends Application {

	public static final String PROVICE_GUIZHOU = "52";// 贵州省
	public static final String PROVICE_LUOYANG = "4103";// 洛阳市

	/** 系统代码 **/
	public static final String SYS_CODE = PROVICE_LUOYANG;

	/** 用户信息 **/
	public static JSONObject LOGINUSER = null;// loginUser json

	/**
	 * @Fields dynamicLayers : 图层
	 */
	public static Map<String, String> dynamicLayers = new HashMap<String, String>();

	static {
		dynamicLayers.put("G", "/6");
		dynamicLayers.put("S", "/7");
		dynamicLayers.put("X", "/8");
		dynamicLayers.put("Y", "/9");
		dynamicLayers.put("C", "/10");
		dynamicLayers.put("Z", "/11");
		dynamicLayers.put("XZ", "/0");
		dynamicLayers.put("JZC", "/1");
		dynamicLayers.put("CX", "/2");
		dynamicLayers.put("QL", "/3");
		dynamicLayers.put("SD", "/4");
		dynamicLayers.put("DK", "/5");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化服务器地址相关配置
		initUrl();
		TypefaceProvider.registerDefaultIconSets();
		// exceptionHandler
		// AppExceptionHandler exceptionHandler =
		// AppExceptionHandler.getInstance();
		// exceptionHandler.init(getApplicationContext());

	}

	public void initUrl() {
		// init ip,port
		String ip = SharedPreferencesUtil.get(this, AppConstants.SP_SERVERDATA,
				Context.MODE_PRIVATE, "ip", AppUrls.SERVICE_IP);
		String port = SharedPreferencesUtil.get(this,
				AppConstants.SP_SERVERDATA, Context.MODE_PRIVATE, "port",
				AppUrls.SERVICE_PORT);
		if (ObjectUtils.isNullOrEmptyString(ip)) {
			SharedPreferencesUtil.put(this, AppConstants.SP_SERVERDATA,
					Context.MODE_PRIVATE, "ip", ip);

		}
		if (ObjectUtils.isNullOrEmptyString(port)) {
			SharedPreferencesUtil.put(this, AppConstants.SP_SERVERDATA,
					Context.MODE_PRIVATE, "port", port);

		}
		AppUrls.SERVICE_IP = ip;
		AppUrls.SERVICE_PORT = port;
	}

}
