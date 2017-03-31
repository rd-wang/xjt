package com.catsic.biz.lz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.lz.adapter.LzXcListViewAdapter;
import com.catsic.biz.lz.adapter.LzXcListViewAdapter.State;
import com.catsic.biz.lz.bean.LzXcjl;
import com.catsic.biz.lz.service.LzXcjlService;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.StringUtil;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LzXcjlListActivity extends BaseActivity{

	private @ViewInject(id=R.id.listView) ListView listView;   
    public LzXcListViewAdapter adapter;   
    /**
	 * 信息提示 ： 无数据
	 */
	public @ViewInject(id=R.id.tv_msg) TextView tv_msg;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_common_list);   
	    String centerTitle = getResources().getString(R.string.LzXcjlListActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		List<Map<String, Object>> listItems = initItems();
        adapter = new LzXcListViewAdapter(this, listItems); //创建适配器   
        listView.setAdapter(adapter);
        //无记录
		if (adapter.listItems == null || adapter.listItems.size() == 0) {
			tv_msg.setVisibility(View.VISIBLE);
		}else{
			tv_msg.setVisibility(View.INVISIBLE);
		}
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
        FinalDb db = FinalDb.create(this,AppConstants.DB_NAME);
        List<LzXcjl> list =  db.findAll(LzXcjl.class,"xzqh desc");
        for(int i = 0; i < list.size(); i++) {   
            Map<String, Object> map = new HashMap<String, Object>();    
            LzXcjl obj = list.get(i);
            map.put("crowid",obj.getCrowid());
            map.put("lxbm", StringUtil.Stringtrim(obj.getLxbm()));   
            map.put("lxmc", obj.getLxmc());
            map.put("fzr", obj.getFzr());
            map.put("kssj", obj.getXcsjStart());
            map.put("jssj", obj.getXcsjEnd());
            map.put("shbz", obj.getShbz());
            listItems.add(map);   
        }      
        return listItems;   
    }   
       
    /**
     * 初始化菜单栏的新增 删除按钮
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.common_addordelete_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
			case R.id.action_add:
				startActivity(new Intent(this, LzXcjlActivity.class));
				break;
			case R.id.action_del:
				if (adapter.isChecked()) {
					//批量删除
					new LzXcjlService(this).operationDel(adapter.listItems, adapter.hasChecked);
				}else{
					//pre
					adapter.state = State.BATCH;
				}
				adapter.notifyDataSetChanged();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		if (adapter.state == State.NORMAL) {
			super.onBackPressed();
		}else{
			adapter.state = State.NORMAL;
			adapter.notifyDataSetChanged();
		}
	}
	
}
