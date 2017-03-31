package com.catsic.core.tools;

import android.view.Menu;
import android.view.Window;

/**  
  * @Description: Menu 工具类 
  * @author wuxianling  
  * @date 2014年8月21日 下午3:39:03    
  */ 
public class MenuUtil {
	
	/**  
	  * @Title: setOverflowIconVisible  
	  * @Description: 设置Menu 显示icon 
	  * @param @param featureId
	  * @param @param menu     
	  * @return void   
	  * @throws  
	  */ 
	public static void setOverflowIconVisible(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					java.lang.reflect.Method m =  menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
				    m.setAccessible(true);
				    m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
