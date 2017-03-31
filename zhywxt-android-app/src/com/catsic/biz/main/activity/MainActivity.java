package com.catsic.biz.main.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.catsic.R;
import com.catsic.biz.main.adapter.MainMenuAdapter;
import com.catsic.core.custom.viewpagerindicator.IconTabPageIndicator;
import com.catsic.core.tools.ActionBarManager;
import com.viewpagerindicator.PageIndicator;


/**  
  * @Description: 主页面 
  *   以GridView 简洁大方的形式展示菜单 ，并且支持左右滑动切换，方便用户查找相关功能入口
  *   如需扩展主页菜单入口：
  *   			com.catsic.biz.main.adapter.MainFragmentAdapter.pageTitles  指定Fragment的数量（即页面个数）
  *             com.catsic.biz.main.fragment.MainFragment  images  指定菜单显示图标
  *             com.catsic.biz.main.fragment.MainFragment  clazz   指定菜单导向Class
  *   
  * @author wuxianling  
  * @date 2014年7月29日 下午4:50:24    
  */ 
public class MainActivity extends FragmentActivity {
	
	private MainMenuAdapter adapter;
	private ViewPager viewPager;
	private PageIndicator pageIndicator;
	
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_main);
        
        String centerTitle = getResources().getString(R.string.appName);
		ActionBarManager.initTitleCenterActionBar(this, getActionBar(), centerTitle);

        adapter = new MainMenuAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        pageIndicator = (IconTabPageIndicator)findViewById(R.id.indicator);
        pageIndicator.setViewPager(viewPager);
    }

}
