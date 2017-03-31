package com.catsic.core.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**  
  * @Description: Bitmap
  * @author wuxianling  
  * @date 2014年8月22日 下午2:16:02    
  */ 
public class Bimp {
	/**已处理的图片**/
	public static int max = 0;
	public static boolean act_bool = true;
	/**压缩后的图片**/
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();	
	
	/**图片sd地址  上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显**/
	public static List<String> drr = new ArrayList<String>();
	

	/**  
	  * @Title: revitionImageSize  
	  * @Description: 压缩图片 
	  * @param @param path
	  * @param @return
	  * @param @throws IOException     
	  * @return Bitmap   
	  * @throws  
	  */ 
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		//获取图片的宽高
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(new FileInputStream(new File(path)));
				//图片压缩比例
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	/**  
	  * @Title: reset  
	  * @Description: 重置 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public static void reset(){
		Bimp.drr.clear();
		Bimp.bmp.clear();
		Bimp.max = 0;
	}
}
