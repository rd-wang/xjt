package com.catsic.core.service;

import java.util.List;

import net.tsz.afinal.db.sqlite.DbModel;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.CatsicAppConfig;
import com.catsic.core.bean.CatsicCode;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.T_XZQH;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**  
  * @Description: 应用配置 
  * @author wuxianling  
  * @date 2014年9月18日 下午4:04:33    
  */ 
public class CatsicAppConfigService extends BaseService{
	
	private ResultHandler handler = new ResultHandler();
	
	public CatsicAppConfigService(Context context){
		super(context);
	}
	
	/**  
	  * @Title: loadApkConfig  
	  * @Description: 加载Apk配置 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void loadApkConfig(){
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.COMMON_APPCONFIG_URL),AppUrls.COMMON_APPCONFIG_NAMESPACE,"loadApkConfig",null);
	   
	   new SingleResultThread(handler, requestInfo,AppConstants.STATE_200).start();
	}
	
	private class ResultHandler extends Handler{

	    @Override
	    public void handleMessage(Message message)
	    {
		      switch (message.what)
		      {
			      case AppConstants.STATE_200:
			    	  
			         String result = message.getData().getString("result");
					 List<CatsicAppConfig> list =  new Gson().fromJson(result, new TypeToken<List<CatsicAppConfig>>(){}.getType());  
					 
					 for (CatsicAppConfig catsicAppConfig : list) {
						   //本地SQL更新
					       if (AppConstants.APK_APKSQLVersion.equalsIgnoreCase(catsicAppConfig.getCkey())) {
					    	   
					    	   DbModel dbModel = db.findDbModelBySQL("select count(*) as cnt from sqlite_master where type='table' and name='CATSIC_APPCONFIG' ");
					    	   //表是否存在
					    	   if (dbModel.getInt("cnt")>0) {
					    		   List<CatsicAppConfig> appConfigList = db.findAllByWhere(CatsicAppConfig.class, "ckey = '"+catsicAppConfig.getCkey()+"'");
					    		   
					    		   float oldVersion = Float.valueOf(appConfigList.get(0).getCvalue());
								   float newVersion = Float.valueOf(catsicAppConfig.getCvalue());
								   if (newVersion > oldVersion) {
									   //update
									   db.delete(appConfigList.get(0));
									   
									   db.save(catsicAppConfig);
									   
									   updateSQL();
								   }
							   }
					    	   else{
								 //save 
					    		   db.save(catsicAppConfig);
					    		   
								   updateSQL();
							   }
							   break;
						   }
								
					 }
					 //==========
					 
			  }
	    }
	    
	    /**  
	      * @Title: updateSQL  
	      * @Description: 更新本地SQL 
	      * @return void   
	      * @throws  
	      */ 
	    public void updateSQL(){
	    	//初始化字典
	    	db.deleteAll(CatsicCode.class);
	    	new CatsicCodeService(context).loadCatsicCodes();
	    	
	    	//行政区划
	    	db.deleteAll(T_XZQH.class);
	    	new XzqhService(context).list();
	    }
	    
	}
}
