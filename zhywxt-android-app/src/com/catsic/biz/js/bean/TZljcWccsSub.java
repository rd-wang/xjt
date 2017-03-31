/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.biz.js.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
  * @ClassName: TZljcWccsSub
  * @Description: 弯沉测试子表
  * @author catsic-wuxianling
  * @date 2015年8月18日 下午5:02:31
  */
@Table(name = "T_JS_ZLJC_WCCS_SUB")
public class TZljcWccsSub implements Serializable {
	
	@Id
	private java.lang.String crowid;
	private java.lang.String parentid;
	private java.lang.String cdbh;
	private Double leftCds;
	private Double leftZds;
	private Double rightCds;
	private Double rightZds;
	private Double left;
	private Double right;
	private java.lang.String bz;
	

	public TZljcWccsSub(){
	}

	public TZljcWccsSub(java.lang.String crowid){
		this.crowid = crowid;
	}

	

	public void setCrowid(java.lang.String value) {
		this.crowid = value;
	}
	
	public java.lang.String getCrowid() {
		return this.crowid;
	}
	
	public java.lang.String getParentid() {
		return this.parentid;
	}
	
	public void setParentid(java.lang.String value) {
		this.parentid = value;
	}
	
	public java.lang.String getCdbh() {
		return this.cdbh;
	}
	
	public void setCdbh(java.lang.String value) {
		this.cdbh = value;
	}
	
	public Double getLeftCds() {
		return this.leftCds;
	}
	
	public void setLeftCds(Double value) {
		this.leftCds = value;
	}
	
	public Double getLeftZds() {
		return this.leftZds;
	}
	
	public void setLeftZds(Double value) {
		this.leftZds = value;
	}
	
	public Double getRightCds() {
		return this.rightCds;
	}
	
	public void setRightCds(Double value) {
		this.rightCds = value;
	}
	
	public Double getRightZds() {
		return this.rightZds;
	}
	
	public void setRightZds(Double value) {
		this.rightZds = value;
	}
	
	public Double getLeft() {
		return this.left;
	}
	
	public void setLeft(Double value) {
		this.left = value;
	}
	
	public Double getRight() {
		return this.right;
	}
	
	public void setRight(Double value) {
		this.right = value;
	}
	
	public java.lang.String getBz() {
		return this.bz;
	}
	
	public void setBz(java.lang.String value) {
		this.bz = value;
	}
	
}

