
package com.catsic.biz.js.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.catsic.biz.common.activity.SelXmjbxxListActivity;
import com.catsic.biz.js.bean.TJsZlxc;
import com.catsic.biz.js.service.ZlxcService;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.activity.PhotoActivity;
import com.catsic.core.activity.PhotoAlbumActivity;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.adapter.TargetImageGridAdapter;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.Tfile;
import com.catsic.core.custom.ImagePopupWindows;
import com.catsic.core.service.FileService;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.AlbumHelper;
import com.catsic.core.tools.DateTimePickerUtil;
import com.catsic.core.tools.DateUtil;
import com.catsic.core.tools.FileUtils;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.MediaUtil;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
  * @ClassName: ZlxcActivity
  * @Description: 质量巡查
  * @author catsic-wuxianling
  * @date 2015年9月24日 下午2:03:53
  */
public class ZlxcActivity extends BaseActivity implements OnTouchListener,android.view.View.OnClickListener{
	
	private @ViewInject(id=R.id.et_xmmc) EditText et_xmmc;
	private @ViewInject(id=R.id.et_xcsj) EditText et_xcsj;
	private @ViewInject(id=R.id.et_xcr) EditText et_xcr;
	private @ViewInject(id=R.id.et_xcjg) EditText et_xcjg;
	private @ViewInject(id=R.id.et_zgyj) EditText et_zgyj;
	private @ViewInject(id=R.id.gv_images) GridView gv_images;
	
	private @ViewInject(id=R.id.btn_add) ImageView btn_add;
	BootstrapButton btn_save;
	
	//当前已选图片适配器
	public TargetImageGridAdapter adapter;
	
	/**照片文件绝对路径**/
	public String imageFileName = "",xmid="",crowid="";
	
	/**编辑选择的图片**/
	public final int RESULT_DEL_IMAGES = 1234;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_js_zlgl_zlxc);
		
		Bundle b =  getIntent().getExtras();
		//编辑
		if (b!=null && b.containsKey("crowid")) {
			crowid = b.getString("crowid");
			ProgressDialogUtil.show(this, "正在初始化...", true);
			//初始化质量巡查
			new ZlxcService(this).findById(crowid,"ZLXC");
		}
		
		String centerTitle = getResources().getString(R.string.ZlxcActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		btn_save = (BootstrapButton) this.findViewById(R.id.btn_save);
		
		et_xcsj.setOnTouchListener(this);
		btn_save.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		
		gv_images.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new TargetImageGridAdapter(this);
		adapter.update();
		gv_images.setAdapter(adapter);
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				if (position == Bimp.bmp.size()) {//添加(点击最后一张图片 +号)
					//生成照片文件名
					imageFileName = MediaUtil.generatorFilePath();
					new ImagePopupWindows(ZlxcActivity.this,PhotoAlbumActivity.class, gv_images,imageFileName);
				} else {//查看
					Intent intent = new Intent(ZlxcActivity.this,PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivityForResult(intent, RESULT_DEL_IMAGES);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			//必填项校验
			if (ObjectUtils.isNullOrEmptyString(xmid)) {
				ToastUtil.showShortToast(this,"请选择项目");
				break ;
			}
			if (ObjectUtils.isNullOrEmptyString(et_xcsj.getText().toString().trim())) {
				ToastUtil.showShortToast(this,"请选择巡查时间");
				break ;
			}
			if (ObjectUtils.isNullOrEmptyString(et_xcr.getText().toString().trim())) {
				ToastUtil.showShortToast(this,"请输入巡查人");
				break ;
			}
			ProgressDialogUtil.show(this,getResources().getString(R.string.handing), true);
			//save
			TJsZlxc obj = saveOrUpdate();
			//1.上传数据至服务器
			new ZlxcService(this).saveOrUpdate(obj);
			break;
		//项目选择
		case R.id.btn_add:
			Intent intent = new Intent(this,SelXmjbxxListActivity.class);
			startActivityForResult(intent, SelXmjbxxListActivity.RESULT_SEL_XMJBXX);
			break;
		}
	}
	
	public void initData(String result){
		if (!ObjectUtils.isNullOrEmptyString(result)) {
			TJsZlxc obj =  (TJsZlxc) GsonUtils.fromJson(result, TJsZlxc.class);
			xmid = obj.getXmid();
			if (!ObjectUtils.isNullOrEmptyString(obj.getXmjbxx())) {
				et_xmmc.setText(obj.getXmjbxx().getXmmc());
			}
			//主键
			crowid = obj.getCrowid();
			et_xcsj.setText(DateUtil.format(obj.getXcsj(),DateUtil.PATTERN_DATETIME));
			et_xcr.setText(obj.getXcr());
			et_xcjg.setText(obj.getXcjg());
			et_zgyj.setText(obj.getZgyj());
			
			ProgressDialogUtil.show(this, "正在初始化巡查图片", true);
			new FileService(this).list("T_JS_ZLGL_ZLXC",obj.getCrowid());
		}
	}
	
	/**  
	  * @Title: save  
	  * @Description: 信息录入 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private TJsZlxc saveOrUpdate() {
		String xcr = et_xcr.getText().toString().trim();
		String xcsj = et_xcsj.getText().toString().trim();
		String xcjg = et_xcjg.getText().toString().trim();
		String zgyj = et_zgyj.getText().toString().trim();
		
		TJsZlxc obj = new TJsZlxc();
		obj.setXmid(xmid);
		//添加，编辑
		if (ObjectUtils.isNullOrEmptyString(crowid)) {
			obj.setCrowid(UUID.randomUUID().toString());
		}else{
			obj.setCrowid(crowid);
		}
		if (!ObjectUtils.isNullOrEmptyString(xcr)) {
			obj.setXcr(xcr);
		}
		if (!ObjectUtils.isNullOrEmptyString(xcsj)) {
			obj.setXcsj(DateUtil.parse(xcsj, DateUtil.PATTERN_DATETIME));
		}
		if (!ObjectUtils.isNullOrEmptyString(xcjg)) {
			obj.setXcjg(xcjg);
		}
		if (!ObjectUtils.isNullOrEmptyString(zgyj)) {
			obj.setZgyj(zgyj);
		}
		JSONObject loginUser = AppContext.LOGINUSER;
		if (loginUser!=null) {
			try {
				obj.setXcdw(loginUser.getString("orgname"));
				obj.setTbdw(loginUser.getString("orgid"));
				obj.setTbr(loginUser.getString("userid"));
				obj.setTbsj(new Date());
			} catch (JSONException e) {
				Log.e("ZlxcActivity", "JSONException");
				e.printStackTrace();
			}
		}
		
		FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
		if (ObjectUtils.isNullOrEmptyString(crowid)) {
			db.save(obj);
		}else{
			db.update(obj);
		}
		
		List<String> filePaths = Bimp.drr; 
		List<Tfile> files = new ArrayList<Tfile>();
		for (String filePath : filePaths) {
			File f = new File(filePath);
			
			Tfile file = new Tfile();
			file.setFileId(UUID.randomUUID().toString());
			//filePath处理
			file.setFilePath(FileUtils.absolutePathToFilePath(f.getAbsolutePath(), "T_JS_ZLGL_ZLXC"));
			file.setFileSize(f.length()+"");
			
			String fileType = "";
			String fileName = f.getName();
			if (ObjectUtils.isNotEmpty(fileName)&&(fileName.indexOf(".")>0)) {
				fileType = fileName.substring(fileName.lastIndexOf(".")+1);
			}
			file.setFileType(fileType);
			file.setFileTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			file.setRelationId(obj.getCrowid());
			file.setGroupId("T_JS_ZLGL_ZLXC");
			file.setFileName(fileName);
			if (ObjectUtils.isNullOrEmptyString(crowid)) {
				db.save(file);
			}else{
				db.update(file);
			}
			
			//服务端处理
			file.setFileId("");
			String content = FileUtils.fileToBase64(filePath);
			file.setContent(content);
			files.add(file);
			
			obj.setFiles(files);
		}
		//clear
		Bimp.reset();
		return obj;
	}
	
	/**
	 * 拍照,从相册中选择
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 switch (requestCode) {
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
		    case SelXmjbxxListActivity.RESULT_SEL_XMJBXX://项目选择
				if (data == null) {
					return;
				}
				if (!ObjectUtils.isNullOrEmptyString(data.getStringExtra("xmid"))) {
		    		xmid = data.getStringExtra("xmid");
		    	}
		    	if (!ObjectUtils.isNullOrEmptyString(data.getStringExtra("xmmc"))) {
		    		et_xmmc.setText(data.getStringExtra("xmmc"));
				}
		    	break;
		 }
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (v.getId() == R.id.et_xcsj) {
				ProgressDialogUtil.show(this, "正在加载...", true);
				DateTimePickerUtil.showDateTimePicker(this,et_xcsj);
				ProgressDialogUtil.dismiss();
			}
			break;
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		//清空已经选择的图片
		Bimp.reset();
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		Bimp.reset();
		super.onDestroy();
	}


}
