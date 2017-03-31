package com.catsic.biz.js.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuInflater;
import com.catsic.BuildConfig;
import com.catsic.R;
import com.catsic.biz.js.adapter.XmjbxxListViewAdapter;
import com.catsic.biz.js.fragment.XmjbxxQueryFragment;
import com.catsic.biz.js.service.XmjbxxService;
import com.catsic.biz.main.activity.MainActivity;
import com.catsic.core.AppContext;
import com.catsic.core.activity.base.BaseSlidingFragmentPageActivity;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.widget.pullview.AbPullToRefreshView;
import com.catsic.core.widget.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxianling
 * @Description: 项目信息列表
 * @date 2014年9月15日 上午10:51:40
 */
public class XmjbxxListActivity extends BaseSlidingFragmentPageActivity
        implements OnItemClickListener, OnFooterLoadListener {

    public XmjbxxListViewAdapter adapter;
    public XmjbxxQueryFragment xmjbxxQueryFragment;

    public AbPullToRefreshView mAbPullToRefreshView;
    public ListView mListView;

    public XmjbxxListActivity() {
        super(R.string.XmjbxxListActivityTitle, R.id.fragment_ptr_list,
                R.layout.frame_common);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String centerTitle = bundle.getString("title");
        ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
        setContentView(R.layout.common_fragment_ptrl);

        tv_msg = (TextView) findViewById(R.id.tv_msg);

        // 初始化查询Fragment
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (xmjbxxQueryFragment == null) {
            xmjbxxQueryFragment = new XmjbxxQueryFragment(this);
        }
        t.replace(R.id.frame_common, xmjbxxQueryFragment);
        t.commit();

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        mAbPullToRefreshView = (AbPullToRefreshView) this
                .findViewById(R.id.mPullRefreshView);
        mListView = (ListView) this.findViewById(R.id.mListView);

        mListView.setOnItemClickListener(this);

// 设置监听器
        mAbPullToRefreshView.setPullRefreshEnable(false);

        mAbPullToRefreshView.setOnFooterLoadListener(this);

// 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        // 来源
        String tag = "";
        if (bundle != null && bundle.containsKey("tag")) {
            tag = bundle.getString("tag");
        }
        adapter = new XmjbxxListViewAdapter(this,
                new ArrayList<Map<String, Object>>(), tag);


        mListView.setAdapter(adapter);


        // 加载初始数据
        Map<String, String> map = pageRequest.getFilters();
        if (map == null) {
            map = new HashMap();
        }
        try {
            map.put("xzqh", StringUtil.Stringtrim0(AppContext.LOGINUSER
                    .getString("xzqh")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageRequest.setFilters(map);

        ProgressDialogUtil.show(this, getResources()
                .getString(R.string.loading), true);
        new XmjbxxService(this).list(pageRequest);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(this, JsjcLocationActivity.class);
        Map<String, Object> map = adapter.listItems.get(position );
        String xmlxmc = "";
        if (!ObjectUtils.isNullOrEmptyString(map.get("xmlxdm"))) {
            xmlxmc = new CatsicCodeService(this).translate("tc_xmlx",
                    map.get("xmlxdm").toString());
        }
        intent.putExtra("xmlx", StringUtil.toString(map.get("xmlx")));
        intent.putExtra("lxbm", StringUtil.toString(map.get("lxbm")));
        intent.putExtra("qlbm", StringUtil.toString(map.get("qlbm")));
        intent.putExtra("titleStr", StringUtil.toString(map.get("jhnf")) + ","
                + StringUtil.toString(map.get("xmmc")) + "," + xmlxmc);
        if (BuildConfig.DEBUG) Log.d(getClass().getSimpleName(),
                "xmlx=" + StringUtil.toString(map.get("xmlx")) + "  lxbm="
                        + StringUtil.toString(map.get("lxbm")) + "   qlbm="
                        + StringUtil.toString(map.get("qlbm")) + "   titleStr="
                        + StringUtil.toString(map.get("jhnf")) + ","
                        + StringUtil.toString(map.get("xmmc")) + "," + xmlxmc);
        startActivity(intent);
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
        // 翻页
        pageRequest.setPageNumber(pageRequest.getPageNumber() + 1);
        new XmjbxxService(this).list(pageRequest);
    }


    public void finishLoad() {
        mAbPullToRefreshView.onFooterLoadFinish();
        mAbPullToRefreshView.onHeaderRefreshFinish();
    }
}
