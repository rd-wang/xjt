package com.catsic.biz.yh.utils;

import com.catsic.core.AppConstants;


/***********************************************************************************************
 * 标志位工具类-养护计划
 * @author wuxianling
 * “处理”几个审核标志位的含义如下：
 * <logic:equal name="result" property="shbz" value="1">县级未上报</logic:equal>	
 * <logic:equal name="result" property="shbz" value="2">县级已上报市级未审核</logic:equal>
 * <logic:equal name="result" property="shbz" value="3">市级审核通过未上报</logic:equal>
 * <logic:equal name="result" property="shbz" value="4">市级审核未通过退回县级</logic:equal>	
 * <logic:equal name="result" property="shbz" value="5">市级已上报省级未审核</logic:equal>
 * <logic:equal name="result" property="shbz" value="6">省级审核通过</logic:equal>
 * <logic:equal name="result" property="shbz" value="7">省级审核未通过退回市级</logic:equal>	
 * <logic:equal name="result" property="shbz" value="8">省级已下达市级</logic:equal>	
 * <logic:equal name="result" property="shbz" value="9">市级已下达县级</logic:equal>
 ***********************************************************************************************/
public class ShbzUtils {
	
	public static String MENU_SB="jhsb";//查询上报的标示符
	public static String MENU_SH="jhsh";//查询审核的标示符
	public static String MENU_SP="jhsp";//查询审批的标示符
	public static String MENU_XD="jhxd";//查询审批的标示符
	public static String MENU_TZ="jhtz";//查询调整的标示符
	public static String MENU_CX="zhcx";//查询综合查询标示符
	public static String MENU_CZ="ztcz";//查询状态重置标示符
	
	public static String OPER_SB="sb";//上报操作符
	public static String OPER_SH="sh";//审核操作符
	public static String OPER_TH="th";//退回操作符
	public static String OPER_XD="xd";//审批操作符
	public static String OPER_CZ="cz";//重置操作符
	
	public static String OPER_WSB="wsb";//未上报
	public static String OPER_YSB="ysb";//未上报
	
	/*************************************************************
	 * 该方法应用于增加或者修改操作
	 * 根据用户信息计算审核标志位
	 * @param orgLevel 用户级别
	 * @return
	 *************************************************************/
	public static  String getShbzByUser(String orgLevel){		
		if(orgLevel.equals(AppConstants.S))
			return "5";
		if(orgLevel.equals(AppConstants.DS))
			return "3";
		if(orgLevel.equals(AppConstants.X))
			return "1";
		else
			return "1";
	}
	
	/************************************************************
	 * 该方法应用于审核操作  
	 * 如果出现以下情形之外的状况默认状态位变为当前用户级别下应该有的默认值
	 * 根据操作符获取操作之后的shbz
	 * oper:left传入的不同单位下的操作符
	 * @return 操作之后的shbz位	 
	 ************************************************************/
	public static String getShbzByOper(String oper,String userDepartLeavel){
		String shbz=ShbzUtils.getShbzByUser(userDepartLeavel);
		
		/************************县********************************/
		if(userDepartLeavel.equals(AppConstants.X)){
			//上报操作
			if(oper.equals(ShbzUtils.OPER_SB))	{shbz="2";}
		}
		/************************地市********************************/
		else if(userDepartLeavel.equals(AppConstants.DS)){
			//上报操作
			if(oper.equals(ShbzUtils.OPER_SB))	{shbz="5";}
			//审核通过操作
			else if(oper.equals(ShbzUtils.OPER_SH))	{shbz="3";}
			//退回至县操作
			else if(oper.equals(ShbzUtils.OPER_TH))	{shbz="4";}
			//下达操作
			else if(oper.equals(ShbzUtils.OPER_XD))	{shbz="9";}
		}
		/************************省************************************/
		else if(userDepartLeavel.equals(AppConstants.S)){
			//审核通过操作
			if(oper.equals(ShbzUtils.OPER_SH))	{shbz="6";}
			//下达操作
			else if(oper.equals(ShbzUtils.OPER_XD))	{shbz="8";}
			//退回地市操作
			else if(oper.equals(ShbzUtils.OPER_TH))	{shbz="7";}
			//重置操作
			else if(oper.equals(ShbzUtils.OPER_CZ))	{shbz="5";}
		}
		return shbz;
	}
	
	/*************************************************************
	 * @param oper
	 * @param userDepartLeavel
	 * @return
	 *************************************************************/
	public static String getShbzQueryStr(String menu,String userDepartLeavel){
		String shbz=getShbzByUser(userDepartLeavel);
		
		/***************************县**************************/
		if(userDepartLeavel.equals(AppConstants.X)){
			//待上报（含被退回）
			if(menu.equals(ShbzUtils.MENU_SB))	{shbz="'1','4'";}
			//综合查询
			else if(menu.equals(ShbzUtils.MENU_CX))	{shbz="'1','2','3','4','5','6','7','8','9'";}
			//计划调整
			else if(menu.equals(ShbzUtils.MENU_TZ))	{shbz="'9'";}
		}
		/***************************地市*************************/
		else if(userDepartLeavel.equals(AppConstants.DS)){	
			//待上报（含被退回）
			if(menu.equals(ShbzUtils.MENU_SB))	{shbz="'3','7'";}	
			//待审核
			else if(menu.equals(ShbzUtils.MENU_SH))	{shbz="'2'";}	
			//待下达
			else if(menu.equals(ShbzUtils.MENU_XD))	{shbz="'8'";}	
			//综合查询
			else if(menu.equals(ShbzUtils.MENU_CX))	{shbz="'2','3','5','6','7','8','9'";}
			//计划调整
			else if(menu.equals(ShbzUtils.MENU_TZ))	{shbz="'9'";}
		}
		/***************************省***************************/
		else if(userDepartLeavel.equals(AppConstants.S)){
			//待审核
			if(menu.equals(ShbzUtils.MENU_SH))	{shbz="'5'";}
			//待下达
			else if(menu.equals(ShbzUtils.MENU_XD))	{shbz="'6'";}
			//综合查询
			else if(menu.equals(ShbzUtils.MENU_CX))	{shbz="'5','6','8','9'";}
			//计划调整
			else if(menu.equals(ShbzUtils.MENU_TZ))	{shbz="'9'";}
			//状态重置
			else if(menu.equals(ShbzUtils.MENU_CZ))	{shbz="'9'";}
		}
		return shbz;
	}
	
	/**  
	  * @Title: getOperByShbz  
	  * @Description: TODO 
	  * @param @param shbz
	  * @param @return     
	  * @return String   
	  * @throws  
	  */ 
	public static String getOperByShbz(String shbz){
		if (shbz==null ||"".equals(shbz)) {
			return null;
		}
		
		if ("1".equals(shbz) || "3".equals(shbz)) {
		    return OPER_WSB;	
	  	}else if ("4".equals(shbz) || "7".equals(shbz)) {
			return OPER_TH;
		}else if ("2".equals(shbz) || "5".equals(shbz)) {
			return OPER_YSB;
		}
		return null;
	}
}
