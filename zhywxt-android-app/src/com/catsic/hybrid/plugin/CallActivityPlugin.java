
package com.catsic.hybrid.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

/**
  * @ClassName: CallActivityPlugin
  * @Description: cordova
  * @author wuxianling
  * @date 2015年4月21日 上午9:47:31
  */
public class CallActivityPlugin extends CordovaPlugin {
	
	public static final String ACTION = "call";
	public static final String P_ACTIONNAME = "activityName";
	public static final String RESULT_SUCCESS = "success";
	
	@Override
	public boolean execute(String action, JSONArray jsonArray,CallbackContext callbackContext) throws JSONException {
		if (ACTION.equals(action))
			try {
				if (jsonArray.length()>=0) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(0);
					if (jsonObject.has(P_ACTIONNAME)){
						startOtherActivity(callbackContext, jsonObject);
					}
					else{
						callbackContext.success(jsonObject);
					}
				}
			} catch (Exception exception) {
				Log.e("e", exception.getMessage());
				return false;
			}
		return true;
	}
	
	private void startOtherActivity(CallbackContext callbackContext,JSONObject jsonObject) throws ClassNotFoundException,JSONException {
		Intent intent = new Intent().setClass(this.cordova.getActivity(),Class.forName(jsonObject.getString(P_ACTIONNAME)));
		intent.putExtra("flag", "call");
		this.cordova.startActivityForResult(this, intent, 1);
		PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
		pluginResult.setKeepCallback(true);
		callbackContext.sendPluginResult(pluginResult);
		callbackContext.success(RESULT_SUCCESS);
	}

}
