package com.catsic.core.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuInflater;
import com.catsic.R;
import com.catsic.biz.yh.service.LxService;
import com.catsic.core.AppContext;
import com.catsic.core.activity.base.BaseSlidingFragmentPageActivity;
import com.catsic.core.adapter.LxListViewAdapter;
import com.catsic.core.fragment.LxQueryFragment;
import com.catsic.core.tools.ActionBarManager;
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
  * @Description: 路线列表
  * @author wuxianling
  * @date 2014年12月12日 下午1:27:54
  */
public class LxListActivity extends BaseSlidingFragmentPageActivity implements OnFooterLoadListener{

	public AbPullToRefreshView mAbPullToRefreshView;
	public ListView mListView;

	public LxListViewAdapter adapter;

	public LxQueryFragment queryFragment;

	public LxListActivity() {
		super(R.string.LxListActivityTitle,R.id.fragment_ptr_list, R.layout.frame_common);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_fragment_ptrl);
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		ActionBarManager.initBackTitle(this, getActionBar(), "路线列表");
		//初始化查询Fragment
		FragmentTransaction  t = getSupportFragmentManager().beginTransaction();
		if (queryFragment == null) {
			queryFragment = new LxQueryFragment(this);
		}
		t.replace(R.id.frame_common, queryFragment);
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

		adapter = new LxListViewAdapter(this, new ArrayList<Map<String,Object>>());

		// You can also just use setListAdapter(mAdapter) or
		// pullRefreshListView.setAdapter(mAdapter)
		mListView.setAdapter(adapter);


		ProgressDialogUtil.show(this, getResources().getString(R.string.loading), true);

		//加载初始数据
		Map map =  pageRequest.getFilters();
		if (map ==null) {
			map = new HashMap();
		}
		try {
			map.put("xzqh", StringUtil.Stringtrim0(AppContext.LOGINUSER.getString("xzqh")));
			String tbdw4Query = AppContext.LOGINUSER.getString("tbdw4Query");
			map.put("lxjgxzqh", StringUtil.Stringtrim0(AppContext.LOGINUSER.getString("xzqh")));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		pageRequest.setFilters(map);
		new LxService(this, mAbPullToRefreshView).getLxs(pageRequest);
	}


	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.common_query, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				SlidingMenu slidingMenu = getSlidingMenu();
				if (slidingMenu.isMenuShowing()) {
					slidingMenu.showContent();
				}else{
//					slidingMenu.showMenu();
//					Intent intent = new Intent(this,LSActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					startActivity(intent);
					finish();
				}
				break;
			case R.id.action_query:
				//SldingMenu自动判断当前是打开还是关闭(toggle())
				toggle();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		//翻页
		pageRequest.setPageNumber(pageRequest.getPageNumber()+1);
		new LxService(this,mAbPullToRefreshView).getLxs(pageRequest);
	}
}
