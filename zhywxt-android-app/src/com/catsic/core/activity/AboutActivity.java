package com.catsic.core.activity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.custom.AppFooterView;
import com.catsic.core.custom.FooterMenuItem;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.DeviceUtil;
import com.catsic.core.tools.VersionUtil;

/**  
  * @Description: 关于页面 
  * @author wuxianling  
  * @date 2014年7月3日 上午11:51:47    
  */ 
public class AboutActivity extends FinalActivity{
	
//	@ViewInject(id=R.id.about_header) AppHeaderView appHeaderView;
	@ViewInject(id=R.id.about_footer) AppFooterView appFooterView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		String centerTitle = getResources().getString(R.string.AboutActivityTitle);
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
		//Header
//		this.appHeaderView.setText("关于");
		
		//Footer
		//上传
//		FooterMenuItem footerMenuItem_upload = new FooterMenuItem();
//		footerMenuItem_upload.setImgRes(R.drawable.btn_upload_normal, R.drawable.btn_upload_press);
//		footerMenuItem_upload.setListener(new AppFooterView.OnMenuItemClickListener() {
//			@Override
//			public void onMenuItemClicked() {
//				
//				
//			}
//		});
		
		//返回
		FooterMenuItem footerMenuItem_back = new FooterMenuItem();
		footerMenuItem_back.setImgRes(R.drawable.btn_back_normal, R.drawable.btn_back_press);
		footerMenuItem_back.setListener(new AppFooterView.OnMenuItemClickListener() {
			@Override
			public void onMenuItemClicked() {
				AboutActivity.this.finish();
			}
		});
		
		//添加菜单项
//		this.appFooterView.setMenuItem(0, footerMenuItem_upload);
		this.appFooterView.setMenuItem(3, footerMenuItem_back);
		
		//信息显示
		((TextView)this.findViewById(R.id.textview_version)).setText(String.format("%1$s", new Object[] { VersionUtil.getVersionName(this)}));
		((TextView)this.findViewById(R.id.textview_scope)).setText(String.format("Android%1$s和%1$s以上", new Object[] { "4.0" }));
//		((TextView)this.findViewById(R.id.textview_esn)).setText(DeviceUtil.getESN(this));
//		((TextView)this.findViewById(R.id.textview_imsi)).setText(DeviceUtil.getIMSI(this));
		
	}

}
