package com.catsic.core.QueryTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.catsic.BuildConfig;
import com.catsic.R;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;

import java.util.Map;

/**
 * Created by Litao-pc on 2016/4/18.
 */
public class QLQueryTask extends AsyncTask<String, Void, FeatureResult> {

    private GraphicsLayer graphicsLayer;

    @Override
    protected FeatureResult doInBackground(String... queryArray) {

        try {
            if (queryArray == null || queryArray.length <= 1)
                return null;
            String url = queryArray[0];
            String whereClause = queryArray[1];
            if (BuildConfig.DEBUG) Log.d("GPSQueryTask", url + "&" + whereClause);
            QueryParameters qParameters = new QueryParameters();
            qParameters.setReturnGeometry(true);
            String[] array = {"FID", "Shape", "QLBM", "CROWID"};
            qParameters.setOutFields(array);
            qParameters.setWhere(whereClause);
            QueryTask qTask = new QueryTask(url);
            FeatureResult results = qTask.execute(qParameters);
            if (BuildConfig.DEBUG) Log.d("GPSQueryTask", "back FeatureResult");
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {


    }

    protected void onPostExecute(FeatureResult results) {
        if (results == null) {
            if (BuildConfig.DEBUG) Log.d("GPSQueryTask", "results=null");
        }
        graphicsLayer = new GraphicsLayer();
        int size = (int) results.featureCount();
        if (BuildConfig.DEBUG) Log.d("GPSQueryTask", "size=" + size);
        for (Object element : results) {
            if (element instanceof Feature) {
                Feature feature = (Feature) element;
                Map<String, Object> attributes = feature.getAttributes();
                if (BuildConfig.DEBUG)
                    Log.d("GPSQueryTask", "attributes=" + attributes.get("QLBM"));

                //红色小点
                SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(Color.RED, 15, SimpleMarkerSymbol.STYLE.CIRCLE);
                //构建  简单填充符号，该对象指定了一种“呈现方式”
                Graphic graphic = new Graphic(feature.getGeometry(), simpleMarkerSymbol
                        , feature.getAttributes());
                graphicsLayer.addGraphic(graphic);
                //得到坐标
                Envelope envelope = new Envelope();
                feature.getGeometry().queryEnvelope(envelope);
                Point center = envelope.getCenter();

                mapView.centerAt(center, true);

                mapView.setScale(29588);

            }
        }
        mapView.addLayer(graphicsLayer);
        //设置该点的点击事件
        mapView.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(float v, float v1) {
                selectFeature(v,v1);
            }


        });
    }

    private void selectFeature(float x, float y) {
        int[] graphicIDs = graphicsLayer.getGraphicIDs(x, y, 10, 1);
        if (BuildConfig.DEBUG) Log.d("GPSQueryTask", "graphicIDs.length="+graphicIDs.length);
        if (graphicIDs==null||graphicIDs.length==0){
            mapView.getCallout().hide();
            return;
        }
        graphicsLayer.clearSelection();
        Envelope envelope=new Envelope();
        Graphic graphic = graphicsLayer.getGraphic(graphicIDs[0]);
        graphic.getGeometry().queryEnvelope(envelope);
        Point p=envelope.getCenter();
        Callout callout = mapView.getCallout();
        callout.setStyle(R.xml.callout);
        View inflate = View.inflate(c, R.layout.map_callout_item, null);
        callout.setCoordinates(p);
        callout.setContent(inflate);
        mapView.getCallout().show();
    }
    public Context c;
    public MapView mapView;
    public void execute(String[] array, MapView map, Context content) {
        mapView = map;
        c = content;
        execute(array);

    }
}
