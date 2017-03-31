package com.catsic.biz.yh.activity;

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
import com.catsic.biz.yh.bean.LsJbxx;
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

/**  
  * @Description: 养护巡查 Activity
  * @author wuxianling  
  * @date 2014年7月29日 下午5:00:58    
  */ 
public class LSActivity extends BaseActivity implements OnTouchListener,android.view.View.OnClickListener{
	
	private @ViewInject(id=R.id.et_szdm) EditText et_szdm;
	public  @ViewInject(id=R.id.et_shlx) EditText et_shlx;
	private @ViewInject(id=R.id.et_qdzh) EditText et_qdzh;
	private @ViewInject(id=R.id.et_zdzh) EditText et_zdzh;
	private @ViewInject(id=R.id.et_fxsj) EditText et_fxsj;
	private @ViewInject(id=R.id.et_xcqk) EditText et_xcqk;
	private @ViewInject(id=R.id.et_czcs) EditText et_czcs;
	BootstrapButton btn_save;
	private @ViewInject(id=R.id.btn_shlx) ImageView btn_shlx;
	private @ViewInject(id=R.id.gv_images) GridView gv_images;
	
	//当前已选图片适配器
	public TargetImageGridAdapter adapter;
	
	/**
	 * 路线选择
	 */
	public String lxbm = "";
	public String lxmc = "";
	public String lxjgxzqh = "";
	
	/**照片文件绝对路径**/
	public String imageFileName = "";
	
	/**编辑选择的图片**/
	public final int RESULT_DEL_IMAGES = 1234;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yhxc_ls);
		
		String centerTitle = getResources().getString(R.string.LSActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		btn_save = (BootstrapButton) this.findViewById(R.id.btn_save);
		
		et_fxsj.setOnTouchListener(this);
		btn_save.setOnClickListener(this);
		btn_shlx.setOnClickListener(this);
		
		gv_images.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new TargetImageGridAdapter(this);
		adapter.update();
		gv_images.setAdapter(adapter);
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				if (position == Bimp.bmp.size()) {//添加(点击最后一张图片 +号)
					//生成照片文件名
					imageFileName = MediaUtil.generatorFilePath();
					new ImagePopupWindows(LSActivity.this,PhotoAlbumActivity.class, gv_images,imageFileName);
				} else {//查看
					Intent intent = new Intent(LSActivity.this,PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivityForResult(intent, RESULT_DEL_IMAGES);
				}
			}
		});
	}
	
	/**
	 * 拍照,从相册中选择
	 */
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
		 		
		 		et_shlx.setText(lxmc);
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


	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				//save
				if(save()){
					startActivity(new Intent(this,LSListActivity.class));
				}
				break;
			case R.id.btn_shlx:
				startActivityForResult(new Intent(this,LxListActivity.class), AppConstants.$SELECT);
				break;
			}
		
	}
	
	/**  
	  * @Title: save  
	  * @Description: 路损信息录入 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private boolean save() {
		if (ObjectUtils.isNullOrEmptyString(lxbm)) {
			ToastUtil.showShortToast(this, "请选择路线！");
			return false;
		}
		
		LsJbxx obj = new LsJbxx();
		obj.setCrowid(UUID.randomUUID().toString());
		obj.setSzdm(et_szdm.getText().toString().trim());
		obj.setLxbm(lxbm);
		obj.setLxmc(lxmc);
		obj.setXzqh(lxjgxzqh);
		
		String qdzh = et_qdzh.getText().toString().trim();
		String zdzh = et_zdzh.getText().toString().trim();
		String fxsj = et_fxsj.getText().toString();
		if (!ObjectUtils.isNullOrEmptyString(qdzh)) {
			obj.setQdzh(Float.valueOf(qdzh));
		}
		if (!ObjectUtils.isNullOrEmptyString(zdzh)) {
			obj.setZdzh(Float.valueOf(zdzh));
		}
		if (!ObjectUtils.isNullOrEmptyString(fxsj)) {
			obj.setFxsj(fxsj);
		}
		
		obj.setXcqk(et_xcqk.getText().toString());
		obj.setCzcs(et_czcs.getText().toString());
		JSONObject loginUser = AppContext.LOGINUSER;
		if (loginUser!=null) {
			try {
				obj.setTbdwdm(loginUser.getString("orgid"));
				obj.setTbdwmc(loginUser.getString("orgname"));
				obj.setUserid(loginUser.getString("userid"));
				obj.setUsername(loginUser.getString("username"));
				obj.setTbsj(DateUtil.format(new Date(), DateUtil.PATTERN_DATETIME));
				obj.setShbz(ShbzUtils.getShbzByUser(loginUser.getString("orglevel")));
			} catch (JSONException e) {
				Log.e("LsActivity.onClick() btn_save", "JSONException");
				e.printStackTrace();
			}
		}
		
		FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
		db.save(obj);
		
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
			file.setRelationId(obj.getCrowid());
			file.setGroupId("T_LS");
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
				if (v.getId() == R.id.et_fxsj) {
					ProgressDialogUtil.show(this, "正在加载...", true);
					DateTimePickerUtil.showDateTimePicker(this,et_fxsj);
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
