package com.catsic.biz.yh.activity;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.catsic.R;
import com.catsic.core.AppContext;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.ActionBarManager;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
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


/**  
  * @Description: 路损定位 
  * @author wuxianling  
  * @date 2014年11月15日 下午4:35:23    
  */ 
public class LSLocationActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.map) MapView map;
	private LocationManager locMag;
	private GraphicsLayer gl;
	private Location loc ;
	
	@Override
	protected void onCreate(Bundle bundle) {
		setContentView(R.layout.activity_yhxc_ls_location);
		super.onCreate(bundle);
		Bundle b =  getIntent().getExtras();
		
		String title = "路损:"+b.getString("lxmc")+"("+b.getString("lxbm")+")";
		ActionBarManager.initBackTitle(this, getActionBar(), title);
		
		ArcGISDynamicMapServiceLayer dynamicLayout1 = new ArcGISDynamicMapServiceLayer(super.getString(R.string.basemap_url));
		ArcGISDynamicMapServiceLayer dynamicLayout2 = new ArcGISDynamicMapServiceLayer(getResources().getString(R.string.operationallayer_url));
		map.addLayer(dynamicLayout1);
		map.addLayer(dynamicLayout2);
		
		gl = new GraphicsLayer();
		map.addLayer(gl);
		
		//要定位在地图中的位置，需要知道当前位置，而当前位置有Location对象决定，
		//但是，Location对象又需要LocationManager对象来创建。
		locMag = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);//创建LocationManager的唯一方法
		
	    String provider = LocationManager.NETWORK_PROVIDER;
	    loc = locMag.getLastKnownLocation(provider);
	    if(loc==null){
	    	provider = LocationManager.NETWORK_PROVIDER;
	    	loc = locMag.getLastKnownLocation(provider);
	    }
	    
	    LocationListener locationListener = new LocationListener(){
			@Override
			public void onLocationChanged(Location location) {
				//刷新图层
				//markLocation(location);
			}
			@Override
			public void onProviderDisabled(String arg0) {
			}
			@Override
			public void onProviderEnabled(String arg0) {
			}
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
		};

	    locMag.requestLocationUpdates(provider, 100, 0, locationListener);

	    Location loc = locMag.getLastKnownLocation(provider);
		if(loc!=null){
			//开始画图
			markLocation(loc);
		}
		
		//查询，并标记路线
		search(b.getString("lxbm"),b.getFloat("qdzh"),b.getFloat("zdzh"));
		
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
	
	/**  
	  * @Title: markLocation  
	  * @Description: 当前位置标记 
	  * @param @param location     
	  * @return void   
	  * @throws  
	  */ 
	private void markLocation(Location location){
		double locx = location.getLongitude();
		double locy = location.getLatitude();
		
		gl.removeAll();
		
		Point wgspoint = new Point(locx, locy);  
		Point mapPoint = (Point) GeometryEngine.project(wgspoint,SpatialReference.create(4326),map.getSpatialReference());
	
		//图层的创建
		Graphic graphic = new Graphic(mapPoint,new SimpleMarkerSymbol(Color.RED,25,STYLE.CIRCLE));
		gl.addGraphic(graphic);
	}
	
	/**  
	  * @Title: search  
	  * @Description: 查询 
	  * @param @param lxbm
	  * @param @param qdzh
	  * @param @param zdzh     
	  * @return void   
	  * @throws  
	  */ 
	public void search(String lxbm,float qdzh,float zdzh){
		Query query = new Query();
		
		String [] outfields = null; 
		query.setWhere(" LXBM = '"+lxbm+"'");
		outfields = new String[] { "OBJECTID", "LXBM","LXMC", "QDZH","ZDZH"};
		query.setReturnGeometry(true);
		if (outfields!=null) {
			query.setOutFields(outfields);
		}
		query.setOutSpatialReference(map.getSpatialReference());

		Query[] queryParams = { query };
		AsyncQueryTask qt = new AsyncQueryTask();
		qt.execute(queryParams);

	}
	
	
	
	private class AsyncQueryTask extends AsyncTask<Query, Void, FeatureSet> {

		protected FeatureSet doInBackground(Query... params) {
			if (params.length > 0) {
				Query query = params[0];
				String url = getResources().getString(R.string.operationallayer_url);
				Bundle bundle = getIntent().getExtras();
				
				String lxbmPrefix = bundle.getString("lxbm").substring(0, 1);
				String targetLayer = AppContext.dynamicLayers.get(lxbmPrefix);
				
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

		protected void onPostExecute(FeatureSet fs) {
			if (fs==null || fs.getGraphics()==null || fs.getGraphics().length==0) {
				return ;
			}
			
			gl.removeAll();
			
			if (fs.getGraphics()!=null && fs.getGraphics().length >0) {
				Graphic  graphic = fs.getGraphics()[0];
				Type  type =  graphic.getGeometry().getType();
				for (int i = 0; i < fs.getGraphics().length; i++) {
//					PictureMarkerSymbol pic = new PictureMarkerSymbol(getResources().getDrawable(R.drawable.family));
//					SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.RED, 25,STYLE.CIRCLE);
//					SimpleLineSymbol sls = new SimpleLineSymbol(Color.RED, 10, com.esri.core.symbol.SimpleLineSymbol.STYLE.SOLID);

					Graphic nGraphic = null;
					if (type.POINT.equals(Type.POINT)) {
						SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(Color.RED, 25,STYLE.CIRCLE);
						nGraphic = new Graphic(graphic.getGeometry(),symbol,graphic.getAttributes());
					}else if (type.POLYLINE.equals(Type.POLYLINE)) {
						SimpleLineSymbol symbol = new SimpleLineSymbol(Color.RED, 10, com.esri.core.symbol.SimpleLineSymbol.STYLE.SOLID);
						new Graphic(graphic.getGeometry(),symbol,graphic.getAttributes());
					}
					if (nGraphic !=null) {
						gl.addGraphic(nGraphic);
					}
				}
				
				if (Type.POINT.equals(type)) {
					Point point =  (Point) graphic.getGeometry();
					map.zoomTo(point, 200f);
				}else if (Type.POLYLINE.equals(type)) {
					Polyline polyline =  (Polyline) graphic.getGeometry();
					map.zoomTo(polyline.getPoint(0),200f);
				}
				
			}
			
		}

	}

}
