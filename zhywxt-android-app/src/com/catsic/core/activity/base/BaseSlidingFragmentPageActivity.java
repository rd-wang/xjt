package com.catsic.core.activity.base;

import android.os.Bundle;
import android.widget.TextView;

import com.catsic.core.page.PageRequest;

import java.util.Map;

/**  
  * @Description: 带有滑动Fragment的分页 
  * @author wuxianling  
  * @date 2014年11月17日 上午9:22:44    
  */ 
public class BaseSlidingFragmentPageActivity extends BaseSlidingFragmentActivity{
	
	/**
	 * 分页参数
	 */
	public PageRequest<Map> pageRequest = new PageRequest<Map>();
	
	/**
	 * 信息提示 ： 无数据
	 */
	public TextView tv_msg;

	public BaseSlidingFragmentPageActivity(int titleRes, int contentView,int behindContentView) {
		super(titleRes, contentView, behindContentView);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}



}
