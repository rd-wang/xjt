package com.catsic.biz.yh.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.yh.bean.YhXcjl;
import com.catsic.biz.yh.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.activity.LxListActivity;
import com.catsic.core.activity.PhotoActivity;
import com.catsic.core.activity.PhotoAlbumActivity;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.adapter.TargetImageGridAdapter;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.Tfile;
import com.catsic.core.custom.ImagePopupWindows;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.AlbumHelper;
import com.catsic.core.tools.DateTimePickerUtil;
import com.catsic.core.tools.DateUtil;
import com.catsic.core.tools.MediaUtil;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class YhXcjlActivity extends BaseActivity implements OnTouchListener,android.view.View.OnClickListener{
	
	private @ViewInject(id=R.id.yh_lxmc) EditText yh_lxmc;
	public @ViewInject(id=R.id.yh_kssj) EditText yh_kssj;
	private @ViewInject(id=R.id.yh_jssj) EditText yh_jssj;
	private @ViewInject(id=R.id.yh_fzr) EditText yh_fzr;
	private @ViewInject(id=R.id.yh_jlr) EditText yh_jlr;
	private @ViewInject(id=R.id.yh_xcry) EditText yh_xcry;
	private @ViewInject(id=R.id.yh_weather) EditText yh_weather;
	private @ViewInject(id=R.id.yh_fxwt) EditText yh_fxwt;
	private @ViewInject(id=R.id.yh_clyj) EditText yh_clyj;
	private @ViewInject(id=R.id.yh_cljg) EditText yh_cljg;
	private BootstrapButton btn_save;
	private @ViewInject(id=R.id.btn_xzlx) ImageView btn_xzlx;
	private @ViewInject(id=R.id.gv_images) GridView gv_images;
	
	//当前已选图片适配器
	public TargetImageGridAdapter adapter;
	
	/**照片文件绝对路径**/
	public String imageFileName = "";
	
	/**编辑选择的图片**/
	public final int RESULT_DEL_IMAGES = 1234;
	
	/**
	 * 路线选择
	 */
	public String lxbm = "";
	public String lxmc = "";
	public String lxjgxzqh = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yh_xcjl_add);
		String centerTitle = getResources().getString(R.string.LzXcjlActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		btn_save = (BootstrapButton) this.findViewById(R.id.btn_save);
		
		yh_kssj.setOnTouchListener(this);
		yh_jssj.setOnTouchListener(this);
		btn_save.setOnClickListener(this);
		btn_xzlx.setOnClickListener(this);
		gv_images.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new TargetImageGridAdapter(this);
		adapter.update();
		gv_images.setAdapter(adapter);
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				if (position == Bimp.bmp.size()) {//添加(点击最后一张图片 +号)
					//生成照片文件名
					imageFileName = MediaUtil.generatorFilePath();
					new ImagePopupWindows(YhXcjlActivity.this,PhotoAlbumActivity.class, gv_images,imageFileName);
				} else {//查看
					Intent intent = new Intent(YhXcjlActivity.this,PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivityForResult(intent, RESULT_DEL_IMAGES);
				}
			}
		});
	}

	
	/**
	 * 保存及选择路线按钮事件绑定
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				if(save()){
					startActivity(new Intent(this,YhXcjlListActivity.class));
				}
				break;
			case R.id.btn_xzlx:
				startActivityForResult(new Intent(this,LxListActivity.class), AppConstants.$SELECT);
				break;
			}
		
	}
	
	/**
	 * 保存至移动端
	 * @return
	 */
	private boolean save() {
		if (ObjectUtils.isNullOrEmptyString(lxbm)) {
			ToastUtil.showShortToast(this, "请选择路线！");
		}
		YhXcjl xcjl = new YhXcjl();
		xcjl.setCrowid(UUID.randomUUID().toString());
		xcjl.setLxbm(lxbm);
		xcjl.setLxmc(yh_lxmc.getText().toString().trim());
		xcjl.setXzqh(lxjgxzqh);
		xcjl.setXcsjStart(yh_kssj.getText().toString().trim());
		xcjl.setXcsjEnd(yh_jssj.getText().toString().trim());
		xcjl.setWeather(yh_weather.getText().toString().trim());
		xcjl.setFzr(yh_fzr.getText().toString().trim());
		xcjl.setJlr(yh_jlr.getText().toString().trim());
		xcjl.setXcry(yh_xcry.getText().toString().trim());
		xcjl.setFxwt(yh_fxwt.getText().toString().trim());
		xcjl.setClyj(yh_clyj.getText().toString().trim());
		xcjl.setCljg(yh_cljg.getText().toString().trim());
		xcjl.setTbsj(DateUtil.format(new Date(), "yyyy-MM-dd"));
		xcjl.setMkType("YH");
		JSONObject loginUser = AppContext.LOGINUSER;
		if (loginUser!=null) {
			try {
				xcjl.setDwdm(loginUser.getString("orgid"));
				xcjl.setDwmc(loginUser.getString("orgname"));
				xcjl.setShbz(ShbzUtils.getShbzByUser(loginUser.getString("orglevel")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
		db.save(xcjl);
		
		List<String> filePaths = Bimp.drr; 
		for (String filePath : filePaths) {
			File f = new File(filePath);
			
			Tfile file = new Tfile();
			file.setFileId(UUID.randomUUID().toString());
			file.setFilePath(f.getAbsolutePath());
			file.setFileSize(f.length()+"");
			
			String fileType = "";
			String fileName = f.getName();
			if (ObjectUtils.isNotEmpty(fileName)&&(fileName.indexOf(".")>0)) {
				fileType = fileName.substring(fileName.lastIndexOf(".")+1);
			}
			file.setFileType(fileType);
			file.setFileTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			file.setRelationId(xcjl.getCrowid());
			file.setGroupId("T_YH_XCJL");
			file.setFileName(fileName);
			
//			String content  = FileUtils.getFile(f.getAbsolutePath());
//			file.setContent(null);
			db.save(file);
		}
		
		//clear
		Bimp.reset();
		return true;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (v.getId() == R.id.yh_kssj) {
					ProgressDialogUtil.show(this, "正在加载...", true);
					DateTimePickerUtil.showDateTimePicker(this,yh_kssj);
					ProgressDialogUtil.dismiss();
				}else if(v.getId() == R.id.yh_jssj){
					ProgressDialogUtil.show(this, "正在加载...", true);
					DateTimePickerUtil.showDateTimePicker(this,yh_jssj);
					ProgressDialogUtil.dismiss();
				}
				break;
			}
		return false;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 switch (requestCode) {
		 	case AppConstants.$SELECT://路线选择
		 		if (data == null || "".equals(data)) {
					return;
				}
		 		lxbm = data.getStringExtra("lxbm");
		 		lxmc = data.getStringExtra("lxmc");
		 		lxjgxzqh = data.getStringExtra("lxjgxzqh");
		 		yh_lxmc.setText(lxmc);
		 		break;
		    case MediaUtil.RESULT_CAPTURE_IMAGE://拍照                    
		    	if (resultCode == RESULT_OK) { 
		    		if (Bimp.drr.size() < AppConstants.IMAGES_CNT) {
						Bimp.drr.add(AppConstants.IMAGES_BASEPATH+imageFileName);
						adapter.update();
					}
		    		//update 重新构建相簿
		    		AlbumHelper.getHelper().hasBuildImagesBucketList = false;
		    	}
		        break;
		    case MediaUtil.REQUEST_PHOTO://从相册中选择图片
		    	adapter.update();
		    	break;
		    case RESULT_DEL_IMAGES://已选择图片编辑
		    	adapter.update();
		        break;
		 }
	}
	
	
}
	
