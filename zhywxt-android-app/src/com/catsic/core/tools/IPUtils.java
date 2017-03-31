package com.catsic.core.tools;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @ClassName: IPUtils
 * @Description: 获取IP地址
 * @author wuxianling
 * @date 2015年6月29日 下午4:07:04
 */
public class IPUtils {

	public static String getIp() {
		String ipaddress = "";
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress()&& InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
						return ipaddress = ip.getHostAddress();
					}
				}

			}
		} catch (SocketException e) {
			Log.e("fail", "获取本地ip地址失败");
			e.printStackTrace();
		}
		return ipaddress;

	}

	// 得到本机Mac地址
	public static String getMac(Context context) {
		// 获取wifi管理器
		WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfor = wifiMng.getConnectionInfo();
		return wifiInfor.getMacAddress();
	}

}
