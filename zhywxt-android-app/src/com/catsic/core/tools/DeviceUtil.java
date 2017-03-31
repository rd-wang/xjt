package com.catsic.core.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

/**  
  * @Description: DeviceUtil 
  * @author wuxianling  
  * @date 2014年7月2日 下午3:02:50    
  */ 
public class DeviceUtil {
	
	 /**  
	  * @Title: isHasNetWork  
	  * @Description: 是否存在可连接的网络 
	  * @param @param paramContext
	  * @param @return     
	  * @return boolean   
	  * @throws  
	  */ 
	public static boolean isHasNetWork(Context paramContext)
	  {
	    ConnectivityManager connectivity  = (ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (connectivity == null) {
	    	Log.i("NetWorkState", "Unavailabel"); 
			return false;
		}
	    NetworkInfo [] infos = connectivity.getAllNetworkInfo();
	    if (infos != null) {
			for (NetworkInfo networkInfo : infos) {
				if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
					Log.i("NetWorkState", "Availabel"); 
					return true;
				}
			}
		}
	    return false;
	  }
	
	public static String getESN(Context paramContext) {
		String ESN = null;
		if ((ESN == null) || (ESN.equals("")) || (ESN.length() == 0)){
			try {
				ESN = ((TelephonyManager) paramContext.getSystemService("phone")).getDeviceId();
			} catch (Exception localException) {
			}
		}
		return ESN;
	}

	public static String getIMSI(Context paramContext) {
		String IMSI = null;
		if ((IMSI == null) || (IMSI.equals("")) || (IMSI.length() == 0)){
			try {
				IMSI = ((TelephonyManager) paramContext.getSystemService("phone")).getSubscriberId();
			} catch (Exception localException) {
			}
		}
		return IMSI;
	}
	
	/**  
	  * @Title: hasSDCard  
	  * @Description: 是否存在SDCard 
	  * @param @return     
	  * @return boolean   
	  * @throws  
	  */ 
	public static boolean hasSDCard(){
		return Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState());
	}

}
