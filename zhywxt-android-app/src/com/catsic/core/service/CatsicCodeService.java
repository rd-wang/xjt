package com.catsic.core.service;

import java.util.List;
import java.util.UUID;

import net.tsz.afinal.FinalDb;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.CatsicCode;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.ProgressDialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**  
  * @Description: 数据字典 
  * @author wuxianling  
  * @date 2014年9月18日 下午2:07:56    
  */ 
public class CatsicCodeService  extends BaseService{
	
	private ResultHandler handler = new ResultHandler();
	
	public CatsicCodeService(Context context){
		super(context);
	}
	
	/**  
	  * @Title: loadCatsicCodes  
	  * @Description: 离线数据加载 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void loadCatsicCodes(){
	   
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.COMMON_CODES_URL),AppUrls.COMMON_CODES_NAMESPACE,"list",null);
	   
	   new SingleResultThread(handler, requestInfo,AppConstants.STATE_200).start();
	}
	
	/**  
	  * @Title: translate  
	  * @Description: 字典翻译 
	  * @param @param cname
	  * @param @param xxdm
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public String translate(String cname,String xxdm){
		CatsicCode code = findCodeItem(cname, xxdm);
		if (code!=null) {
			return code.getXxdmhy();
		}
		return "";
	}
	
	/**  
	  * @Title: findCodeItem  
	  * @Description: 查找字典项 
	  * @param @param cname
	  * @param @param xxdm
	  * @param @return     
	  * @return CatsicCode   
	  * @throws  
	  */ 
	public CatsicCode findCodeItem(String cname,String xxdm){
		List<CatsicCode> list =  db.findAllByWhere(CatsicCode.class, "cname = '"+cname+"' and xxdm = '"+xxdm+"'");

		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**  
	  * @Title: findCode  
	  * @Description: 查找字典 
	  * @param @param cname
	  * @param @return     
	  * @return List<CatsicCode>   
	  * @throws  
	  */ 
	public List<CatsicCode> findCode(String cname){
		return  db.findAllByWhere(CatsicCode.class, "cname = '"+cname+"'");
	}
	
	/**  
	  * @Title: fill  
	  * @Description: 填充Spinner 
	  * @param @param context
	  * @param @param spinner
	  * @param @param dic
	  * @param @param defaultVal     
	  * @return void   
	  * @throws  
	  */ 
	public void fill(Context context,Spinner spinner,String dic,String defaultVal){
		 List<CatsicCode> jhnfList =  db.findAllByWhere(CatsicCode.class, " cname = '"+dic+"'");
		 //default 
		 CatsicCode code = new CatsicCode();
		 code.setXxdmhy(defaultVal);
		 jhnfList.add(0, code);
		 
		 ArrayAdapter<CatsicCode> adapter = new ArrayAdapter<CatsicCode>(context, android.R.layout.simple_spinner_dropdown_item,jhnfList);
		 spinner.setAdapter(adapter);
	}
	
	/**  
	  * @Title: fill  
	  * @Description: 填充Spinner  
	  * @param @param context
	  * @param @param spinner
	  * @param @param dic
	  * @param @param orderBy
	  * @param @param defaultVal     
	  * @return void   
	  * @throws  
	  */ 
	public void fill(Context context,Spinner spinner,String dic,String orderBy,String defaultVal){
		 List<CatsicCode> jhnfList =  db.findAllByWhere(CatsicCode.class, " cname = '"+dic+"'",orderBy);
		 //default 
		 CatsicCode code = new CatsicCode();
		 code.setXxdmhy(defaultVal);
		 jhnfList.add(0, code);
		 
		 ArrayAdapter<CatsicCode> adapter = new ArrayAdapter<CatsicCode>(context, android.R.layout.simple_spinner_dropdown_item,jhnfList);
		 spinner.setAdapter(adapter);
	}
	
	private class ResultHandler extends Handler{

	    @Override
	    public void handleMessage(Message message)
	    {
		      ProgressDialogUtil.dismiss();
		      switch (message.what)
		      {
			      case AppConstants.STATE_200:
			         String result = message.getData().getString("result");
					 List<CatsicCode> list =  new Gson().fromJson(result, new TypeToken<List<CatsicCode>>(){}.getType());  
					 FinalDb db = FinalDb.create(context, AppConstants.DB_NAME);
					 
					 for (CatsicCode catsicCode : list) {
						 catsicCode.setCrowid(UUID.randomUUID().toString());
					     db.save(catsicCode);
					 }
			         break;
			  }
	    }
	    
	}
}
