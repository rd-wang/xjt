package com.catsic.core.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.catsic.R;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.bean.Bimp;
import com.catsic.core.tools.FileUtils;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**  
  * @Description: 图片预览，删除 
  * @author wuxianling  
  * @date 2014年8月22日 下午4:51:52    
  */ 
public class PhotoActivity extends BaseActivity implements OnClickListener{ 
	
	private @ViewInject(id=R.id.viewpager) ViewPager pager;
	private @ViewInject(id=R.id.photo_bt_exit)  Button photo_bt_exit;
	private @ViewInject(id=R.id.photo_bt_del)   Button phtot_bt_del;
	private @ViewInject(id=R.id.photo_bt_enter) Button photo_bt_enter;
	
	private @ViewInject(id=R.id.photo_relativeLayout) RelativeLayout photo_relativeLayout;
	
	private ArrayList<View> listViews = null;
	private MyPageAdapter adapter;
	private int count;

	/**选择图片相关数据**/
	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	public int max;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		photo_bt_exit.setOnClickListener(this);
		phtot_bt_del.setOnClickListener(this);
		photo_bt_enter.setOnClickListener(this);
		pager.setOnPageChangeListener(pageChangeListener);

		photo_relativeLayout.setBackgroundColor(0x70000000);
		
		//init 
		bmp.addAll(Bimp.bmp);
		drr.addAll(Bimp.drr);
		max = Bimp.max;
		
		for (int i = 0; i < bmp.size(); i++) {
			initListViews(bmp.get(i));//
		}

		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		int id = getIntent().getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		ImageView img = new ImageView(this);// 构造textView对象
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		listViews.add(img);// 添加view
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {// 页面选择响应函数
			count = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

		}

		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

		}
	};

	class MyPageAdapter extends PagerAdapter {

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

	/**  
	  * @Title: btn_del  
	  * @Description: 删除操作 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	private void btn_del() {
		if (listViews.size() == 1) {
			Bimp.reset();
			FileUtils.deleteDir();
			finish();
		} else {
			String newStr = drr.get(count).substring(drr.get(count).lastIndexOf("/") + 1, drr.get(count).lastIndexOf("."));
			bmp.remove(count);
			drr.remove(count);
			del.add(newStr);
			max--;
			pager.removeAllViews();
			listViews.remove(count);
			adapter.setListViews(listViews);
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.photo_bt_exit:
				finish();
				break;
			case R.id.photo_bt_del:
				btn_del();
				break;
			case R.id.photo_bt_enter:
				Bimp.bmp = bmp;
				Bimp.drr = drr;
				Bimp.max = max;
				for(int i=0;i<del.size();i++){				
					FileUtils.delFile(del.get(i)+".JPEG"); 
				}
				finish();
				break;
		}
	}


}
