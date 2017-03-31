package com.catsic.biz.yh.service;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.catsic.biz.yh.activity.QljcEditListActivity;
import com.catsic.biz.yh.bean.QlcxListBean;
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
 * @param 桥梁编辑列表 Created by Litao-pc on 2016/4/27.
 */
public class QljcEditListServer extends BaseService {
    private QljcEditListActivity activity;

    public QljcEditListServer(Context context) {
        super(context);
        activity = (QljcEditListActivity) context;
    }

    public void list(PageRequest pageRequest) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", GsonUtils.toJson(pageRequest));
        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, "list", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    LogUtils.outString(result);
                    Page page = new Gson().fromJson(result, Page.class);
                    for (int i = 0; i < page.getResult().size(); i++) {
                        LinkedTreeMap<String, String> o = (LinkedTreeMap) page.getResult().get(i);
                        Gson gson = new Gson();
                        String s = gson.toJson(o);
                        QlcxListBean bean = new Gson().fromJson(s, QlcxListBean.class);
                        String str = "";
                        for (int x = 0; x < bean.getQljcmxSet().size(); x++) {

                            if (x == bean.getQljcmxSet().size() - 1) {
                                str += bean.getQljcmxSet().get(x).getQslx() + "," + bean.getQljcmxSet().get(x).getQsfw();
                            } else {
                                str += bean.getQljcmxSet().get(x).getQslx() + "," + bean.getQljcmxSet().get(x).getQsfw() + ",";
                            }
                        }
                        bean.setXmmsInfo(str);
                        activity.adapter.listItems.add(bean);
                    }
                    //无记录
                    if (activity.adapter.listItems == null || activity.adapter.listItems.size() == 0) {
                        activity.tv_msg.setVisibility(View.VISIBLE);
                    } else {
                        activity.tv_msg.setVisibility(View.INVISIBLE);
                    }
                    activity.adapter.notifyDataSetChanged();
                    activity.mAbPullToRefreshView.onFooterLoadFinish();
                }
            }

            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                Log.d("Tag", result + "onFailure");
                if (activity.adapter.listItems.size()==0) {
                    activity.tv_msg.setVisibility(View.VISIBLE);
                } else {
                    activity.tv_msg.setVisibility(View.INVISIBLE);
                }
                activity.mAbPullToRefreshView.onFooterLoadFinish();
            }
        });


    }

}
