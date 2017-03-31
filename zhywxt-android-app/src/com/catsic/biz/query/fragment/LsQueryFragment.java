package com.catsic.biz.query.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.query.activity.LsListActivity;
import com.catsic.biz.yh.service.LsJbxxService;
import com.catsic.core.AppContext;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**  
  * @Description: 路损查询 
  * @author wuxianling  
  * @date 2014年11月17日 下午2:08:25    
  */ 
@SuppressLint("ValidFragment")
public class LsQueryFragment extends Fragment implements OnClickListener{
	
	private Context context;
	
	public Spinner topSpinner ;
	public Spinner subSpinner;
	
	public EditText et_szdm ;
	public EditText et_lxbm ;
	public EditText et_lxmc ;
	
	public BootstrapButton btn_query;
	public BootstrapButton  btn_reset;
	
	public LsQueryFragment(){}
	
	public LsQueryFragment(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.frame_yh_ls_list_query, null);
		topSpinner = (Spinner) view.findViewById(R.id.TopSpinner);
		subSpinner = (Spinner) view.findViewById(R.id.SubSpinner);
		
		et_szdm = (EditText) view.findViewById(R.id.et_szdm);
		et_lxbm = (EditText) view.findViewById(R.id.et_lxbm);
		et_lxmc = (EditText) view.findViewById(R.id.et_lxmc);
		btn_query = (BootstrapButton) view.findViewById(R.id.btn_query);
		btn_reset = (BootstrapButton) view.findViewById(R.id.btn_reset);
		 
		btn_query.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
		 
		CatsicCodeService catsicCodeService = new CatsicCodeService(context);
		
		//行政区划初始化
		new XzqhService(context).fill(context,topSpinner,subSpinner); 
		return view;
	}

	@Override
	public void onClick(View v) {
		LsListActivity activity = (LsListActivity) context;
		
		switch (v.getId()) {
			case R.id.btn_query:
				Map map = activity.pageRequest.getFilters();
				if (map==null) {
					map = new HashMap();
				}
				
				try {
					String xzqh = new XzqhService(context).getXzqh(AppContext.LOGINUSER.getString("orglevel"),topSpinner,subSpinner);
					if (ObjectUtils.isNullOrEmptyString(xzqh)) {
						xzqh = AppContext.LOGINUSER.getString("xzqh");
					}
					map.put("xzqh", StringUtil.Stringtrim0(xzqh));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				map.put("szdm", StringUtil.toString(et_szdm.getText()));
				map.put("lxbm", StringUtil.toString(et_lxbm.getText()));
				map.put("lxmc", StringUtil.toString(et_lxmc.getText()));
				activity.pageRequest.setFilters(map);
				
				ProgressDialogUtil.show(context, getResources().getString(R.string.loading), true);
				
				//clear
				activity.adapter.listItems.clear();
				
				//loadData
				new LsJbxxService(context).list(activity.pageRequest);
				activity.getSlidingMenu().showContent();
				break;
			case R.id.btn_reset:
				new XzqhService(context).fill(context,topSpinner,subSpinner); 
				et_szdm.setText("");
				et_lxbm.setText("");
				et_lxmc.setText("");
				break;
		}
		
	}
	
}
