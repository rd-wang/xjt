package com.catsic.biz.query.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuInflater;
import com.catsic.R;
import com.catsic.biz.main.activity.MainActivity;
import com.catsic.biz.query.adapter.LsListViewAdapter;
import com.catsic.biz.query.fragment.LsQueryFragment;
import com.catsic.biz.yh.service.LsJbxxService;
import com.catsic.core.AppContext;
import com.catsic.core.activity.base.BaseSlidingFragmentPageActivity;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.widget.pullview.AbPullToRefreshView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**  
  * @Description: 养护路损 
  * @author wuxianling  
  * @date 2014年11月17日 上午9:18:29    
  */ 
public class LsListActivity extends BaseSlidingFragmentPageActivity implements AbPullToRefreshView.OnFooterLoadListener {

	public AbPullToRefreshView mAbPullToRefreshView;
	public ListView mListView;
	
	public LsListViewAdapter adapter;
	
	public LsQueryFragment queryFragment;
	
	public LsListActivity() {
		super(R.string.LSListActivityTitle,R.id.fragment_ptr_list, R.layout.frame_common);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_fragment_ptrl);
		
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		
		//初始化查询Fragment
		FragmentTransaction  t = getSupportFragmentManager().beginTransaction();
		if (queryFragment == null) {
			queryFragment = new LsQueryFragment(this);
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

		adapter = new LsListViewAdapter(this, new ArrayList<Map<String,Object>>());


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
			map.put("tbdwdm", tbdw4Query);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pageRequest.setFilters(map);
		new LsJbxxService(this).list(pageRequest);
		
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
					slidingMenu.showMenu();
					Intent intent = new Intent(this,MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
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
		new LsJbxxService(this).list(pageRequest);
	}
}
