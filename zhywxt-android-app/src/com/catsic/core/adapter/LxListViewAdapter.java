package com.catsic.core.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.LxListActivity;
import com.catsic.core.tools.StringUtil;

/**  
  * @Description: 路线列表适配器 
  * @author wuxianling  
  * @date 2014年12月12日 下午2:00:22    
  */ 
public class LxListViewAdapter extends BaseAdapter implements View.OnClickListener{

	private Context context; // 运行上下文
	public List<Map<String, Object>> listItems; // 信息集合
	private LayoutInflater listContainer; // 视图容器
	
	public LxListViewAdapter(Context context,List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}
	
	public final class ListItemView {
		public String crowid;
		public TextView tv_lxbm;
		public TextView tv_lxmc;
//		public TextView tv_xzqh;
		public String lxjgxzqh;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.common_lx_list_item, null);
			// 获取控件对象
			listItemView.tv_lxbm = (TextView) convertView.findViewById(R.id.tv_lxbm);
			listItemView.tv_lxmc = (TextView) convertView.findViewById(R.id.tv_lxmc);
//			listItemView.tv_xzqh = (TextView) convertView.findViewById(R.id.tv_xzqh);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		// 设置文字
		listItemView.crowid = listItems.get(position).get("crowid").toString();
		
		listItemView.tv_lxbm.setText(StringUtil.toString(listItems.get(position).get("lxbm")));
		listItemView.tv_lxmc.setText(StringUtil.toString(listItems.get(position).get("lxmc")));
//		listItemView.tv_xzqh.setText(StringUtil.toString(listItems.get(position).get("lxjgxzqh")));
		listItemView.lxjgxzqh = StringUtil.toString(listItems.get(position).get("lxjgxzqh"));
		
		/**
		 * 选择
		 */
		convertView.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.common_lx_list_item:
				ListItemView liv =  (ListItemView) v.getTag();
				
				Intent intent = new Intent();
				intent.putExtra("crowid", liv.crowid);
				intent.putExtra("lxbm", StringUtil.toString(liv.tv_lxbm.getText()));
				intent.putExtra("lxmc", StringUtil.toString(liv.tv_lxmc.getText()));
				intent.putExtra("lxjgxzqh", StringUtil.toString(liv.lxjgxzqh));
				
				LxListActivity activity = (LxListActivity) context;
				activity.setResult(AppConstants.$SELECT, intent);  
				activity.finish();
				break;
		}
	}


}
