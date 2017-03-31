
package com.catsic.biz.js.service;

import android.content.Context;

import com.catsic.biz.js.activity.ZljcListActivity;
import com.catsic.biz.js.bean.TZljc;
import com.catsic.biz.js.bean.TZljcWccs;
import com.catsic.biz.js.bean.TZljcWccsSub;
import com.catsic.biz.js.bean.TZljcYsd;
import com.catsic.biz.js.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.AppUrls;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.tools.ToastUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @ClassName: ZljcService
  * @Description: 质量检测
  * @author catsic-wuxianling
  * @date 2015年8月14日 上午11:39:33
  */
public class ZljcService extends BaseService{

	private  ZljcListActivity activity;

	public ZljcService(Context context){
		super(context);
		activity = (ZljcListActivity) context;
	}

	/**
	  * @Title: saveOrUpdate
	  * @Description: 保存
	  * @param @param zljc    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void saveOrUpdate(TZljc zljc) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params", GsonUtils.toJson(zljc));
		SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLJC_URL), AppUrls.JSGL_ZLGL_ZLJC_NAMESPACE, "saveOrUpdate", paramMap, new SoapUtil.WebServiceCallBack(){
			
			@Override
			public void onSucced(String result) {
				ProgressDialogUtil.dismiss();
				if (result!=null && result.equals(AppConstants.STATE_200+"")) {
				}else{
					ToastUtil.showShortToast(context, "操作失败！");
				}
			}
			
			@Override
			public void onFailure(String result) {
				ProgressDialogUtil.dismiss();
				ToastUtil.showShortToast(context, "操作失败！");
			}
		});
	}
	
	/**
	  * @Title: delete
	  * @Description: 删除
	  * @param @param crowid    设定文件
	  * @return void    返回类型
	  * @throws
	 * @ Des position listview位置
	  */
	public void delete(String crowid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params", crowid);
		SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLJC_URL), AppUrls.JSGL_ZLGL_ZLJC_NAMESPACE, "delete", paramMap, new SoapUtil.WebServiceCallBack(){
			
			@Override
			public void onSucced(String result) {
				ProgressDialogUtil.dismiss();
				if (result!=null && result.equals(AppConstants.STATE_200+"")) {
					ToastUtil.showShortToast(context, "操作成功！");

				}else{
					ToastUtil.showShortToast(context, "操作失败！");
				}
			}
			
			@Override
			public void onFailure(String result) {
				ProgressDialogUtil.dismiss();
				ToastUtil.showShortToast(context, "操作失败！");
			}
		});
	}
	
	/**
	  * @Title: deleteAll
	  * @Description: 批量删除
	  * @param @param crowids    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void deleteAll(String crowids) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params", crowids);
		SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLJC_URL), AppUrls.JSGL_ZLGL_ZLJC_NAMESPACE, "deleteAll", paramMap, new SoapUtil.WebServiceCallBack(){

			@Override
			public void onSucced(String result) {
				ProgressDialogUtil.dismiss();
				if (result!=null && result.equals(AppConstants.STATE_200+"")) {
					ToastUtil.showShortToast(context, "操作成功！");
				}else{
					ToastUtil.showShortToast(context, "操作失败！");
				}
			}

			@Override
			public void onFailure(String result) {
				ProgressDialogUtil.dismiss();
				ToastUtil.showShortToast(context, "操作失败！");
			}
		});
	}

	/**
	 * @throws JSONException 
	  * @Title: operationSB
	  * @Description: 数据上报
	  * @param @param listItems
	  * @param @param selectID    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void operationSB(List<Map<String, Object>> listItems, int selectID) throws JSONException {
		ProgressDialogUtil.show(context,"正在提交，请稍后...",true);
		String crowid = listItems.get(selectID).get("crowid")+"";
		TZljc obj =  db.findById(crowid, TZljc.class);
		//save 
		String shbz = ShbzUtils.getShbzByOper(ShbzUtils.OPER_SB, AppContext.LOGINUSER.getString("orglevel").toString());
		
		obj.setShbz(shbz);
		
		if (obj!=null && obj.getJclb()!=null) {
			if (AppConstants.ZLJC_YSD.equals(obj.getJclb())) {
				List<TZljcYsd> list = db.findAllByWhere(TZljcYsd.class, " parentid = '"+crowid+"'");
				if (list!=null && list.size()>0) {
					obj.setTZljcYsd(list.get(0));
				}
			}else if (AppConstants.ZLJC_WCCS.equals(obj.getJclb())) {
				List<TZljcWccs> list = db.findAllByWhere(TZljcWccs.class," parentid = '"+crowid+"'");
				if (list!=null && list.size()>0) {
					TZljcWccs  zljcWcss = list.get(0);
					if (zljcWcss!=null && zljcWcss.getCrowid()!=null) {
						List<TZljcWccsSub> subList = db.findAllByWhere(TZljcWccsSub.class," parentid = '"+zljcWcss.getCrowid()+"'");
						zljcWcss.setZljcWccsSubs(subList);
						
						obj.setTZljcWccs(zljcWcss);
					}
				}
			}
		}
		
		try {
			//数据上传至服务端
			this.saveOrUpdate(obj);
			
			//update db
			db.update(obj);
			
			//update listview
			Map<String,Object> map = listItems.get(selectID);
			map.put("shbz", shbz);
			listItems.set(selectID, map);
		} catch (Exception e) {
			
		}
	}

	/**
	  * @Title: operationDel
	  * @Description: 删除
	  * @param @param listItems
	  * @param @param selectID    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void operationDel(List<Map<String, Object>> listItems, int selectID) {
		//delete
		String crowid = listItems.get(selectID).get("crowid")+"";
		try {
			//服务器删除
			this.delete(crowid);

			db.deleteById(TZljc.class, crowid);
			//listItems remove
			listItems.remove(selectID);
			activity.listViewAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	  * @Title: operationDel
	  * @Description: 批量删除
	  * @param @param listItems
	  * @param @param hasChecked    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void operationDel(List<Map<String, Object>> listItems,boolean[] hasChecked) {
		//crowid string
		StringBuffer sb = new StringBuffer();
		String [] items = null;
		for (int i = 0; i < hasChecked.length; i++) {
			if (hasChecked[i]) {
				sb.append(listItems.get(i).get("crowid")+",");
			}
		}
		
		try {
			//服务器删除
			this.deleteAll(sb.toString());
			
			//delete db
			if (sb.toString().indexOf(",")>0) {
				items = sb.toString().split(",");
				String inSql = StringUtil.getInSql(items);
				db.deleteByWhere(TZljc.class, "crowid in "+inSql);
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
