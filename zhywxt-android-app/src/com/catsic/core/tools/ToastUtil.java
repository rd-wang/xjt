package com.catsic.core.tools;

import android.content.Context;
import android.widget.Toast;

/**  
  * @Description: 提示工具类 
  * @author wuxianling  
  * @date 2014年7月3日 上午11:42:50    
  */ 
public class ToastUtil {

	/**  
	  * @Title: showLongToast  
	  * @Description: TODO 
	  * @param @param context
	  * @param @param msg     string
	  * @return void   
	  * @throws  
	  */ 
	public static void showLongToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	/**  
	  * @Title: showLongToast  
	  * @Description: TODO 
	  * @param @param context
	  * @param @param resId   R.string.x   
	  * @return void   
	  * @throws  
	  */ 
	public static void showLongToast(Context context,int resId){
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}

	/**  
	  * @Title: showShortToast  
	  * @Description: TODO 
	  * @param @param context
	  * @param @param msg     string
	  * @return void   
	  * @throws  
	  */ 
	public static void showShortToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**  
	  * @Title: showShortToast  
	  * @Description: TODO 
	  * @param @param context
	  * @param @param resId     R.string.x
	  * @return void   
	  * @throws  
	  */ 
	public static void showShortToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}
	

}
