package com.catsic.core.custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.catsic.R;
import com.catsic.core.tools.MediaUtil;

/**  
  * @Description: 图片选择，弹出菜单框 
  * @author wuxianling  
  * @date 2014年8月22日 下午2:27:42    
  */ 
public class ImagePopupWindows extends PopupWindow{
	
	public ImagePopupWindows(Activity activity,Class target,View parent,final String fileName) {
		
		final Activity tmpActivity = activity;
		final Class targetClazz= target;

		//可自定义布局
		View view = View.inflate(activity, R.layout.common_item_popupwindows, null);
		view.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.push_bottom_in_2));

		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.FILL_PARENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();

		class ClickListener implements View.OnClickListener{
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.item_popupwindows_camera:
						MediaUtil.camera(tmpActivity, MediaUtil.RESULT_CAPTURE_IMAGE,fileName);
						break;
					case R.id.item_popupwindows_Photo:
//						tmpActivity.startActivity(new Intent(tmpActivity,targetClazz));
						Intent intent = new Intent(tmpActivity,targetClazz);
						tmpActivity.startActivityForResult(intent, MediaUtil.REQUEST_PHOTO);
						break;
					case R.id.item_popupwindows_cancel:
						
						break;
				}
				dismiss();
			}
			
		}
		
		ClickListener listener = new ClickListener();
		
		//处理相应按钮事件
		Button btn_camera = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button btn_photo = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button btn_cancel = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		
		btn_camera.setOnClickListener(listener);
		btn_photo.setOnClickListener(listener);
		btn_cancel.setOnClickListener(listener);
	}
}
