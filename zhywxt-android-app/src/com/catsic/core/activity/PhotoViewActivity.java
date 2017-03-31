package com.catsic.core.activity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.catsic.R;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.FileService;

/**  
  * @Description: 图片预览 
  * @author wuxianling  
  * @date 2014年11月15日 下午2:20:18    
  */ 
public class PhotoViewActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.viewpager) ViewPager pager;
	
	//显示视图
	private ArrayList<View> listViews = null;
	public MyPageAdapter adapter;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoview);
		
		String relationId = getIntent().getStringExtra("relationId");
		//当前显示postion
		position = getIntent().getIntExtra("ID", 0);
		try {
			if (listViews == null)
				listViews = new ArrayList<View>();
			
			List<Tfile> fileList = new FileService(this).getLocalFileList(relationId, "fileTime asc");
			if (fileList==null ||fileList.size() == 0) {
				new FileService(this).list("",relationId);
			}else{
				initData(fileList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * @Title: initData
	  * @Description: 初始化数据
	  * @param @param fileList    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public void initData(List<Tfile> fileList){
		try {
			if (fileList!=null && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					ImageView img = new ImageView(this);// 构造textView对象
					img.setBackgroundColor(0xff000000);
					FileInputStream fis;
					fis = new FileInputStream(new File(fileList.get(i).getFilePath()));
					img.setImageBitmap(BitmapFactory.decodeStream(fis));
					fis.close();
					img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
					listViews.add(img);// 添加view
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		pager.setCurrentItem(position);
	}
	
	public class MyPageAdapter extends PagerAdapter {
		private ArrayList<View> listViews;// content
		private int size;// 页数

		public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
			// 初始化viewpager的时候给的一个页面
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {// 返回数量
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
			((ViewPager) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View view, int arg1) {// 返回view对象
			try {
				((ViewPager) view).addView(listViews.get(arg1 % size), 0);
			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

}
