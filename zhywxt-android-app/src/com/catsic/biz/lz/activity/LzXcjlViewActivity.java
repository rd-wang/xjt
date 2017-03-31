package com.catsic.biz.lz.activity;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.lz.bean.LzXcjl;
import com.catsic.biz.lz.service.LzXcjlService;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.PhotoViewActivity;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.adapter.TargetImageGridAdapter;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.FileService;
import com.catsic.core.tools.ActionBarManager;


/**
 * 
 * @author Administrator
 *
 */
public class LzXcjlViewActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.lz_lxmc) TextView lz_lxmc;
	public  @ViewInject(id=R.id.lz_kssj) TextView lz_kssj;
	private @ViewInject(id=R.id.lz_jssj) TextView lz_jssj;
	private @ViewInject(id=R.id.lz_fzr) TextView lz_fzr;
	private @ViewInject(id=R.id.lz_jlr) TextView lz_jlr;
	private @ViewInject(id=R.id.lz_xcry) TextView lz_xcry;
	private @ViewInject(id=R.id.lz_weather) TextView lz_weather;
	private @ViewInject(id=R.id.lz_fxwt) TextView lz_fxwt;
	private @ViewInject(id=R.id.lz_clyj) TextView lz_clyj;
	private @ViewInject(id=R.id.lz_cljg) TextView lz_cljg;
	private @ViewInject(id=R.id.btn_save) Button btn_save;
	private @ViewInject(id=R.id.btn_xzlx) ImageView btn_xzlx;
	private @ViewInject(id=R.id.gv_images) GridView gv_images;
	
	public TargetImageGridAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lz_xcjl_view);
		String centerTitle = getResources().getString(R.string.LzXcjlViewActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		String crowid = getIntent().getStringExtra("crowid");
		
		FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
		LzXcjl obj =  db.findById(crowid, LzXcjl.class);
		//如果本地不存在 ，就从服务端获取
		if (obj == null) {
			new LzXcjlService(this).findById(crowid);
		}else{
			initViewData(obj);
		}
		
		List<Tfile> fileList = new FileService(this).getLocalFileList("T_LZ_XCJL", crowid, "fileTime asc");
		//如果本地不存在 ，就从服务端获取
		if (fileList == null || fileList.size() == 0) {
			new FileService(this).list("T_LZ_XCJL", crowid);
		}
		adapter = new TargetImageGridAdapter(this, fileList,AppConstants.VIEW);
		gv_images.setAdapter(adapter);
		//图片查看
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(LzXcjlViewActivity.this,PhotoViewActivity.class);
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
	public void initViewData(LzXcjl obj){
		  lz_lxmc.setText(obj.getLxmc());
		  lz_kssj.setText(obj.getXcsjStart());
		  lz_jssj.setText(obj.getXcsjEnd());
		  lz_fzr.setText(obj.getFzr());
		  lz_jlr.setText(obj.getJlr());
		  lz_xcry.setText(obj.getXcry());
		  lz_weather.setText(obj.getWeather());
		  lz_fxwt.setText(obj.getFxwt());
		  lz_clyj.setText(obj.getClyj());
		  lz_cljg.setText(obj.getCljg());
	}
}
