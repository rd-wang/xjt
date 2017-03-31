package com.catsic.core.service;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.catsic.biz.main.activity.MainActivity;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.RequestParamSerializable;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.ToastUtil;

/**  
  * @Description: LoginService 
  * @author wuxianling  
  * @date 2014年7月7日 下午2:03:35    
  */ 
public class LoginService extends BaseService{
	
	private ResultHandler handler = new ResultHandler();
	
	public LoginService(Context context){
		super(context);
	}
	
	/**  
	  * @Title: login  
	  * @Description: 登陆 
	  * @param @param username
	  * @param @param password     
	  * @return void   
	  * @throws  
	  */ 
	public void login(String username, String password){
	   ProgressDialogUtil.show(this.context, "登陆中，请稍候...", true);
	   
	    
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.COMMON_AUTH_LOGIN_URL),AppUrls.COMMON_AUTH_NAMESPACE,"login",null);
	   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   params.add(new RequestParamSerializable("username", username));
	   params.add(new RequestParamSerializable("password", password));
	   requestInfo.setReqParamList(params);
	   
	   new SingleResultThread(handler, requestInfo,AppConstants.STATE_200).start();
	    
	  }
	
	private class ResultHandler extends Handler{

	    @Override
	    public void handleMessage(Message message)
	    {
		      ProgressDialogUtil.dismiss();
		      switch (message.what)
		      {
			      case AppConstants.STATE_200:
			         Intent intent = new Intent(LoginService.this.context,MainActivity.class);
			         String result = message.getData().getString("result");
						try {
							AppContext.LOGINUSER = new JSONObject(result);
						} catch (JSONException e) {
							e.printStackTrace();
						}
			         LoginService.this.context.startActivity(intent);
			         break;
			      default :
			    	  String error = "登录失败，用户名或密码错误！";
				      ToastUtil.showLongToast(LoginService.this.context, error);
			    	break;
			  }
	    }
	    
	}
}
