package com.catsic.biz.js.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.QueryTask.LXQueryTask;
import com.catsic.core.QueryTask.QLQueryTask;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.ActionBarManager;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.Geometry.Type;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.ags.query.QueryTask;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * @author wuxianling
 * @Description: 建设检查工程定位
 * @date 2014年9月22日 下午5:26:10
 */
public class JsjcLocationActivity extends BaseActivity {

    @ViewInject(id = R.id.map)
    MapView map;
    private GraphicsLayer gl;
    private final String LUOYANG = "http://124.207.79.163:6080/arcgis/rest/services/luoyang_Mercator/MapServer";
    private String lxbm;
    private String qlbm;
    private String xmlx;
    private String urlQuery;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_jsjc_location);
        Bundle b = getIntent().getExtras();
        lxbm = b.getString("lxbm");
        qlbm = b.getString("qlbm");
        xmlx = b.getString("xmlx");
        String centerTitle = b.getString("titleStr");
        ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
        ArcGISTiledMapServiceLayer layer = new ArcGISTiledMapServiceLayer(LUOYANG);
        map.addLayer(layer);
        gl = new GraphicsLayer();
        map.addLayer(gl);
        GraphicsLayer graphicsLayer = new GraphicsLayer();
        map.addLayer(graphicsLayer);
        //要定位在地图中的位置，需要知道当前位置，而当前位置有Location对象决定，
        //但是，Location对象又需要LocationManager对象来创建。

        if (AppConstants.XMLX_LX.equals(xmlx)) {
            urlQuery = LUOYANG.concat("/6");
            String[] array = {urlQuery, "LXBM='"+lxbm+"'"};
            LXQueryTask lxqueryTask = new LXQueryTask();
            lxqueryTask.execute(array, map, this);
        }
        if (AppConstants.XMLX_QL.equals(xmlx)) {
            urlQuery = LUOYANG.concat("/3");
            String[] array = {urlQuery, " QLBM = '" + qlbm + "'"};
            QLQueryTask qlqueryTask = new QLQueryTask();
            qlqueryTask.execute(array, map, this);
        }
    }


    /**
     * @param @param location
     * @return void
     * @throws
     * @Title: markLocation
     * @Description: 当前位置标记
     */
    private void markLocation(Location location) {
        double locx = location.getLongitude();
        double locy = location.getLatitude();

        gl.removeAll();

        Point wgspoint = new Point(locx, locy);
        Point mapPoint = (Point) GeometryEngine.project(wgspoint, SpatialReference.create(4326), map.getSpatialReference());

        //图层的创建
        Graphic graphic = new Graphic(mapPoint, new SimpleMarkerSymbol(Color.RED, 25, STYLE.CIRCLE));
        gl.addGraphic(graphic);
    }

    /**
     * @param @param xmlx
     * @param @param lxbm
     * @param @param qlbm
     * @return void
     * @throws
     * @Title: search
     * @Description: 查询
     */
    public void search(String xmlx, String lxbm, String qlbm) {
        Query query = new Query();

        String[] outfields = null;
        if (AppConstants.XMLX_LX.equals(xmlx)) {
            query.setWhere(" LXBM = '" + lxbm + "' ");
            outfields = new String[]{"OBJECTID", "LXBM", "LXMC", "QDZH", "ZDZH"};
        } else if (AppConstants.XMLX_QL.equals(xmlx)) {
            query.setWhere(" QLBM = '" + qlbm + "'");
            outfields = new String[]{"OBJECTID", "QLBM", "QLMC", "LXBM", "LXMC", "QDZH", "ZDZH"};
        }

        query.setReturnGeometry(true);
        if (outfields != null) {
            query.setOutFields(outfields);
        }
        query.setOutSpatialReference(map.getSpatialReference());

        Query[] queryParams = {query};
        AsyncQueryTask qt = new AsyncQueryTask();
        qt.execute(queryParams);

    }


    private class AsyncQueryTask extends AsyncTask<Query, Void, FeatureSet> {

        protected FeatureSet doInBackground(Query... params) {
            if (params.length > 0) {
                Query query = params[0];
                String url = getResources().getString(R.string.operationallayer_url);
                Bundle bundle = getIntent().getExtras();

                String targetLayer = null;
                if (AppConstants.XMLX_LX.equals(bundle.getString("xmlx"))) {
                    String lxbmPrefix = bundle.getString("lxbm").substring(0, 1);
                    targetLayer = AppContext.dynamicLayers.get(lxbmPrefix);
                } else if (AppConstants.XMLX_QL.equals(bundle.getString("xmlx"))) {
                    targetLayer = AppContext.dynamicLayers.get("QL");
                }

                if (targetLayer == null) {
                    return null;
                }
                QueryTask queryTask = new QueryTask(url.concat(targetLayer));
                try {
                    FeatureSet fs = queryTask.execute(query);
                    return fs;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }

        @SuppressWarnings("static-access")
        protected void onPostExecute(FeatureSet fs) {
            if (fs == null || fs.getGraphics() == null || fs.getGraphics().length == 0) {
                return;
            }

            gl.removeAll();

            if (fs.getGraphics() != null && fs.getGraphics().length > 0) {
                Graphic graphic = fs.getGraphics()[0];
                Type type = graphic.getGeometry().getType();
                for (int i = 0; i < fs.getGraphics().length; i++) {
//					PictureMarkerSymbol pic = new PictureMarkerSymbol(getResources().getDrawable(R.drawable.family));
//					SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.RED, 25,STYLE.CIRCLE);
//					SimpleLineSymbol sls = new SimpleLineSymbol(Color.RED, 10, com.esri.core.symbol.SimpleLineSymbol.STYLE.SOLID);

                    Graphic nGraphic = null;
                    if (type.POINT.equals(Type.POINT)) {
                        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(Color.RED, 25, STYLE.CIRCLE);
                        nGraphic = new Graphic(graphic.getGeometry(), symbol, graphic.getAttributes());
                    } else if (type.POLYLINE.equals(Type.POLYLINE)) {
                        SimpleLineSymbol symbol = new SimpleLineSymbol(Color.RED, 10, com.esri.core.symbol.SimpleLineSymbol.STYLE.SOLID);
                        new Graphic(graphic.getGeometry(), symbol, graphic.getAttributes());
                    }
                    if (nGraphic != null) {
                        gl.addGraphic(nGraphic);
                    }
                }

                if (Type.POINT.equals(type)) {
                    Point point = (Point) graphic.getGeometry();
                    map.zoomTo(point, 200f);
                } else if (Type.POLYLINE.equals(type)) {
                    Polyline polyline = (Polyline) graphic.getGeometry();
                    map.zoomTo(polyline.getPoint(0), 200f);
                }

            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.unpause();
    }

}
