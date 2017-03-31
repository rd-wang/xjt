package com.catsic.biz.js.fragment;


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
import com.catsic.biz.js.activity.XmjbxxListActivity;
import com.catsic.biz.js.service.XmjbxxService;
import com.catsic.core.AppContext;
import com.catsic.core.bean.CatsicCode;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**  
  * @Description: 项目查询Fragment 
  * @author wuxianling  
  * @date 2014年9月18日 上午9:39:16    
  */ 
@SuppressLint("ValidFragment")
public class XmjbxxQueryFragment extends Fragment implements OnClickListener{
	
	private Context context;
	
	public Spinner sp_jhnf;
	public Spinner sp_xmlxdm ;
	public Spinner topSpinner ;
	public Spinner subSpinner;
	
	public EditText et_xmmc ;
	
	public BootstrapButton btn_query;
	public BootstrapButton  btn_reset;
	
	public XmjbxxQueryFragment(){}
	
	public XmjbxxQueryFragment(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.frame_jsjc_xmjbxx_list_query, null);
		sp_jhnf =  (Spinner) view.findViewById(R.id.sp_jhnf);
		sp_xmlxdm = (Spinner) view.findViewById(R.id.sp_xmlxmc);
		topSpinner = (Spinner) view.findViewById(R.id.TopSpinner);
		subSpinner = (Spinner) view.findViewById(R.id.SubSpinner);
		
		et_xmmc = (EditText) view.findViewById(R.id.et_xmmc);
		btn_query = (BootstrapButton) view.findViewById(R.id.btn_query);
		btn_reset = (BootstrapButton) view.findViewById(R.id.btn_reset);
		 
		btn_query.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
		 
		CatsicCodeService catsicCodeService = new CatsicCodeService(context);
		
		//字典:计划年份，项目类型 初始化
		catsicCodeService.fill(context, sp_jhnf, "tc_jhnf", getResources().getString(R.string.hint_jhnf));
		catsicCodeService.fill(context, sp_xmlxdm, "tc_xmlx", getResources().getString(R.string.hint_xmlx));
		
		//行政区划初始化
		new XzqhService(context).fill(context,topSpinner,subSpinner); 
		 
		return view;
	}

	@Override
	public void onClick(View v) {
		XmjbxxListActivity activity = (XmjbxxListActivity) context;
		
		switch (v.getId()) {
			case R.id.btn_query:
				Map map = activity.pageRequest.getFilters();
				if (map==null) {
					map = new HashMap();
				}
				
				CatsicCode jhnfDic = (CatsicCode) sp_jhnf.getSelectedItem();
				CatsicCode xmlxdmDic = (CatsicCode) sp_xmlxdm.getSelectedItem();
				
				map.put("jhnf", StringUtil.toString(jhnfDic.getXxdm()));
				map.put("xmlxdm", StringUtil.toString(xmlxdmDic.getXxdm()));
				try {
					String xzqh = new XzqhService(context).getXzqh(AppContext.LOGINUSER.getString("orglevel"),topSpinner,subSpinner);
					if (ObjectUtils.isNullOrEmptyString(xzqh)) {
						xzqh = AppContext.LOGINUSER.getString("xzqh");
					}
					map.put("xzqh", StringUtil.Stringtrim0(xzqh));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				map.put("xmmc", StringUtil.toString(et_xmmc.getText()));
				activity.pageRequest.setFilters(map);
				
				ProgressDialogUtil.show(context, getResources().getString(R.string.loading), true);
				
				//clear
				activity.adapter.listItems.clear();
				
				//loadData
				activity.pageRequest.setPageNumber(1);
				new XmjbxxService(context).list(activity.pageRequest);

				activity.getSlidingMenu().showContent();
				break;
			case R.id.btn_reset:
				sp_jhnf.setSelection(0);
				sp_xmlxdm.setSelection(0);
				new XzqhService(context).fill(context,topSpinner,subSpinner); 
				et_xmmc.setText("");
				break;

		}
		
	}
	
}
