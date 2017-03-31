package com.catsic.core.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.adapter.ImageGridAdapter;
import com.catsic.core.adapter.ImageGridAdapter.TextCallback;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.ImageItem;
import com.catsic.core.tools.AlbumHelper;
import com.catsic.core.tools.MediaUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**  
  * @Description: 单个相册查看 
  * @author wuxianling  
  * @date 2014年8月22日 下午3:16:28    
  */ 
public class ImageGridActivity extends FinalActivity implements OnClickListener{
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	private @ViewInject(id=R.id.gridview) GridView gridView;
	private @ViewInject(id=R.id.bt) Button bt;
	
	private  List<ImageItem> dataList;
	private ImageGridAdapter adapter;
	private AlbumHelper helper;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					ToastUtil.showLongToast(ImageGridActivity.this, "最多选择 "+AppConstants.IMAGES_CNT+"张图片");
				    break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_image_grid);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		//当前相册所有相片
		dataList = (List<ImageItem>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);
		initView();
	}

	/**
	 * 初始化
	  * @Title: initView  
	  * @Description: TODO 
	  * @param      
	  * @return void   
	  * @throws
	 */
	private void initView() {
		
		bt.setOnClickListener(this);
		
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,mHandler);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				adapter.notifyDataSetChanged();
			}

		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt:
			btnComplete();
			break;
		}
		
	}

	/**  
	  * @Title: btnComplete  
	  * @Description: 完成 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private void btnComplete() {
		/**处理选中图片**/
		ArrayList<String> list = new ArrayList<String>();
		Collection<String> c = adapter.map.values();
		Iterator<String> it = c.iterator();
		for (; it.hasNext();) {
			list.add(it.next());
		}

		//是否需要启动Activity
//		if (Bimp.act_bool) {
//			startActivity(new Intent(ImageGridActivity.this,LSActivity.class));
//			Bimp.act_bool = false;
//		}
		for (int i = 0; i < list.size(); i++) {
			if (Bimp.drr.size() < AppConstants.IMAGES_CNT) {
				Bimp.drr.add(list.get(i));
			}
		}
		
		setResult(MediaUtil.REQUEST_PHOTO);
		
		finish();
	}
}
