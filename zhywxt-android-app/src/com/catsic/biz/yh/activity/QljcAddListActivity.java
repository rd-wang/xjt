package com.catsic.biz.yh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuInflater;
import com.catsic.R;
import com.catsic.biz.main.activity.MainActivity;
import com.catsic.biz.yh.adapter.QljcAddListAdapter;
import com.catsic.biz.yh.bean.QlAddCardBean;
import com.catsic.biz.yh.fragment.QlcxQueryFragment;
import com.catsic.biz.yh.service.QljcAddListServer;
import com.catsic.core.activity.base.BaseSlidingFragmentPageActivity;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.widget.pullview.AbPullToRefreshView;
import com.catsic.core.widget.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 桥梁添加界面
 */
public class QljcAddListActivity extends BaseSlidingFragmentPageActivity implements OnFooterLoadListener, AdapterView.OnItemClickListener {
    public static QljcAddListActivity qljcAddListActivity;
    public QlcxQueryFragment qlcxQueryFragment;
    public AbPullToRefreshView mAbPullToRefreshView;
    public ListView mListView;
    public QljcAddListAdapter adapter;
    public QljcAddListActivity() {
        super(R.string.QljclbListViewtitle, R.id.fragment_ptr_list,
                R.layout.frame_common);
        qljcAddListActivity=this;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("title")) {
            String centerTitle = bundle.getString("title");
            ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
        }
        setContentView(R.layout.common_fragment_ptrl);
        tv_msg = (TextView) findViewById(R.id.tv_msg);

        // 初始化查询Fragment
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (qlcxQueryFragment == null) {
            qlcxQueryFragment = new QlcxQueryFragment(this);
        }
        t.replace(R.id.frame_common, qlcxQueryFragment);
        t.commit();


        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
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

        //设置adapter
        adapter = new QljcAddListAdapter(this,
                new ArrayList<QlAddCardBean>());
        mListView.setAdapter(adapter);

        //加载初始数据
        Map<String, String> map = pageRequest.getFilters();
        if (map == null) {
            map = new HashMap();
        }
        try {
            map.put("sortColumns", "tbsj desc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pageRequest.setFilters(map);
        ProgressDialogUtil.show(this, getResources()
                .getString(R.string.loading), true);

        new QljcAddListServer(QljcAddListActivity.this).list(pageRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }



    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.common_query, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(
            com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SlidingMenu slidingMenu = getSlidingMenu();
                if (slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                    // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
                    // slidingMenu.showMenu();
                    Intent intent = new Intent(this, MainActivity.class);
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null && bundle.containsKey("tag")) {
                        intent.putExtra("tag", bundle.getString("tag"));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                break;
            case R.id.action_query:
                // SldingMenu自动判断当前是打开还是关闭(toggle())
                toggle();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        pageRequest.setPageNumber(pageRequest.getPageNumber() + 1);
        new QljcAddListServer(this).list(pageRequest);
    }
}
