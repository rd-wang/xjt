package com.catsic.biz.yh.activity;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.catsic.R;
import com.catsic.biz.yh.bean.YhXcjl;
import com.catsic.biz.yh.service.YhXcjlService;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.PhotoViewActivity;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.adapter.TargetImageGridAdapter;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.FileService;
import com.catsic.core.tools.ActionBarManager;

public class YhXcjlViewActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.yh_lxmc) TextView yh_lxmc;
	public  @ViewInject(id=R.id.yh_kssj) TextView yh_kssj;
	private @ViewInject(id=R.id.yh_jssj) TextView yh_jssj;
	private @ViewInject(id=R.id.yh_fzr) TextView yh_fzr;
	private @ViewInject(id=R.id.yh_jlr) TextView yh_jlr;
	private @ViewInject(id=R.id.yh_xcry) TextView yh_xcry;
	private @ViewInject(id=R.id.yh_weather) TextView yh_weather;
	private @ViewInject(id=R.id.yh_fxwt) TextView yh_fxwt;
	private @ViewInject(id=R.id.yh_clyj) TextView yh_clyj;
	private @ViewInject(id=R.id.yh_cljg) TextView yh_cljg;
	private @ViewInject(id=R.id.btn_save) Button btn_save;
	private @ViewInject(id=R.id.btn_xzlx) ImageView btn_xzlx;
	private @ViewInject(id=R.id.gv_images) GridView gv_images;
	
	public TargetImageGridAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yh_xcjl_view);
		String centerTitle = getResources().getString(R.string.LzXcjlViewActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		String crowid = getIntent().getStringExtra("crowid");
		
		FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
		YhXcjl obj =  db.findById(crowid, YhXcjl.class);
		//如果本地不存在 ，就从服务端获取
		if (obj == null) {
			new YhXcjlService(this).findById(crowid);
		}else{
			initViewData(obj);
		}
		
		List<Tfile> fileList = new FileService(this).getLocalFileList("T_YH_XCJL", crowid, "fileTime asc");
		//如果本地不存在 ，就从服务端获取
		if (fileList == null || fileList.size() == 0) {
			new FileService(this).list("T_YH_XCJL", crowid);
		}
		adapter = new TargetImageGridAdapter(this, fileList,AppConstants.VIEW);
		gv_images.setAdapter(adapter);
		//图片查看
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(YhXcjlViewActivity.this,PhotoViewActivity.class);
				intent.putExtra("crowid", getIntent().getStringExtra("crowid"));
				intent.putExtra("ID", position);
				startActivity(intent);
			}
		});
		
	}
	
	/**
	 * 初始化view
	 * @param obj
	 */
	public void initViewData(YhXcjl obj){
		  yh_lxmc.setText(obj.getLxmc());
		  yh_kssj.setText(obj.getXcsjStart());
		  yh_jssj.setText(obj.getXcsjEnd());
		  yh_fzr.setText(obj.getFzr());
		  yh_jlr.setText(obj.getJlr());
		  yh_xcry.setText(obj.getXcry());
		  yh_weather.setText(obj.getWeather());
		  yh_fxwt.setText(obj.getFxwt());
		  yh_clyj.setText(obj.getClyj());
		  yh_cljg.setText(obj.getCljg());
	}

}
