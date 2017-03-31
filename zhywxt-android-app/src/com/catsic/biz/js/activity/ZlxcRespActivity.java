
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

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.js.bean.TJsZlxc;
import com.catsic.biz.js.bean.TJsZlxcResp;
import com.catsic.biz.js.service.ZlxcRespService;
import com.catsic.biz.js.service.ZlxcService;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.activity.PhotoActivity;
import com.catsic.core.activity.PhotoAlbumActivity;
import com.catsic.core.activity.PhotoViewActivity;
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
import com.catsic.core.tools.StringUtil;
import com.catsic.core.tools.ToastUtil;
import com.google.gson.internal.LinkedTreeMap;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

/**
  * @ClassName: ZlxcRespActivity
  * @Description: 质量巡查反馈
  * @author catsic-wuxianling
  * @date 2015年9月28日 下午3:50:54
  */
public class ZlxcRespActivity extends BaseActivity implements OnTouchListener,android.view.View.OnClickListener{
	
	private @ViewInject(id=R.id.et_xmmc) EditText et_xmmc;
	private @ViewInject(id=R.id.et_xcsj) EditText et_xcsj;
	private @ViewInject(id=R.id.et_xcdw) EditText et_xcdw;
	private @ViewInject(id=R.id.et_xcr) EditText et_xcr;
	private @ViewInject(id=R.id.et_xcjg) EditText et_xcjg;
	private @ViewInject(id=R.id.et_zgyj) EditText et_zgyj;
	
	
	private @ViewInject(id=R.id.et_fksj) EditText et_fksj;
	private @ViewInject(id=R.id.et_fkr) EditText et_fkr;
	private @ViewInject(id=R.id.et_fkqk) EditText et_fkqk;
	private @ViewInject(id=R.id.gv_images) GridView gv_images;//反馈图片
	private @ViewInject(id=R.id.gv_xc_images) GridView gv_xc_images;//巡查图片
	
	private BootstrapButton btn_save;
	//当前已选图片适配器
	public TargetImageGridAdapter adapter;
	public TargetImageGridAdapter xc_adapter;
	/**照片文件绝对路径**/
	public String imageFileName = "",crowid="";
	/**编辑选择的图片**/
	public final int RESULT_DEL_IMAGES = 1234;
	
	private String zlxcId = "";
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_js_zlgl_zlxcresp);
		
		Bundle b =  getIntent().getExtras();
		if (b!=null && b.containsKey("zlxcId")) {
			zlxcId = b.getString("zlxcId");
			//初始化质量巡查
			ProgressDialogUtil.show(this, "正在努力加载中", true);
			new ZlxcService(this).findById(zlxcId,"ZLXCRESP");
		}
		
		String centerTitle = getResources().getString(R.string.ZlxcRespActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		btn_save = (BootstrapButton) this.findViewById(R.id.btn_save);
		et_fksj.setOnTouchListener(this);
		btn_save.setOnClickListener(this);
		
		//巡查图片
		gv_xc_images.setSelector(new ColorDrawable(Color.TRANSPARENT));
		xc_adapter = new TargetImageGridAdapter(this);
		gv_xc_images.setAdapter(xc_adapter);
		gv_xc_images.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				//查看
				Intent intent = new Intent(ZlxcRespActivity.this,PhotoViewActivity.class);
				intent.putExtra("ID", position);
				intent.putExtra("relationId",zlxcId);
				startActivityForResult(intent, RESULT_DEL_IMAGES);
			}
		});
		
		gv_images.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new TargetImageGridAdapter(this);
		adapter.update();
		gv_images.setAdapter(adapter);
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				if (position == Bimp.bmp.size()) {//添加(点击最后一张图片 +号)
					//生成照片文件名
					imageFileName = MediaUtil.generatorFilePath();
					new ImagePopupWindows(ZlxcRespActivity.this,PhotoAlbumActivity.class, gv_images,imageFileName);
				} else {//查看
					Intent intent = new Intent(ZlxcRespActivity.this,PhotoActivity.class);
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
			if (ObjectUtils.isNullOrEmptyString(et_fksj.getText().toString().trim())) {
				ToastUtil.showShortToast(this,"请选择反馈时间");
				break ;
			}
			if (ObjectUtils.isNullOrEmptyString(et_fkr.getText().toString().trim())) {
				ToastUtil.showShortToast(this,"请输入反馈人");
				break ;
			}
			ProgressDialogUtil.show(this,getResources().getString(R.string.handing), true);
			//save
			TJsZlxcResp obj = saveOrUpdate();
			//1.上传数据至服务器
			new ZlxcRespService(this).saveOrUpdate(obj);
			break;
		}
	}
	
	/**
	  * @Title: initZlxc
	  * @Description: 初始化质量巡查信息
	  * @param @param result    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void initZlxc(String result){
		if (!ObjectUtils.isNullOrEmptyString(result)) {
			TJsZlxc obj =  (TJsZlxc)GsonUtils.fromJson(result, TJsZlxc.class);
			if (!ObjectUtils.isNullOrEmptyString(obj.getXmjbxx())) {
				et_xmmc.setText(obj.getXmjbxx().getXmmc());
			}
			//主键
			et_xcsj.setText(DateUtil.format(obj.getXcsj(),DateUtil.PATTERN_DATETIME));
			et_xcdw.setText(obj.getXcdw());
			et_xcr.setText(obj.getXcr());
			et_xcjg.setText(obj.getXcjg());
			et_zgyj.setText(obj.getZgyj());
			//初始化图片
			ProgressDialogUtil.show(this, "正在初始化巡查图片", true);
			new FileService(this).list("T_JS_ZLGL_ZLXC",obj.getCrowid());
			
			//质量巡查反馈
			LinkedHashSet<LinkedTreeMap> set = (LinkedHashSet<LinkedTreeMap>) obj.getZlxcRespSet();
			if (set!=null && set.size()>0) {
				LinkedTreeMap map = set.iterator().next();
				crowid = StringUtil.toString(map.get("crowid"));
				et_fksj.setText(StringUtil.toString(map.get("fksj")));
				et_fkr.setText(StringUtil.toString(map.get("fkr")));
				et_fkqk.setText(StringUtil.toString(map.get("fkqk")));
				
				ProgressDialogUtil.show(this, "正在初始化巡查反馈图片", true);
				new FileService(this).list("T_JS_ZLGL_ZLXCRESP",crowid);
			}
		}
	}
	
	/**  
	  * @Title: save  
	  * @Description: 信息录入 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private TJsZlxcResp saveOrUpdate() {
		String fkr = et_fkr.getText().toString().trim();
		String fksj = et_fksj.getText().toString().trim();
		String fkqk = et_fkqk.getText().toString().trim();
		
		TJsZlxcResp obj = new TJsZlxcResp();
		obj.setZlxcid(zlxcId);
		//添加,编辑
		if (ObjectUtils.isNullOrEmptyString(crowid)) {
			obj.setCrowid(UUID.randomUUID().toString());
		}else{
			obj.setCrowid(crowid);
		}
		if (!ObjectUtils.isNullOrEmptyString(fkr)) {
			obj.setFkr(fkr);
		}
		if (!ObjectUtils.isNullOrEmptyString(fksj)) {
			obj.setFksj(fksj);
		}
		if (!ObjectUtils.isNullOrEmptyString(fkqk)) {
			obj.setFkqk(fkqk);
		}
		JSONObject loginUser = AppContext.LOGINUSER;
		if (loginUser!=null) {
			try {
				obj.setFkdw(loginUser.getString("orgname"));
				obj.setTbdw(loginUser.getString("orgname"));
				obj.setTbr(loginUser.getString("userid"));
				obj.setTbsj(new Date());
			} catch (JSONException e) {
				Log.e("ZlxcRespActivity.onClick() btn_save", "JSONException");
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
			file.setFilePath(FileUtils.absolutePathToFilePath(f.getAbsolutePath(), "T_JS_ZLGL_ZLXCRESP"));
			file.setFileSize(f.length()+"");
			
			String fileType = "";
			String fileName = f.getName();
			if (ObjectUtils.isNotEmpty(fileName)&&(fileName.indexOf(".")>0)) {
				fileType = fileName.substring(fileName.lastIndexOf(".")+1);
			}
			file.setFileType(fileType);
			file.setFileTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			file.setRelationId(obj.getCrowid());
			file.setGroupId("T_JS_ZLGL_ZLXCRESP");
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
		}
		obj.setFiles(files);
		//clear
		Bimp.reset();
		return obj;
	}
	
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
		 }
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (v.getId() == R.id.et_fksj) {
				ProgressDialogUtil.show(this, "正在加载...", true);
				DateTimePickerUtil.showDateTimePicker(this,et_fksj);
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

}
