package com.catsic.core.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Description: WS 请求信息
 * @author wuxianling
 * @date 2014年7月7日 上午11:06:37
 */
public class RequestInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String className;
	private String methodName;
	private String nameSpace;
	private ArrayList<RequestParamSerializable> reqParamList = new ArrayList<RequestParamSerializable>();
	private ArrayList<String> rspdetailNodeName = new ArrayList<String>();
	private String serviceUrl;
	
	public RequestInfo(){}
	
	public RequestInfo(String serviceUrl,String nameSpace, String methodName,String className,
			ArrayList<RequestParamSerializable> reqParamList,
			ArrayList<String> rspdetailNodeName) {
		this.className = className;
		this.methodName = methodName;
		this.nameSpace = nameSpace;
		this.reqParamList = reqParamList;
		this.rspdetailNodeName = rspdetailNodeName;
		this.serviceUrl = serviceUrl;
	}
	
	public RequestInfo(String serviceUrl,String nameSpace, String methodName,String className) {
		this.className = className;
		this.methodName = methodName;
		this.nameSpace = nameSpace;
		this.serviceUrl = serviceUrl;
	}
	
	public RequestInfo(String serviceUrl,String nameSpace, String methodName) {
		this.methodName = methodName;
		this.nameSpace = nameSpace;
		this.serviceUrl = serviceUrl;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public ArrayList<RequestParamSerializable> getReqParamList() {
		return reqParamList;
	}

	public void setReqParamList(ArrayList<RequestParamSerializable> reqParamList) {
		this.reqParamList = reqParamList;
	}

	public ArrayList<String> getRspdetailNodeName() {
		return rspdetailNodeName;
	}

	public void setRspdetailNodeName(ArrayList<String> rspdetailNodeName) {
		this.rspdetailNodeName = rspdetailNodeName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getSoapAction() {
		return this.nameSpace + this.methodName;
	}

}
