package com.catsic.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ListView;

import com.catsic.core.tools.LogUtils;

/**
 * 有弹性的ScrollView
 * 实现下拉弹回和上拉弹回
 *
 * @author zhangjg
 * @date Feb 13, 2014 6:11:33 PM
 */

/**
 * 弹性ListView。
 *
 * @author E
 */
public class ScrollListView extends ListView {
    //初始可拉动Y轴方向距离
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
    //上下文环境
    private Context mContext;
    //实际可上下拉动Y轴上的距离
    private int mMaxYOverscrollDistance;
    private float rawY;

    public ScrollListView(Context context) {
        super(context);
        mContext = context;
        initBounceListView();
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBounceListView();
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView();
    }

    private void initBounceListView() {
        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    boolean Tag;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawY = ev.getRawY();
                Tag = true;
                break;
            case MotionEvent.ACTION_UP:
                Tag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float raw1Y = ev.getRawY();

                Tag = raw1Y>rawY;
                LogUtils.outString("raw1Y" + raw1Y + "&&rawY" + rawY+"&&Tag"+Tag );
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //实现的本质就是在这里动态改变了maxOverScrollY的值

        LogUtils.outString("deltaY" + deltaX + "&&scrollY" + scrollY + "&&scrollRangeY" + scrollRangeY);

        if (Tag) {
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
        }
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);


    }
}