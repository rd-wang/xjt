package com.catsic.biz.yh.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.yh.activity.QljcAddListActivity;
import com.catsic.biz.yh.bean.QlAddCardBean;
import com.catsic.core.AppUrls;
import com.catsic.core.page.Page;
import com.catsic.core.page.PageRequest;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Litao-pc on 2016/4/11.
 */
@SuppressLint("ValidFragment")
public class QlcxQueryFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private EditText qljc_qlmc;
    private EditText qijc_qlbm;
    private EditText qljc_lxmc;
    private EditText qljc_lxbm;
    private BootstrapButton btn_js_zljc_ysd_save;
    private QljcAddListActivity activity;
    private PageRequest<Map> pageRequest;
    private String qlmc;
    private String qlbm;
    private String lxmc;
    private String lxbm;

    public QlcxQueryFragment() {

    }

    public QlcxQueryFragment(Context context) {
        this.context = context;
        activity = (QljcAddListActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_query_qljc, null);
        btn_js_zljc_ysd_save = (BootstrapButton) view.findViewById(R.id.btn_js_zljc_ysd_save);
        qljc_qlmc = (EditText) view.findViewById(R.id.qljc_qlmc);
        qijc_qlbm = (EditText) view.findViewById(R.id.qijc_qlbm);
        qljc_lxmc = (EditText) view.findViewById(R.id.qljc_lxmc);
        qljc_lxbm = (EditText) view.findViewById(R.id.qljc_lxbm);
        btn_js_zljc_ysd_save.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        pageRequest = new PageRequest<Map>();
        if (!getInputString()) {
            return;
        }
        ProgressDialogUtil.show(context, getResources()
                .getString(R.string.loading), true);
        Map map = pageRequest.getFilters();
        if (map == null) {
            map = new HashMap();
            pageRequest.setFilters(map);
        }
        map.put("qlmc", qlmc);
        map.put("qlbm", qlbm);
        map.put("lxmc", lxmc);
        map.put("lxbm", lxbm);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("params", GsonUtils.toJson(pageRequest));
        LogUtils.outString(GsonUtils.toJson(pageRequest));
        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, "listQlkp", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                Log.d("Tag", result + "onSucced");

                if (result != null) {
                    if (pageRequest.getPageNumber() == 1) {
                        activity.adapter.listItems.clear();
                    }
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
                    activity.pageRequest = pageRequest;
                }
            }


            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean getInputString() {
        qlmc = qljc_qlmc.getText().toString();
        qlbm = qijc_qlbm.getText().toString();
        lxmc = qljc_lxmc.getText().toString();
        lxbm = qljc_lxbm.getText().toString();
        if (TextUtils.isEmpty(qlmc) && TextUtils.isEmpty(qlbm) && TextUtils.isEmpty(lxmc) && TextUtils.isEmpty(lxbm)) {
            return false;
        }

        return true;


    }
}
