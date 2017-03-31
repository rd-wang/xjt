package com.catsic.biz.js.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.js.activity.ZlxcActivity;
import com.catsic.biz.js.activity.ZlxcRespActivity;
import com.catsic.biz.yh.activity.LSViewActivity;
import com.catsic.biz.yh.adapter.LSListViewAdapter.ListItemView;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.tools.ToastUtil;

/**
  * @ClassName: ZlxcListViewAdapter
  * @Description: 质量巡查列表Adapter
  * @author catsic-wuxianling
  * @date 2015年9月24日 下午2:58:23
  */
public class ZlxcListViewAdapter extends BaseAdapter  implements View.OnClickListener{
	
	private Context context; // 运行上下文
	public  List<Map<String, Object>> listItems; // 信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public String crowid;
		public String xmid;
		public int zlxcRespSize;
		public TextView tv_xmmc;
		public TextView tv_xzqh;
		public TextView tv_xmlxmc;
		private TextView tv_xcsj;
		public Button btn_item_update;
		public Button btn_item_resp;
	}

	public ZlxcListViewAdapter(Context context,List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
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
			convertView = listContainer.inflate(R.layout.frame_zlgl_zlxc_list_item, null);
			
			//通知单编辑
			listItemView.btn_item_update = (Button) convertView.findViewById(R.id.btn_zlxc_item_update);
			listItemView.btn_item_update.setBackgroundResource(R.color.icon_js_zlxc_selector);
			//通知单反馈
			listItemView.btn_item_resp = (Button) convertView.findViewById(R.id.btn_zlxc_item_resp);
			listItemView.btn_item_resp.setBackgroundResource(R.color.icon_commmon_location_selector);
			// 获取控件对象
			listItemView.tv_xmmc = (TextView) convertView.findViewById(R.id.tv_xmmc);
			listItemView.tv_xzqh = (TextView) convertView.findViewById(R.id.tv_xzqh);
			listItemView.tv_xmlxmc = (TextView) convertView.findViewById(R.id.tv_xmlxmc);
			listItemView.tv_xcsj = (TextView) convertView.findViewById(R.id.tv_xcsj);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字
		listItemView.crowid = listItems.get(position).get("crowid").toString();
		listItemView.xmid = listItems.get(position).get("xmid").toString();
		listItemView.zlxcRespSize = Integer.parseInt(listItems.get(position).get("zlxcRespSize").toString());
		
		listItemView.tv_xmmc.setText(StringUtil.toString(listItems.get(position).get("xmmc")));
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xzqh"))) {
			String xzqhmc = new XzqhService(context).translate(listItems.get(position).get("xzqh").toString());
			listItemView.tv_xzqh.setText(xzqhmc);
		}
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xmlxdm"))) {
			String xmlxmc = new CatsicCodeService(context).translate("tc_xmlx",listItems.get(position).get("xmlxdm").toString());
			listItemView.tv_xmlxmc.setText(xmlxmc);
		}
		listItemView.tv_xcsj.setText(StringUtil.toString(listItems.get(position).get("xcsj")));
		
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("zlxcRespSize"))) {
			int zlxcRespSize = Integer.parseInt(listItems.get(position).get("zlxcRespSize").toString());
			//相关单位已经有反馈
			if (zlxcRespSize>0) {
				listItemView.btn_item_update.setVisibility(View.INVISIBLE);
				listItemView.btn_item_resp.setVisibility(View.VISIBLE);
			}else{
				listItemView.btn_item_update.setVisibility(View.VISIBLE);
				listItemView.btn_item_resp.setVisibility(View.VISIBLE);
			}
		}
		listItemView.btn_item_update.setTag(listItemView);
		listItemView.btn_item_resp.setTag(listItemView);
		listItemView.btn_item_update.setOnClickListener(this);
		listItemView.btn_item_resp.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		ProgressDialogUtil.show(context, "正在加载...", true);
		switch (v.getId()) {
			//修改通知单
			case R.id.btn_zlxc_item_update:
				ListItemView liv =  (ListItemView) v.getTag();
				if (liv.zlxcRespSize<=0) {
					Intent intent = new Intent(context,ZlxcActivity.class);
					intent.putExtra("crowid", liv.crowid);
					context.startActivity(intent);
				}else{
					ToastUtil.showShortToast(context, "整改通知单已得到反馈，无法修改");
				}
				break;
			//通知单反馈
			case R.id.btn_zlxc_item_resp:
				ListItemView liv1 =  (ListItemView) v.getTag();
				Intent intent1 = new Intent(context,ZlxcRespActivity.class);
				intent1.putExtra("zlxcId", liv1.crowid);
				context.startActivity(intent1);
				break;
		}
		ProgressDialogUtil.dismiss();
		
		
	}
	
}
