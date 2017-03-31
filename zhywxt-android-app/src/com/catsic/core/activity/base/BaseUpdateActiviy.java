package com.catsic.core.activity.base;

import java.io.File;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.ApkInfo;

import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.ToastUtil;
import com.catsic.core.tools.VersionUtil;
import com.google.gson.Gson;

/**  
  * @Description: 软件更新 
  * @author wuxianling  
  * @date 2014年7月2日 下午3:00:12    
  */ 
public abstract class BaseUpdateActiviy extends FinalActivity{
	
	public ProgressDialog progressDialog;//文件下载进度条
	protected UpdateHandler handler;
	
	private String apkName;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		//检查网络是否通畅
//		if (DeviceUtil.isHasNetWork(this)) {
//			this.handler = new UpdateHandler();
//			//更新========================
//			new UpdateVersionService(this,handler).getLastestApkInfo();
//			//更新========================
//		}else{
//			Log.i("BaseUpdateActiviy is running...", "请先打开3G或则WIFI网络,否则无法正常使用软件...");
//			ToastUtil.showShortToast(this, "请先打开3G或则WIFI网络,否则无法正常使用软件");
//		}
		
	}
	
	/**  
	  * @Title: reboot  
	  * @Description: 程序安装 
	  * @param @param uri
	  * @param @return     
	  * @return boolean   
	  * @throws  
	  */ 
	public boolean reboot(String uri) {
		File apkfile = new File(uri);
        if (!apkfile.exists()) {
            return false;
        }
        
		try {
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.setDataAndType(Uri.parse("file://" + uri),"application/vnd.android.package-archive");
			startActivity(intent);
			finish();
			return true;
		} catch (Exception localException) {
			ToastUtil.showShortToast(this, "程序出错，无法重新安装主程序");
			Log.i("BaseUpdateActiviy is running...", "程序出错, 无法重新安装程序...");
			Log.e("BaseUpdateActiviy is running...",localException.getMessage());
			Log.e("error", localException.getMessage());
		}
		return false;
	}

	/**  
	  * @Description: 版本更新 
	  * @author wuxianling  
	  * @date 2014年7月2日 下午3:17:38    
	  */ 
	public class UpdateHandler extends Handler{
		
		@Override
		public void handleMessage(Message msg) {
			ProgressDialogUtil.dismiss();
			
			String result = msg.getData().getString("result");
			ApkInfo apkInfo = new Gson().fromJson(result, ApkInfo.class);
			switch (msg.what) {
				case AppConstants.STATE_200:
					String localVersion = VersionUtil.getVersionName(BaseUpdateActiviy.this);
					if (apkInfo!=null && Float.parseFloat(apkInfo.getLastestVersion())>Float.parseFloat(localVersion)) {
						handle(apkInfo);
					}
					break;
				case AppConstants.STATE_NULL:
				case AppConstants.STATE_500:
					break;
			}
			
		}
	}
		
		/**  
		  * @Title: handle  
		  * @Description: 更新处理 
		  * @param @param apkInfo     
		  * @return void   
		  * @throws  
		  */ 
		public void handle(final ApkInfo apkInfo){
			  new AlertDialog.Builder(BaseUpdateActiviy.this)
	          .setTitle("版本更新")
	          .setMessage("发现新版本，是否更新？")
	          .setPositiveButton("立即更新", new DialogInterface.OnClickListener(){
		            @SuppressWarnings("unchecked")
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
		            {
		            	FinalHttp fh = new FinalHttp();
		    			//下载Apk
		            	if (!ObjectUtils.isNullOrEmptyString(apkInfo.getFileName())) {
							apkName = apkInfo.getFileName();
						}else{
							apkName = AppConstants.APK_NAME_PREFIX+apkInfo.getLastestVersion()+".apk";
						}
						fh.download(AppUrls.getServiceURL("")+AppConstants.UPLOAD_APK_PATH+apkName,
//		    					true,//true:断点续传 false:不断点续传（全新下载）
		    					Environment.getExternalStorageDirectory().toString()+"/"+apkName,//保存到本地的路径
		    					new AjaxCallBack() {
									@Override
									public void onStart() {
										progressDialog = new ProgressDialog(BaseUpdateActiviy.this);
		    							progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//// 设置水平进度条 
		    							progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
		    							progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//		    							progressDialog.setIcon(icon);// 设置提示的title的图标，默认是没有的 
		    							progressDialog.setTitle("正在下载，请稍后...");
		    							progressDialog.show();
										super.onStart();
									}
		    						@Override
		    						public void onLoading(long count, long current) {
		    							double _count = Double.parseDouble(count+"");
		    							double _current = Double.parseDouble(current+"");
		    							//显示格式化
		    							String vMax = "";
		    							String vCurrent = "";
		    							if (_count > 1*1024*1024) {
											vMax = String.format("%.2f",(_count / (1024 * 1024))) + "MB";
											vCurrent = String.format("%.2f",(_current / (1024 * 1024))) + "MB";
										}
		    							else if (_count >= 1*1024 && _count < (1*1024*1024)) {
											vMax = String.format("%.2f",(_count / 1*1024)) + "KB";
											vCurrent = String.format("%.2f",(_current / 1*1024)) + "KB";
										}else if (_count >= 0 && _count < 1024) {
											vMax = _count + "B";
											vCurrent = _current + "B";
										}
		    							
		    							progressDialog.setMax(Integer.parseInt(count+""));
		    							progressDialog.setProgress(Integer.parseInt(current+""));
		    							//显示格式化
		    							progressDialog.setProgressNumberFormat(vCurrent+"/"+vMax);
		    							super.onLoading(count, current);
		    						}
		    						@Override
		    						public void onSuccess(Object t) {
		    							progressDialog.dismiss();
		    							
		    							//安装新版本
		    							if(!reboot(Environment.getExternalStorageDirectory().toString()+"/"+apkName)){
		    							}
		    							super.onSuccess(t);
		    						}
		    						@Override
		    						public void onFailure(Throwable t,int errorNo, String strMsg) {
		    							ToastUtil.showShortToast(BaseUpdateActiviy.this, "下载失败！");
		    							super.onFailure(t, errorNo, strMsg);
		    						}
		    			});
		            }
	          }).setNegativeButton("稍后更新", new DialogInterface.OnClickListener(){
		            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
		            {
		              paramAnonymousDialogInterface.cancel();
		            }
	          }).create().show();
		}
		

}
