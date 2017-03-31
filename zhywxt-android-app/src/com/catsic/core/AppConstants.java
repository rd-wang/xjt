package com.catsic.core;

import android.os.Environment;

/**  
  * @Description: 应用相关常量 
  * @author wuxianling  
  * @date 2014年7月2日 下午3:46:01    
  */ 
public class AppConstants {
	
	public static final String BASE_PACKAGE = "com.catsic";
	
	/**Apk路径**/
	public static final String UPLOAD_APK_PATH = "/upload/apk/";
	
	/**Apk错误日志**/
	public static final String UPLOAD_APK_EXCEPTION_LOG = "/upload/apk_exception_log/";
	
	/**Apk下载名称前缀**/
	public static String APK_NAME_PREFIX = "catsic_";
	
	/**图片存放路径**/
	public static String IMAGES_BASEPATH = Environment.getExternalStorageDirectory().toString() + "/DOWNLOAD_"+BASE_PACKAGE+"/";
	
	/**拍照 图片后缀**/
	public static String IMAGES_SUFFIX = ".jpg";
	
	/**最大上传图片个数**/
	public static int IMAGES_CNT = 9;
	
	public static final String SP_USERDATA="userdata";//用户相关数据
	public static final String SP_SERVERDATA="serverdata";//服务器相关数据
	
	/**用户信息 key**/
	public static final String USER_USERALIAS="useralias";
	public static final String USER_PASSWORD="password";
	
	
	public static final String SUCCESS="success";
	public static final String FAIL="fail";
	public static final String ERROR="error";
	
	/**操作**/
	public static final String ADD="add";
	public static final String DELETE="delete";
	public static final String UPDATE="update";
	public static final String QUERY="query";
	public static final String LIST="list";
	public static final String VIEW="view";
	
	/**操作*/
	public static final int $ADD = 1000;
	public static final int $DELETE = 1001;
	public static final int $UPDATE = 1002;
	public static final int $QUERY = 1003;
	public static final int $LIST = 1004;
	public static final int $VIEW = 1005;
	public static final int $SELECT = 1006;
	
	/**
	 * 用户级别
	 */
	public static final String S = "2";
	public static final String DS = "3";
	public static final String X = "4";
	
	/**
	 * 项目类型
	 */
	public static final String XMLX_LX="lx";//路线
	public static final String XMLX_QL="ql";//桥梁
	
	/**
	 * 数据库名称
	 */
	public static final String DB_NAME="catsic.db";
	

	/**  
	 * 服务端状态
	  */
	public static final int STATE_200 = 200;
	public static final int STATE_500 = 500;
	public static final int STATE_NULL = -1;
	
	
	/**
	 * 字符集编码
	 */
	public static final String ENCODING_UTF8="UTF-8";
	public static final String ENCODING_GBK="GBK";
	
	/**工程检测项**/
	public static final String ZLJC_YSD = "01";//压实度
	public static final String ZLJC_WCCS = "02";//弯沉测试
	
	/**
	 * Apk相关配置
	 */
	public static final String APK_APKSQLVersion = "APK_ApkSQLVersion";
	
	
	/**
	 * 状态 normal :正常  ,batch:批量操作 
	 */
	public enum State{
		NORMAL,BATCH
	}
	
	

}
