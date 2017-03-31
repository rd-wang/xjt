package com.catsic.biz.yh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.yh.activity.QljcJEditeQlActivity;
import com.catsic.biz.yh.bean.QlcxListBean;
import com.catsic.biz.yh.service.QljcUpdateAndDeleteService;
import com.catsic.core.activity.LocationMapActivity;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ToastUtil;
import com.catsic.core.widget.swipe.SwipeLayout;
import com.catsic.core.widget.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

/**
 * Created by Litao-pc on 2016/4/28.
 */
public class QljcEditSwipeAdapter extends BaseSwipeAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater listContainer;
    public static List<QlcxListBean> listItems;
    public boolean[] hasChecked; // 记录Item选中状态

    public QljcEditSwipeAdapter(Context context, List<QlcxListBean> listItems) {
        this.mContext = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;
        hasChecked = new boolean[getCount()];
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    @Override
    public View generateView(int position, ViewGroup parent) {
        QlHolder holder = new QlHolder();
        View containerView = LayoutInflater.from(mContext).inflate(R.layout.qljc_listview_item, null);
        holder. swipeLayout = (SwipeLayout) containerView.findViewById(getSwipeLayoutResourceId(position));
        holder.iv_location = (ImageView) containerView.findViewById(R.id.iv_location);
        holder.cb_item_edit = (ImageView) containerView.findViewById(R.id.cb_item_edit);
        holder.btn_delete = (Button) containerView.findViewById(R.id.btn_delete);
        holder.tv_qlmc = (TextView) containerView.findViewById(R.id.tv_qlmc);
        holder.tv_rerultTime = (TextView) containerView.findViewById(R.id.tv_rerultTime);
        holder.tv_xmlx = (TextView) containerView.findViewById(R.id.tv_xmlx);
        holder.tv_xzqh = (TextView) containerView.findViewById(R.id.tv_xzqh);
        holder.position = position;
        containerView.setTag(holder);
//        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//            @Override
//            public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//            }
//        });

        return containerView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        QlHolder holder = (QlHolder) convertView.getTag();
        QlcxListBean qlcxListBean = listItems.get(position);
        LogUtils.outString(qlcxListBean.getQlmc() + "&&QljcEditListAdapter");

        holder.tv_qlmc.setText(qlcxListBean.getQlmc() + "");
        holder.tv_rerultTime.setText(qlcxListBean.getJcrq());
        holder.tv_xmlx.setText(qlcxListBean.getQlcd() + "*" + qlcxListBean.getQmqk() + "米");
        holder.tv_xzqh.setText(qlcxListBean.getXmmsInfo());

        //点击事件
        holder.iv_location.setOnClickListener(this);
        holder.iv_location.setTag(holder);
        holder.cb_item_edit.setOnClickListener(this);
        holder.cb_item_edit.setTag(holder);
        holder.btn_delete.setOnClickListener(this);
        holder.btn_delete.setTag(holder);

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


    static class QlHolder {
        public  SwipeLayout swipeLayout;
        public int position;
        public Button btn_delete;
        public ImageView iv_location;
        public ImageView cb_item_edit;
        public TextView tv_qlmc;
        public TextView tv_xmlx;
        public TextView tv_xzqh;
        public TextView tv_rerultTime;
    }

    public void onClick(View view) {
        int id = view.getId();
        QlHolder  tagHolder = (QlHolder) view.getTag();
        int tag=tagHolder.position;
        switch (id) {
            case R.id.iv_location:
                ToastUtil.showShortToast(mContext, tag + "TAg");
                Intent intent1 =new Intent(mContext, LocationMapActivity.class);
                intent1.putExtra("QLBM",listItems.get(tag).getQlbm());
                mContext.startActivity(intent1);
                break;
            case R.id.cb_item_edit:
                ToastUtil.showShortToast(mContext, tag + "TAg");
                Intent intent = new Intent(mContext, QljcJEditeQlActivity.class);
                intent.putExtra("mId",listItems.get(tag).getParentCrowid());
                intent.putExtra("position", tag);
                intent.putExtra("form","桥梁编辑");
                mContext.startActivity(intent);
                break;
            case R.id.btn_delete:

                tagHolder.swipeLayout.close();
                ToastUtil.showShortToast(mContext, tag + "删除");
                QljcUpdateAndDeleteService.deleteQljcmx(mContext,tag,listItems.get(tag).getCrowid(),"delete");
                break;

        }
    }
}
