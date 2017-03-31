package com.catsic.biz.js.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.js.activity.ZlxcListActivity;
import com.catsic.biz.js.service.ZlxcService;
import com.catsic.core.AppContext;
import com.catsic.core.bean.CatsicCode;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.DateTimePickerUtil;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
  * @ClassName: ZlxcQueryFragment
  * @Description: 质量巡查Fragment
  * @author catsic-wuxianling
  * @date 2015年9月24日 下午3:11:23
  */
@SuppressLint("ValidFragment")
public class ZlxcQueryFragment extends Fragment implements OnClickListener,OnTouchListener{
	
	private Context context;
	
	public Spinner sp_jhnf;
	public Spinner sp_xmlxdm ;
	public Spinner topSpinner ;
	public Spinner subSpinner;
	
	public EditText et_xmmc ;
	public EditText et_startTime;
	public EditText et_endTime;
	
	public BootstrapButton btn_query;
	public BootstrapButton  btn_reset;
	
	public ZlxcQueryFragment(){}
	
	public ZlxcQueryFragment(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.frame_js_zlxc_list_query, null);
		sp_jhnf =  (Spinner) view.findViewById(R.id.sp_jhnf);
		sp_xmlxdm = (Spinner) view.findViewById(R.id.sp_xmlxmc);
		topSpinner = (Spinner) view.findViewById(R.id.TopSpinner);
		subSpinner = (Spinner) view.findViewById(R.id.SubSpinner);
		
		et_xmmc = (EditText) view.findViewById(R.id.et_xmmc);
		et_startTime = (EditText) view.findViewById(R.id.et_startTime);
		et_endTime = (EditText) view.findViewById(R.id.et_endTime);
		btn_query = (BootstrapButton) view.findViewById(R.id.btn_query);
		btn_reset = (BootstrapButton) view.findViewById(R.id.btn_reset);
		 
		et_startTime.setOnTouchListener(this);
		et_endTime.setOnTouchListener(this);
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

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		ZlxcListActivity activity = (ZlxcListActivity) context;
		
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
				map.put("startTime",StringUtil.toString(et_startTime.getText()));
				map.put("endTime",StringUtil.toString(et_endTime.getText()));
				activity.pageRequest.setFilters(map);
				
				ProgressDialogUtil.show(context, getResources().getString(R.string.loading), true);
				
				//clear
				activity.adapter.listItems.clear();
				
				//loadData
				activity.pageRequest.setPageNumber(1);
				new ZlxcService(context).list(activity.pageRequest);
				
				activity.getSlidingMenu().showContent();
				break;
			case R.id.btn_reset:
				sp_jhnf.setSelection(0);
				sp_xmlxdm.setSelection(0);
				new XzqhService(context).fill(context,topSpinner,subSpinner); 
				et_xmmc.setText("");
				et_startTime.setText("");
				et_endTime.setText("");
				break;
		}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (v.getId() == R.id.et_startTime) {
					ProgressDialogUtil.show(context, "正在加载...", true);
					DateTimePickerUtil.showDatePicker(context,et_startTime);
					ProgressDialogUtil.dismiss();
				}
				else if (v.getId() == R.id.et_endTime) {
					ProgressDialogUtil.show(context, "正在加载...", true);
					DateTimePickerUtil.showDatePicker(context,et_endTime);
					ProgressDialogUtil.dismiss();
				}
				break;
		}
		return false;
	}
	
}
