package com.catsic.core.activity.base;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.catsic.core.tools.MenuUtil;
import com.catsic.core.tools.ScreenUtils;

import net.tsz.afinal.FinalActivity;

/**
 * @author wuxianling
 * @Description: 基类
 * @date 2014年8月22日 下午1:05:33
 */
public class BaseActivity extends FinalActivity {

    private float startY;


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
