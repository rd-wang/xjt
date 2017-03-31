/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.biz.js.bean;

import java.io.Serializable;
import java.util.List;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
  * @ClassName: TZljcWccs
  * @Description: 弯沉测试
  * @author catsic-wuxianling
  * @date 2015年8月18日 下午5:01:54
  */
@Table(name = "T_JS_ZLJC_WCCS")
public class TZljcWccs implements Serializable {
	
	@Id
	private java.lang.String crowid;
	private java.lang.String parentid;
	private java.lang.String bh;
	private java.lang.String hth;
	private java.lang.String csld;
	private java.lang.String cx;
	private Double dwyl;
	private java.lang.String sgdw;
	private java.lang.String cdcc;
	private Double hzz;
	private Double zh;
	private Double yxwcz;
	private java.util.Date cdrq;
	private Double bzxs;
	
	private List<TZljcWccsSub> zljcWccsSubs;
	
	public TZljcWccs(){
	}

	public TZljcWccs(java.lang.String crowid){
		this.crowid = crowid;
	}
	
	public void setCrowid(java.lang.String value) {
		this.crowid = value;
	}
	
	public java.lang.String getCrowid() {
		return this.crowid;
	}
	
	public void setParentid(java.lang.String parentid) {
		this.parentid = parentid;
	}
	
	public java.lang.String getParentid() {
		return parentid;
	}
	
	public java.lang.String getBh() {
		return this.bh;
	}
	
	public void setBh(java.lang.String value) {
		this.bh = value;
	}
	
	public java.lang.String getHth() {
		return this.hth;
	}
	
	public void setHth(java.lang.String value) {
		this.hth = value;
	}
	
	public java.lang.String getCsld() {
		return this.csld;
	}
	
	public void setCsld(java.lang.String value) {
		this.csld = value;
	}
	
	public java.lang.String getCx() {
		return this.cx;
	}
	
	public void setCx(java.lang.String value) {
		this.cx = value;
	}
	
	public Double getDwyl() {
		return this.dwyl;
	}
	
	public void setDwyl(Double value) {
		this.dwyl = value;
	}
	
	public java.lang.String getSgdw() {
		return this.sgdw;
	}
	
	public void setSgdw(java.lang.String value) {
		this.sgdw = value;
	}
	
	public java.lang.String getCdcc() {
		return this.cdcc;
	}
	
	public void setCdcc(java.lang.String value) {
		this.cdcc = value;
	}
	
	public Double getHzz() {
		return this.hzz;
	}
	
	public void setHzz(Double value) {
		this.hzz = value;
	}
	
	public Double getZh() {
		return this.zh;
	}
	
	public void setZh(Double value) {
		this.zh = value;
	}
	
	public Double getYxwcz() {
		return this.yxwcz;
	}
	
	public void setYxwcz(Double value) {
		this.yxwcz = value;
	}
	
	public java.util.Date getCdrq() {
		return this.cdrq;
	}
	
	public void setCdrq(java.util.Date value) {
		this.cdrq = value;
	}
	
	public void setBzxs(Double bzxs) {
		this.bzxs = bzxs;
	}
	
	public Double getBzxs() {
		return bzxs;
	}
	
	public void setZljcWccsSubs(List<TZljcWccsSub> zljcWccsSubs) {
		this.zljcWccsSubs = zljcWccsSubs;
	}
	
	public List<TZljcWccsSub> getZljcWccsSubs() {
		return zljcWccsSubs;
	}

}

