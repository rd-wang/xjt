package com.catsic.core.tools;

import android.os.Handler;
import android.os.Message;

import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
  * @ClassName: SoapUtil
  * @Description: WS工具类
  * @author Comsys-wuxianling
  * @date 2015年5月28日 下午3:20:04
  */
public class SoapUtil {
	
	// 含有3个线程的线程池
	private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

	/**
	 * 
	 * @param url WebService服务器地址
	 * @param namespace 命名空间
	 * @param methodName WebService的调用方法名
	 * @param properties WebService的参数
	 * @param webServiceCallBack 返回结果回调接口
	 */
	public static void callService(String url,final String namespace,final String methodName,Map<String,String> properties,final WebServiceCallBack webServiceCallBack) {
		// 创建HttpTransportSE对象，传递WebService服务器地址
		final HttpTransportSE httpTransportSE = new HttpTransportSE(url,10000);
		// 创建SoapObject对象
		SoapObject soapObject = new SoapObject(namespace, methodName);
		
		// SoapObject添加参数
		if (properties != null) {
			for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				soapObject.addProperty(entry.getKey(), entry.getValue());
			}
		}
		
		// 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
		final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//extra auth data
	    Element[] header = authData(namespace,AppContext.LOGINUSER);
	    soapEnvelope.headerOut = header;
		soapEnvelope.setOutputSoapObject(soapObject);

		// 用于子线程与主线程通信的Handler
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == AppConstants.STATE_200){
					webServiceCallBack.onSucced((String)msg.obj);
				}else if(msg.what == AppConstants.STATE_NULL){
					webServiceCallBack.onSucced(null);
				}else{
					webServiceCallBack.onFailure((String)msg.obj);
				}
			}
		};

		// 开启线程去访问WebService
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				SoapObject resultSoapObject = null;
				Message message = handler.obtainMessage();
				try {
//					httpTransportSE.call(namespace + methodName, soapEnvelope);
					httpTransportSE.call(null, soapEnvelope);
					if (soapEnvelope.getResponse() != null) {
						// 获取服务器响应返回的SoapObject
						resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
					}
					
					if (resultSoapObject == null) {
//						message.what = AppConstants.STATE_500;
//						message.obj = "fail!";
						message.what = AppConstants.STATE_NULL;
					}
				    else if (resultSoapObject.getPropertyCount()>0) {
				    	  message.what = AppConstants.STATE_200;
				    	  String result = resultSoapObject.getProperty(0).toString();
				    	  result = URLDecoder.decode(result, AppConstants.ENCODING_UTF8);
				    	  message.obj = result;
					}else{
						  message.what = AppConstants.STATE_NULL;
					}
				} catch (Exception e) {
					message.what = AppConstants.STATE_500;
					message.obj = e.getMessage();
				}

				// 将获取的消息利用Handler发送到主线程
				handler.sendMessage(message);
			}
		});
	}
	
	/**
	  * @Title: authData
	  * @Description: 用户认证数据
	  * @param @param namespace
	  * @param @param jsonObject
	  * @param @return    设定文件
	  * @return Element[]    返回类型
	  * @throws
	  */
	private static Element[] authData(String namespace,JSONObject jsonObject) {
		if (namespace == null) {
			return null;
		}
		
		if (jsonObject == null) {
			return null;
		}
		Element[] header = new Element[1]; 
		header[0] = new Element().createElement(namespace, "authHeader"); 
    
		try {
			if (jsonObject.getString(AppConstants.USER_USERALIAS) == null) {
				return null;
			}
			Element username = new Element().createElement(namespace, "username"); 
			username.addChild(Node.TEXT, jsonObject.getString(AppConstants.USER_USERALIAS));
			header[0].addChild(Node.ELEMENT, username);
			if (jsonObject.getString(AppConstants.USER_PASSWORD) == null) {
				return null;
			}
			Element password = new Element().createElement(namespace, "password"); 
			password.addChild(Node.TEXT,jsonObject.getString(AppConstants.USER_PASSWORD)); 
			header[0].addChild(Node.ELEMENT, password);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return header;
	}
	
	/**
	 * WebService回调接口
	 *
	 */
	public interface WebServiceCallBack {
		
		public void onSucced(String result);
		
		public void onFailure(String result);
	}

}