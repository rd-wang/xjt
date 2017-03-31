
package com.catsic.core.tools;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.catsic.R;

/**
  * @ClassName: ActionBarManager
  * @Description: 对ActionBar管理
  * @author Comsys-wuxianling
  * @date 2015年5月27日 下午3:59:00
  */
public class ActionBarManager {
	
	/**
	 * 设置居中标题
	 * @param context 上下文
	 * @param actionBar actionbar
	 * @param strCenterTitle 中间居中显示标题
	 */
	public static void initTitleCenterActionBar(Context context,ActionBar actionBar,String strCenterTitle){
		LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View centerTitle = inflator.inflate(R.layout.view_actionbar_title, null);
		TextView title = (TextView) centerTitle.findViewById(R.id.actionbar_title);
        title.setText(strCenterTitle);
		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(centerTitle,layoutParams);
	}
	
	/**
	 * 更新ActionBar中间标题
	 * @param context 上下文
	 * @param actionBar actionbar
	 * @param strCenterTitle 中间居中显示标题
	 */
	public static void updateActionCenterTitle(Context context,ActionBar actionBar,String strCenterTitle){
        ((TextView)actionBar.getCustomView().findViewById(R.id.actionbar_title)).setText(strCenterTitle);
	}
	
	/**
	 * 订制一个返回+标题的标题栏
	 * @param context
	 * @param actionBar
	 * @param strCenterTitle
	 */
	public static void initBackTitle(Context context,ActionBar actionBar,String strCenterTitle){
		LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View centerTitle = inflator.inflate(R.layout.view_actionbar_title, null);
		TextView title = (TextView) centerTitle.findViewById(R.id.actionbar_title);
        title.setText(strCenterTitle);
		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		
		actionBar.setTitle("返回");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(centerTitle,layoutParams);
	}

}
