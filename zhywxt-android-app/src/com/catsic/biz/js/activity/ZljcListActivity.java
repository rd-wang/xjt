
package com.catsic.biz.js.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.catsic.R;
import com.catsic.biz.js.adapter.ZljcListViewAdapter;
import com.catsic.biz.js.bean.TZljc;
import com.catsic.biz.js.service.ZljcService;
import com.catsic.core.AppConstants;
import com.catsic.core.AppConstants.State;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.DateUtil;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @ClassName: ZljcListActivity
  * @Description: 质量检测列表
  * @author catsic-wuxianling
  * @date 2015年8月18日 下午5:23:43
  */
public class ZljcListActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.listView) ListView listView;   
    public  ZljcListViewAdapter listViewAdapter;
	public List<Map<String, Object>> listItems;
    
    private String jclb;
    private String xmid;
    private String xmmc;
    private String xmlxdm;
    private String xzqh;
    private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_list);

		Bundle bundle =  getIntent().getExtras();
		if (bundle!=null) {
			jclb = bundle.getString("jclb");
			xmid = bundle.getString("xmid");
			xmmc = bundle.getString("xmmc");
			xmlxdm = bundle.getString("xmlxdm");
			xzqh = bundle.getString("xzqh");
			title = bundle.getString("title");
		}
		
        ActionBarManager.initBackTitle(this, getActionBar(), "质量检测-"+title);
        
        listItems = initItems();   
        listViewAdapter = new ZljcListViewAdapter(this, listItems); //创建适配器   
        listView.setAdapter(listViewAdapter);

		super.onCreate(savedInstanceState);
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
       List<TZljc> list =  db.findAllByWhere(TZljc.class,"xmid='"+xmid+"' and jclb='"+jclb+"' order by jcsj desc");
       if (list!=null && list.size()>0) {
    	   for(int i = 0; i < list.size(); i++) {   
    		   Map<String, Object> map = new HashMap<String, Object>();    
    		   TZljc obj = list.get(i);
    		   map.put("crowid",obj.getCrowid());
    		   map.put("xmid",xmid);
    		   map.put("xmmc",xmmc);
    		   map.put("xmlxdm",xmlxdm);
    		   map.put("xzqh",xzqh);
    		   map.put("jcsj",DateUtil.format(obj.getJcsj(), DateUtil.PATTERN_DATETIME));
    		   map.put("result",obj.getResult());
    		   map.put("shbz",obj.getShbz());
    		   listItems.add(map);   
    	   }      
	   }
       return listItems;   
   }   
   
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
				Intent intent = null;
				if (AppConstants.ZLJC_YSD.equals(jclb)) {
					intent = new Intent(this, ZljcYsdActivity.class);
				}else if (AppConstants.ZLJC_WCCS.equals(jclb)) {
					intent = new Intent(this, ZljcWccsActivity.class);
				}
				Bundle bundle = new Bundle();
				bundle.putString("xmid", xmid);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.action_del:
				if (listViewAdapter.isChecked()) {
					//批量删除
					new ZljcService(this).operationDel(listItems, listViewAdapter.hasChecked);
				}else{
					//pre
					listViewAdapter.state = State.BATCH;
				}
				listViewAdapter.notifyDataSetChanged();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
   
   @Override
	public void onBackPressed() {
		if (listViewAdapter.state == State.NORMAL) {
			super.onBackPressed();
		}else{
			listViewAdapter.state = State.NORMAL;
			listViewAdapter.notifyDataSetChanged();
		}
	}


}
