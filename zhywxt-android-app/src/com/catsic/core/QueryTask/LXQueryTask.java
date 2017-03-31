package com.catsic.core.QueryTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.catsic.BuildConfig;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;

import java.util.Map;

/**
 * Created by Litao-pc on 2016/4/18.
 */
public class LXQueryTask extends AsyncTask<String, Void, FeatureResult> {
    public Context c;
    public MapView mapView;

    @Override
    protected FeatureResult doInBackground(String... queryArray) {
        try {
            if (queryArray == null || queryArray.length <= 1)
                return null;
            String url = queryArray[0];
            String whereClause = queryArray[1];
            if (BuildConfig.DEBUG) Log.d("LXQueryTask", url + "&" + whereClause);
            QueryParameters qParameters = new QueryParameters();
            qParameters.setReturnGeometry(true);
            qParameters.setWhere(whereClause);
            QueryTask qTask = new QueryTask(url);
            FeatureResult results = qTask.execute(qParameters);
            if (BuildConfig.DEBUG) Log.d("LXQueryTask", "back FeatureResult");
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(FeatureResult results) {
        if (results == null) {
            if (BuildConfig.DEBUG) Log.d("LXQueryTask", "results=null");
            return;
        }
        GraphicsLayer graphicsLayer = (GraphicsLayer) mapView.getLayer(1);
        int size = (int) results.featureCount();
        if (BuildConfig.DEBUG) Log.d("LXQueryTask", "size=" + size);
        for (Object element : results) {
            if (element instanceof Feature) {
                Feature feature = (Feature) element;
                Map<String, Object> attributes = feature.getAttributes();
                if (BuildConfig.DEBUG)
                    Log.d("LXQueryTask", "attributes=" + attributes.get("LXBM"));
                //红色小点
                // SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(Color.RED, 5, SimpleMarkerSymbol.STYLE.SQUARE);
                //构建  简单填充符号，该对象指定了一种“呈现方式”
                SimpleLineSymbol line = new SimpleLineSymbol(Color.RED, 5, SimpleLineSymbol.STYLE.SOLID);
                Graphic graphic = new Graphic(feature.getGeometry(), line);
                graphicsLayer.addGraphic(graphic);
                //得到坐标
                Envelope envelope = new Envelope();
                feature.getGeometry().queryEnvelope(envelope);
                Point center = envelope.getCenter();

                Polyline polyline = (Polyline) graphic.getGeometry();
                mapView.zoomTo(polyline.getPoint(0), 2000f);
//              mapView.setScale(20588);
//              mapView.centerAt(center, true);
            }
        }
    }

    public void execute(String[] array, MapView map, Context content) {
        mapView = map;
        c = content;
        execute(array);
    }
}
