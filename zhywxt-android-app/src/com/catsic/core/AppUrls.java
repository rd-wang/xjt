package com.catsic.core;

/**  
  * @Description: WS相关常量 
  * @author wuxianling  
  * @date 2014年7月7日 下午2:12:32    
  */ 
/**
  * @ClassName: AppUrls
  * @Description: TODO
  * @author catsic-wuxianling
  * @date 2015年9月24日 下午3:25:52
  */
public class AppUrls {
	
	/**
	 * ip 124.207.79.162
	  */
	public static String SERVICE_IP = "124.207.79.162";
	/**
	 * port 4103
	  */
	public static String SERVICE_PORT = "4103";
	public static String SERVICE_PATH = "zhywxt";
	/**
	 * test
	 */
	public static String TESTServerUrl = "http://192.168.200.98:8080/zhywxt/services/yhgl/jhgl/ql/QlJcService";
	public static String TESTSpaceName = "http://ql.yhgl.services.catsic.com/";
	//test获取服务器图片接口
	public static String TESTFileServerUrl = "http://192.168.200.98:8080/zhywxt/services/common/file/FileService";
	public static String TESTFileSpaceName = "http://file.common.services.catsic.com/";
	/**
	 *========================================== 命名空间 ================================================
	 */
	
	/**
	 * common
	  */
	public static final String COMMON_APK_NAMESPACE = "http://apk.common.services.catsic.com/";
	public static final String COMMON_APPCONFIG_NAMESPACE = "http://appconfig.common.services.catsic.com/";
	public static final String COMMON_AUTH_NAMESPACE = "http://auth.common.services.catsic.com/";
	public static final String COMMON_CODES_NAMESPACE = "http://codes.common.services.catsic.com/";
	public static final String COMMON_FILE_NAMESPACE = "http://file.common.services.catsic.com/";
	public static final String COMMON_XZQH_NAMESPACE = "http://xzqh.common.services.catsic.com/";
	
	/**
	 * jcxx
	  */
	public static final String JCXX_LX_NAMESPACE = "http://lx.jcxx.services.catsic.com/";
	public static final String JCXX_MX_NAMESPACE = "http://mx.jcxx.services.catsic.com/";
	public static final String JCXX_TJ_NAMESPACE = "http://tj.jcxx.services.catsic.com/";
	
	/**
	 * jsgl
	  */
	public static final String JSGL_TJ_NAMESPACE = "http://tj.jsgl.services.catsic.com/";
	public static final String JSGL_XMXX_NAMESPACE = "http://xmxx.jsgl.services.catsic.com/";
	public static final String JSGL_ZLGL_ZLJC_NAMESPACE = "http://zljc.zlgl.jsgl.services.catsic.com/";
	public static final String JSGL_ZLGL_ZLXC_NAMESPACE = "http://zlxc.zlgl.jsgl.services.catsic.com/";
	public static final String JSGL_ZLGL_ZLXCRESP_NAMESPACE = "http://zlxcresp.zlgl.jsgl.services.catsic.com/";
	
	/**
	 * yhgl
	  */
	public static final String YHGL_JHGL_NAMESPACE = "http://jhgl.yhgl.services.catsic.com/";
	public static final String YHGL_LS_NAMESPACE = "http://ls.yhgl.services.catsic.com/";
	public static final String YHGL_TJ_NAMESPACE = "http://tj.yhgl.services.catsic.com/";
	
	/**
	 * lzgl
	  */
	public static final String LZGL_XCJL_NAMESPACE = "http://xcjl.lzgl.services.catsic.com/";
	
	
	/**
	 * ===========================================URL==================================================
	 */
	
	/**
	 * common
	  */
	public static String COMMON_APK_URL = "/services/common/apk/ApkInfoService";
	public static String COMMON_APPCONFIG_URL = "/services/common/appconfig/CatsicAppConfigService";
	public static String COMMON_AUTH_LOGIN_URL = "/services/common/auth/LoginService";
	public static String COMMON_AUTH_SSO_URL = "/services/common/auth/SSOUserService";
	public static String COMMON_CODES_URL = "/services/common/codes/CatsicCodesService";
	public static String COMMON_FILE_URL = "/services/common/file/FileService";
	public static String COMMON_XZQH_URL = "/services/common/xzqh/XzqhService";
	
	/**
	 * jcxx
	  */
	public static String JCXX_LX_URL = "/services/jcxx/lx/LxService";
	public static String JCXX_MX_URL = "/services/jcxx/mx/JcxxMxService";
	public static String JCXX_TJ_URL = "/services/jcxx/tj/JcxxTjService";
	
	/**
	 * jsgl
	  */
	public static String JSGL_TJ_URL = "/services/jsgl/tj/JsxxTjService";
	public static String JSGL_XMXX_URL = "/services/jsgl/xmxx/JsxxMxService";
	public static String JSGL_ZLGL_ZLJC_URL = "/services/jsgl/zlgl/zljc/ZljcService";
	public static String JSGL_ZLGL_ZLXC_URL = "/services/jsgl/zlgl/zlxc/ZlxcService";
	public static String JSGL_ZLGL_ZLXCRESP_URL = "/services/jsgl/zlgl/zlxcresp/ZlxcRespService";
	
	/**
	 * yhgl
	  */
	public static String YHGL_JHGL_URL = "/services/yhgl/jhgl/YhxxMxService";
	public static String YHGL_LS_URL = "/services/yhgl/ls/LsJbxxService";
	public static String YHGL_TJ_URL = "/services/yhgl/tj/YhxxTjService";
	
	/**
	 * lzgl
	  */
	public static String LZGL_XCJL_URL = "/services/lzgl/xcjl/LzXcjlService";


	/**
	 * qljc
	 */
	public static String QLJC_QLCP_URL = "/services/yhgl/jhgl/ql/QlJcService";
	/**  
	  * @Title: getServiceURL  
	  * @Description: 获取serviceURL  http://ip:port/zhywxt
	  * @param @param url
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String getServiceURL(String url){
		return "http://"+SERVICE_IP +":"+SERVICE_PORT +"/"+ SERVICE_PATH +url;
	}



}
