package com.catsic.biz.yh.service;

import android.content.Context;
import android.view.View;

import com.catsic.biz.yh.activity.QljcAddListActivity;
import com.catsic.biz.yh.bean.QlAddCardBean;
import com.catsic.core.AppUrls;
import com.catsic.core.page.Page;
import com.catsic.core.page.PageRequest;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @des 桥梁添加列表 Created by Litao-pc on 2016/4/27.
 */
public class QljcAddListServer extends BaseService {
    public QljcAddListActivity activity;

    public QljcAddListServer(Context context) {
        super(context);
        activity = (QljcAddListActivity) context;

    }

    public void list(PageRequest pageRequest) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", GsonUtils.toJson(pageRequest));
        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, "listQlkp", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                LogUtils.outString(result);
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    Page page = new Gson().fromJson(result, Page.class);
                    for (int i = 0; i < page.getResult().size(); i++) {
                        LinkedTreeMap<String, String> o = (LinkedTreeMap) page.getResult().get(i);
                        Gson gson = new Gson();
                        String s = gson.toJson(o);
                        QlAddCardBean bean = new Gson().fromJson(s, QlAddCardBean.class);
                        activity.adapter.listItems.add(bean);
                    }
                    //无记录
                    if (activity.adapter.listItems == null || activity.adapter.listItems.size() == 0) {
                        activity.tv_msg.setVisibility(View.VISIBLE);
                    } else {
                        activity.tv_msg.setVisibility(View.INVISIBLE);
                    }
                    activity.adapter.notifyDataSetChanged();
                    activity.getSlidingMenu().showContent();
                }


            }

            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                if (activity.adapter.listItems.size() == 0) {
                    activity.tv_msg.setVisibility(View.VISIBLE);
                } else {
                    activity.tv_msg.setVisibility(View.INVISIBLE);
                }
                activity.getSlidingMenu().showContent();
            }
        });


    }

}
