package com.catsic.core.activity.base;

import android.app.Activity;
import android.view.MotionEvent;

import com.catsic.core.tools.ScreenUtils;

/**
 * Created by Litao-pc on 2016/5/4.
 */
public class Base2Activity extends Activity {

    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float endY = event.getRawY();
                if (endY - startY > 150) {
                    ScreenUtils.toggleSoftPan(getApplication());
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
