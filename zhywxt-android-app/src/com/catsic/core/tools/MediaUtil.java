package com.catsic.core.tools;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import com.catsic.core.AppConstants;

/**  
  * @Description: 多媒体工具类 
  * @author wuxianling  
  * @date 2014年8月21日 下午5:01:27    
  */ 
public class MediaUtil {
	
	/** 照相的requestCode**/
	public static final int RESULT_CAPTURE_IMAGE = 1;
	
	/**相册**/
	public static final int REQUEST_PHOTO = 2;
	
	
	/**  
	  * @Title: generatorFilePath  
	  * @Description: 拍照生成的文件名 
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String generatorFilePath(){
		return DateUtil.format(new Date(),"yyyyMMddHHmmss")+AppConstants.IMAGES_SUFFIX;
	}
	
	
	/**  
	  * @Title: camera  
	  * @Description: 拍照 
	  * @param      
	  * @return void   
	  * @throws  
	  */ 
	public static void camera(Activity activity,int RESULT_CAPTURE_IMAGE,String imageFileName) { 
		
		if (!DeviceUtil.hasSDCard()) {
			ToastUtil.showShortToast(activity, "不存在SD卡");
			return;
		}
		
		String imagePath = AppConstants.IMAGES_BASEPATH;
		//照片命名               
		File out = new File(imagePath);              
		if (!out.exists()) {                      
			out.mkdirs();               
		}           
//		out = new File(imagePath, fileName);             
//		imagePath = imagePath + fileName;//该照片的绝对路径             
//		Uri uri = Uri.fromFile(out);          
		
		/**
		 * 一个新的ContentValues对象。该ContentValues对象是我们希望在记录创建时与它相关联的元数据
		 */
		ContentValues contentValues = new ContentValues(3);
		contentValues.put(Media.DATA, imagePath+imageFileName);
		contentValues.put(Media.DISPLAY_NAME, imageFileName);
		contentValues.put(Media.MIME_TYPE, "image/jpeg");
		Uri uri = activity.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);       
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);               
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		activity.startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
	}
	
}
