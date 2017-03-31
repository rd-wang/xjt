package com.catsic.biz.yh.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.catsic.biz.yh.bean.YhXcjl;
import com.catsic.biz.yh.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.AppUrls;
import com.catsic.core.activity.LxListActivity;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.RequestParamSerializable;
import com.catsic.core.bean.Tfile;
import com.catsic.core.page.Page;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.FileUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 养护巡查记录service
 * @author Administrator
 *
 */
public class YhXcjlService extends BaseService{
	
	private ResultHandler handler = new ResultHandler();
	
	public YhXcjlService(Context context){
		super(context);
//		this.context = context;
	}
	
	private class ResultHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			ProgressDialogUtil.dismiss();
			
			switch (msg.what) {
				case AppConstants.$LIST:
					String result = msg.getData().getString("result");
					if (result!=null) {
						LxListActivity activity = (LxListActivity) context;
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
						activity.mAbPullToRefreshView.onFooterLoadFinish();
					}
					break;
			}
		}
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
			db.deleteByWhere(YhXcjl.class, "crowid in "+inSql);
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
	
	/**  
	  * @Title: save  
	  * @Description: 路政巡查 
	  * @param @param     
	  * @return void   
	  * @throws  
	  */ 
	public void save(YhXcjl yhXcjl) {
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.LZGL_XCJL_URL),AppUrls.LZGL_XCJL_NAMESPACE,"save",null);
		   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   params.add(new RequestParamSerializable("jsonObj",new Gson().toJson(yhXcjl)));
	   requestInfo.setReqParamList(params);
		   
      new SingleResultThread(handler, requestInfo,AppConstants.$ADD).start();
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
		YhXcjl obj =  db.findById(crowid, YhXcjl.class);
		//save 
		String shbz = ShbzUtils.getShbzByOper(ShbzUtils.OPER_SB, AppContext.LOGINUSER.getString("orglevel").toString());
		
		obj.setShbz(shbz);
		
		//图片处理
		List<Tfile> files =  db.findAllByWhere(Tfile.class, "relationId = '"+obj.getCrowid()+"'");
		if(files.size()>0){
			for (Tfile tfile : files) {
				tfile.setFileId("");
				String filePath = tfile.getFilePath();
				String content = FileUtils.fileToBase64(filePath);
				tfile.setContent(content);
			}
			obj.setFiles(files);
		}
		new YhXcjlService(context).save(obj);
		
		//update db
		db.update(obj);
		
		//update listview
		Map<String,Object> map = listItems.get(selectID);
		map.put("shbz", shbz);
		listItems.set(selectID, map);
		
	}
	
	/**  
	  * @Title: findById  
	  * @Description: 明细 
	  * @param @param crowid     
	  * @return void   
	  * @throws  
	  */ 
	public void findById(String crowid){
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.LZGL_XCJL_URL),AppUrls.LZGL_XCJL_NAMESPACE,"findById",null);
		   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   params.add(new RequestParamSerializable("params",crowid));
	   requestInfo.setReqParamList(params);
		   
      new SingleResultThread(handler, requestInfo,AppConstants.$VIEW).start();
	}
	
	
}

