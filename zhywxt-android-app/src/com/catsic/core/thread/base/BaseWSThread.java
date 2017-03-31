package com.catsic.core.thread.base;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import android.os.Handler;

import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.RequestParamSerializable;
import com.catsic.core.tools.ReflectionUtil;

/**
 * @Description: BaseWSThread
 * @author wuxianling
 * @date 2014年7月7日 上午11:07:43
 */
public abstract class BaseWSThread extends Thread {

	private Handler handler;
	private RequestInfo requestInfo;
	private int successWhat = AppConstants.STATE_200;//成功
	private int failWhat = AppConstants.STATE_500;//失败
	private int nullWhat = AppConstants.STATE_NULL;//空值
	

	public BaseWSThread(Handler handler, RequestInfo requestInfo) {
		this.handler = handler;
		this.requestInfo = requestInfo;
	}
	
	public BaseWSThread(Handler handler, RequestInfo requestInfo,int successWhat) {
		this.handler = handler;
		this.requestInfo = requestInfo;
		this.successWhat = successWhat;
	}
	
	public BaseWSThread(Handler handler, RequestInfo requestInfo,int successWhat,int failWhat,int nullWhat) {
		this.handler = handler;
		this.requestInfo = requestInfo;
		this.successWhat = successWhat;
		this.failWhat = failWhat;
		this.nullWhat = nullWhat;
	}

	public abstract void resolve(Handler handler);

	@Override
	public void run() {
		resolve(this.handler);
	}

	protected SoapObject getPreResult() {
		SoapObject soapObject = new SoapObject(this.requestInfo.getNameSpace(),this.requestInfo.getMethodName());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 初始化参数
		initParams(soapObject);
		
		//extra auth data
		Element[] header = authData(requestInfo,AppContext.LOGINUSER); 
         
		envelope.headerOut = header;
		envelope.setOutputSoapObject(soapObject);
		
		HttpTransportSE httpTransportSE = new HttpTransportSE(this.requestInfo.getServiceUrl());
		try {
			httpTransportSE.call(null,envelope);
			SoapObject resultSoapObject = (SoapObject) envelope.bodyIn;
			return resultSoapObject;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	  * @Title: authData
	  * @Description: 用户认证数据
	  * @param @param requestInfo
	  * @param @param jsonObject
	  * @param @return    设定文件
	  * @return Element[]    返回类型
	  * @throws
	  */
	private Element[] authData(RequestInfo requestInfo,JSONObject jsonObject) {
		if (requestInfo == null || requestInfo.getNameSpace() == null) {
			return null;
		}
		
		if (jsonObject == null) {
			return null;
		}
		Element[] header = new Element[1]; 
		header[0] = new Element().createElement(requestInfo.getNameSpace(), "authHeader"); 
     
		try {
			if (jsonObject.getString(AppConstants.USER_USERALIAS) == null) {
				return null;
			}
			Element username = new Element().createElement(requestInfo.getNameSpace(), "username"); 
			username.addChild(Node.TEXT, jsonObject.getString(AppConstants.USER_USERALIAS));
			header[0].addChild(Node.ELEMENT, username); 
			if (jsonObject.getString(AppConstants.USER_PASSWORD) == null) {
				return null;
			}
			Element password = new Element().createElement(requestInfo.getNameSpace(), "password"); 
			password.addChild(Node.TEXT,jsonObject.getString(AppConstants.USER_PASSWORD)); 
			header[0].addChild(Node.ELEMENT, password);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return header;
	}

	/**
	 * @Title: initParams
	 * @Description: 初始化参数
	 * @param @param soapObject
	 * @return void
	 * @throws
	 */
	protected void initParams(SoapObject soapObject) {
		if (this.requestInfo != null
				&& this.requestInfo.getReqParamList().size() > 0) {
			for (RequestParamSerializable param : this.requestInfo.getReqParamList()) {
				soapObject.addProperty(param.getKey(), param.getValue());
			}
		}
	}
	
	public Object getResult(SoapObject soapObject){
		Object object = null;
	    if (soapObject == null){
	      return object;
	    }
        object = ReflectionUtil.getInstanceByClassName(this.getRequestInfo().getClassName());
        
        Field[] fields = object.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
        	Field field = fields[j];
        	String fieldName = field.getName();
        	Object val = soapObject.getProperty(fieldName);
        	
        	ReflectionUtil.invokeSetter(object, fieldName, val);
		}
	   return object;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public int getSuccessWhat() {
		return successWhat;
	}

	public void setSuccessWhat(int successWhat) {
		this.successWhat = successWhat;
	}

	public int getFailWhat() {
		return failWhat;
	}

	public void setFailWhat(int failWhat) {
		this.failWhat = failWhat;
	}

	public int getNullWhat() {
		return nullWhat;
	}

	public void setNullWhat(int nullWhat) {
		this.nullWhat = nullWhat;
	}

}
