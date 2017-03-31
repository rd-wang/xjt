package com.catsic.biz.task.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.widget.ListView;

import com.catsic.R;
import com.catsic.biz.task.adapter.TaskListAdapter;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.DateUtil;

/**  
  * @Description: 任务中心 
  * @author wuxianling  
  * @date 2014年8月28日 下午2:11:29    
  */ 
public class TaskListActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.lv_tasklist) ListView  lv_tasklist;
	public TaskListAdapter taskListAdapter;
	private List<Map<String, Object>> listItems;   
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_task_list);
		
		String centerTitle = getResources().getString(R.string.TaskListActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
		listItems = initItems();   
		taskListAdapter = new TaskListAdapter(this, listItems); //创建适配器   
		lv_tasklist.setAdapter(taskListAdapter);
	}
	
	 /**  
     * @Title: initItems  
     * @Description: 初始化Item 
     * @param @return     
     * @return List<Map<String,Object>>   
     * @throws  
     */ 
   private List<Map<String, Object>> initItems() {   
       List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();   
       
       Map<String, Object> map = new HashMap<String, Object>();    
       map.put("crowid",1);
       map.put("taskname", "标志标牌大风刮倒");   
       map.put("createtime", DateUtil.format(new Date(), DateUtil.PATTERN_DATE));
       listItems.add(map);   
       
       Map<String, Object> map1 = new HashMap<String, Object>();    
       map1.put("crowid",1);
       map1.put("taskname", "新建村三叉路口坑洼");   
       map1.put("createtime", DateUtil.format(new Date(), DateUtil.PATTERN_DATE));
       listItems.add(map1);   
       
       
       return listItems;   
   }   

}
