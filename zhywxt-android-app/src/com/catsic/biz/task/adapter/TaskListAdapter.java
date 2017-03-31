package com.catsic.biz.task.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.task.activity.TaskLocationActivity;

/**  
  * @Description: 任务列表适配器 
  * @author wuxianling  
  * @date 2014年8月28日 下午2:11:07    
  */ 
public class TaskListAdapter extends BaseAdapter{
	
	private List<Map<String, Object>> listItems; 
	private LayoutInflater listContainer; // 视图容器
	private Context  context;
	
	public final class ListItemView{
		public String crowid;
		public TextView tv_taskName;
		public TextView tv_createTime;
		public Button btn_item_go;
		public Button btn_item_upload;
	}
	
	public TaskListAdapter(Context context,List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.e("method", "getView");
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.activity_task_list_item, null);
			
			// 获取控件对象
			listItemView.tv_taskName = (TextView) convertView.findViewById(R.id.tv_taskname);
			listItemView.tv_createTime = (TextView) convertView.findViewById(R.id.tv_createTime);
			listItemView.btn_item_go = (Button) convertView.findViewById(R.id.btn_item_go);
			listItemView.btn_item_upload = (Button) convertView.findViewById(R.id.btn_item_upload);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		// 设置文字
		listItemView.crowid = listItems.get(position).get("crowid").toString();
		listItemView.tv_taskName.setText(listItems.get(position).get("taskname").toString());
		listItemView.tv_createTime.setText(listItems.get(position).get("createtime").toString());
		listItemView.btn_item_go.setBackgroundResource(R.color.icon_commmon_location_selector);
		listItemView.btn_item_upload.setBackgroundResource(R.color.icon_common_upload_selector);
		
		
		class btnClickListener implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.btn_item_go:
						context.startActivity(new Intent(context, TaskLocationActivity.class));
						break;
					case R.id.btn_item_upload:
						break;
				}
				
			}
			
		}
		
		btnClickListener btnClickListener = new btnClickListener();
		
		listItemView.btn_item_go.setOnClickListener(btnClickListener);
		listItemView.btn_item_upload.setOnClickListener(btnClickListener);
		
		return convertView;
	}

}
