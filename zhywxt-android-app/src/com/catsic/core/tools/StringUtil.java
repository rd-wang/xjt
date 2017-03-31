package com.catsic.core.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**  
  * @Description: 字符串的工具类 
  * @author wuxianling  
  * @date 2014年7月3日 上午11:28:28    
  */ 
public class StringUtil {

    /**
     * 将str将多个分隔符进行切分，
     * 
     * 示例：StringUtils.split("1,2;3 4"," ,;");
     * 返回: ["1","2","3","4"]
     * 
     * @param str
     * @param String[]
     * @return
     */
	public static String[] split(String str,String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str,seperators);
		List result = new ArrayList();
		
		while(tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[])result.toArray(new String[result.size()]);
	}
	
	/**
	 * 将空的字符串转换成空格，使得界面中不出现“NULL”字样
	 * 
	 * @param str
	 * @return String
	 */
	public static String changeNull(String str) {
		if (str != null && str.trim().length() > 0)
			return str.trim();
		else
			return "&nbsp;";
	}

	/**
	 * 将空的整型转换成空格，使得界面中不出现“NULL”字样
	 * 
	 * @param str
	 */
	public static String changeNull(Integer str) {
		if (str != null)
			return String.valueOf(str).trim();
		else
			return "&nbsp;";
	}

	public static String changeNull(int str) {
		return String.valueOf(str).trim();
	}

	/** ************************ Stringtrim(String strValue) ***************** */
	/**
	 * 静态方法，String去空格. 如果String为null或者"null"，则返回'',其他的则去掉String两端的空格.
	 * 
	 * @param strValue
	 *            需要处理的String.
	 * @return 处理后的String.
	 */
	public static String Stringtrim(String strValue) {
		if (strValue == null || strValue.equals("null")) {
			return "";
		} else {
			return strValue.trim();
		}
	}
	
	/** ************************ getInSql(String[] items) ***************** */
	/**
	 * 静态方法，多个字段值需要用in查询的情况 如传入String['a','b','c']
	 * 返回String="('a','b','c','catsic')"
	 * 
	 * @param items 需要处理的String数组.
	 * @return 处理后的String.
	 */
	public static String getInSql(String[] items) {		
		String inSql="(";
		if(items!=null && items.length>0){
			for(int i=0;i<items.length;i++){
				inSql+="'"+items[i]+"',";
			}
		}
		inSql+="'catsic')";
		return inSql;
	}
	
	public static String getInSqlzw(String[] items) {		
		String inSql="";
		if(items!=null && items.length>0){
			for(int i=0;i<items.length;i++){
				inSql+="'"+items[i]+"',";
			}
		}
		inSql+="'catsic'";
		return inSql;
	}
	/** ************************ Stringtrim0(String strValue) ***************** */
	/**
	 * 静态方法，String去0. 如果String为 '45000'，则返回'45',去掉String右端的空格.
	 * 
	 * @param strValue
	 *            需要处理的String.
	 * @return 处理后的String.
	 */
	public static String Stringtrim0(String strValue) {
		if(strValue==null)
			return "";
		
		while (strValue.length() > 0) {
			char c = strValue.charAt(strValue.length() - 1);
			if (c == '0') {
				strValue = strValue.substring(0, strValue.length() - 1);
			} else {
				//如532530，去0后位53253，如果不加0，则会吧532531的数据查询出来
				if(strValue.length()==1||strValue.length()==3||strValue.length()==5){
					strValue=strValue+"0";
				}
				return strValue;
			}
		}
		return "";
	}
	
	/**  
	  * @Title: Stringtrim04OrgID  
	  * @Description: orgId trim 0 
	  * @param @param strValue
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String Stringtrim04OrgID(String strValue){
		if(strValue==null)
			return "";
		
		while (strValue.length() > 0) {
			char c = strValue.charAt(strValue.length() - 1);
			if (c == '0') {
				strValue = strValue.substring(0, strValue.length() - 1);
			} else {
				return strValue;
			}
		}
		return "";
	}
	
	
	 /**
	 * 判断字符串是否是空串
	 * @param str
	 * @return
	 */	
	   public static boolean isString(String str){
		      if(null == str || str.length() == 0){
		    	  return true;
		      }
		      return false;
	   }
	
	 /**
	 * 判断某一个字符串数组是否包含某字符串元素
	 * 
	 * @param str
	 * @return
	 */	
   public static boolean isContains(String subString,String[] source){
	     if(source==null||source.length==0){
	    	 return false;
	     }
	     for(int i=0;i<source.length;i++){
	    	 String tempStr=source[i].trim();
	    	 if(tempStr.equals(subString)){
	    		 return true;
	    	 }
	     }
	     return false;
   }
	   
   /********************************************************************************
	 * 根据传入的所有的数组和其中的一部分数组 解析获取另外一部分数组
	 * 一般用于批量操作中部分数据出错时候使用 
	 * @param 	String[] items				如：【"1","2","3","4","5","6","7","8","9","10"】
	 * @param 	String[] partItems			如：【"1","2","3"】
	 * @return 	String[] antoherItems		如：【"4","5","6","7","8","9","10"】
	 * 
	 *****************************************************************************/	
	public static String[] splitArrays(String[] items,String partItems[]){
		//如果传入的要拆分的数组为空
		if(ObjectUtils.isEmpty(items)){
			return null;
		}
		//如果传入的要剔除去的数组不为空
	    if(!ObjectUtils.isEmpty(partItems)){		    	
	    	//如果两者相等
			if(partItems.length==items.length){
				return null;
			}
			String joinItems="";
	    	for(int k=0;k<items.length;k++){
	    		joinItems+=items[k]+"#";
	    	}							  
	    	for(int i=0;i<partItems.length;i++){
	    		String tempObjectBm=partItems[i];
	    		joinItems=joinItems.replace(tempObjectBm+"#", "");
	    	}
	    	if(joinItems.equals("")){
	    		return null;
	    	}
	    	return joinItems.split("#");
	   }
		return items;
	} 
	
	public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
                .append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }
	
	/**
	 * 组合SQL语句
	 * @param dm
	 * @param tj
	 * @return
	 */
	public String toSql(String dm,String tj){
		String info="";
		tj=tj.trim();
		tj=tj.replaceAll("，", ",");
		if(tj==null||"".equals(tj)){
			info=" 1=1 ";
		}else{
			if(tj.substring(tj.length()-1, tj.length()).equals(",")){
				tj=tj.substring(0,tj.length()-1);
			}
			String tjs[]=tj.split(",",0);
			for(int i=0;i<tjs.length;i++){
				String newtjs=tjs[i].trim();
				if(!"".equals(newtjs)){
					info+=" "+dm+" like '"+trim0(newtjs)+"%'";
					if(i!=tjs.length-1){
						info+=" or ";
					}
				}
			}
		}
		return info;
	}
	
	public String trim0(String temp){
		String newtemp="";
		while (temp.length() > 0) {
			char c = temp.charAt(temp.length() - 1);
			if (c == '0') {
				temp = temp.substring(0, temp.length() - 1);
			} else {
				break;
			}
		}			
		newtemp=temp;
		if(newtemp.length()==3||newtemp.length()==5){
			newtemp=newtemp+"0";
		}
		return newtemp;
	}
	
	/**  
	  * @Title: isIp  
	  * @Description: 是否是合法的IP 
	  * @param @param paramString
	  * @param @return     
	  * @return boolean   
	  * @throws  
	  */ 
	public static boolean isIp(String ip) {
		return Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}")
				.matcher(ip).matches();
	}
	
	/**  
	  * @Title: toString  
	  * @Description: Object 转成String 
	  * @param @param obj
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String toString(Object obj){
		if (obj == null || "".equals(obj)) {
			return "";
		}
		return obj.toString();
	}
	
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer ();       
	    for (int i=0; i<s.length(); i++) {       
	        char c = s.charAt(i);       
	        switch (c) {       
		        case '\"':       
		            sb.append("\\\"");       
		            break;       
		        case '\\':       
		            sb.append("\\\\");       
		            break;       
		        case '/':       
		            sb.append("\\/");       
		            break;       
		        case '\b':       
		            sb.append("\\b");       
		            break;       
		        case '\f':       
		            sb.append("\\f");       
		            break;       
		        case '\n':       
		            sb.append("\\n");       
		            break;       
		        case '\r':       
		            sb.append("\\r");       
		            break;       
		        case '\t':       
		            sb.append("\\t");       
		            break;       
	           default:       
	            sb.append(c);       
	          }
	    }    
	    return sb.toString();       
	}
	
	/**
	  * @Title: isDecimal
	  * @Description: 是否是数字
	  * @param @param str
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	  */
	public static boolean isDecimal(String str){  
		if (str==null||str.trim().length()==0) {
			return false;
		}
	  return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();  
	}  
	
}
