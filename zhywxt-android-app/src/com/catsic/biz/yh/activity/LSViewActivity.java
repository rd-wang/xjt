package com.catsic.biz.yh.activity;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.yh.bean.LsJbxx;
import com.catsic.biz.yh.service.LsJbxxService;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.PhotoViewActivity;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.adapter.TargetImageGridAdapter;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.FileService;
import com.catsic.core.tools.ActionBarManager;

/**  
  * @Description: 路损明细 
  * @author wuxianling  
  * @date 2014年11月15日 上午11:34:12    
  */ 
public class LSViewActivity extends BaseActivity{
	
	public @ViewInject(id=R.id.tv_szdm) TextView tv_szdm;
	public @ViewInject(id=R.id.tv_shlx) TextView tv_shlx;
	public @ViewInject(id=R.id.tv_qzdzh) TextView tv_qzdzh;
	public @ViewInject(id=R.id.tv_fxsj) TextView tv_fxsj;
	public @ViewInject(id=R.id.tv_xcqk) TextView tv_xcqk;
	public @ViewInject(id=R.id.tv_czcs) TextView tv_czcs;
	public @ViewInject(id=R.id.gv_images) GridView gv_images;
	
	public TargetImageGridAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yhxc_ls_view);
		
		String centerTitle = getResources().getString(R.string.LSViewActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		String crowid = getIntent().getStringExtra("crowid");
		
		FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
		LsJbxx obj =  db.findById(crowid, LsJbxx.class);
		//如果本地不存在 ，就从服务端获取
		if (obj == null) {
			new LsJbxxService(this).findById(crowid);
		}else{
			initViewData(obj);
		}
		
		List<Tfile> fileList = new FileService(this).getLocalFileList("T_LS", crowid, "fileTime asc");
		//如果本地不存在 ，就从服务端获取
		if (fileList == null || fileList.size() == 0) {
			new FileService(this).list("T_LS", crowid);
		}
		adapter = new TargetImageGridAdapter(this, fileList,AppConstants.VIEW);
		gv_images.setAdapter(adapter);
		//图片查看
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(LSViewActivity.this,PhotoViewActivity.class);
				intent.putExtra("relationId", getIntent().getStringExtra("crowid"));
				intent.putExtra("ID", position);
				startActivity(intent);
			}
		});
		
	}
	
	public void initViewData(LsJbxx obj){
		 tv_szdm.setText(obj.getSzdm());
		 tv_shlx.setText(obj.getLxmc()+"("+obj.getLxbm()+")");
		 tv_qzdzh.setText("K"+obj.getQdzh()+"~K"+obj.getZdzh());
		 tv_fxsj.setText(obj.getFxsj());
		 tv_xcqk.setText(obj.getXcqk());
		 tv_czcs.setText(obj.getCzcs());
	}

}
