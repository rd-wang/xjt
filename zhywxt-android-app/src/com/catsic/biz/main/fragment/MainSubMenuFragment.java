package com.catsic.biz.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.catsic.R;
import com.catsic.biz.main.adapter.MainSubMenuAdapter;
import com.catsic.core.AppContext;
import com.catsic.core.bean.Menu;
import com.catsic.core.conf.MenuEnum;

import java.util.Map;

/**  
  * @Description: 自定义主页具体 Fragment
  *             以ListView实现，并且简单易配置
  * @author wuxianling  
  * @date 2014年7月29日 下午4:57:09    
  */ 
public class MainSubMenuFragment extends Fragment{
	
		//当前选中的Fragment的下标
		private int index;
		
	    public static Menu [][] menus = {}; //菜单
	    
	    static{
	    	 for (MenuEnum menuEnum : MenuEnum.values()) {
				if(AppContext.SYS_CODE.equals(menuEnum.getSysCode())) {
					menus = menuEnum.getMenus();
					break;
				}
			 }
	    }

	    public static MainSubMenuFragment newInstance(int index) {
	    	MainSubMenuFragment fragment = new MainSubMenuFragment();
	    	fragment.index = index;
	        return fragment;
	    }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	
	    	ListView listView = (ListView) inflater.inflate(R.layout.main_listview, null);
	    	listView.setAdapter(new MainSubMenuAdapter(getActivity(),menus[index]));
	    	listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					Intent intent = new Intent(getActivity(),menus[index][position].getClazz());
					Map<String,String> p = menus[index][position].getParam();
					if (p!=null) {
						Bundle bundle = new Bundle();
						for (Map.Entry<String, String> entry : p.entrySet()) {
							 bundle.putString(entry.getKey(), entry.getValue());
						}
						intent.putExtras(bundle);
					}
					startActivity(intent);
				}
	    		
	    	});
	    	
	        LinearLayout layout = new LinearLayout(getActivity());
	        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	        layout.setGravity(Gravity.LEFT);
	        layout.addView(listView);

	        return layout;
	    }
	    
	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	    }

}
