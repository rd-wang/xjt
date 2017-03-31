package com.catsic.biz.task.activity;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.ActionBarManager;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

/**  
  * @Description: TaskLocationActivity
  * @author wuxianling  
  * @date 2014年8月28日 下午2:41:13    
  */ 
public class TaskLocationActivity extends BaseActivity{
	
	private @ViewInject(id=R.id.map) MapView map;
	private LocationManager locMag;
	private GraphicsLayer gLayer;
	private Location loc ;
	
	Callout callout;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_task_location);
		
		String centerTitle = getResources().getString(R.string.TaskListActivityTitle);
		ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);
		
//		ArcGISTiledMapServiceLayer tileLayout = new ArcGISTiledMapServiceLayer(super.getString(R.string.basemap_url)super.getString(R.string.basemap_url));
		ArcGISDynamicMapServiceLayer dynamicLayout1 = new ArcGISDynamicMapServiceLayer(super.getString(R.string.basemap_url));
		ArcGISDynamicMapServiceLayer dynamicLayout2 = new ArcGISDynamicMapServiceLayer(getResources().getString(R.string.operationallayer_url));
		map.addLayer(dynamicLayout1);
		map.addLayer(dynamicLayout2);
		
		gLayer = new GraphicsLayer();
		map.addLayer(gLayer);
		
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
				System.out.println("位置发生变化，新位置：　" + location.getLatitude() + "  ,  " + location.getLongitude());
				//刷新图层
				markLocation(location);
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
			double latitude = loc.getLatitude();
			double longitude = loc.getLongitude();
			//开始画图
			markLocation(loc);
		}
		
		/**
		 * 地图点击
		 */
		map.setOnSingleTapListener(new OnSingleTapListener() {
			
			@Override
			public void onSingleTap(float x, float y) {
				if (!map.isLoaded()) {
					return;
				}
				
				int [] uids = gLayer.getGraphicIDs(x, y, 2);
				if (uids!=null && uids.length>0) {
					for (int i = 0; i < uids.length; i++) {
						Graphic g = gLayer.getGraphic(uids[i]);
					}
					int targetId = uids[0];
					Graphic gr = gLayer.getGraphic(targetId);
					callout = map.getCallout();
					callout.setStyle(R.xml.countypop);
					String [] names = gr.getAttributeNames();
					for (int i = 0; i < names.length; i++) {
						System.out.println(names[i]);
					}
					callout.setContent(loadView("aaa","bbb"));
					callout.show(map.toMapPoint(new Point(x,y)));
				}else{
					if (callout!=null && callout.isShowing()) {
						callout.hide();
					}
				}
				
			}
		});
	    
	}
	
	private View loadView(String countyName, String pop07) {
		View view = LayoutInflater.from(this).inflate(R.layout.sqmi, null);

		final TextView name = (TextView) view.findViewById(R.id.county_name);
		name.setText(countyName + "'s SQMI");

		final TextView number = (TextView) view.findViewById(R.id.pop_sqmi);
		number.setText(pop07);

		final ImageView photo = (ImageView) view.findViewById(R.id.family_photo);
		photo.setImageDrawable(this.getResources().getDrawable(R.drawable.family));

		return view;
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
	
	private void markLocation(Location location){
		double locx = location.getLongitude();
		double locy = location.getLatitude();
		gLayer.removeAll();
		
		Point wgspoint = new Point(locx, locy);  
		Point mapPoint = (Point) GeometryEngine.project(wgspoint,SpatialReference.create(4326),map.getSpatialReference());
	
		//图层的创建
		Graphic graphic = new Graphic(mapPoint,new SimpleMarkerSymbol(Color.RED,25,STYLE.CIRCLE));
		gLayer.addGraphic(graphic);
	}

}
