package com.catsic.core.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.yh.service.LxService;
import com.catsic.core.AppContext;
import com.catsic.core.activity.LxListActivity;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**  
  * @Description: 路线查询 
  * @author wuxianling  
  * @date 2014年12月12日 下午1:42:11    
  */ 
@SuppressLint("ValidFragment")
public class LxQueryFragment extends Fragment implements OnClickListener{
	
	private Context context;
	
	public EditText et_lxbm ;
	public EditText et_lxmc ;
	
	public BootstrapButton btn_query;
	public BootstrapButton  btn_reset;
	
	public LxQueryFragment(){}
	
	public LxQueryFragment(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.common_fragment_lx_query, null);
		
		et_lxbm = (EditText) view.findViewById(R.id.et_lxbm);
		et_lxmc = (EditText) view.findViewById(R.id.et_lxmc);
		btn_query = (BootstrapButton) view.findViewById(R.id.btn_query);
		btn_reset = (BootstrapButton) view.findViewById(R.id.btn_reset);
		 
		btn_query.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
		 
		return view;
	}

	@Override
	public void onClick(View v) {
		LxListActivity activity = (LxListActivity) context;
		
		switch (v.getId()) {
			case R.id.btn_query:
				Map map = activity.pageRequest.getFilters();
				if (map==null) {
					map = new HashMap();
				}
				
				String tbdw4Query = "";
				try {
					tbdw4Query = AppContext.LOGINUSER.getString("tbdw4Query");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				map.put("tbdwdm", tbdw4Query);
				map.put("lxbm", StringUtil.toString(et_lxbm.getText()));
				map.put("lxmc", StringUtil.toString(et_lxmc.getText()));
				activity.pageRequest.setFilters(map);
				
				ProgressDialogUtil.show(context, getResources().getString(R.string.loading), true);
				
				//clear
				activity.adapter.listItems.clear();
				//loadData
				new LxService(context, activity.mAbPullToRefreshView).getLxs(activity.pageRequest);
				activity.switchContent();
				break;
			case R.id.btn_reset:
				et_lxbm.setText("");
				et_lxmc.setText("");
				break;
		}
	}

}
