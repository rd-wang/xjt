package com.catsic.biz.yh.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.yh.adapter.QljcEditSwipeAdapter;
import com.catsic.biz.yh.bean.QlcxListBean;
import com.catsic.biz.yh.service.QljcEditListServer;
import com.catsic.core.AppContext;
import com.catsic.core.page.PageRequest;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.widget.pullview.AbPullToRefreshView;
import com.catsic.core.widget.pullview.AbPullToRefreshView.OnFooterLoadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Litao-pc on 2016/4/11.
 */
public class QljcEditListActivity extends FragmentActivity implements OnFooterLoadListener {
    public PageRequest<Map> pageRequest = new PageRequest<Map>();
    public QljcEditSwipeAdapter adapter;
    public TextView tv_msg;
    public AbPullToRefreshView mAbPullToRefreshView;
    public ListView mListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String centerTitle = "桥梁检测";
        ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
        setContentView(R.layout.common_fragment_ptrl);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        mAbPullToRefreshView = (AbPullToRefreshView) this
                .findViewById(R.id.mPullRefreshView);
        mListView = (ListView) this.findViewById(R.id.mListView);

// 设置监听器
        mAbPullToRefreshView.setOnFooterLoadListener(this);
        mAbPullToRefreshView.setPullRefreshEnable(false);

// 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        //初始化数据源从本地数据库读取
        adapter = new QljcEditSwipeAdapter(this,
                new ArrayList<QlcxListBean>());
        mListView.setAdapter(adapter);
        //加载初始数据
        Map<String, String> map = pageRequest.getFilters();
        if (map == null) {
            map = new HashMap();
        }
        try {
            // map.put("tbdwdm", StringUtil.Stringtrim0(AppContext.LOGINUSER.getString("tbdwdm")));
            map.put("sortColumns", "tbsj desc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pageRequest.setFilters(map);
        LogUtils.outString(AppContext.LOGINUSER.toString());
        ProgressDialogUtil.show(this, getResources()
                .getString(R.string.loading), true);
        new QljcEditListServer(this).list(pageRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_add:
                Intent intent = new Intent(this, QljcAddListActivity.class);
                intent.putExtra("title", "桥梁检测");
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        // 翻页
        pageRequest.setPageNumber(pageRequest.getPageNumber() + 1);
        new QljcEditListServer(this).list(pageRequest);
    }
}
