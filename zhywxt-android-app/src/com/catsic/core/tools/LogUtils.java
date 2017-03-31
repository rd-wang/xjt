package com.catsic.core.tools;

import android.util.Log;

/**
 * Created by Litao-pc on 2016/4/27.
 */
public class LogUtils {
    static final boolean DEBUG=true;


    public static void outString(String str){
        if (DEBUG){
            Log.d("LogUtils",str+"");
        }
    }
}
