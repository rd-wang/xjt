package com.catsic.core.service;

import android.content.Context;
import android.os.Handler;

import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.ProgressDialogUtil;
/**  
  * @Description: 版本更新 
  * @author wuxianling  
  * @date 2014年9月23日 上午11:06:51    
  */ 
public class UpdateVersionService extends BaseService {

	private Handler handler;

	public UpdateVersionService(Context context,Handler handler){
		super(context);
		this.handler = handler;
	}
	public UpdateVersionService(Context context){
		super(context);
	}

	/**  
	  * @Title: getLastestApkInfo  
	  * @Description: 获取Apk最新信息 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void getLastestApkInfo(){
		
	   ProgressDialogUtil.show(context, "正在检查新版本...", true);
		
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.COMMON_APK_URL),AppUrls.COMMON_APK_NAMESPACE,"getLastestApkInfo",null);
	   new SingleResultThread(handler, requestInfo,AppConstants.STATE_200).start();
	}

}
