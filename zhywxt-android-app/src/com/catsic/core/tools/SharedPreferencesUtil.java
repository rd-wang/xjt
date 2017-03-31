package com.catsic.core.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**  
  * @Description: SharedPreferences 数据操作 
  * @author wuxianling  
  * @date 2014年7月3日 上午11:59:01    
  */ 
public class SharedPreferencesUtil {
	
	/**  
	  * @Title: put  
	  * @Description: 参数存储 
	  * @param @param context
	  * @param @param name
	  * @param @param mode  默认 Context.MODE_PRIVATE
	  * SharedPreferences的四种操作模式:
			Context.MODE_PRIVATE  为默认操作模式 ,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
			Context.MODE_APPEND   模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件
			Context.MODE_WORLD_READABLE  用来控制其他应用是否有权限读写该文件(当前文件可以被其他应用读取)
			Context.MODE_WORLD_WRITEABLE 用来控制其他应用是否有权限读写该文件(当前文件可以被其他应用写入)
	  * 
	  * @param @param key
	  * @param @param value     
	  * @return void   
	  * @throws  
	  */ 
	public static void put(Context context,String name,int mode,String key,String value){
		SharedPreferences.Editor editor = context.getSharedPreferences(name, mode).edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**  
	  * @Title: put  
	  * @Description: 参数存储 
	  * @param @param context
	  * @param @param name
	  * @param @param mode  默认 Context.MODE_PRIVATE
	  * SharedPreferences的四种操作模式:
			Context.MODE_PRIVATE  为默认操作模式 ,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
			Context.MODE_APPEND   模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件
			Context.MODE_WORLD_READABLE  用来控制其他应用是否有权限读写该文件(当前文件可以被其他应用读取)
			Context.MODE_WORLD_WRITEABLE 用来控制其他应用是否有权限读写该文件(当前文件可以被其他应用写入)
	  * 
	  * @param @param keys
	  * @param @param values      
	  * @return void   
	  * @throws  
	  */ 
	public static void put(Context context,String name,int mode,String [] keys,String []values){
		SharedPreferences.Editor editor = context.getSharedPreferences(name, mode).edit();
		if (keys!=null && keys.length>0 && values!=null && values.length >0 && values.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				editor.putString(keys[i], values[i]);
			}
		}
		editor.commit();
	}
	
	/**  
	  * @Title: get  
	  * @Description: 参数获取
	  * @param @param context
	  * @param @param name
	  * @param @param mode
	  * @param @param key
	  * @param @param defaultVal
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String get(Context context,String name,int mode,String key,String defaultVal){
		SharedPreferences preferences=context.getSharedPreferences(name, mode);
		return preferences.getString(key,defaultVal);
	}
	
	/**  
	  * @Title: remove  
	  * @Description: 参数删除 
	  * @param @param context
	  * @param @param name
	  * @param @param mode
	  * @param @param key     
	  * @return void   
	  * @throws  
	  */ 
	public static void remove(Context context,String name,int mode,String key){
		SharedPreferences.Editor editor = context.getSharedPreferences(name, mode).edit();
		editor.remove(key);
		editor.commit();
	}
	
	
	/**  
	  * @Title: clear  
	  * @Description: 数据清空 
	  * @param @param context
	  * @param @param name
	  * @param @param mode     
	  * @return void   
	  * @throws  
	  */ 
	public static void clear(Context context,String name,int mode){
		SharedPreferences.Editor editor = context.getSharedPreferences(name, mode).edit();
		editor.clear();
		editor.commit();
	}
	
}
