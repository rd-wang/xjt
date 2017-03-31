package com.catsic.biz.yh.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.catsic.biz.query.activity.LsListActivity;
import com.catsic.biz.yh.activity.LSViewActivity;
import com.catsic.biz.yh.bean.LsJbxx;
import com.catsic.biz.yh.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.RequestParamSerializable;
import com.catsic.core.bean.Tfile;
import com.catsic.core.page.Page;
import com.catsic.core.page.PageRequest;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.FileUtils;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.tools.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**  
  * @Description: LsJbxxService 
  * @author wuxianling  
  * @date 2014年7月31日 下午12:01:54    
  */ 
public class LsJbxxService extends BaseService{
	
	private ResultHandler handler = new ResultHandler();
	
	public LsJbxxService(Context context){
		super(context);
	}
	
	/**  
	  * @Title: save  
	  * @Description: 路损填报 
	  * @param @param lsJbxx     
	  * @return void   
	  * @throws  
	  */ 
	public void save(LsJbxx lsJbxx) {
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.YHGL_LS_URL),AppUrls.YHGL_LS_NAMESPACE,"save",null);
		   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   params.add(new RequestParamSerializable("jsonObj",new Gson().toJson(lsJbxx)));
	   requestInfo.setReqParamList(params);
		   
       new SingleResultThread(handler, requestInfo,AppConstants.$ADD).start();
	}
	
	/**  
	  * @Title: findById  
	  * @Description: 明细 
	  * @param @param crowid     
	  * @return void   
	  * @throws  
	  */ 
	public void findById(String crowid){
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.YHGL_LS_URL),AppUrls.YHGL_LS_NAMESPACE,"findById",null);
		   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   params.add(new RequestParamSerializable("params",crowid));
	   requestInfo.setReqParamList(params);
		   
       new SingleResultThread(handler, requestInfo,AppConstants.$VIEW).start();
	}
	
	/**  
	  * @Title: list  
	  * @Description: 列表查询 
	  * @param @param pageRequest     
	  * @return void   
	  * @throws  
	  */ 
	public void list(PageRequest pageRequest) {
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.YHGL_LS_URL),AppUrls.YHGL_LS_NAMESPACE,"list",null);
		   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   
	   params.add(new RequestParamSerializable("params",GsonUtils.toJson(pageRequest)));
	   requestInfo.setReqParamList(params);
		   
      new SingleResultThread(handler, requestInfo,AppConstants.$LIST).start();
	}
	
	/**
	 * @throws JSONException   
	  * @Title: operationSB  
	  * @Description: 上报 
	  * @param @param selectID     
	  * @return void   
	  * @throws  
	  */ 
	public void operationSB(List<Map<String, Object>> listItems,int selectID) throws JSONException {
		String crowid = listItems.get(selectID).get("crowid")+"";
		LsJbxx obj =  db.findById(crowid, LsJbxx.class);
		//save 
		String shbz = ShbzUtils.getShbzByOper(ShbzUtils.OPER_SB, AppContext.LOGINUSER.getString("orglevel").toString());
		
		obj.setShbz(shbz);
		
		//图片处理
		List<Tfile> files =  db.findAllByWhere(Tfile.class, "relationId = '"+obj.getCrowid()+"'");
		for (Tfile tfile : files) {
			tfile.setFileId("");
			String filePath = tfile.getFilePath();
			String content = FileUtils.fileToBase64(filePath);
			tfile.setContent(content);
		}
		obj.setFiles(files);
		new LsJbxxService(context).save(obj);
		
		//update db
		db.update(obj);
		
		//update listview
		Map<String,Object> map = listItems.get(selectID);
		map.put("shbz", shbz);
		listItems.set(selectID, map);
		
	}
	
	/**  
	  * @Title: operationDel  
	  * @Description: 删除 
	  * @param @param selectID     
	  * @return void   
	  * @throws  
	  */ 
	public void operationDel(List<Map<String, Object>> listItems,int selectID){
		//delete
		String crowid = listItems.get(selectID).get("crowid")+"";
		db.deleteById(LsJbxx.class,crowid);
		
		//listItems remove
		listItems.remove(selectID);
	}
	
	/**  
	  * @Title: operationDel  
	  * @Description: 批量删除 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void operationDel(List<Map<String, Object>> listItems,boolean [] hasChecked){
		//crowid string
		StringBuffer sb = new StringBuffer();
		String [] items = null;
		for (int i = 0; i < hasChecked.length; i++) {
			if (hasChecked[i]) {
				sb.append(listItems.get(i).get("crowid")+",");
			}
		}
		//delete db
		if (sb.toString().indexOf(",")>0) {
			items = sb.toString().split(",");
			String inSql = StringUtil.getInSql(items);
			db.deleteByWhere(LsJbxx.class, "crowid in "+inSql);
		}
		
		//remove listItems
		if (items!=null && items.length>0) {
			for (int i = 0; i < items.length; i++) {
				for (int j = 0; j < listItems.size(); j++) {
					String crowid = "";
					if (listItems.get(j).get("crowid")!=null) {
						crowid = listItems.get(j).get("crowid")+"";
					}
					if (crowid.equals(items[i])) {
						listItems.remove(j);
						break;
					}
				}
			}
		}
		
	}
	
	
	private class ResultHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			ProgressDialogUtil.dismiss();
			
			String result = msg.getData().getString("result");
			switch (msg.what) {
				case AppConstants.$ADD:
					if (result!=null && result.equals(AppConstants.STATE_200+"")) {
						ToastUtil.showShortToast(context, "操作成功！");
					}else{
						ToastUtil.showShortToast(context, "操作失败！");
					}
					break;
				case AppConstants.$LIST:
					if (result!=null) {
						LsListActivity activity = (LsListActivity) context;
						Page page = new Gson().fromJson(result, Page.class);
						
						for(int i = 0; i < page.getResult().size(); i++) {   
				            LinkedTreeMap obj = (LinkedTreeMap) page.getResult().get(i);
							activity.adapter.listItems.add(obj);   
				        } 
						//无记录
						if (activity.adapter.listItems == null || activity.adapter.listItems.size() == 0) {
							activity.tv_msg.setVisibility(View.VISIBLE);
						}else{
							activity.tv_msg.setVisibility(View.INVISIBLE);
						}
						
						activity.adapter.notifyDataSetChanged();
						activity.getSlidingMenu().showContent();
					}
					break;
				case AppConstants.$VIEW:
					if (result!=null) {
						 LSViewActivity activity = (LSViewActivity) context;
						 LsJbxx obj = new Gson().fromJson(result, LsJbxx.class);
						 activity.initViewData(obj);
					}
					break;
			}
		}
		
	}

}
