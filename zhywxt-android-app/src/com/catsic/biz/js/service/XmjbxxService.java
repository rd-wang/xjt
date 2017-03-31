package com.catsic.biz.js.service;

import android.content.Context;
import android.view.View;

import com.catsic.biz.common.activity.SelXmjbxxListActivity;
import com.catsic.biz.js.activity.XmjbxxListActivity;
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
import java.util.List;
import java.util.Map;

/**
 * @author wuxianling
 * @Description: 项目信息
 * @date 2014年9月15日 下午5:08:42
 */
public class XmjbxxService extends BaseService {

    public XmjbxxService(Context context) {
        super(context);
    }

    /**
     * @param @param pageRequest
     * @return void
     * @throws
     * @Title: list
     * @Description: 列表查询
     */
    public void list(PageRequest pageRequest) {


        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", GsonUtils.toJson(pageRequest));
        SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_XMXX_URL), AppUrls.JSGL_XMXX_NAMESPACE, "getXmjbxxList", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

                    Page page = new Gson().fromJson(result, Page.class);
                    for (int i = 0; i < page.getResult().size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        LinkedTreeMap obj = (LinkedTreeMap) page.getResult().get(i);
                        map.put("xmid", obj.get("xmid"));
                        map.put("xmmc", obj.get("xmmc"));
                        map.put("xzqh", obj.get("xzqh"));
                        map.put("xmlx", obj.get("xmlx"));
                        map.put("xmlxdm", obj.get("xmlxdm"));
                        map.put("jhnf", obj.get("jhnf"));
                        map.put("lxbm", obj.get("lxbm"));
                        map.put("qlbm", obj.get("qlbm"));
                        listItems.add(map);
                    }
                    if (context instanceof XmjbxxListActivity) {
                        XmjbxxListActivity activity = (XmjbxxListActivity) context;
                        activity.adapter.listItems.addAll(listItems);
                        //无记录
                        if (activity.adapter.listItems == null || activity.adapter.listItems.size() == 0) {
                            activity.tv_msg.setVisibility(View.VISIBLE);
                        } else {
                            activity.tv_msg.setVisibility(View.INVISIBLE);
                        }
                        activity.finishLoad();
                        activity.adapter.notifyDataSetChanged();
                        activity.getSlidingMenu().showContent();
                    } else if (context instanceof SelXmjbxxListActivity) {
                        SelXmjbxxListActivity activity = (SelXmjbxxListActivity) context;
                        activity.adapter.listItems.addAll(listItems);
                        //无记录
                        if (activity.adapter.listItems == null || activity.adapter.listItems.size() == 0) {
                            activity.tv_msg.setVisibility(View.VISIBLE);
                        } else {
                            activity.tv_msg.setVisibility(View.INVISIBLE);
                        }
                        activity.finishLoad();
                        activity.adapter.notifyDataSetChanged();
                        activity.getSlidingMenu().showContent();
                    }
                } else {
                    ToastUtil.showShortToast(context, "context 不能识别！");
                }
            }

            @Override
            public void onFailure(String result) {
                if (context instanceof XmjbxxListActivity) {
                    XmjbxxListActivity activity = (XmjbxxListActivity) context;
                    activity.finishLoad();
                } else if (context instanceof SelXmjbxxListActivity) {
                    SelXmjbxxListActivity activity = (SelXmjbxxListActivity) context;
                    activity.finishLoad();
                }

                ProgressDialogUtil.dismiss();
                ToastUtil.showShortToast(context, "加载失败");
            }
        });
    }


}
