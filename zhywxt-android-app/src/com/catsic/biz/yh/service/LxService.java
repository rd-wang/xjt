package com.catsic.biz.yh.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SimpleAdapter;

import com.catsic.R;
import com.catsic.biz.yh.activity.LSActivity;
import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.activity.LxListActivity;
import com.catsic.core.bean.RequestInfo;
import com.catsic.core.bean.RequestParamSerializable;
import com.catsic.core.page.Page;
import com.catsic.core.page.PageRequest;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.thread.SingleResultThread;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.tools.ToastUtil;
import com.catsic.core.widget.pullview.AbPullToRefreshView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
  * @Description: 路线
  * @author wuxianling  
  * @date 2014年8月21日 上午10:53:13    
 * 
  */ 
public class LxService extends BaseService{
	// TODO: 2016/5/5  
	private ResultHandler handler = new ResultHandler();
	private AbPullToRefreshView mAbPullToRefreshView;
	public LxService(Context context, AbPullToRefreshView mAbPullToRefreshView){
		this.context = context;
		this.mAbPullToRefreshView=mAbPullToRefreshView;
	}
	
	/**  
	  * @Title: getLxs  
	  * @Description: 路线列表 
	  * @param @param tbdw4Query
	  * @return void
	  * @throws  
	  */ 
	public void getLxs(PageRequest pageRequest){
	   ProgressDialogUtil.show(context, "正在加载...", false);
		/**
		 * 路线列表 服务器：AppUrls.getServiceURL(AppUrls.JCXX_LX_URL)
		 * 命名空间：AppUrls.JCXX_LX_NAMESPACE
		 */
		// TODO: 2016/5/9 现测试接口
	   RequestInfo requestInfo = new RequestInfo(AppUrls.getServiceURL(AppUrls.JCXX_LX_URL),AppUrls.JCXX_LX_NAMESPACE,"getLxs",null);
		   
	   ArrayList<RequestParamSerializable> params = new ArrayList<RequestParamSerializable>();
	   params.add(new RequestParamSerializable("params",GsonUtils.toJson(pageRequest)));
	   requestInfo.setReqParamList(params);
	   new SingleResultThread(handler, requestInfo,AppConstants.$LIST).start();
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
						activity.switchContent();
					}
					break;
			}
		}

		/**  
		  * @Title: lxDialog  
		  * @Description: 路线弹出框 
		  * @param      
		  * @return void   
		  * @throws  
		  */ 
		private void lxDialog(JSONArray jsonArray) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setIcon(android.R.drawable.btn_plus);
			builder.setTitle("路线选择");
			
			final List<Map<String,Object>> list = getData(jsonArray);
			SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.common_dialog_lx, new String[]{"lxmc","lxbm"}, new int []{R.id.tv_lxmc,R.id.tv_lxbm});
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LSActivity activity = (LSActivity) context;
					activity.lxmc = list.get(which).get("lxmc").toString().replace("(","").replace(")", "");
					activity.lxbm = list.get(which).get("lxbm").toString().replace("(","").replace(")", "");
					activity.lxjgxzqh = StringUtil.toString(list.get(which).get("lxjgxzqh"));
					activity.et_shlx.setText(activity.lxmc+activity.lxbm);
				}
			}).show();
			
		}
		
		/**  
		  * @Title: getData  
		  * @Description: 路线数据构造 
		  * @param @param jsonArray
		  * @param @return     
		  * @return List<Map<String,Object>>   
		  * @throws  
		  */ 
		public List<Map<String,Object>> getData(JSONArray jsonArray){
			 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			 
			 for (int i = 0; i < jsonArray.length(); i++) {
				 Map<String, Object> map = new HashMap<String, Object>();
				try {
					JSONObject obj = (JSONObject) jsonArray.get(i);
					map.put("crowid", obj.get("crowid"));
					map.put("lxbm", "("+obj.get("lxbm")+")");
					map.put("lxmc", obj.get("lxmc"));
					map.put("lxjgxzqh", obj.get("lxjgxzqh"));
					list.add(map);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			 }
			return list;
		}
	}
	public void list(PageRequest pageRequest) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params", GsonUtils.toJson(pageRequest));
		SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JCXX_LX_URL), AppUrls.JCXX_LX_NAMESPACE, "getLxs", paramMap, new SoapUtil.WebServiceCallBack() {
			@Override
			public void onSucced(String result) {
				ProgressDialogUtil.dismiss();
				LogUtils.outString(result+"success");
				if (result != null && result.equals(AppConstants.STATE_200 + "")) {
					ToastUtil.showShortToast(context, "操作成功！");
				} else {
					ToastUtil.showShortToast(context, "操作失败！");
				}
			}

			@Override
			public void onFailure(String result) {
				ProgressDialogUtil.dismiss();
				ToastUtil.showShortToast(context, "操作失败！");
			}
		});
	}}
