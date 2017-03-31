package com.catsic.core.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.base.BaseUpdateActiviy;
import com.catsic.core.custom.ClearEditText;
import com.catsic.core.service.LoginService;
import com.catsic.core.service.UpdateVersionService;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.DeviceUtil;
import com.catsic.core.tools.MenuUtil;
import com.catsic.core.tools.ScreenUtils;
import com.catsic.core.tools.SharedPreferencesUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.annotation.view.ViewInject;

/**  
  * @Description: 登陆页面 
  * @author wuxianling  
  * @date 2014年7月2日 下午4:18:12    
  */ 
public class LoginActivity extends  BaseUpdateActiviy implements OnClickListener{

	@ViewInject(id=R.id.et_username) ClearEditText et_username;
	@ViewInject(id=R.id.et_password) ClearEditText et_password;
	@ViewInject(id=R.id.CheckBox_remmeber_username) CheckBox cb_remmeber_username;
	@ViewInject(id=R.id.CheckBox_remmeber_password) CheckBox cb_remmeber_password;
    BootstrapButton btn_login;
	private float startX;
	private float startY;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_login);
		
		btn_login = (BootstrapButton) this.findViewById(R.id.btn_login);
		String centerTitle = getResources().getString(R.string.LoginActivityTitle);
		ActionBarManager.initTitleCenterActionBar(this, getActionBar(), centerTitle);
		Log.i("LoginActivity", "登录界面载入...");
		btn_login.setOnClickListener(this);
		
		/**预加载个性化数据**/
		preLoadAppData();
		
		if (!DeviceUtil.isHasNetWork(this)) {
			Log.i("LoginActiviy", "请先打开3G或则WIFI网络,否则无法正常使用软件...");
			ToastUtil.showShortToast(this, "请先打开3G或则WIFI网络,否则无法正常使用软件");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_login:
			login();
			break;
		}
		
	}
	
	public void login(){
		String username = LoginActivity.this.et_username.getText().toString().trim();
		String password = LoginActivity.this.et_password.getText().toString().trim();
		
		if(TextUtils.isEmpty(username)){
			//设置晃动
			et_username.setShakeAnimation();
			ToastUtil.showShortToast(LoginActivity.this, "帐号不能为空！");
			return;
		}
		
		if(TextUtils.isEmpty(password)){
			//设置晃动
			et_password.setShakeAnimation();
			ToastUtil.showShortToast(LoginActivity.this, "密码不能为空！");
			return;
		}
		
		Log.i("LoginActivity", "开始登录...帐号：" + username );
		
		//个性化数据保存
		LoginActivity.this.saveAppData();
		
		//=========================登陆======================================
		
		new LoginService(LoginActivity.this).login(username, password);
	}
	
	/**  
	  * @Title: exitApplication  
	  * @Description: 应用退出 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private void exitApplication() {
		new AlertDialog.Builder(this)
				.setMessage("确认退出程序吗?")
				.setTitle("提示")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramAnonymousDialogInterface,int paramAnonymousInt) {
						LoginActivity.this.saveAppData();
						LoginActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramAnonymousDialogInterface,int paramAnonymousInt) {
						
					}
				}).show();
	}
	
	
	/**  
	  * @Title: saveAppData  
	  * @Description: 应用程序数据个性化保存 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void saveAppData(){
	    if (this.cb_remmeber_username.isChecked()){
	    	SharedPreferencesUtil.put(this,AppConstants.SP_USERDATA , Context.MODE_PRIVATE, "username",this.et_username.getText().toString().trim());
	    }else{
	    	SharedPreferencesUtil.remove(this, AppConstants.SP_USERDATA, Context.MODE_PRIVATE, "username");
	    }
	    if (this.cb_remmeber_password.isChecked()){
	    	SharedPreferencesUtil.put(this,AppConstants.SP_USERDATA , Context.MODE_PRIVATE, "password",this.et_password.getText().toString().trim());
	    }else{
	    	SharedPreferencesUtil.remove(this, AppConstants.SP_USERDATA, Context.MODE_PRIVATE, "password");
	    }
	}
	
	/**  
	  * @Title: preLoadAppData  
	  * @Description: 预加载 App个性化数据 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void preLoadAppData(){
		String username = SharedPreferencesUtil.get(this, AppConstants.SP_USERDATA, Context.MODE_PRIVATE, "username", "");
		String password = SharedPreferencesUtil.get(this, AppConstants.SP_USERDATA, Context.MODE_PRIVATE, "password", "");
		if (username!=null && (!"".equals(username))) {
			this.cb_remmeber_username.setChecked(true);
			this.et_username.setText(username);
		}else{
			this.cb_remmeber_username.setChecked(false);
			this.et_username.setText("");
		}
		
		if (password!=null && (!"".equals(password))) {
			this.cb_remmeber_password.setChecked(true);
			this.et_password.setText(password);
		}else{
			this.cb_remmeber_password.setChecked(false);
			this.et_password.setText("");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater =  getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_setting:
				LoginActivity.this.startActivity(new Intent(LoginActivity.this, SettingActivity.class));
				break;
			case R.id.action_about:
				LoginActivity.this.startActivity(new Intent(LoginActivity.this, AboutActivity.class));
				break;
			case R.id.action_apkUpdate:
				this.handler = new UpdateHandler();
				//更新========================
				new UpdateVersionService(this,handler).getLastestApkInfo();
				//更新========================
				break;
			case R.id.action_exit:
				exitApplication();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		MenuUtil.setOverflowIconVisible(featureId, menu);
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				startX = event.getRawX();
				startY = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				float endX = event.getRawX();
				float endY = event.getRawY();
				if (endY-startY>100){
					ScreenUtils.toggleSoftPan(getApplication());
				}
				break;
		}
		return super.onTouchEvent(event);
	}
}
