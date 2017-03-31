package com.catsic.core.activity;

import java.io.Serializable;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.catsic.R;
import com.catsic.core.adapter.ImageBucketAdapter;
import com.catsic.core.bean.ImageBucket;
import com.catsic.core.tools.AlbumHelper;
import com.catsic.core.tools.MediaUtil;

/**  
  * @Description:  照片集
  * @author wuxianling  
  * @date 2014年8月22日 下午1:59:54    
  */ 
public class PhotoAlbumActivity extends FinalActivity{
	private @ViewInject(id=R.id.gridview) GridView gridView;
	
	private List<ImageBucket> dataList;
	private ImageBucketAdapter adapter;// 自定义的适配器
	private AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_common_image_bucket);
		
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(getResources(),R.drawable.icon_addpic_unfocused);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		adapter = new ImageBucketAdapter(this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(PhotoAlbumActivity.this,ImageGridActivity.class);
				//传递选中相册的所有相片
				intent.putExtra(PhotoAlbumActivity.EXTRA_IMAGE_LIST,(Serializable) dataList.get(position).imageList);
//				startActivity(intent);
				startActivityForResult(intent, MediaUtil.REQUEST_PHOTO);
				finish();
			}

		});
	}

}

