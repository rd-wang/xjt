package com.catsic.core.tools;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

/**  
  * @Description: Gson 工具类 
  * @author wuxianling  
  * @date 2014年9月18日 上午11:14:36    
  */ 
public class GsonUtils {
	
	/**  
	  * @Title: toJson  
	  * @Description: 转成json字符串 
	  * @param @param obj
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String toJson(Object obj){
		 Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
				 .setDateFormat("yyyy-MM-dd HH:mm:ss")
				 .create();  
		 return gson.toJson(obj);
	}
	
	@SuppressWarnings("unchecked")
	public static Object fromJson(String json,Class clazz){
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
//				.registerTypeAdapter(Date.class,new DateTypeAdapter())
				.create();  
		return gson.fromJson(json,clazz);
	}
	

}
