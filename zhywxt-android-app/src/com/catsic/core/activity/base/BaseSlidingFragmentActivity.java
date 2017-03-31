package com.catsic.core.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;

import com.catsic.R;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.ScreenUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**  
  * @Description: SlidingMenu 
  * @author wuxianling  
  * @date 2014年9月15日 下午1:14:32    
  */ 
public class BaseSlidingFragmentActivity extends SlidingFragmentActivity{
	
	protected int titleRes;
	protected int contentView;
	protected int behindContentView;
	

	public BaseSlidingFragmentActivity(int titleRes,int contentView,int behindContentView) {
		this.titleRes = titleRes;
		this.contentView = contentView;
		this.behindContentView = behindContentView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String centerTitle = getResources().getString(titleRes);
		ActionBarManager.initTitleCenterActionBar(this, getActionBar(), centerTitle);

		// set the Behind View
		
		setBehindContentView(behindContentView);

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	/**  
	  * @Title: switchContent  
	  * @Description:切换主页面显示的Fragment
	  * @param @param fragment     
	  * @return void   
	  * @throws  
	  */ 
	public void switchContent(){
		getSlidingMenu().showContent();
	}
	
	/**  
	  * @Title: switchContent  
	  * @Description: 切换至指定的Frgment 
	  * @param @param fragmentId
	  * @param @param fragment     
	  * @return void   
	  * @throws  
	  */ 
	public void switchContent(int fragmentId,Fragment fragment){
		FragmentTransaction  t = getSupportFragmentManager().beginTransaction();
		t.replace(fragmentId, fragment);
		t.commit();
		getSlidingMenu().showContent();
	}



}
