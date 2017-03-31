
package com.catsic.biz.js.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuInflater;
import com.catsic.R;
import com.catsic.biz.js.adapter.ZlxcListViewAdapter;
import com.catsic.biz.js.fragment.ZlxcQueryFragment;
import com.catsic.biz.js.service.ZlxcService;
import com.catsic.biz.main.activity.MainActivity;
import com.catsic.core.AppContext;
import com.catsic.core.activity.base.BaseSlidingFragmentPageActivity;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.widget.pullview.AbPullToRefreshView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author catsic-wuxianling
 * @ClassName: ZlxcListActivity
 * @Description: 质量巡查列表
 * @date 2015年9月24日 下午2:50:34
 */
public class ZlxcListActivity extends BaseSlidingFragmentPageActivity  implements  AbPullToRefreshView.OnFooterLoadListener {

    public ZlxcListViewAdapter adapter;
    public AbPullToRefreshView mAbPullToRefreshView;
    public ListView mListView;
    public ZlxcQueryFragment queryFragment;

    public ZlxcListActivity() {
        super(R.string.ZlxcListActivityTitle, R.id.fragment_ptr_list, R.layout.frame_common);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarManager.initBackTitle(this, getActionBar(), "质量巡查");
        setContentView(R.layout.common_fragment_ptrl);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        //初始化查询Fragment
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (queryFragment == null) {
            queryFragment = new ZlxcQueryFragment(this);
        }
        t.replace(R.id.frame_common, queryFragment);
        t.commit();

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mAbPullToRefreshView = (AbPullToRefreshView) this
                .findViewById(R.id.mPullRefreshView);
        mListView = (ListView) this.findViewById(R.id.mListView);

// 设置监听器
        mAbPullToRefreshView.setPullRefreshEnable(false);
        mAbPullToRefreshView.setOnFooterLoadListener(this);

// 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

        adapter = new ZlxcListViewAdapter(this, new ArrayList<Map<String, Object>>());
        mListView.setAdapter(adapter);

        //加载初始数据
        Map<String, String> map = pageRequest.getFilters();
        if (map == null) {
            map = new HashMap();
        }
        try {
            map.put("xzqh", StringUtil.Stringtrim0(AppContext.LOGINUSER.getString("xzqh")));
            map.put("sortColumns", "tbsj desc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageRequest.setFilters(map);

        ProgressDialogUtil.show(this, getResources().getString(R.string.loading), true);
        new ZlxcService(this).list(pageRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.common_addorquery_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SlidingMenu slidingMenu = getSlidingMenu();
                if (slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
//					getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
                    //slidingMenu.showMenu();
                    Intent intent = new Intent(this, MainActivity.class);
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null && bundle.containsKey("tag")) {
                        intent.putExtra("tag", bundle.getString("tag"));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.action_query:
                //SldingMenu自动判断当前是打开还是关闭(toggle())
                toggle();
                break;
            case R.id.action_add:
                startActivity(new Intent(this, ZlxcActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        pageRequest.setPageNumber(pageRequest.getPageNumber() + 1);
        new ZlxcService(this).list(pageRequest);
    }



}
