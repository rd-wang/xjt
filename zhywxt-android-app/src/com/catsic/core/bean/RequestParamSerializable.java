package com.catsic.core.bean;

import java.io.Serializable;

/**  
  * @Description: RequestParamSerializable 
  * @author wuxianling  
  * @date 2014年7月7日 上午11:03:09    
  */ 
public class RequestParamSerializable implements Serializable{

	/**  
	  * @Fields serialVersionUID : TODO 
	  */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;

	public RequestParamSerializable(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	public void setKey(String paramString) {
		this.key = paramString;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

}
