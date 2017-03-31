package com.catsic.core.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.custom.AppFooterView;
import com.catsic.core.custom.FooterMenuItem;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.SharedPreferencesUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**  
  * @Description: 设置后台IP及端口 
  * @author wuxianling  
  * @date 2014年7月2日 下午5:17:51    
  */ 
public class SettingActivity extends FinalActivity{
	
	@ViewInject(id=R.id.ip) EditText et_ip;
	@ViewInject(id=R.id.port) EditText et_port;
//	@ViewInject(id=R.id.setting_header) AppHeaderView appHeaderView;
	@ViewInject(id=R.id.setting_footer) AppFooterView appFooterView;
	
	//上次保存的ip,port
	private String old_ip;
	private String old_port;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_setting);
		String centerTitle = getResources().getString(R.string.SettingActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		initControls();
		
	}
	/**
	  * @Title: initControls  
	  * @Description: 初始化控制栏 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private void initControls(){
		//初始化ip,port
		String ip = SharedPreferencesUtil.get(this, AppConstants.SP_SERVERDATA, Context.MODE_PRIVATE, "ip","");
		String port = SharedPreferencesUtil.get(this, AppConstants.SP_SERVERDATA, Context.MODE_PRIVATE, "port","");
		if (ObjectUtils.isNullOrEmptyString(ip)) {
		   ip = AppUrls.SERVICE_IP;
		}
		if (ObjectUtils.isNullOrEmptyString(port)) {
			port = AppUrls.SERVICE_PORT;
		}
		this.et_ip.setText(ip);
		this.et_port.setText(port);
		//记录old ip ,port
		this.old_ip = this.et_ip.getText().toString().trim();
		this.old_port = this.et_port.getText().toString().trim();
//		this.appHeaderView.setText("服务器地址设置");
		//确定
		FooterMenuItem footerMenuItem_ok = new FooterMenuItem();
		footerMenuItem_ok.setImgRes(R.drawable.btn_ok_normal, R.drawable.btn_ok_press);
		footerMenuItem_ok.setListener(new AppFooterView.OnMenuItemClickListener() {
			@Override
			public void onMenuItemClicked() {
				 SettingActivity.this.saveServerSetting();
		         SettingActivity.this.finish();
			}
			
		});
		
		//取消
		FooterMenuItem footerMenuItem_cancel = new FooterMenuItem();
		footerMenuItem_cancel.setImgRes(R.drawable.btn_cancel_normal, R.drawable.btn_cancel_press);
		footerMenuItem_cancel.setListener(new AppFooterView.OnMenuItemClickListener() {
			@Override
			public void onMenuItemClicked() {
						String ip = SettingActivity.this.et_ip.getText().toString().trim();
						String port = SettingActivity.this.et_port.getText().toString().trim();
						if ((!ip.equals(SettingActivity.this.old_ip))|| (!port.equals(SettingActivity.this.old_port))) {
							new AlertDialog.Builder(SettingActivity.this)
									.setMessage("是否保存设置?")
									.setTitle("返回确认")
									.setPositiveButton("保存",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface paramAnonymous2DialogInterface,int paramAnonymous2Int) {
													SettingActivity.this.saveServerSetting();
													SettingActivity.this.finish();
												}
											})
									.setNegativeButton("取消",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface paramAnonymous2DialogInterface,int paramAnonymous2Int) {
													SettingActivity.this.finish();
												}
											}).show();
							return;
						}
						SettingActivity.this.finish();
			}
			
		});
		
		//添加菜单项
		this.appFooterView.setMenuItem(0, footerMenuItem_ok);
		this.appFooterView.setMenuItem(3, footerMenuItem_cancel);
	}
	
	/**  
	  * @Title: saveServerSetting  
	  * @Description: TODO 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public void saveServerSetting()
	  {
	    String ip = this.et_ip.getText().toString().trim();
	    String port = this.et_port.getText().toString().trim();
	    if (ObjectUtils.isNullOrEmptyString(ip)){
	    	ToastUtil.showShortToast(this, "请填写IP地址!");
	    	return;
	    }
	    if (!StringUtil.isIp(ip)){
	    	ToastUtil.showShortToast(this, "请输入正确格式的IP地址，比如202.102.100.100");
	    	return;
	    }
	    if (ObjectUtils.isNullOrEmptyString(port)) {
	    	ToastUtil.showShortToast(this, "请填写端口号!");
	    	return;
		}
	    SharedPreferencesUtil.put(this, AppConstants.SP_SERVERDATA, Context.MODE_PRIVATE, new String[]{"ip","port"},new String[]{ip,port});
	    AppUrls.SERVICE_IP = ip;
	    AppUrls.SERVICE_PORT = port;
	    Log.i("SettingActivity", "IP：" + ip + " Port：" + port);
	    finish();
	  }
	

}
