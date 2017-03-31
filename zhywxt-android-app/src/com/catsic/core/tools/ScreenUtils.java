package com.catsic.core.tools;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Litao-pc on 2016/5/4.
 */
public class ScreenUtils {
    public static void toggleSoftPan(Context c) {
/**隐藏软键盘**/
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
