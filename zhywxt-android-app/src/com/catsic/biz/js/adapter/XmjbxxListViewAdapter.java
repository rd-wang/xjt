package com.catsic.biz.js.adapter;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.js.activity.ZljcListActivity;
import com.catsic.core.AppConstants;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.StringUtil;
/**  
  * @Description: 项目信息Adapter 
  * @author wuxianling  
  * @date 2014年9月15日 下午4:54:43    
  */ 
public class XmjbxxListViewAdapter extends BaseAdapter{
	
	private Context context; // 运行上下文
	public List<Map<String, Object>> listItems; // 信息集合
	private LayoutInflater listContainer; // 视图容器
	private String from;//来源

	public final class ListItemView { // 自定义控件集合
		public String crowid;
		public TextView tv_xmmc;
		public TextView tv_xzqh;
		public TextView tv_xmlxmc;
		public Button btn_item_go;
	}

	public XmjbxxListViewAdapter(Context context,List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}
	
	public XmjbxxListViewAdapter(Context context,List<Map<String, Object>> listItems,String from) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.from = from;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * ListView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int selectID = position;
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			//质量检测
			if ("zljc".equals(from)) {
				convertView = listContainer.inflate(R.layout.frame_zljc_xmjbxx_list_item, null);
				
				listItemView.btn_item_go = (Button) convertView.findViewById(R.id.btn_zljc_item_go);
				listItemView.btn_item_go.setBackgroundResource(R.color.icon_js_zljc_selector);
			}
			else{
				convertView = listContainer.inflate(R.layout.frame_jsjc_xmjbxx_list_item, null);
			}
			// 获取控件对象
			listItemView.tv_xmmc = (TextView) convertView.findViewById(R.id.tv_xmmc);
			listItemView.tv_xzqh = (TextView) convertView.findViewById(R.id.tv_xzqh);
			listItemView.tv_xmlxmc = (TextView) convertView.findViewById(R.id.tv_xmlxmc);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		// 设置文字
		listItemView.crowid = listItems.get(position).get("xmid").toString();
		listItemView.tv_xmmc.setText(StringUtil.toString(listItems.get(position).get("xmmc")));
		
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xzqh"))) {
			String xzqhmc = new XzqhService(context).translate(listItems.get(position).get("xzqh").toString());
			listItemView.tv_xzqh.setText(xzqhmc);
		}
		
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xmlxdm"))) {
			String xmlxmc = new CatsicCodeService(context).translate("tc_xmlx",listItems.get(position).get("xmlxdm").toString());
			listItemView.tv_xmlxmc.setText(xmlxmc);
		}
		
		
		/**
		  * @ClassName: btnClickListener
		  * @Description: 质量检测 操作
		  * @author catsic-wuxianling
		  * @date 2015年8月31日 上午10:15:09
		  */
		class btnClickListener implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				final String [] titles = new String[]{"压实度测试","弯沉测试"};
				switch (v.getId()) {
				    //质量检测
					case R.id.btn_zljc_item_go:
						new AlertDialog.Builder(context)
						.setTitle("检测项")  
						.setSingleChoiceItems(titles, 0, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(context,ZljcListActivity.class);
								Bundle bundle = new Bundle();
								//压实度测试
								if (which ==0) {
//									 intent = new Intent(context,ZljcYsdActivity.class);
									 bundle.putString("jclb", AppConstants.ZLJC_YSD);
								}else if (which==1) {//弯沉测试
//									 intent = new Intent(context,ZljcWccsActivity.class);
									 bundle.putString("jclb", AppConstants.ZLJC_WCCS);
								}
								bundle.putString("title", titles[which]);
								bundle.putString("xmid", listItems.get(selectID).get("xmid").toString());
								bundle.putString("xmmc", listItems.get(selectID).get("xmmc").toString());
								bundle.putString("xmlxdm", listItems.get(selectID).get("xmlxdm").toString());
								bundle.putString("xzqh", listItems.get(selectID).get("xzqh").toString());
								intent.putExtras(bundle);
								context.startActivity(intent);
								dialog.dismiss();
							}
						})  
						.setNegativeButton("取消",new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})  
						.show();  
						break;
				}
				
			}
			
		}
		
		//质量检测
		if ("zljc".equals(from)) {
			btnClickListener btnClickListener = new btnClickListener();
			listItemView.btn_item_go.setOnClickListener(btnClickListener);
		}
		return convertView;
	}
	
}
