
package com.catsic.biz.js.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;

import com.catsic.biz.js.activity.ZlxcListActivity;
import com.catsic.biz.js.activity.ZlxcRespActivity;
import com.catsic.biz.js.bean.TJsZlxcResp;
import com.catsic.core.AppUrls;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.ToastUtil;

/**
  * @ClassName: ZlxcRespService
  * @Description: 质量巡查反馈
  * @author catsic-wuxianling
  * @date 2015年9月28日 下午5:33:08
  */
public class ZlxcRespService extends BaseService{
	
	public ZlxcRespService(Context context){
		super(context);
	}
	
	/**
	 * @Title: saveOrUpdate
	 * @Description: 质量巡查反馈
	 * @param @param crowid    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void saveOrUpdate(TJsZlxcResp obj) {
		final TJsZlxcResp obj1 = obj;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params",GsonUtils.toJson(obj));
		SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLXCRESP_URL), AppUrls.JSGL_ZLGL_ZLXCRESP_NAMESPACE, "saveOrUpdate", paramMap, new SoapUtil.WebServiceCallBack() {
			@Override
			public void onSucced(String result) {
				ProgressDialogUtil.dismiss();
				if(result != null){
					ZlxcRespActivity activity = (ZlxcRespActivity) context;
					//2.删除本地数据
					db.deleteById(TJsZlxcResp.class,obj1.getCrowid());
					//db.deleteByWhere(Tfile.class,"  relationId='"+obj1.getCrowid()+"'");
					
					//3.跳转至列表页面
					Intent intent = new Intent(context,ZlxcListActivity.class);
					intent.putExtra("title", "质量巡查列表");
					context.startActivity(intent);
				}else{	
					ToastUtil.showShortToast(context, "加载失败！");
				}
			}
			@Override
			public void onFailure(String result) {
				ProgressDialogUtil.dismiss();
				ToastUtil.showShortToast(context, "加载失败");
			}
		});
	}

}
