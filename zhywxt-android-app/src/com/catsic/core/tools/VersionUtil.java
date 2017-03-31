package com.catsic.core.tools;

import android.content.Context;
import android.content.pm.PackageManager;

import com.catsic.core.AppConstants;

/**  
  * @Description: 版本工具 
  * @author wuxianling  
  * @date 2014年7月2日 下午3:48:58    
  */ 
public class VersionUtil {
	
	 public static String getVersionName(Context context){
	    try{
	      String str = context.getPackageManager().getPackageInfo(AppConstants.BASE_PACKAGE, 0).versionName;
	      return str;
	    }catch (PackageManager.NameNotFoundException localNameNotFoundException){
	    }
	    return "1.0";
	 }
	 public static int getVersionCode(Context context){
		 try{
			 return context.getPackageManager().getPackageInfo(AppConstants.BASE_PACKAGE, 0).versionCode;
		 }catch (PackageManager.NameNotFoundException localNameNotFoundException){
		 }
		 return 1;
	 }

}
