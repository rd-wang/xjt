package com.catsic.biz.yh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.biz.yh.activity.QljcJEditeQlActivity;
import com.catsic.biz.yh.bean.QlAddCardBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Litao-pc on 2016/4/13.
 *
 * @descreption 添加桥梁列表adapter
 */
public class QljcAddListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context; // 运行上下文
    public static List<QlAddCardBean> listItems; // 信息集合
    private LayoutInflater listContainer; // 视图容器

    public QljcAddListAdapter(Context context, List<QlAddCardBean> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = listContainer.inflate(R.layout.qljc_tjql_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.ivEdit.setOnClickListener(this);
        holder.ivEdit.setTag(i);
        holder.tvQlmc.setText(listItems.get(i).getQlmc()+" ("+listItems.get(i).getQlbm()+")");
        holder.tvQljs.setText(listItems.get(i).getQlcd()+"米,"+listItems.get(i).getLxmc()+"("+listItems.get(i).getLxbm()+" ,k"+listItems.get(i).getZxzh()+")");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_Edit:
                int tag = (int) view.getTag();
                Intent intent = new Intent(context, QljcJEditeQlActivity.class);
                intent.putExtra("position",tag);
                intent.putExtra("mId",listItems.get(tag).getCrowid());
                intent.putExtra("form","桥梁卡片");
                context.startActivity(intent);
                break;
        }
    }


    static class ViewHolder {
        @Bind(R.id.tv_qlmc)
        TextView tvQlmc;
        @Bind(R.id.tv_qljs)
        TextView tvQljs;
        @Bind(R.id.iv_Edit)
        ImageView ivEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
