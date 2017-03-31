package com.catsic.biz.query.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.yh.activity.LSLocationActivity;
import com.catsic.biz.yh.activity.LSViewActivity;
import com.catsic.biz.yh.bean.LsJbxx;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.StringUtil;

/**  
  * @Description: 路损列表适配器
  * @author wuxianling  
  * @date 2014年11月17日 上午9:55:44    
  */ 
public class LsListViewAdapter extends BaseAdapter implements View.OnClickListener{
	
	private Context context; // 运行上下文
	public List<Map<String, Object>> listItems; // 信息集合
	private LayoutInflater listContainer; // 视图容器
	
	public LsListViewAdapter(Context context,List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}
	
	public final class ListItemView {
		public String crowid;
		public TextView tv_lx;
		public TextView tv_qzzh;
		public TextView tv_fxsj;
		public Button btn_item_location;
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
			convertView = listContainer.inflate(R.layout.frame_yh_ls_list_item, null);
			// 获取控件对象
			listItemView.tv_lx = (TextView) convertView.findViewById(R.id.tv_lx);
			listItemView.tv_qzzh = (TextView) convertView.findViewById(R.id.tv_qzzh);
			listItemView.tv_fxsj = (TextView) convertView.findViewById(R.id.tv_fxsj);
			listItemView.btn_item_location = (Button) convertView.findViewById(R.id.btn_location);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		// 设置文字
		listItemView.crowid = listItems.get(position).get("crowid").toString();
		
		listItemView.tv_lx.setText(listItems.get(position).get("lxmc")+"("+listItems.get(position).get("lxbm")+")");
		listItemView.tv_qzzh.setText("K"+listItems.get(position).get("qdzh")+"~K"+listItems.get(position).get("zdzh"));
		listItemView.tv_fxsj.setText(StringUtil.toString(listItems.get(position).get("fxsjString")));
		listItemView.btn_item_location.setBackgroundResource(R.color.icon_commmon_location_selector);
		
		/**
		 * 定位
		 */
		LsJbxx obj = new LsJbxx();
		obj.setLxbm(StringUtil.Stringtrim(StringUtil.toString(listItems.get(position).get("lxbm"))));
		obj.setLxmc(StringUtil.toString(listItems.get(position).get("lxmc")));
		if (listItems.get(position).get("qdzh")!=null) {
			obj.setQdzh(Float.parseFloat(listItems.get(position).get("qdzh").toString()));
			
		}
		if (listItems.get(position).get("zdzh")!=null) {
			obj.setZdzh(Float.parseFloat(listItems.get(position).get("zdzh").toString()));
		}
		listItemView.btn_item_location.setTag(obj);
		listItemView.btn_item_location.setOnClickListener(this);
		/**
		 * 明细
		 */
		convertView.setOnClickListener(this);
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		ProgressDialogUtil.show(context, "正在加载...", true);
		switch (v.getId()) {
			case R.id.frame_yhxc_ls_list_item:
				ListItemView liv =  (ListItemView) v.getTag();
				
				Intent intent = new Intent(context,LSViewActivity.class);
				intent.putExtra("crowid", liv.crowid);
				context.startActivity(intent);
				break;
			case R.id.btn_location:
				LsJbxx obj = (LsJbxx) v.getTag();
				Intent intentLocation = new Intent(context,LSLocationActivity.class);
				intentLocation.putExtra("lxbm", obj.getLxbm());
				intentLocation.putExtra("lxmc", obj.getLxmc());
				intentLocation.putExtra("qdzh", obj.getQdzh());
				intentLocation.putExtra("zdzh", obj.getZdzh());
				context.startActivity(intentLocation);
				break;
		}
		ProgressDialogUtil.dismiss();
	}

}
