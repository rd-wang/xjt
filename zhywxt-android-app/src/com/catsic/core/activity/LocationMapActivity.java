package com.catsic.core.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.catsic.BuildConfig;
import com.catsic.R;
import com.catsic.core.QueryTask.QLQueryTask;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.GpsUtils;
import com.catsic.core.tools.ModifyOffset;
import com.catsic.core.tools.OffSetUtils;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;

public class LocationMapActivity extends Activity implements View.OnClickListener {

    private final String MapServerUrl = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";
    private final String LUOYANG = "http://124.207.79.163:6080/arcgis/rest/services/luoyang_Mercator/MapServer";
    private MapView map;
    private GraphicsLayer gLayerGps;
    private MyLcationListener lcationListener;
    private Double[] lastPoint = null;
    private Button btn_location;
    private boolean isgps;
    private String qlbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        map = (MapView) findViewById(R.id.map);
        btn_location = (Button) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(this);
        ActionBarManager.initBackTitle(this, getActionBar(), "位置");
        initMap();
        qurey();
        qlbm = getIntent().getStringExtra("QLBM");
    }

    //初始化地图
    private void initMap() {
        ArcGISTiledMapServiceLayer tileLayer = new ArcGISTiledMapServiceLayer(LUOYANG);
        ArcGISTiledMapServiceLayer layer = new ArcGISTiledMapServiceLayer(MapServerUrl);
        map.addLayer(layer);
        map.addLayer(tileLayer);
        gLayerGps = new GraphicsLayer();
        map.addLayer(gLayerGps);
        lcationListener = new MyLcationListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GpsUtils.getInstance().stopLocation(this, lcationListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_location:
                //        开启定位
                if (isgps) {
                    GpsUtils.getInstance().stopLocation(this, lcationListener);
                    isgps = false;
                    if (BuildConfig.DEBUG) Log.d("MainActivity", "remove");
                    return;
                }
                GpsUtils.getInstance().startLocation(this, lcationListener);

        }

    }

    class MyLcationListener implements LocationListener {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        private Context context;

        public MyLcationListener(Context context) {
            super();
            this.context = context;
        }

        // 条件 坐标改变
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            Log.i("wzx", "地球坐标 lat:" + lat + "lon:" + lon);
            ModifyOffset.PointDouble p = OffSetUtils.getChinaLocation(context, lat, lon);
            Log.i("wzx", "火星坐标  lat:" + p.x + "lon:" + p.y);

            //记录自己最后位置经纬度
            if (lastPoint == null) {
                lastPoint = new Double[2];
            }
            lastPoint[0] = p.y;
            lastPoint[1] = p.x;
            ShowGpsOnMap(lastPoint[0], lastPoint[1]);
        }
    }

    public void ShowGpsOnMap(double lat, double lon) {
        //清空定位图层
        gLayerGps.removeAll();
        Point wgspoint = new Point(lon, lat);
        Point mapPoint = (Point) GeometryEngine.project(wgspoint, SpatialReference.create(4326), map.getSpatialReference());
        //图层的创建
        Graphic graphic = new Graphic(mapPoint, new SimpleMarkerSymbol(Color.RED, 11, SimpleMarkerSymbol.STYLE.CIRCLE));
        gLayerGps.addGraphic(graphic);
//        map.centerAt(mapPoint, true);
//        map.setScale(11288);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void qurey() {
        // TODO: 2016/5/5
        if (qlbm != null && TextUtils.isEmpty(qlbm)) {

        } else {

        }

        String urlQuery = LUOYANG.concat("/3");
        String[] array = {urlQuery, "QLBM='X035410328L0020'"};
        QLQueryTask queryTask = new QLQueryTask();
        queryTask.execute(array, map, this);
    }

    ;
}
