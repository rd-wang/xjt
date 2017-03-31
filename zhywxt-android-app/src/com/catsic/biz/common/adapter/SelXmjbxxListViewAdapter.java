package com.catsic.biz.common.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.StringUtil;

/**
  * @ClassName: SelXmjbxxListViewAdapter
  * @Description: 项目选择Adapter
  * @author catsic-wuxianling
  * @date 2015年9月25日 下午3:33:12
  */
public class SelXmjbxxListViewAdapter extends BaseAdapter{
	
	private Context context; // 运行上下文
	public List<Map<String, Object>> listItems; // 信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public String xmid;
		public TextView tv_xmmc;
		public TextView tv_xzqh;
		public TextView tv_xmlxmc;
	}

	public SelXmjbxxListViewAdapter(Context context,List<Map<String, Object>> listItems) {
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
			convertView = listContainer.inflate(R.layout.frame_common_sel_xmjbxx_list_item, null);
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
		listItemView.xmid = listItems.get(position).get("xmid").toString();
		listItemView.tv_xmmc.setText(StringUtil.toString(listItems.get(position).get("xmmc")));
		
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xzqh"))) {
			String xzqhmc = new XzqhService(context).translate(listItems.get(position).get("xzqh").toString());
			listItemView.tv_xzqh.setText(xzqhmc);
		}
		if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xmlxdm"))) {
			String xmlxmc = new CatsicCodeService(context).translate("tc_xmlx",listItems.get(position).get("xmlxdm").toString());
			listItemView.tv_xmlxmc.setText(xmlxmc);
		}
		return convertView;
	}
	
}
