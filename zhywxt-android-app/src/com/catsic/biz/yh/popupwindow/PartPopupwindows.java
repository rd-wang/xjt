package com.catsic.biz.yh.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.yh.activity.QljcJEditeQlActivity;
import com.catsic.biz.yh.bean.TQljcmx;
import com.catsic.biz.yh.service.QljcUpdateAndDeleteService;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Litao-pc on 2016/4/15.
 */
public class PartPopupwindows extends PopupWindow {


    @Bind(R.id.ed_jcbw)
    EditText edJcbw;
    @Bind(R.id.et_qslx)
    EditText etQslx;
    @Bind(R.id.et_qsfw)
    EditText etQsfw;
    @Bind(R.id.et_byyj)
    EditText etByyj;
    @Bind(R.id.btn_save)
    BootstrapButton btnSave;
    @Bind(R.id.ll_popup)
    LinearLayout llPopup;
    private Context mContext;

    public PartPopupwindows(Activity activity, View parent,  final String mId, final String form) {
        mContext = activity;
        View view = View.inflate(mContext, R.layout.common_part_popupwindows, null);
        ButterKnife.bind(this, view);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.CENTER, 0, 100);
        update();

        class ClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_save:
                        Toast.makeText(mContext, "btn_save", Toast.LENGTH_SHORT).show();
                        TQljcmx obj = new TQljcmx();
                        String jcbw = edJcbw.getText().toString();
                        String qslx = etQslx.getText().toString();
                        String qsfw = etQsfw.getText().toString();
                        String byyj = etByyj.getText().toString();

                        if (TextUtils.isEmpty(jcbw) && TextUtils.isEmpty(qslx) && TextUtils.isEmpty(qsfw) && TextUtils.isEmpty(byyj)) {
                            Toast.makeText(mContext, "请填写信息", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        obj.setQsfw(qsfw);
                        obj.setQslx(qslx);
                        obj.setBycsyj(byyj);
                        obj.setJcbj(jcbw);
                        obj.setQljcCrowid(mId);
                        obj.setCrowid(UUID.randomUUID().toString());

                        if (form.equals("QljcEditSwipeAdapter")){
                            new QljcUpdateAndDeleteService().saveQljcmx(mContext, obj);
                        }else {
                            obj.setQljcCrowid(QljcJEditeQlActivity.uUid);
                            new QljcUpdateAndDeleteService().saveQljcmx(mContext, obj);
                        }

                }
                dismiss();
            }
        }
        ClickListener listener = new ClickListener();
        //处理相应按钮事件
        btnSave.setOnClickListener(listener);
    }


}
