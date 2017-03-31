package com.catsic.hybrid.activity;

import org.apache.cordova.DroidGap;

import android.os.Bundle;

/**
  * @ClassName: MainActivity
  * @Description: hybrid app 
  * @author wuxianling
  * @date 2015年4月21日 上午10:26:41
  */
public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.loadUrl("file:///android_asset/www/index.html", 3000);
	}

}
