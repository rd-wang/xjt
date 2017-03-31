package com.catsic.biz.js.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.catsic.BuildConfig;
import com.catsic.biz.js.activity.ZlxcActivity;
import com.catsic.biz.js.activity.ZlxcListActivity;
import com.catsic.biz.js.activity.ZlxcRespActivity;
import com.catsic.biz.js.bean.TJsZlxc;
import com.catsic.core.AppUrls;
import com.catsic.core.page.Page;
import com.catsic.core.page.PageRequest;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author catsic-wuxianling
 * @ClassName: ZlxcService
 * @Description: 质量巡查
 * @date 2015年9月24日 下午3:19:59
 */
public class ZlxcService extends BaseService {

    private ZlxcListActivity activity;

    public ZlxcService(Context context) {
        super(context);
        if (context instanceof ZlxcListActivity) {
            activity = (ZlxcListActivity) context;
        }
    }

    /**
     * @param @param pageRequest
     * @return void
     * @throws
     * @Title: list
     * @Description: 列表查询
     */
    @SuppressWarnings("rawtypes")
    public void list(PageRequest pageRequest) {

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", GsonUtils.toJson(pageRequest));
        SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLXC_URL), AppUrls.JSGL_ZLGL_ZLXC_NAMESPACE, "list", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    Page page = new Gson().fromJson(result, Page.class);
                    for (int i = 0; i < page.getResult().size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        LinkedTreeMap obj = (LinkedTreeMap) page.getResult().get(i);
                        map.put("crowid", obj.get("crowid"));
                        map.put("xmid", obj.get("xmid"));
                        //项目信息
                        LinkedTreeMap xmjbxxMap = (LinkedTreeMap) obj.get("xmjbxx");
                        map.put("xmmc", xmjbxxMap.get("xmmc"));
                        map.put("xzqh", xmjbxxMap.get("xzqh"));
                        map.put("xmlx", xmjbxxMap.get("xmlx"));
                        map.put("xmlxdm", xmjbxxMap.get("xmlxdm"));
                        map.put("jhnf", xmjbxxMap.get("jhnf"));

                        map.put("xcsj", obj.get("xcsj"));
                        //质量巡查反馈
                        ArrayList zlxcList = (ArrayList) obj.get("zlxcRespSet");
                        map.put("zlxcRespSize", zlxcList == null ? 0 : zlxcList.size());
                        activity.adapter.listItems.add(map);
                    }
                    //无记录
                    if (activity.adapter.listItems == null || activity.adapter.listItems.size() == 0) {
                        activity.tv_msg.setVisibility(View.VISIBLE);
                    } else {
                        activity.tv_msg.setVisibility(View.INVISIBLE);
                    }

                    activity.adapter.notifyDataSetChanged();
                    activity.getSlidingMenu().showContent();
                } else {
                    ToastUtil.showShortToast(context, "加载失败！");
                }
                activity.mAbPullToRefreshView.onFooterLoadFinish();
            }

            @Override
            public void onFailure(String result) {
                activity.mAbPullToRefreshView.onFooterLoadFinish();
                ProgressDialogUtil.dismiss();
                ToastUtil.showShortToast(context, "加载失败");
            }
        });
    }

    /**
     * @param @param crowid    设定文件
     * @return void    返回类型
     * @throws
     * @Title: findById
     * @Description: 质量巡查
     */
    public void findById(String crowid, final String tag) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", crowid);
        SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLXC_URL), AppUrls.JSGL_ZLGL_ZLXC_NAMESPACE, "findById", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    if ("ZLXC".equals(tag)) {
                        ZlxcActivity activity = (ZlxcActivity) context;
                        activity.initData(result);
                    } else if ("ZLXCRESP".equals(tag)) {
                        ZlxcRespActivity activity = (ZlxcRespActivity) context;
                        activity.initZlxc(result);
                    }
                } else {
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

    /**
     * @param @param crowid    设定文件
     * @return void    返回类型
     * @throws
     * @Title: saveOrUpdate
     * @Description: 质量巡查
     */
    public void saveOrUpdate(TJsZlxc obj) {
        final TJsZlxc obj1 = obj;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", GsonUtils.toJson(obj));
        SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_ZLGL_ZLXC_URL), AppUrls.JSGL_ZLGL_ZLXC_NAMESPACE, "saveOrUpdate", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    if (BuildConfig.DEBUG) Log.d("ZlxcService", result);
                    ZlxcActivity activity = (ZlxcActivity) context;
                    //2.删除本地数据
                    db.deleteById(TJsZlxc.class, obj1.getCrowid());
                    //db.deleteByWhere(Tfile.class,"  relationId='"+obj1.getCrowid()+"'");

                    //3.跳转至列表页面
                    Intent intent = new Intent(context, ZlxcListActivity.class);
                    intent.putExtra("title", "质量巡查列表");
                    context.startActivity(intent);
                } else {
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
