/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.core.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * @version 1.0
 * @since 1.0
 * @author catsic
 * @date 2014-03-20 14:34:27
 * 使用模板创建<请手动在此处增加注释>
 */


@Table(name = "CATSIC_APPCONFIG")
public class CatsicAppConfig {
	
	//columns START
	@Id
	private java.lang.String crowid;
	private java.lang.String ckey;
	private java.lang.String cvalue;
	private java.lang.String bz;
	private java.lang.String cgroup;
	//columns END


	public CatsicAppConfig(){
	}

	public CatsicAppConfig(java.lang.String crowid){
		this.crowid = crowid;
	}

	public void setCrowid(java.lang.String value) {
		this.crowid = value;
	}
	
	public java.lang.String getCrowid() {
		return this.crowid;
	}
	
	public java.lang.String getCkey() {
		return this.ckey;
	}
	
	public void setCkey(java.lang.String value) {
		this.ckey = value;
	}
	
	public java.lang.String getCvalue() {
		return this.cvalue;
	}
	
	public void setCvalue(java.lang.String value) {
		this.cvalue = value;
	}
	
	public java.lang.String getBz() {
		return this.bz;
	}
	
	public void setBz(java.lang.String value) {
		this.bz = value;
	}
	
	public java.lang.String getCgroup() {
		return this.cgroup;
	}
	
	public void setCgroup(java.lang.String value) {
		this.cgroup = value;
	}
	
}

