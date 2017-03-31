package com.catsic.core.tools;

import android.app.ProgressDialog;
import android.content.Context;

/**  
  * @Description: ProgressDialogUtil 
  * @author wuxianling  
  * @date 2014年7月4日 下午1:45:24    
  */ 
public class ProgressDialogUtil {
	
	public static ProgressDialog progressDialog;

	public static void dismiss() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public static void setMessage(String message) {
		if (progressDialog != null)
			progressDialog.setMessage(message);
	}

	public static void show(Context context, String message,boolean cancelFlag) {
		if (progressDialog != null)
			dismiss();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(cancelFlag);
		progressDialog.setIndeterminate(true);
		progressDialog.setProgressStyle(0);
		//点击屏幕，消失
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	public static void showing() {
		if (progressDialog != null)
			progressDialog.show();
	}

}
