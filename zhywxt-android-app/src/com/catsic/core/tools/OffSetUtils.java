package com.catsic.core.tools;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;


public class OffSetUtils {

	/**
	 * 地球坐标转火星坐标
	 * @param context
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static ModifyOffset.PointDouble getChinaLocation(Context context, double lat, double lon) {
		ModifyOffset.PointDouble p=new ModifyOffset.PointDouble(lon, lat);
		try {
			// 载入数据库
			InputStream input = context.getAssets().open("axisoffset.dat");
			if (input==null){
				Log.d("TAG", "input=null");
			}

			//获取工具
			ModifyOffset mo=ModifyOffset.getInstance(input);
			p =mo.s2c(p);
			Log.d("TAG", "坐标矫正成功");
		} catch (Exception e) {
		}
		return p;
	}
}
