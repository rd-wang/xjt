package com.catsic.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.catsic.R;
import com.catsic.core.service.CatsicAppConfigService;

/**  
  * @Description: 欢迎页面 
  * @author wuxianling  
  * @date 2014年7月2日 下午4:14:00    
  */ 
public class WelcomeActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle paramBundle) {
	    super.onCreate(paramBundle);
	    
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.loading, null);
		setContentView(view);
		
		//渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(3000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				finish();
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {
				 //是否需要初始化相关数据[字典，行政区划等]
			  	new CatsicAppConfigService(WelcomeActivity.this).loadApkConfig();
			}
			
		});
		
	}
	
}

