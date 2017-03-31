package com.catsic.core.tools;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**  
  * @Description: 文件工具类 
  * @author wuxianling  
  * @date 2014年8月22日 下午2:19:18    
  */ 
public class FileUtils {

	public static String SDPATH = AppConstants.IMAGES_BASEPATH + "formats/";

	/**  
	  * @Title: saveBitmap  
	  * @Description: 保存压缩的图片 
	  * @param @param bm
	  * @param @param picName     
	  * @return void   
	  * @throws  
	  */ 
	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!exists("")) {
				createSDDir("");
			}
			
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			//压缩图片
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			dir.mkdirs();
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static boolean exists(String fileName){
		return new File(SDPATH + fileName).exists();
	}

	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}
	
	public static String fileToBase64(String filePath){
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(filePath);
			baos = new ByteArrayOutputStream();  
			byte[] buffer = new byte[1024];  
			int count = 0;  
			while((count = fis.read(buffer)) >= 0){  
				baos.write(buffer, 0, count);  
			}  
			//android.util.Base64.encodeToString(input, flags)

			String content = new String(Base64.encode(baos.toByteArray()));  //进行Base64编码
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static File base64ToFile(String content,String fileName){
		//保存文件至本地
		File file = new File(AppConstants.IMAGES_BASEPATH,fileName);
		if (file.exists()) {
			return file;
		}
		//
		if (ObjectUtils.isNullOrEmptyString(content)) {
			return null;
		}
		
		byte [] bytes = android.util.Base64.decode(content, android.util.Base64.DEFAULT);
		ByteArrayOutputStream baos = null;
		FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				if (fos!=null){
					fos.flush();
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        }
        return file;
	}
	
	/**
	  * @Title: absolutePathToFilePath
	  * @Description: 构造filePath
	  * @param @param groupId
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	  */
	public static String absolutePathToFilePath(String absolutePath,String groupId){
		String filePath ="";
		//上传文件保存路径（upload/yyyy-MM/orgid/）
		JSONObject loginUser =  AppContext.LOGINUSER;
		String dateStr=DateUtil.format(new Date(), "yyyy-MM");
		try {
			if (loginUser!=null&&loginUser.getString("orgid")!=null) {
				String orgid = loginUser.getString("orgid");
				filePath = "\\upload\\"+dateStr+"\\"+orgid+"\\"+groupId+"\\";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return filePath;
	}
	
}
