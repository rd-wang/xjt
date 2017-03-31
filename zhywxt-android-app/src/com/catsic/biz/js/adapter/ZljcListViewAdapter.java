package com.catsic.biz.js.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.js.bean.TZljc;
import com.catsic.biz.js.service.ZljcService;
import com.catsic.biz.js.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppConstants.State;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.StringUtil;
import com.catsic.core.widget.swipe.adapters.BaseSwipeAdapter;

import net.tsz.afinal.FinalDb;

import java.util.List;
import java.util.Map;


/**
 * @author catsic-wuxianling
 * @ClassName: ZljcListViewAdapter
 * @Description: 质量检测Adapter
 * @date 2015年8月18日 下午5:34:42
 */
public class ZljcListViewAdapter extends BaseSwipeAdapter implements View.OnClickListener {

    private Context context; // 运行上下文
    public List<Map<String, Object>> listItems; // 信息集合
    private LayoutInflater listContainer; // 视图容器
    public State state = State.NORMAL;
    public boolean[] hasChecked; // 记录Item选中状态

    public final class ListItemView { // 自定义控件集合
        public String crowid;
        public TextView tv_xmmc;
        public TextView tv_xmlx;
        public TextView tv_xzqh;
        public TextView tv_jcsj;
        public TextView tv_result;
        public Button  btn_delete;
        public Button btn_item_oper;
    }

    public ZljcListViewAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
        this.listItems = listItems;
        hasChecked = new boolean[getCount()];
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
     * @param @return
     * @return boolean
     * @throws
     * @Title: isChecked
     * @Description: 是否存在被选中的checkbox
     */
    public boolean isChecked() {
        for (int i = 0; i < hasChecked.length; i++) {
            if (hasChecked[i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        // 自定义视图
        ListItemView listItemView = new ListItemView();

        View convertView = listContainer.inflate(R.layout.activity_js_zljc_list_item, null);
        // 获取控件对象
        listItemView.tv_xmmc = (TextView) convertView.findViewById(R.id.tv_xmmc);
        listItemView.tv_xmlx = (TextView) convertView.findViewById(R.id.tv_xmlx);
        listItemView.tv_xzqh = (TextView) convertView.findViewById(R.id.tv_xzqh);
        listItemView.tv_jcsj = (TextView) convertView.findViewById(R.id.tv_jcsj);
//			listItemView.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
        listItemView.btn_item_oper = (Button) convertView.findViewById(R.id.btn_item_oper);
        listItemView.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
        // 设置控件集到convertView
        convertView.setTag(listItemView);


        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ListItemView listItemView = (ListItemView) convertView.getTag();
// 设置文字
        listItemView.crowid = listItems.get(position).get("crowid").toString();
        listItemView.tv_xmmc.setText(StringUtil.toString(listItems.get(position).get("xmmc")));
        if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xzqh"))) {
            String xzqhmc = new XzqhService(context).translate(listItems.get(position).get("xzqh").toString());
            listItemView.tv_xzqh.setText(xzqhmc);
        }

        if (!ObjectUtils.isNullOrEmptyString(listItems.get(position).get("xmlxdm"))) {
            String xmlxmc = new CatsicCodeService(context).translate("tc_xmlx", listItems.get(position).get("xmlxdm").toString());
            listItemView.tv_xmlx.setText(xmlxmc);
        }

        listItemView.tv_jcsj.setText(StringUtil.toString(listItems.get(position).get("jcsj")));
//		listItemView.tv_result.setText(StringUtil.toString(listItems.get(position).get("result")));
        String shbz = StringUtil.toString(listItems.get(position).get("shbz"));
        String oper = ShbzUtils.getOperByShbz(shbz);
        //上报
        if (ShbzUtils.OPER_WSB.equals(oper)) {
            listItemView.btn_item_oper.setEnabled(true);
            listItemView.btn_item_oper.setBackgroundResource(R.color.icon_common_upload_selector);
        }//已上报
        else if (ShbzUtils.OPER_YSB.equals(oper)) {
            listItemView.btn_item_oper.setEnabled(false);
            listItemView.btn_item_oper.setBackgroundResource(R.color.icon_common_success_selector);
        }//被退回
        else if (ShbzUtils.OPER_TH.equals(oper)) {
            listItemView.btn_item_oper.setEnabled(true);
            listItemView.btn_item_oper.setBackgroundResource(R.color.icon_common_upload_selector);
        }
        /**
         * 上报
         */
        listItemView.btn_item_oper.setTag(position);
        listItemView.btn_item_oper.setOnClickListener(this);
        listItemView.btn_delete.setTag(position);
        listItemView.btn_delete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int selectID = (int) v.getTag();
        switch (v.getId()) {
            case R.id.btn_item_oper:
                try {
                    new ZljcService(context).operationSB(listItems, selectID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //refresh listview
                notifyDataSetChanged();
                break;
            case R.id.btn_delete:
                new ZljcService(context).operationDel(listItems,selectID);
                break;
        }
    }


    /**
     * @author wuxianling
     * @Description: 菜单处理
     * @date 2014年11月15日 上午11:28:33
     */
    private class ContextMenuListener implements OnCreateContextMenuListener {
        private int selectID;

        public ContextMenuListener(int selectID) {
            this.selectID = selectID;
        }

        /**
         * @author wuxianling
         * @Description: OnMenuItemClickListener
         * @date 2014年8月20日 上午11:34:44
         */
        class MenuItemClickListener implements MenuItem.OnMenuItemClickListener {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1://上报
                        try {
                            new ZljcService(context).operationSB(listItems, selectID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2://删除
                        new ZljcService(context).operationDel(listItems, selectID);
                        break;

                }
                return true;
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
            MenuItemClickListener menuItemClickListener = new MenuItemClickListener();
            ListItemView liv = (ListItemView) v.getTag();

            FinalDb db = FinalDb.create(context, AppConstants.DB_NAME);
            TZljc obj = db.findById(liv.crowid, TZljc.class);

            menu.setHeaderTitle("操作");
            if (ShbzUtils.OPER_SB.equals(ShbzUtils.getOperByShbz(obj.getShbz()))) {
                menu.add(0, 1, 1, "上报").setOnMenuItemClickListener(menuItemClickListener);
                menu.add(0, 2, 2, "删除").setOnMenuItemClickListener(menuItemClickListener);
            } else if (ShbzUtils.OPER_SH.equals(ShbzUtils.getOperByShbz(obj.getShbz()))) {
                menu.add(0, 2, 2, "删除").setOnMenuItemClickListener(menuItemClickListener);
            }

        }
    }


}
