package com.catsic.core.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.catsic.core.AppConstants;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.thread.base.BaseWSThread;
import com.catsic.core.tools.LogUtils;

import org.ksoap2.serialization.SoapObject;

import java.net.URLDecoder;

/**  
  * @Description: 单一json返回 
  * @author wuxianling  
  * @date 2014年8月21日 上午11:00:53    
  */ 
public class SingleResultThread extends BaseWSThread{
	
	public SingleResultThread(Handler handler, RequestInfo requestInfo,int successWhat){
	    super(handler, requestInfo,successWhat);
	}
	
	public SingleResultThread(Handler handler, RequestInfo requestInfo,int what,int failWhat){
		super(handler, requestInfo,what);
	}

	@Override
	public void resolve(Handler handler) {
		Message message = handler.obtainMessage();
	    Bundle bundler = new Bundle();
	    try{
		      SoapObject soapObject = super.getPreResult();
		      if (soapObject == null) {
				message.what = super.getFailWhat();
			  }
		      else if (soapObject.getPropertyCount()>0) {
		    	  
		    	  message.what = super.getSuccessWhat();
		    	  String result = soapObject.getProperty(0).toString();
		    	  result = URLDecoder.decode(result, AppConstants.ENCODING_UTF8);
				  LogUtils.outString("URLDecoder"+result);
		    	  bundler.putString("result", result);
			  }else{
				  message.what = super.getNullWhat();
			  }
		     
	          message.setData(bundler);
	    }
	    catch (Exception ex){
	    	message.what = super.getFailWhat();
	        ex.printStackTrace();
	    }
	    handler.sendMessage(message);
	}

}
