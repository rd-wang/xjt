package com.catsic.biz.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuInflater;
import com.catsic.R;
import com.catsic.biz.common.adapter.SelXmjbxxListViewAdapter;
import com.catsic.biz.common.fragment.SelXmjbxxQueryFragment;
import com.catsic.biz.js.service.XmjbxxService;
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
  * @ClassName: SelXmjbxxListActivity
  * @Description: 项目选择
  * @author catsic-wuxianling
  * @date 2015年9月25日 下午3:31:54
  */
public class SelXmjbxxListActivity  extends BaseSlidingFragmentPageActivity  implements  OnItemClickListener,AbPullToRefreshView.OnFooterLoadListener,AbPullToRefreshView.OnHeaderRefreshListener{
	public AbPullToRefreshView mAbPullToRefreshView;
	public ListView mListView;
	public SelXmjbxxListViewAdapter adapter;

	public Fragment fragment;
	
	public static final int RESULT_SEL_XMJBXX = 12345;
	
	public SelXmjbxxListActivity(){
		super(R.string.SelXmjbxxListActivityTitle,R.id.fragment_ptr_list, R.layout.frame_common);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_fragment_ptrl);
		ActionBarManager.initBackTitle(this,getActionBar(),"项目选择");
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		
		//初始化查询Fragment
		FragmentTransaction  t = getSupportFragmentManager().beginTransaction();
		if (fragment == null) {
			fragment = new SelXmjbxxQueryFragment(this);
		}
		t.replace(R.id.frame_common, fragment);
		t.commit();
		
		
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		mAbPullToRefreshView = (AbPullToRefreshView) this
				.findViewById(R.id.mPullRefreshView);
		mListView = (ListView) this.findViewById(R.id.mListView);

// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);

// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));


		adapter = new SelXmjbxxListViewAdapter(this, new ArrayList<Map<String,Object>>());

		mListView.setOnItemClickListener(this);
		mListView.setAdapter(adapter);


		//加载初始数据
		Map<String,String> map =  pageRequest.getFilters();
		if (map ==null) {
			map = new HashMap();
		}
		try {
			map.put("xzqh", StringUtil.Stringtrim0(AppContext.LOGINUSER.getString("xzqh")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pageRequest.setFilters(map);
		
		ProgressDialogUtil.show(this, getResources().getString(R.string.loading), true);
		new XmjbxxService(this).list(pageRequest);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent intent = new Intent();
		Map<String,Object> map =  adapter.listItems.get(position);
		intent.putExtra("xmid", StringUtil.toString(map.get("xmid")));
		intent.putExtra("xmmc", StringUtil.toString(map.get("xmmc")));
		//返回
		setResult(RESULT_SEL_XMJBXX, intent);
		finish();
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
		new XmjbxxService(this).list(pageRequest);
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {

	}
	public void finishLoad() {
		mAbPullToRefreshView.onFooterLoadFinish();
		mAbPullToRefreshView.onHeaderRefreshFinish();
	}
}
