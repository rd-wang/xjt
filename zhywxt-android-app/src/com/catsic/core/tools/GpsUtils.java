package com.catsic.core.tools;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import java.util.List;


public class GpsUtils {


    private static GpsUtils gpsUtils = null;

    private GpsUtils() {
    }

    public synchronized static GpsUtils getInstance() {

        if (gpsUtils == null) {
            gpsUtils = new GpsUtils();
        }
        return new GpsUtils();
    }

    public void startLocation(Context context, LocationListener listener) {


        // ①　配置权限
        // ②　创建LocationManager
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        List<String> ps = lm.getAllProviders();
        for (String p : ps) {
            Log.i("wzx", p);
        }
        // ③　添加监听器
        Criteria c = new Criteria();
        // Criteria
        // 查询条件对象
        // 1.付费
        c.setCostAllowed(true);
        // 2.电量
        c.setPowerRequirement(Criteria.POWER_HIGH);
        // 3.精度
        c.setAccuracy(Criteria.ACCURACY_FINE);// gps
        // provider:定位方式
        String bestProvider = lm.getBestProvider(c, true);
        Log.i("wzx", "bestProvider " + bestProvider);
        // ListView OnItemClickListener
        // LocationManager LocationListener

        // lm.requestLocationUpdates(定位方式, 每隔n毫秒, 移动多少米, 监听器 获取坐标);
//        lm.requestLocationUpdates("gps", 1000, 0, listener);
        lm.requestLocationUpdates("network", 15000, 0, listener);
//        lm.requestLocationUpdates(bestProvider, 1000, 0, listener);
    }

    public void stopLocation(Context context, LocationListener listener) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(listener);
    }


}
