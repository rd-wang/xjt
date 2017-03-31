package com.catsic.core.service;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.T_XZQH;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.ProgressDialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**  
  * @Description: 行政区划 
  * @author wuxianling  
  * @date 2014年9月18日 下午3:26:26    
  */ 
public class XzqhService extends BaseService{
	
	private ResultHandler handler = new ResultHandler();
	
	public XzqhService(Context context){
		super(context);
	}
	
	/**  
	  * @Title: list  
	  * @Description: 离线数据 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void list(){
	   
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.COMMON_XZQH_URL),AppUrls.COMMON_XZQH_NAMESPACE,"list",null);
	   
	   new SingleResultThread(handler, requestInfo,AppConstants.STATE_200).start();
	}
	
	/**  
	  * @Title: translate  
	  * @Description: 行政区划翻译 
	  * @param @param xzqhdm
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public String translate(String xzqhdm){
		T_XZQH xzqh = findByDm(xzqhdm);
		if (xzqh!=null) {
			return xzqh.getXzqhmc();
		}
		return "";
	}
	
	/**  
	  * @Title: findByDm  
	  * @Description: 查找行政区划 
	  * @param @param xzqhdm
	  * @param @return     
	  * @return T_XZQH   
	  * @throws  
	  */ 
	public T_XZQH findByDm(String xzqhdm){
		List<T_XZQH> list =  db.findAllByWhere(T_XZQH.class, " xzqhdm = '"+xzqhdm+"'");
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**  
	  * @Title: fill  
	  * @Description: 填充行政区划 
	  * @param @param context
	  * @param @param topSpinner
	  * @param @param subSpinner     
	  * @return void   
	  * @throws  
	  */ 
	public void fill(final Context context,Spinner topSpinner, Spinner subSpinner) {
		
		final T_XZQH obj = new T_XZQH();
		obj.setXzqhmc("全部");
		
		try {
			final String orglevel = AppContext.LOGINUSER.getString("orglevel");
			List<T_XZQH> topXzqhList =  db.findAllByWhere(T_XZQH.class, " xzqhdm = '"+AppContext.LOGINUSER.getString("xzqh")+"'");
			List<T_XZQH> subXzqhList = null;
			
			if (AppConstants.S.equals(orglevel)) {
				List<T_XZQH> list =  db.findAllByWhere(T_XZQH.class, " sjdm = '"+AppContext.LOGINUSER.getString("xzqh")+"'");
				topXzqhList.addAll(list);
				
			}else if (AppConstants.DS.equals(orglevel)) {
				subXzqhList = db.findAllByWhere(T_XZQH.class, " sjdm = '"+AppContext.LOGINUSER.getString("xzqh")+"'");
				subXzqhList.add(0, obj);
			}else if (AppConstants.X.equals(orglevel)) {
				subSpinner.setVisibility(View.INVISIBLE);
			}
			
			 ArrayAdapter<T_XZQH> topAdapter = new ArrayAdapter<T_XZQH>(context, android.R.layout.simple_spinner_dropdown_item,topXzqhList);
			 topSpinner.setAdapter(topAdapter);
			 
			 if (AppConstants.DS.equals(orglevel)) {
				 ArrayAdapter<T_XZQH> subAdapter = new ArrayAdapter<T_XZQH>(context, android.R.layout.simple_spinner_dropdown_item,subXzqhList);
				 subSpinner.setAdapter(subAdapter);
			 }
			 
			 final Spinner tmpTopSpinnner = topSpinner;
			 final Spinner tmpSubSpinnner = subSpinner;
			 
			 topSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
					
					if (AppConstants.S.equals(orglevel)) {
						T_XZQH xzqh =  (T_XZQH) tmpTopSpinnner.getSelectedItem();
						List<T_XZQH> list = new ArrayList<T_XZQH>();
						
						if (tmpTopSpinnner.getSelectedItemPosition() > 0) {
							list = db.findAllByWhere(T_XZQH.class, " sjdm = '"+xzqh.getXzqhdm()+"'");
						}
						list.add(0,obj);
						ArrayAdapter<T_XZQH> subAdapter = new ArrayAdapter<T_XZQH>(context, android.R.layout.simple_spinner_dropdown_item,list);
						tmpSubSpinnner.setAdapter(subAdapter);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
			 
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	/**  
	  * @Title: getXzqh  
	  * @Description: 从级联行政区划中 获取行政区划代码 
	  * @param @param orglevel
	  * @param @param topSpinner
	  * @param @param subSpinner
	  * @param @return
	  * @param @throws JSONException     
	  * @return String   
	  * @throws  
	  */ 
	public String getXzqh(String orglevel,Spinner topSpinner, Spinner subSpinner) throws JSONException {
		T_XZQH topXzqh = (T_XZQH) topSpinner.getSelectedItem();
		T_XZQH subXzqh = (T_XZQH) subSpinner.getSelectedItem();
		String xzqh = "";
		if (AppConstants.S.equals(orglevel) || AppConstants.DS.equals(orglevel)) {
			if (subXzqh !=null && subXzqh.getXzqhdm()!=null) {
				 xzqh = subXzqh.getXzqhdm();
			}else if (subXzqh == null || subXzqh.getXzqhdm() == null ||subXzqh.getXzqhdm().equals("")) {
				xzqh = AppContext.LOGINUSER.getString("xzqh");
			}
		}else if (AppConstants.X.equals(orglevel)) {
			xzqh = topXzqh.getXzqhdm();
		}
		return xzqh;
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
					 List<T_XZQH> list =  new Gson().fromJson(result, new TypeToken<List<T_XZQH>>(){}.getType());  
					 FinalDb db = FinalDb.create(context, AppConstants.DB_NAME);
					 
					 for (T_XZQH xzqh : list) {
					     db.save(xzqh);
					 }
			         break;
			  }
	    }
	    
	}

}
