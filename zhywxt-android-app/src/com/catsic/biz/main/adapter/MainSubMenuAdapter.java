package com.catsic.biz.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.bean.Menu;

/**  
  * @Description: ListView 的item适配器 
  * @author wuxianling  
  * @date 2014年7月29日 下午5:00:23    
  */ 
public class MainSubMenuAdapter extends BaseAdapter{
	
	 //上下文对象 
    private Context context; 
    private Menu [] menus;
    
    private LayoutInflater listContainer; // 视图容器
    
    public final class ListItemView { // 自定义控件集合
		public ImageView iv_icon;
		public TextView tv_title;
		public ImageView iv_go;
	}
    
    public MainSubMenuAdapter(Context context,Menu [] menus){ 
        this.context = context; 
        this.menus = menus;
        listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
    } 

	@Override
	public int getCount() {
		return menus.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.common_simple_listview_item, null);
			// 获取控件对象
			listItemView.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			listItemView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			listItemView.iv_go = (ImageView) convertView.findViewById(R.id.iv_go);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		listItemView.iv_icon.setImageResource(menus[position].getImage());//子菜单图片资源 
		listItemView.tv_title.setText(menus[position].getTitle());//子菜单标题
		listItemView.iv_go.setImageResource(R.drawable.go);

        return convertView; 
	}

}
