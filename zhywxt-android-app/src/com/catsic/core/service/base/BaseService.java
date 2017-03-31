package com.catsic.core.service.base;

import net.tsz.afinal.FinalDb;
import android.content.Context;

import com.catsic.core.AppConstants;

/**  
  * @Description: BaseService 
  * @author wuxianling  
  * @date 2014年9月19日 上午10:57:00    
  */ 
public class BaseService {
	
	protected Context context;
	
	protected FinalDb db;
	
	public BaseService(){}
	
	public BaseService(Context context){
		this.context = context;
		db = FinalDb.create(context,AppConstants.DB_NAME);
	}
	
}
